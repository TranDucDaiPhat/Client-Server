package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextArea;

import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Account;
import model.Message;
import model.Request;
import model.User;

// Xử lý tất cả truyền nhận dữ liệu giữa client và server tại đây
public class ClientHandler extends Thread {
	private Socket clientSocket;
	private JTextArea textArea;
	private EntityManager em;
	private Account account;
	private BufferedReader in;
	private PrintWriter out;

	public ClientHandler(Socket socket, JTextArea textArea) {
		this.clientSocket = socket;
		this.textArea = textArea;
		this.em = Persistence.createEntityManagerFactory("task").createEntityManager();
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
			while (true) { // Lắng nghe liên tục
				// Nếu nhận được dữ liệu từ client (Request)
				String request = in.readLine();
				if (request != null) {
					textArea.append("request: " + request + "\n");
					try (JsonReader reader = Json.createReader(new StringReader(request))) {
						JsonObject jo = reader.readObject();
						if (jo != null) {
							String message = jo.getString("message");
							JsonObject joData = jo.getJsonObject("data");
							handleRequest(message, joData);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
//				if (receivedObj instanceof Request<?>) {
//					Request<?> request = (Request<?>) receivedObj;
//					textArea.append("Nhận từ client: " + request.getMessage() + "\n");
//					String message = request.getMessage();
//
//					// Phản hồi lại Client
//					switch (message) {
//					case "LOGIN":
//						if (request.getData() instanceof Account) {
//							// Nhận account từ client
//							Account accReceive = (Account) request.getData();
//							textArea.append("accountName: " + accReceive.getAccountName() + "\n");
//	    					textArea.append("password: " + accReceive.getPassword() + "\n");
//	    					String receivedJson = inR.readLine();
//	    					Gson gson = new Gson();
//	    					Message data = gson.fromJson(receivedJson, Message.class);
//	    					textArea.append(gson.fromJson(data.getData(), Account.class).toString());
//	    					// Tìm account trong cơ sở dữ liệu
//	    					// Nếu có trả về thông tin account, ngược lại trả về null
//							ServiceUser serviceUser = new ServiceUser(em);
//							Account acc = serviceUser.login(accReceive.getAccountName(), accReceive.getPassword());
//							if (acc != null) {
//								Service.getInstance().addClient(acc, out);
//								this.account = acc;
//							}
//							
//							// Tạo request (hoặc gọi là response) mới để gửi về client
//							Request<Account> response = new Request<Account>(message, acc);
//							// Gửi dữ liệu
//							out.writeObject(response);
//							out.flush();
//						} else {
//							// Gửi request dữ liệu nhận được không hợp lệ
//						}
//						break;
//					
//					case "REGISTER": // Tạo tài khoản
//						if (request.getData() instanceof Account) {
//							// Nhận account từ client
//							Account accReceive = (Account) request.getData();
//							textArea.append("accountName: " + accReceive.getAccountName() + "\n");
//							textArea.append("password: " + accReceive.getPassword() + "\n");
//							ObjectOutputStream outManager = Service.getInstance().getClientOutputStreamByRole("Manager");
//							if (outManager != null) {
//								Request<Account> response = new Request<Account>("REGISTER", accReceive);
//								// Gửi thông báo đến Manager
//								outManager.writeObject(response);
//								outManager.flush();
//							} else {
//								textArea.append("Thông báo: Manager không hoạt động" + "\n");
//							}
//						}
//						break;
//						
//					case "CREATE_ACCOUNT":
//						// Nhận account từ client
//						Account accReceive = (Account) request.getData();
//						User user = accReceive.getUser();
//						textArea.append("account: " + accReceive + "\n");
//						textArea.append("user: " + user + "\n");
//						ServiceUser serviceUser = new ServiceUser(em);
//						serviceUser.createAccount(accReceive);
//						break;
//					default:
//						break;
//					}
//				}
			}

		} catch (SocketException e) {
			textArea.append("Client ngắt kết nối!\n");
			Service.getInstance().removeClientOutputStream(account);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleRequest(String message, JsonObject joData) {
		switch (message) {
		case "LOGIN":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					textArea.append("Lỗi: Không tìm thấy dữ liệu!\n");
					break;
				}
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					textArea.append("khong tim thay account");
					break;
				}
				JsonObject joAcc = joData.getJsonObject("account");
				Gson gson = new Gson();
				Account accReceive = gson.fromJson(joAcc.toString(), Account.class);
				ServiceUser serviceUser = new ServiceUser(em);
				Account acc = serviceUser.login(accReceive.getAccountName(), accReceive.getPassword());		
				
				if (acc != null) {
					Service.getInstance().addClient(acc, out);
					this.account = acc;
				}
				
				ServiceMessage sm = ServiceMessage.getInstance();
				String response = sm.createMessage("LOGIN", sm.createObjectJson("account", gson.toJson(acc)));
				textArea.append("response: " + response + "\n");
				out.println(response);
				out.flush();
				break;
			}
		case "REGISTER":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					textArea.append("Lỗi: Không tìm thấy dữ liệu!\n");
					break;
				}
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					textArea.append("khong tim thay account");
					break;
				}
				textArea.append("response to manager" + "\n");
				ServiceMessage sm = ServiceMessage.getInstance();
				JsonObject joAcc = joData.getJsonObject("account");
				ServiceUser service = new ServiceUser(em);
				String response = "";
				// Nếu đã tồn tại tên tài khoản thì không cho tạo
				if (service.isExistAccountName(joAcc.getString("accountName"))) {
					response = sm.createMessage("NOTIFY", sm.createObjectJson("notify", "\"Tên tài khoản đã tồn tại\""));
					out.println(response);
					out.flush();
					break;
				}
				
				PrintWriter outManager = Service.getInstance().getClientOutputStreamByRole("Manager");
				if (outManager != null) {
					// Gửi thông báo đến Manager
					response = sm.createMessage("REGISTER", sm.createObjectJson("account", joData.getJsonObject("account").toString()));
					outManager.println(response);
					outManager.flush();
				} else {
					textArea.append("Thông báo: Manager không hoạt động" + "\n");
				}
				break;
			}
		case "CREATE_ACCOUNT":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					textArea.append("Lỗi: Không tìm thấy dữ liệu!\n");
					break;
				}
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					textArea.append("khong tim thay account");
					break;
				}
				Gson gson = new Gson();
				Account acc = gson.fromJson(joData.getJsonObject("account").toString(), Account.class);
				User user = acc.getUser();
				textArea.append("add account: " + acc);
				textArea.append("add user: " + user);
				ServiceUser serviceUser = new ServiceUser(em);
				serviceUser.createAccount(acc);
			}
			break;
		}
	}
}
