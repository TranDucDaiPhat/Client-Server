package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import design.Constants;
import design.TextBubbleBorder;

public class Panel_DanhSachProject extends JPanel implements ActionListener {

	private ArrayList<Panel_Project_Item> listProjectItem;
	private GUI_TrangChu mainFrame;
	private JButton btnTaoDuAn;
	private String role;
	

	// Panel này hiển thị tất cả các Project
	public Panel_DanhSachProject(GUI_TrangChu mainFrame) {
		this.mainFrame = mainFrame;
		role = mainFrame.getRole();
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,1,15,15);
		Font font = Constants.DEFAULT_FONT;
		
		setLayout(new BorderLayout());
		
		JPanel pCen = new JPanel();
		
		pCen.setLayout(new BoxLayout(pCen, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("Danh sách các dự án");
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setFont(Constants.TITLE_FONT);
		
		pCen.add(Box.createVerticalStrut(40));
		pCen.add(lblTitle);
		pCen.add(Box.createVerticalStrut(35));
		
		Box b1;
		pCen.add(b1 = Box.createHorizontalBox());
		pCen.add(Box.createVerticalStrut(10));
		b1.setMaximumSize(new Dimension(1070, 55));
		JLabel lblDuAn, lblSoNguoi, lblNgay, lblGhiChu, lblXoa;
		b1.add(lblDuAn = new JLabel("Tên dự án"));
		b1.add(Box.createHorizontalStrut(255));
		b1.add(lblSoNguoi = new JLabel("Số người thực hiện"));
		b1.add(Box.createHorizontalStrut(30));
		b1.add(lblNgay = new JLabel("Ngày lập/Thời hạn"));
		b1.add(Box.createHorizontalStrut(45));
		b1.add(lblGhiChu = new JLabel("Mô tả"));
		b1.add(Box.createHorizontalStrut(255));
		b1.add(lblXoa = new JLabel("Xoá"));
		
		lblDuAn.setFont(font);
		lblSoNguoi.setFont(font);
		lblNgay.setFont(font);
		lblGhiChu.setFont(font);
		lblXoa.setFont(font);
        
        listProjectItem = new ArrayList<Panel_Project_Item>();
        
        // Tạo 8 project mẫu
        for (int i = 0; i < 8; i++) {
        	Panel_Project_Item panel_Project_Item = new Panel_Project_Item("Project example " + i);
        	pCen.add(panel_Project_Item);
        	pCen.add(Box.createVerticalStrut(15));
        	listProjectItem.add(panel_Project_Item);
        	panel_Project_Item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mainFrame.setPanel(new Panel_DanhSachTask(panel_Project_Item.getProjectName(), mainFrame));
                }
            });
        }
        
        // Button tạo dự án
        btnTaoDuAn = new JButton("Tạo dự án (F1)");
        btnTaoDuAn.setBorder(brdr);
        btnTaoDuAn.setBackground(new java.awt.Color(0, 235, 235));
        btnTaoDuAn.setPreferredSize(new Dimension(195, 40));
        btnTaoDuAn.setFont(font);
        btnTaoDuAn.setFocusable(false);
        
        // Tạo panelSouthBtn và set flow layout để đưa button vào khu vực bên phải
        if (role.equals("Manager")) {
        	JPanel panelSouthBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        	panelSouthBtn.add(btnTaoDuAn);
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
		btnTaoDuAn.setCursor(cursor);
		for (Panel_Project_Item panel : listProjectItem) {
			panel.setCursor(cursor);
		}
        
		// Đặt thanh cuộn lên đầu (Do lỗi gì đấy nên nó tự cuộn xuống cuối)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue(vertical.getMinimum());  
            }
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
	}
}
