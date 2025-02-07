package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import design.Constants;
import design.TextBubbleBorder;

public class Panel_DanhSachTask extends JPanel implements ActionListener, MouseListener{
	
	private GUI_TrangChu mainFrame;
	private ArrayList<JButton> listBtnTask;
	private JButton btnBack;
	private JButton btnTaoTask;
	private String role;
	private String projectName;

	// Đổi tên file: từ Panel_Project -> Panel_DanhSachTask
	// Panel_DanhSachTask hiển thị các Task có trong một Project
	public Panel_DanhSachTask(String projectName, GUI_TrangChu mainFrame) {
		this.mainFrame = mainFrame;
		role = mainFrame.getRole();
		this.projectName = projectName;
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,1,15,15);
		Font font = Constants.DEFAULT_FONT;
		
		setLayout(new BorderLayout());
		
		// Tạo Button quay lại
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnBack = new JButton("Quay lại");
		btnBack.setFont(font);
		btnBack.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("img\\24-go-previous.png")));
		// Tạo màu trong suốt cho button
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled (false);
		btnBack.setFocusPainted (false);
		
		pNorth.add(btnBack);
		add(pNorth, BorderLayout.NORTH);
		
		JPanel pCen = new JPanel();
		
		pCen.setLayout(new BoxLayout(pCen, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel(projectName);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setFont(Constants.TITLE_FONT);
		
		pCen.add(lblTitle);
		pCen.add(Box.createVerticalStrut(35));
        
        listBtnTask = new ArrayList<JButton>();
        
        // Tạo 10 Task mẫu
        for (int i = 0; i < 10; i++) {
        	JButton btn = new JButton("Task " + i);
        	listBtnTask.add(btn);
        	pCen.add(btn);
        	pCen.add(Box.createVerticalStrut(15));
        	btn.setBorder(brdr);
        	btn.setBackground(Color.white);
        	btn.setMaximumSize(new Dimension(355, 55));
        	btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        	btn.setFont(font);
        	btn.addActionListener(this);
        }
        
        // Button tạo Task
        btnTaoTask = new JButton("Tạo Công việc (F2)");
        btnTaoTask.setBorder(brdr);
        btnTaoTask.setBackground(new java.awt.Color(0, 235, 235));
        btnTaoTask.setPreferredSize(new Dimension(195, 40));
        btnTaoTask.setFont(font);
        btnTaoTask.setFocusable(false);
        
        // Tạo panelSouthBtn và set flow layout để đưa button vào khu vực bên phải
        if (role.equals("Manager")) {
        	JPanel panelSouthBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        	panelSouthBtn.add(btnTaoTask);
        	add(panelSouthBtn, BorderLayout.SOUTH);
        	panelSouthBtn.setBorder(new EmptyBorder(15, 0, 35, 35));
        }
        
        JScrollPane scroll = new JScrollPane(pCen);
        scroll.setBorder(null);
        
        // Thay đổi tốc độ scroll
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
        
        // Thay đổi con trỏ chuột khi di chuyển vào các thành phần
 		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
 		btnBack.setCursor(cursor);
 		btnTaoTask.setCursor(cursor);
        
        btnBack.addActionListener(this);
        btnBack.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			mainFrame.setPanel(new Panel_DanhSachProject(mainFrame));
			return;
		}
		for (JButton btn : listBtnTask) {
			if (o.equals(btn)) {
				mainFrame.setPanel(new Panel_DanhSachTaskCon(btn.getText(), mainFrame, projectName));
				break;
			}
		}
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
		if (o.equals(btnBack)) {
			btnBack.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			btnBack.setForeground(Color.black);
		}
	}
}
