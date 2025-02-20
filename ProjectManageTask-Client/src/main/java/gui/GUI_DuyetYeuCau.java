package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

import com.google.gson.Gson;

import design.Constants;
import design.TextBubbleBorder;
import model.Account;
import model.Gender;
import model.Request;
import model.User;
import service.Service;
import service.ServiceMessage;

public class GUI_DuyetYeuCau extends JFrame implements ActionListener{
	
	public static void screenDuyetYeuCau(Account account) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_DuyetYeuCau frameDuyetYeuCau = new GUI_DuyetYeuCau(account);
					frameDuyetYeuCau.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JButton btnHuy;
	private JButton btnDuyet;
	private Account account;
	
	public GUI_DuyetYeuCau(Account acc) {
		this.account = acc;
		User user = acc.getUser();
		
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,1,9,15);
		
		Font font = Constants.DEFAULT_FONT;
		Color colorButton = Constants.COLOR_BUTTON;
		
		JPanel pCen = new JPanel();
		Box b = Box.createVerticalBox();
		Box b1,b2,b3,b4,b5,b6,b7;
		
		b.add(Box.createVerticalStrut(15));
		b.add(b1 = Box.createHorizontalBox());
		JLabel lblTitle, lblTenTaiKhoan, lblTenNhanVien, lblGioiTinh, lblTuoi, lblEmail;
		b1.add(lblTitle = new JLabel("Thông tin"));
		lblTitle.setFont(Constants.TITLE_FONT);
		
		// Tên tài khoản
		b.add(Box.createVerticalStrut(35));
		b.add(b2 = Box.createHorizontalBox());
		b2.add(lblTenTaiKhoan = new JLabel("Tên tài khoản:  " + acc.getAccountName()));
		
		// Tên nhân viên
		b.add(Box.createVerticalStrut(15));
		b.add(b3 = Box.createHorizontalBox());
		b3.add(lblTenNhanVien = new JLabel("Tên nhân viên: " + user.getName()));
		
		// Giới tính
		b.add(Box.createVerticalStrut(15));
		b.add(b4 = Box.createHorizontalBox());
		String gioiTinh = "";
		if (user.getGender() == Gender.MALE) {
			gioiTinh = "Nam"; 
		} else if (user.getGender() == Gender.FAMALE) {
			gioiTinh = "Nữ"; 
		} else if (user.getGender() == Gender.OTHER) {
			gioiTinh = "Khác"; 
		}
		b4.add(lblGioiTinh = new JLabel("Giới tính: " + gioiTinh));
		
		// Tuổi
		b.add(Box.createVerticalStrut(15));
		b.add(b5 = Box.createHorizontalBox());
		b5.add(lblTuoi = new JLabel("Tuổi: " + user.getAge()));
		
		// Email
		b.add(Box.createVerticalStrut(15));
		b.add(b6 = Box.createHorizontalBox());
		b6.add(lblEmail = new JLabel("Email: " + user.getEmail()));
		
		// button Huy và Duyệt yêu cầu
		b.add(Box.createVerticalStrut(35));
		b.add(b6 = Box.createHorizontalBox());
		b6.add(btnHuy = new JButton("   Huỷ   "));
		b6.add(Box.createHorizontalStrut(45));
		b6.add(btnDuyet = new JButton(" Duyệt yêu cầu "));
		
		// Set font và kích thước
		lblTenTaiKhoan.setFont(font); 
		lblTenNhanVien.setFont(font);
		lblGioiTinh.setFont(font);
		lblTuoi.setFont(font);
		lblEmail.setFont(font);
		
		btnDuyet.setFont(font);
		btnHuy.setFont(font);
		btnDuyet.setBackground(colorButton);
		btnHuy.setBackground(colorButton);
		btnDuyet.setBorder(brdr);
		btnHuy.setBorder(brdr);
		btnDuyet.setFocusable(false);
		btnHuy.setFocusable(false);
		
		lblTenTaiKhoan.setMaximumSize(new Dimension(355, 35));
		lblTenNhanVien.setMaximumSize(new Dimension(355, 35));
		lblGioiTinh.setMaximumSize(new Dimension(355, 35));
		lblTuoi.setMaximumSize(new Dimension(355, 35));
		lblEmail.setMaximumSize(new Dimension(355, 35));
		
		// Thay đổi con trỏ chuột khi di chuyển vào các thành phần
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		btnDuyet.setCursor(cursor);
		btnHuy.setCursor(cursor);
				
		// add action
		btnDuyet.addActionListener(this);
		
		pCen.add(b);
		add(pCen);
		setSize(470, 430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDuyet)) {
			ServiceMessage sm = ServiceMessage.getInstance();
			Gson gson = new Gson();
			Service.getInstance().sendMessage(sm.createMessage("CREATE_ACCOUNT", sm.createObjectJson("account", gson.toJson(account))));
			dispose();
		}
		
	}
}
