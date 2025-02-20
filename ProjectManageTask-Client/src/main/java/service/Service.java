package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import model.Account;
import model.Message;
import model.Request;

public class Service {

	private static Service instance;
	// Truyền nhận dữ liệu bằng object
	private Socket client;
	// Danh sách các lớp implement MessageListener -> thông báo cho tất cả các lớp khi có dữ liệu
	private List<MessageListener> listeners = new ArrayList<>();
	BufferedReader in;
	PrintWriter out;
	
	public static Service getInstance() {
		if (instance == null) {
			instance = new Service();
		}
		return instance;
	}

	private Service() {
	}

	public void connectServer(String ip, int portNumber) {
		try {
			client = new Socket(ip, portNumber);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
			// Thread để lắng nghe dữ liệu từ server
			new Thread(() -> {
				try {
					while (true) {
						// Nếu nhận được dữ liệu (Request)
						String receive = in.readLine();
						if (receive != null) {
							try (JsonReader reader = Json.createReader(new StringReader(receive))) {
								JsonObject jo = reader.readObject();
								if (jo != null) {
									String message = jo.getString("message");
									JsonObject joData = jo.getJsonObject("data");
									handleReceive(message, joData);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
//						if (receivedObj instanceof Request<?>) {
//							Request<?> request = (Request<?>) receivedObj;
//							String message = request.getMessage();
//							switch (message) {
//							case "LOGIN":
//								if (request.getData() == null) {
//									System.out.println("Đăng nhập thất bại");
//								} else if (request.getData() instanceof Account) {
//									Account acc = (Account) request.getData();
//									System.out.println("Đăng nhập thành công");
//									// Gửi dữ liệu đến giao diện 
//									notifyListeners(request);
//								} else {
//									// Response nhận được dữ liệu không hợp lệ
//								}
//								break;
//								
//							case "REGISTER":
//								if (request.getData() == null) {
//									System.out.println("Đăng ký thất bại");
//								} else if (request.getData() instanceof Account) {
//									// Gửi dữ liệu đến giao diện 
//									notifyListeners(request);
//								} else {
//									// Response nhận được dữ liệu không hợp lệ
//								}
//								break;
//							default:
//								break;
//							}
//
//						}
					}
				} catch (Exception e) {
					System.out.println("Kết nối bị gián đoạn: " + e.getMessage());
					e.printStackTrace();
				} finally {
					closeConnection();
				}
			}).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleReceive(String message, JsonObject joData) {
		switch (message) {
		case "LOGIN":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					System.out.println("Lỗi: Không nhận được account!");
					break;
				}
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					System.out.println("khong tim thay account");
					break;
				}
				// Nếu có account
				JsonObject joAcc = joData.getJsonObject("account");
				Gson gson = new Gson();
				Account accReceive = gson.fromJson(joAcc.toString(), Account.class);
				Request<Account> request = new Request<Account>(message, accReceive);
				notifyListeners(request);
				break;
			}
		case "REGISTER":
			if (joData != null) {
				if (!joData.containsKey("account")) {
					System.out.println("Lỗi: Không nhận được account!");
					break;
				}
				// Nếu không tìm thấy account
				if (joData.isNull("account")) {
					System.out.println("khong tim thay account");
					break;
				}
				// Nếu có account
				JsonObject joAcc = joData.getJsonObject("account");
				
				Gson gson = new Gson();
				Account accReceive = gson.fromJson(joAcc.toString(), Account.class);
				Request<Account> request = new Request<Account>(message, accReceive);
				notifyListeners(request);
				break;
			}
		// server gửi thông báo
		case "NOTIFY":
			if (joData != null) {
				if (joData.containsKey("notify")) {
					Request<String> request = new Request<String>(message, joData.getString("notify"));
					notifyListeners(request);
					break;
				}
			}
		}
	}
	
	// Hàm gửi dữ liệu đến server
	public void sendMessage(String request) {
		if (out != null) {
			try {
				out.println(request);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Gặp lỗi trong khi gửi dữ liệu!");
			}
		}
	}
	
	// Thêm một UI vào danh sách lắng nghe
    public void addMessageListener(MessageListener listener) {
        listeners.add(listener);
    }
    
    // Gỡ bỏ listener nếu không cần nữa
    public void removeMessageListener(MessageListener listener) {
        listeners.remove(listener);
    }
    
    // Thông báo cho tất cả UI khi nhận được dữ liệu
    private void notifyListeners(Request<?> request) {
        for (MessageListener listener : listeners) {
            listener.onMessageReceived(request);
        }
    }

    // Đóng luồng sau khi ngắt kết nối
	public void closeConnection() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (client != null && !client.isClosed())
				client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
