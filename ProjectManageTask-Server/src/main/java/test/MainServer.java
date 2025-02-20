package test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import service.Service;

public class MainServer {
	
	public static String createMessage (String message, String... datas) {
		String request = "{\"message\":\"" + message +"\",\"data\":{";
		for (int i = 0; i < datas.length; i++) {
			request += datas[i];
			if (i < datas.length-1) {
				request += ",";
			}
		}
		
		return request + "}}";
	}
	
	public static String createObjectJson(String objectName, String value) {
		return "\"" + objectName + "\":" + value;
	}

	public static void main(String[] args) {
		
//		Account acc = new Account(123, "tranphat", "123456", "Employee", null);
//		
//		List<String> userIds = List.of("1","2","3","4");
//		System.out.println(userIds.toString());
//		
//		Gson gson = new Gson();
//		// Chuyển từ object sang json
//		String jsonData = gson.toJson(acc);
////		System.out.println(jsonData);
//		
//		// Chuyển từ json sang object
////		System.out.println(gson.fromJson(jsonData, Account.class));
//		
//		String message = createMessage("Register", createObjectJson("account", jsonData), createObjectJson("userIds", userIds.toString()));
//		System.out.println(message);
//		
//		// Xử lý dữ liệu
//		try (JsonReader reader = Json.createReader(new StringReader(message))) {
//			JsonObject jo = reader.readObject();
//			if (jo != null) {
//				String request = jo.getString("message");
//				System.out.println(request);
//				JsonObject joData = jo.getJsonObject("data");
//				if (joData != null) {
//					JsonObject joAcount = joData.getJsonObject("account");
//					Account acc2 = gson.fromJson(joAcount.toString(), Account.class);
//					System.out.println(acc2);
//					
//					JsonArray jaUserId = joData.getJsonArray("userIds");
//					jaUserId.forEach(jv -> System.out.print(jv + " "));
//				}
//			}
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		JFrame frame = new JFrame("server");
        JTextArea txt = new JTextArea();
        JScrollPane scroll = new JScrollPane(txt);
        scroll.setBorder(null);
        txt.setBorder(new EmptyBorder(10, 10, 10, 10));
        txt.setEditable(false);
        frame.add(scroll);
        frame.setSize(470, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		int portNumber = 12345;
		try {
            Service.getInstance().startServer(portNumber, txt);
        } catch (Exception e) {
            e.printStackTrace();
            txt.append("Error: " + e + "\n");
        }
	
	}
	
}
