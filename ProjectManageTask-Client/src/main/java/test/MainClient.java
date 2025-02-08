package test;

import gui.GUI_Dang_Nhap;
import service.Service;

public class MainClient {

	public static void main(String[] args) {
		GUI_Dang_Nhap.screenDangNhap();
		try {
			String ip = "localhost";
			int portNumber = 12345;
            Service.getInstance().connectServer(ip, portNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
