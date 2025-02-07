package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import design.Constants;
import model.Project;

public class Panel_Project_Item extends JPanel{

	private JTextArea txtAreaNgay, txtAreaGhiChu;
	private String projectName;
	private JButton btnTrash;

	public Panel_Project_Item(String projectName) {
		this.projectName = projectName;
		initComponents();
		
		initActions();
	}
	
	public String getProjectName() {
		return projectName;
	}

	private void initActions() {
		 MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(225,225,225));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Color.LIGHT_GRAY);
			}
		};
		// Tạo sự kiện riêng cho txt Ghi chú và txt Ngày
		// dispatchEvent(e) -> Nếu click vào 2 thằng trên thì chuyển sự kiện cho panel xử lý 
		MouseAdapter mouseAdapterChild = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispatchEvent(e);
			}
		};
		this.addMouseListener(mouseAdapter);
		txtAreaGhiChu.addMouseListener(mouseAdapter);
		txtAreaGhiChu.addMouseListener(mouseAdapterChild);
		txtAreaNgay.addMouseListener(mouseAdapter);
		txtAreaNgay.addMouseListener(mouseAdapterChild);
	}

	private void initComponents() {
		Font font = Constants.DEFAULT_FONT;
		Box b = Box.createVerticalBox();
		Box b1,b2;
		
		b.add(Box.createVerticalStrut(5));
		b.add(b1 = Box.createHorizontalBox());
		JLabel lblTenDuAn, lblSoNguoi;
		b1.add(lblTenDuAn = new JLabel(projectName));
		b1.add(Box.createHorizontalStrut(35));
		b1.add(lblSoNguoi = new JLabel("25 Người"));
		b1.add(txtAreaNgay = new JTextArea("Từ: " + "15/12/2024" + "\n" + "Đến: " + "15/4/2025"));
		String text = "Năm mới bình an, học hành tiến tới, vạn sự như ý nha anh em Năm mới bình an, học hành tiến tới, vạn sự như ý nha anh em";
		if (text.length() > 85) {
			int index = text.substring(0, 80).lastIndexOf(" ");
			b1.add(txtAreaGhiChu = new JTextArea(text.substring(0, index) + "..."));	
		} else {
			b1.add(txtAreaGhiChu = new JTextArea(text));	
		}
		
		b1.add(Box.createHorizontalStrut(25));
		b1.add(btnTrash = new JButton(""));
		btnTrash.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("img\\24-trash.png")));
		
		// Tạo màu trong suốt cho button
		btnTrash.setBorderPainted(false);
		btnTrash.setContentAreaFilled (false);
		btnTrash.setFocusPainted (false);
		
		lblTenDuAn.setFont(font);
		lblSoNguoi.setFont(font);
		txtAreaNgay.setFont(font);
		txtAreaGhiChu.setFont(font);
		
		// Chỉnh kích thước cho các thành phần
		lblTenDuAn.setPreferredSize(new Dimension(335, 35));
		lblTenDuAn.setMaximumSize(new Dimension(335, 35));
		
		lblSoNguoi.setPreferredSize(new Dimension(165, 35));
		lblSoNguoi.setMaximumSize(new Dimension(165, 35));
		
		txtAreaNgay.setPreferredSize(new Dimension(175, 65));
		
		txtAreaGhiChu.setPreferredSize(new Dimension(265, 70));
		txtAreaGhiChu.setMaximumSize(new Dimension(265, 70));
		
		
		// Không cho chỉnh sửa ngày và ghi chú, chỉnh màu trong suốt
		txtAreaNgay.setEditable(false);
		txtAreaNgay.setOpaque(false);
		txtAreaNgay.setBorder(new EmptyBorder(10, 0, 0, 0));
		txtAreaGhiChu.setEditable(false);
		txtAreaGhiChu.setOpaque(false);
		
		// Cài đặt chữ tự động xuống dòng
		txtAreaGhiChu.setLineWrap(true);
		txtAreaGhiChu.setWrapStyleWord(true);
		
		add(b);
		setMaximumSize(new Dimension(1100, 95));
		setPreferredSize(new Dimension(1100, 95));
		setBackground(new Color(225,225,225));
	}
}
