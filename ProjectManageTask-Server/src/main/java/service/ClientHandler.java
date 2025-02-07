package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextArea;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Account;
import model.Request;

// Xử lý tất cả truyền nhận dữ liệu giữa client và server tại đây
public class ClientHandler extends Thread {
	private Socket clientSocket;
	private JTextArea textArea;
	private EntityManager em;

	public ClientHandler(Socket socket, JTextArea textArea) {
		this.clientSocket = socket;
		this.textArea = textArea;
		this.em = Persistence.createEntityManagerFactory("task").createEntityManager();
	}

	@Override
	public void run() {
		try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
			while (true) { // Lắng nghe liên tục
				// Nếu nhận được dữ liệu từ client (Request)
				Object receivedObj = in.readObject();
				if (receivedObj instanceof Request<?>) {
					Request<?> request = (Request<?>) receivedObj;
					textArea.append("Nhận từ client: " + request.getMessage() + "\n");
					String message = request.getMessage();

					// Phản hồi lại Client
					switch (message) {
					case "LOGIN":
						if (request.getData() instanceof Account) {
							// Nhận account từ client
							Account accReceive = (Account) request.getData();
							textArea.append("accountName: " + accReceive.getAccountName() + "\n");
	    					textArea.append("password: " + accReceive.getPassword() + "\n");
	    					// Tìm account trong cơ sở dữ liệu
	    					// Nếu có trả về thông tin account, ngược lại trả về null
							ServiceUser serviceUser = new ServiceUser(em);
							Account acc = serviceUser.login(accReceive.getAccountName(), accReceive.getPassword());
							// Tạo request (hoặc gọi là response) mới để gửi về client
							Request<Account> response = new Request<Account>(message, acc);
							// Gửi dữ liệu
							out.writeObject(response);
							out.flush();
						} else {
							// Gửi request dữ liệu nhận được không hợp lệ
						}

						break;

					default:
						break;
					}
				}
			}

		} catch (SocketException e) {
			textArea.append("Client ngắt kết nối!");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
