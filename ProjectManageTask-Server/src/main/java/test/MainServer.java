package test;

import java.io.StringReader;

import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Account;
import model.Message;
import model.User;

public class MainServer {

	public static void main(String[] args) {
		
//		Account acc = new Account(123, "tranphat", "123456", "Employee", null);
		
		Gson gson = new Gson();
		// Chuyển từ object sang json
//		String jsonData = gson.toJson(acc);
//		System.out.println(jsonData);
		
		// Chuyển từ json sang object
//		System.out.println(gson.fromJson(jsonData, Account.class));
		
		// Server
		EntityManager em = Persistence.createEntityManagerFactory("task").createEntityManager();
		Account acc2 = em.find(Account.class, 1);
		
//		System.out.println(gson.toJson(acc2));
		
		acc2.getUser().setManagedUsers(null);
		
		System.out.println(gson.toJson(acc2));
		
		// Tạo message có 2 object là account và user
		String jsonData2 = "{\"account\":" + gson.toJson(acc2) + ",\"user\":" + gson.toJson(acc2.getUser()) + "}";
		Message message = new Message("LOGIN", jsonData2);
		
		// chuyển sang json để gửi đến server
		String request = gson.toJson(message);
//		System.out.println(request);
		
		// client nhận dữ liệu
		Message data = gson.fromJson(request, Message.class);
//		System.out.println("message: " + message.getType());
//		System.out.println("data: " + message.getContent());
		
		// Xử lý dữ liệu
		try (JsonReader reader = Json.createReader(new StringReader(data.getData()))) {
			JsonObject jo = reader.readObject();
			if (jo != null) {
				JsonObject joAcc = jo.getJsonObject("account");
				JsonObject joUser = jo.getJsonObject("user");
				Account acc = gson.fromJson(joAcc.toString(), Account.class);
				User user = gson.fromJson(joUser.toString(), User.class);
				System.out.println(acc);
				System.out.println(user);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		JFrame frame = new JFrame("server");
//        JTextArea txt = new JTextArea();
//        JScrollPane scroll = new JScrollPane(txt);
//        scroll.setBorder(null);
//        txt.setBorder(new EmptyBorder(10, 10, 10, 10));
//        txt.setEditable(false);
//        frame.add(scroll);
//        frame.setSize(470, 430);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		
//		int portNumber = 12345;
//		try {
//            Service.getInstance().startServer(portNumber, txt);
//        } catch (Exception e) {
//            e.printStackTrace();
//            txt.append("Error: " + e + "\n");
//        }
	
	}
}
