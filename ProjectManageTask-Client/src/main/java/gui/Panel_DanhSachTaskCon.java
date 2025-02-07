package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import design.Constants;
import design.TextBubbleBorder;

public class Panel_DanhSachTaskCon extends JPanel implements ActionListener, MouseListener{
	
	private GUI_TrangChu mainFrame;
	private String role;
	private JButton btnBack;
	private ArrayList<JButton> listBtnTaskChild;
	private JButton btnTaoTaskChild;
	private String projectName;
	
	// Đổi tên file: từ -> Panel_Task -> Panel_DanhSachTaskCon
	// Panel_DanhSachTaskCon hiển thị các Task con trong một Task
	public Panel_DanhSachTaskCon(String taskName, GUI_TrangChu mainFrame, String projectName) {
		this.mainFrame = mainFrame;
		role = mainFrame.getRole();
		this.projectName = projectName;
		
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,1,9,15);
		Font font = Constants.DEFAULT_FONT;
		
		setLayout(new BorderLayout());
		
		// Tạo Button quay lại
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnBack = new JButton("Quay lại");
		btnBack.setFont(font);
		btnBack.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("img\\24-go-previous.png")));
		// Tạo màu trong suốt
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled (false);
		btnBack.setFocusPainted (false);
		
		pNorth.add(btnBack);
		add(pNorth, BorderLayout.NORTH);
		
		JPanel pCen = new JPanel();
		
		pCen.setLayout(new BoxLayout(pCen, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel(taskName);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setFont(Constants.TITLE_FONT);
		
		pCen.add(lblTitle);
		pCen.add(Box.createVerticalStrut(35));
        
        listBtnTaskChild = new ArrayList<JButton>();
        
        // Tạo 5 Task con mẫu
        for (int i = 0; i < 5; i++) {
        	JButton btn = new JButton("Task child " + i);
        	listBtnTaskChild.add(btn);
        	pCen.add(btn);
        	pCen.add(Box.createVerticalStrut(15));
        	btn.setBorder(brdr);
        	btn.setBackground(Color.white);
        	btn.setMaximumSize(new Dimension(355, 55));
        	btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        	btn.setFont(font);
        	btn.addActionListener(this);
        }
        
        // Button tạo task con
        btnTaoTaskChild = new JButton("Tạo Mục công việc (F3)");
        btnTaoTaskChild.setBorder(brdr);
        btnTaoTaskChild.setBackground(new java.awt.Color(0, 235, 235));
        btnTaoTaskChild.setPreferredSize(new Dimension(225, 40));
        btnTaoTaskChild.setFont(font);
        btnTaoTaskChild.setFocusable(false);
        
        // Tạo panelSouthBtn và set flow layout để đưa button vào khu vực bên phải
        if (role.equals("Manager")) {
        	JPanel panelSouthBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        	panelSouthBtn.add(btnTaoTaskChild);
        	add(panelSouthBtn, BorderLayout.SOUTH);
        	panelSouthBtn.setBorder(new EmptyBorder(15, 0, 35, 35));
        }
        
        JScrollPane scroll = new JScrollPane(pCen);
        scroll.setBorder(null);
        
        // Thay đổi tốc độ scroll
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
        
        btnBack.addActionListener(this);
        btnBack.addMouseListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			mainFrame.setPanel(new Panel_DanhSachTask(projectName ,mainFrame));
			return;
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
