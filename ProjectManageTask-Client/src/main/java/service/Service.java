package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.Account;
import model.Message;
import model.Request;

public class Service {

	private static Service instance;
	// Truyền nhận dữ liệu bằng object
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
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
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			objectInputStream = new ObjectInputStream(client.getInputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
			// Thread để lắng nghe dữ liệu từ server
			new Thread(() -> {
				try {
					while (true) {
						// Nếu nhận được dữ liệu (Request)
						Object receivedObj = objectInputStream.readObject();
						if (receivedObj instanceof Request<?>) {
							Request<?> request = (Request<?>) receivedObj;
							String message = request.getMessage();
							switch (message) {
							case "LOGIN":
								if (request.getData() == null) {
									System.out.println("Đăng nhập thất bại");
								} else if (request.getData() instanceof Account) {
									Account acc = (Account) request.getData();
									System.out.println("Đăng nhập thành công");
									// Gửi dữ liệu đến giao diện 
									notifyListeners(request);
								} else {
									// Response nhận được dữ liệu không hợp lệ
								}
								break;
								
							case "REGISTER":
								if (request.getData() == null) {
									System.out.println("Đăng ký thất bại");
								} else if (request.getData() instanceof Account) {
									// Gửi dữ liệu đến giao diện 
									notifyListeners(request);
								} else {
									// Response nhận được dữ liệu không hợp lệ
								}
								break;
							default:
								break;
							}

						}
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
	
	// Hàm gửi dữ liệu đến server
	public void sendMessage(Request<?> request) {
		if (objectOutputStream != null) {
			try {
				objectOutputStream.writeObject(request);
				objectOutputStream.flush();
				
				Gson gson = new Gson();
				Message message = new Message("Register", gson.toJson(request.getData()));
				out.println(gson.toJson(message));
			} catch (IOException e) {
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
			if (objectInputStream != null)
				objectInputStream.close();
			if (objectOutputStream != null)
				objectOutputStream.close();
			if (client != null && !client.isClosed())
				client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
