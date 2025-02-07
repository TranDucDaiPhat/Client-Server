package service;

import model.Request;

// Khi sevice nhận được dữ liệu từ server, 
// service gọi hàm onMessageReceived và truyền vào dữ liệu 
// Class nào implement MessageListener sẽ nhận được dữ liệu từ service 
public interface MessageListener {
	public void onMessageReceived(Request<?> request);
}
