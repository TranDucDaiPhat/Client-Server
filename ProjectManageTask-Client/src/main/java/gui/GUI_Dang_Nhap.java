package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;

import com.google.gson.Gson;

import design.Constants;
import design.RoundedPasswordField;
import design.RoundedTextField;
import design.TextBubbleBorder;
import model.Account;
import model.Request;
import service.MessageListener;
import service.Service;
import service.ServiceMessage;


public class GUI_Dang_Nhap extends JFrame implements MouseListener, ActionListener, MessageListener {
	  
	public static void screenDangNhap() { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Dang_Nhap frameDangNhap = new GUI_Dang_Nhap();
					frameDangNhap.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JLabel lblTitle;
	private JLabel lblTaiKhoan;
	private JTextField txtTaiKhoan;
	private JLabel lblMatKhau;
	private JTextField txtMatKhau;
	private JButton btnDangNhap;
	private JLabel lblDangKy;
	
	public GUI_Dang_Nhap() {
		super("Đăng nhập");
		
		// Tuỳ chỉnh button (color,thick,height,boder)
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,0,9,5);
		// font mặc định trong package design/Constants
		Font font = Constants.DEFAULT_FONT;
		
		JPanel pCen = new JPanel();
		
		Box b = Box.createVerticalBox();
		Box b1,b2,b3,b4,b5,b6, b7;
		
		// Label Manage Task
		b.add(Box.createVerticalStrut(20));
		b.add(b1 = Box.createHorizontalBox());
		b1.add(lblTitle = new JLabel("Manage Task"));
		lblTitle.setFont(new Font("Cooper Black", Font.PLAIN, 32));
		lblTitle.setForeground(Color.BLUE);
		
		// Label Tài khoản
		b.add(Box.createVerticalStrut(30));
		b.add(b2 = Box.createHorizontalBox());
		b2.add(lblTaiKhoan = new JLabel("Tài khoản :"));
		b2.add(Box.createHorizontalStrut(180));
		lblTaiKhoan.setFont(font);
		lblTaiKhoan.setForeground(Color.black);
		
		// Txt Tài khoản
		b.add(Box.createVerticalStrut(6));
		b.add(b3 = Box.createHorizontalBox());
		b3.add(txtTaiKhoan = new RoundedTextField(15, "Tên tài khoản", 225, 33, 5));
		txtTaiKhoan.setFont(font);
		txtTaiKhoan.setForeground(Color.black);
		
		// Label Mật khẩu
		b.add(Box.createVerticalStrut(25));
		b.add(b4 = Box.createHorizontalBox());
		b4.add(lblMatKhau = new JLabel("Mật khẩu :"));
		b4.add(Box.createHorizontalStrut(180));
		lblMatKhau.setFont(font);
		lblMatKhau.setForeground(Color.black);
		
		// Txt mật khẩu
		b.add(Box.createVerticalStrut(6));
		b.add(b5 = Box.createHorizontalBox());
		b5.add(txtMatKhau = new RoundedPasswordField(15, "Nhập mật khẩu", 225, 33, 5));
		txtMatKhau.setFont(font);
			
		// Btn Đăng nhập
		b.add(Box.createVerticalStrut(35));
		b.add(b6 = Box.createHorizontalBox());
		b6.add(btnDangNhap = new JButton("   Đăng nhập  "));
		btnDangNhap.setBorder(brdr);
		btnDangNhap.setFont(font);
		btnDangNhap.setForeground(Color.black);
		btnDangNhap.setMaximumSize(new Dimension(270, 32));
		btnDangNhap.setBackground(new java.awt.Color(255,153,0));
		btnDangNhap.setFocusPainted(false);         // Xoá tiêu điểm
		
		// Lbl Đăng ký
		b.add(Box.createVerticalStrut(20));
		b.add(b7 = Box.createHorizontalBox());
		b7.add(lblDangKy = new JLabel("Tạo tài khoản"));
		lblDangKy.setFont(font);
		lblDangKy.setForeground(new java.awt.Color(18,136,203));
		
		lblDangKy.addMouseListener(this);
		btnDangNhap.addMouseListener(this);
		btnDangNhap.addActionListener(this);
		
		// Thay đổi con trỏ chuột khi di chuyển vào các thành phần
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		btnDangNhap.setCursor(cursor);
		lblDangKy.setCursor(cursor);
		
		pCen.setBackground(new java.awt.Color(204, 255, 255));
		pCen.add(b);
		add(pCen);
		
		// test
		txtTaiKhoan.setText("kristiepaucek");
		txtMatKhau.setText("834466110689");
		
		// Thêm sự kiện để lắng nghe dữ liệu trả về từ server
		Service.getInstance().addMessageListener(this);
		
		setSize(470, 430);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
//		setUndecorated(true);          // bỏ khung xung quanh
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			// Gửi request tới server
			login();
		}
	}
	
	private void login() {
		Account acc = new Account();
		String accountName = txtTaiKhoan.getText().trim();
		String password = txtMatKhau.getText();
		
		if (accountName.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản");
			txtTaiKhoan.requestFocus();
			return;
		} else if (password.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu");
			txtMatKhau.requestFocus();
			return;
		}
		
		acc.setAccountName(txtTaiKhoan.getText());
		acc.setPassword(txtMatKhau.getText());
		
		// Gọi hàm sendMessage để gửi request đến server
		ServiceMessage sm = ServiceMessage.getInstance();
		Gson gson = new Gson();
		String request = sm.createMessage("LOGIN", sm.createObjectJson("account", gson.toJson(acc)));
		Service.getInstance().sendMessage(request);
	}
	
	// Hàm nhận và xử lý dữ liệu nếu có dữ liệu được gửi đến
	@Override
	public void onMessageReceived(Request<?> request) {
		String message = request.getMessage();
		if (message.equals("LOGIN")) {
			GUI_TrangChu.screenTrangChu((Account) request.getData());
			
			// Tắt giao diện sau khi sự kiện đã được xử lý xong
	        SwingUtilities.invokeLater(() -> {
	            Service.getInstance().removeMessageListener(this); // Gỡ bỏ listener
	            dispose(); // Đóng giao diện
	        });
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(lblDangKy)) {
			GUI_DangKyTaiKhoan.screenDangKyTaiKhoan(this);
			setVisible(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Thay đổi màu chữ khi di chuột vào chữ "Đăng ký tài khoản"
		Object o = e.getSource();
		if (o.equals(lblDangKy)) {
			lblDangKy.setForeground(Color.blue);
			lblDangKy.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
		} else if (o.equals(btnDangNhap)) {
			btnDangNhap.setBackground(new java.awt.Color(215,135,0));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Chuyển về màu cũ khi di chuột ra khỏi chữ "Đăng ký tài khoản"
		Object o = e.getSource();
		if (o.equals(lblDangKy)) {
			lblDangKy.setForeground(new java.awt.Color(18,136,203));
			lblDangKy.setBorder(null);
		} else if (o.equals(btnDangNhap)) {
			btnDangNhap.setBackground(new java.awt.Color(255,153,0));
		}
	}

}
