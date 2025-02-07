package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class Service {

	private static Service instance;
	private JTextArea textArea;
	
	public static Service getInstance(JTextArea textArea) {
		if (instance == null) {
			instance = new Service(textArea);
		}
		return instance;
	}
	
	private Service(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public void startServer(int portNumber) {
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            textArea.setText("Server đang lắng nghe trên cổng " + portNumber + "\n");
            
            // Lắng nghe tất cả client
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Chờ Client kết nối
                textArea.append("Client đã kết nối: " + clientSocket.getInetAddress() + "\n");

                // Tạo luồng riêng cho mỗi client
                new ClientHandler(clientSocket, textArea).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.append("Error: " + e + "\n");
        }
	}
}
