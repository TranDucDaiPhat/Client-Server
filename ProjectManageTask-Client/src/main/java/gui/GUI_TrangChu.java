package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import design.Constants;
import model.Account;
import model.Request;
import service.MessageListener;
import service.Service;

public class GUI_TrangChu extends JFrame implements ActionListener, MouseListener, MessageListener{
	
	public static void screenTrangChu(Account account) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_TrangChu frameTrangChu = new GUI_TrangChu(account);
					frameTrangChu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Panel_DanhSachProject pCen;
	private JList<String> menuList;
	private DefaultListModel<String> listModel;
	private JPanel currentPanel;
	private Account account;

	public GUI_TrangChu(Account account) {
		super(account.getRole());
		this.account = account;
		
		// Giao diện trang chủ sẽ hiển thị dựa theo role
		Font font = Constants.DEFAULT_FONT;
		
		// Tạo menu và đặt bên trái màn hình
		listModel = new DefaultListModel<>();
        listModel.addElement("    Dự Án");
        listModel.addElement("    Thông Báo");
        listModel.addElement("    Cá Nhân");
        
        // Menu sẽ hiển thị danh sách nhân viên nếu user có role là manager
        if (account.getRole().equals("Manager")) {
        	listModel.addElement("    Nhân Viên");
        }
        
        listModel.addElement("    Lịch Sử");
        listModel.addElement("    Đăng Xuất");
		
        // add menu vào menu list
		menuList = new JList<>(listModel);
		menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuList.setBackground(new java.awt.Color(220,220,220));
        menuList.setFont(font);
        menuList.setFixedCellHeight(40); 
        menuList.setFixedCellWidth(180);
        
        // Đặt mục đầu tiên được chọn mặc định
        menuList.setSelectedIndex(0);
        
        // Thêm JList vào JScrollPane (nếu có nhiều mục)
        JScrollPane scrollPane = new JScrollPane(menuList);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.WEST);
        
        // panel head
        JPanel pHead = new JPanel();
        pHead.setLayout(new BorderLayout());
        pHead.setBackground(new java.awt.Color(204, 255, 255));
        JLabel lblheader = new JLabel("Manage Task", JLabel.LEFT);
        pHead.add(lblheader, BorderLayout.WEST);
        lblheader.setFont(new Font("Cooper Black", Font.PLAIN, 24));
        lblheader.setForeground(Color.BLUE);
        
        // icon và username
        JLabel lblIconUser = new JLabel(" " + account.getAccountName() + " ");
        lblIconUser.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("img\\32-person-icon.png")));
        pHead.add(lblIconUser);
        lblIconUser.setFont(font);
        pHead.add(lblIconUser, BorderLayout.EAST);
        // tạo khoảng cách - padding cho panel
        pHead.setBorder(new EmptyBorder(5, 15, 5, 15));
        
        // panel center (Mặc định sẽ là giao diện Danh sách các Project)
        pCen = new Panel_DanhSachProject(this);
        
        // Thay đổi con trỏ chuột khi di chuyển vào các thành phần
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR); 
		menuList.setCursor(cursor);
        
		// add action
        menuList.addMouseListener(this);
        
        // Thêm sự kiện để lắng nghe dữ liệu trả về từ server
     	Service.getInstance().addMessageListener(this);
     		
        // set panel hiện tại (giao diện Danh sách các Project)
        currentPanel = pCen;
        add(currentPanel, BorderLayout.CENTER);
        add(pHead, BorderLayout.NORTH);
		setSize(1280, 720);
		setExtendedState(JFrame.MAXIMIZED_BOTH);      // set full màn hình
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public String getRole() {
		return account.getRole();
	}
	
	// Hàm set Panel để di chuyển giữa các Panel. Hàm sẽ được gọi trong các class Panel
	// Ví dụ: từ Panel Task quay lại Panel Project
	public void setPanel(JPanel newPanel) {
		this.remove(currentPanel);
		currentPanel = newPanel;
		this.add(currentPanel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// Xử lý chuột khi click vào các mục trong menu list
		Object o = e.getSource();
		if (o.equals(menuList)) {
			String selectedValue = menuList.getSelectedValue().trim();
			if (selectedValue.equals("Dự Án")) {
				setPanel(new Panel_DanhSachProject(this));
			} else if (selectedValue.equals("Thông Báo")) {
				setPanel(new Panel_ThongBao());
			} else if (selectedValue.equals("Cá Nhân")) {
				setPanel(new Panel_ThongTinCaNhan());
			} else if (selectedValue.equals("Nhân Viên")) {
				setPanel(new Panel_DanhSachNhanVien());
			} else if (selectedValue.equals("Lịch Sử")) {
				setPanel(new Panel_LichSu());
			} else if (selectedValue.equals("Đăng Xuất")) {
				int thongBao = JOptionPane.showConfirmDialog(null, "Bạn muốn Đăng xuất ? ","Chú ý",JOptionPane.YES_NO_OPTION);
				if(thongBao == JOptionPane.YES_OPTION) {
					dispose();
					GUI_Dang_Nhap.screenDangNhap();
				}
			} 
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void onMessageReceived(Request<?> request) {
		String message = request.getMessage();
		if (message.equals("REGISTER")) {
			Object[] options = {"Huỷ","Xem"};
			int n = JOptionPane.showOptionDialog(this, 
					"Bạn vừa nhận được một yêu cầu đăng ký tài khoản", 
					"Chú ý", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.PLAIN_MESSAGE, 
					null, options, options[1]);
			if (n == 1) {
				Account acc = (Account) request.getData();
				acc.getUser().setManager((account.getUser()));;
				GUI_DuyetYeuCau.screenDuyetYeuCau(acc);
			}
		}
	}
}
