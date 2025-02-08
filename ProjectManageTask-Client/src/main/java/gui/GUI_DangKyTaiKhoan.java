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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import design.Constants;
import design.RoundedPasswordField;
import design.RoundedTextField;
import design.TextBubbleBorder;
import model.Account;
import model.Gender;
import model.Request;
import model.User;
import service.Service;

public class GUI_DangKyTaiKhoan extends JFrame implements ActionListener, MouseListener{

	public static void screenDangKyTaiKhoan(GUI_Dang_Nhap gui) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_DangKyTaiKhoan frameDangKyTaiKhoan = new GUI_DangKyTaiKhoan(gui);
					frameDangKyTaiKhoan.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private RoundedTextField txtHoTen;
	private RoundedTextField txtEmail;
	private RoundedTextField TxtTenTaiKhoan;
	private RoundedPasswordField TxtMatKhau;
	private RoundedPasswordField TxtNhapLaiMK;
	private JButton btnDone;
	private JComboBox<Integer> cbYearOfBirth;
	private JComboBox<Gender> cbGender;
	
	public GUI_DangKyTaiKhoan(GUI_Dang_Nhap gui) {
		super("Đăng ký");
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,0,8,5);
		Font font = Constants.DEFAULT_FONT;
		Font font_B = Constants.FONT_BOLD;
		
		JPanel pCen = new JPanel();
		Box b = Box.createVerticalBox();
		Box b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;
		
		// Label Đăng ký tài khoản
		b.add(Box.createVerticalStrut(20));
		b.add(b1 = Box.createHorizontalBox());
		JLabel lblDangKy;
		b1.add(lblDangKy = new JLabel("Đăng ký tài khoản"));
		lblDangKy.setFont(Constants.TITLE_FONT);
		
		// Label Thông tin cá nhân
		b.add(Box.createVerticalStrut(20));
		b.add(b2 = Box.createHorizontalBox());
		JLabel lblTTCaNhan;
		b2.add(lblTTCaNhan = new JLabel("Thông tin cá nhân"));
		lblTTCaNhan.setFont(font_B);
		lblTTCaNhan.setForeground(Color.blue);
		
		// Label Họ tên và Label email
		b.add(Box.createVerticalStrut(20));
		b.add(b3 = Box.createHorizontalBox());
		JLabel lblHoTen, lblEmail;
		b3.add(lblHoTen = new JLabel("Họ tên:"));
		b3.add(lblEmail = new JLabel("Email:"));
		lblHoTen.setFont(font);
		lblEmail.setFont(font);
		lblHoTen.setMaximumSize(new Dimension(235,35));
		lblEmail.setMaximumSize(new Dimension(200,35));
		
		// Txt Họ tên và Txt email
		b.add(Box.createVerticalStrut(5));
		b.add(b4 = Box.createHorizontalBox());
		b4.add(txtHoTen = new RoundedTextField(1, "Nhập Họ tên", 210, 32,5));
		b4.add(Box.createHorizontalStrut(25));
		b4.add(txtEmail = new RoundedTextField(1, "Nhập Email", 210, 32, 5));
		txtHoTen.setFont(font);
		txtEmail.setFont(font);
		
		// Label Năm sinh và Label Giới tính
		b.add(Box.createVerticalStrut(20));
		b.add(b5 = Box.createHorizontalBox());
		JLabel lblNamSinh, lblGioiTinh;
		b5.add(lblNamSinh = new JLabel("Năm sinh:"));
		b5.add(lblGioiTinh = new JLabel("Giới tính:"));
		lblNamSinh.setFont(font);
		lblGioiTinh.setFont(font);
		lblNamSinh.setMaximumSize(new Dimension(235,35));
		lblGioiTinh.setMaximumSize(new Dimension(200,35));
		
		// cbo Năm sinh và Giới tính
		cbYearOfBirth = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int oldestYear = currentYear-100;
        for (int year = currentYear; year > oldestYear; year--) {
            cbYearOfBirth.addItem(year); 
        }
        cbGender = new JComboBox<Gender>(Gender.values());
        b.add(Box.createVerticalStrut(5));
		b.add(b6 = Box.createHorizontalBox());
		b6.add(cbYearOfBirth);
		b6.add(Box.createHorizontalStrut(25));
		b6.add(cbGender);
		
		cbYearOfBirth.setSelectedIndex(0);
		cbYearOfBirth.setBackground(Color.white);
		cbYearOfBirth.setMaximumSize(new Dimension(210,33));
		cbYearOfBirth.setFont(font);
		
		cbGender.setSelectedIndex(-1);
		cbGender.setBackground(Color.white);
		cbGender.setMaximumSize(new Dimension(210,33));
		cbGender.setFont(font);
		
		b.add(Box.createVerticalStrut(30));
		b.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		// Label Thông tin tài khoản
		b.add(Box.createVerticalStrut(20));
		b.add(b7 = Box.createHorizontalBox());
		JLabel lblTTTaiKhoan;
		b7.add(lblTTTaiKhoan = new JLabel("Thông tin tài khoản"));
		lblTTTaiKhoan.setFont(font_B);
		lblTTTaiKhoan.setForeground(Color.blue);
		
		// Label Tài khoản + Txt Tài khoản
		b.add(Box.createVerticalStrut(20));
		b.add(b8 = Box.createHorizontalBox());
		JLabel lblTenTaiKhoan;
		b8.add(lblTenTaiKhoan = new JLabel("Tên tài khoản:"));
		b8.add(Box.createHorizontalStrut(25));
		b8.add(TxtTenTaiKhoan = new RoundedTextField(1, "", 5));
		TxtTenTaiKhoan.setMaximumSize(new Dimension(210,33));
		lblTenTaiKhoan.setFont(font);
		TxtTenTaiKhoan.setFont(font);
		lblTenTaiKhoan.setMaximumSize(new Dimension(155,35));
		
		// Label Mật khẩu + Txt Mật khẩu
		b.add(Box.createVerticalStrut(20));
		b.add(b9 = Box.createHorizontalBox());
		JLabel lblMatKhau;
		b9.add(lblMatKhau = new JLabel("Mật khẩu:"));
		b9.add(Box.createHorizontalStrut(25));
		b9.add(TxtMatKhau = new RoundedPasswordField(1, "", 5));
		TxtMatKhau.setMaximumSize(new Dimension(210,33));
		lblMatKhau.setFont(font);
		TxtMatKhau.setFont(font);
		lblMatKhau.setMaximumSize(new Dimension(155,35));
		
		// Label Nhập lại mật khẩu + Txt Nhập lại mật khẩu
		b.add(Box.createVerticalStrut(20));
		b.add(b10 = Box.createHorizontalBox());
		JLabel lblNhapLaiMK;
		b10.add(lblNhapLaiMK = new JLabel("Nhập lại mật khẩu:"));
		b10.add(Box.createHorizontalStrut(25));
		b10.add(TxtNhapLaiMK = new RoundedPasswordField(1, "", 5));
		TxtNhapLaiMK.setMaximumSize(new Dimension(210,33));
		lblNhapLaiMK.setFont(font);
		TxtNhapLaiMK.setFont(font);
		lblNhapLaiMK.setMaximumSize(new Dimension(155,35));
		
		// Button Xác nhận gửi yêu cầu
		b.add(Box.createVerticalStrut(35));
		b.add(b11 = Box.createHorizontalBox());
		b11.add(btnDone = new JButton("   Gửi yêu cầu  "));
		btnDone.setBorder(brdr);
		btnDone.setFont(font);
		btnDone.setForeground(Color.black);
		btnDone.setMaximumSize(new Dimension(270, 31));
		btnDone.setBackground(new java.awt.Color(255,153,0));
		btnDone.setFocusPainted(false);
		
		// Thay đổi con trỏ chuột khi di chuyển vào các thành phần
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR); 
		btnDone.setCursor(cursor);
		cbGender.setCursor(cursor);
		cbYearOfBirth.setCursor(cursor);
		
		// add action
		btnDone.addMouseListener(this);
		btnDone.addActionListener(this);
		
		pCen.add(b);
		add(pCen);
		
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.setVisible(true);
            }
        });
		setSize(570, 630);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// Nếu nhấn vào nút Gửi yêu cầu
		if (o.equals(btnDone)) {
			Account acc = getDataInput();
			if (acc != null) {
				Request<Account> request = new Request<Account>("REGISTER", acc);
				Service.getInstance().sendMessage(request);
			}
		}
	}
	
	private Account getDataInput() {
		Account acc = new Account();
		try {
			// Lấy mật khẩu
			char[] passwordChars = TxtMatKhau.getPassword();
			String password = new String(passwordChars);
			
			char[] nhapLaiMKChars = TxtMatKhau.getPassword();
			String nhapLaiMK = new String(nhapLaiMKChars);
			
			String tenTK = TxtTenTaiKhoan.getText().trim();
			String tenNV = txtHoTen.getText().trim();
			Gender gender = (Gender) cbGender.getSelectedItem();
			int tuoi = LocalDate.now().getYear() - Integer.parseInt(cbYearOfBirth.getSelectedItem().toString());
			String email = txtEmail.getText().trim();
			
			User user = new User(tenNV, gender, tuoi, email, null);
			acc.setAccountName(tenTK);
			acc.setPassword(password);
			acc.setRole("Employee");
			acc.setUser(user);
			
		} catch (Exception e) {
			System.out.println("Lỗi nhập dữ liệu!");
			e.printStackTrace();
			return null;
		}
		return acc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDone)) {
			btnDone.setBackground(new java.awt.Color(215,135,0));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDone)) {
			btnDone.setBackground(new java.awt.Color(255,153,0));
		}
	}
}
