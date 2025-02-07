package design;

import javax.swing.*;
import java.awt.*;

public class RoundedPasswordField extends JPasswordField {
    private String placeholder;
    private int width = -1;
    private int height = -1;
    private int border = 0;
    private int padding = 9;

    public RoundedPasswordField(int columns, String placeholder) {
        super(columns);
        this.placeholder = placeholder;
        setOpaque(false); // Đảm bảo nền trong suốt
    }
    
    public RoundedPasswordField(int columns, String placeholder, int width, int height) {
        super(columns);
        this.placeholder = placeholder;
        this.width = width;
        this.height = height;
        setOpaque(false);
    }
    
    public RoundedPasswordField(int columns, String placeholder, int width, int height, int border) {
        super(columns);
        this.placeholder = placeholder;
        this.width = width;
        this.height = height;
        this.border = border;
        setOpaque(false);
    }
    
    public RoundedPasswordField(int columns, String placeholder, int border) {
        super(columns);
        this.placeholder = placeholder;
        this.border = border;
        setOpaque(false);
    }
    
    // Thay đổi padding bằng giá trị default
    @Override
    public Insets getInsets() {
        Insets defaultInsets = super.getInsets();
        return new Insets(defaultInsets.top, padding, defaultInsets.bottom, padding);
    }
    
    // Tuỳ chỉnh giá trị padding
    public void setPadding(int padding) {
        this.padding = padding;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền bo tròn
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), border, border);

        // Vẽ border
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, border, border);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không cần vẽ border vì đã vẽ trong paintComponent
    }
    
    // Thay đổi kích thước textfield
    @Override
    public Dimension getPreferredSize() {
        Dimension defaultSize = super.getPreferredSize();
        // Nếu có kích thước tùy chỉnh, sử dụng chúng
        int w = width > 0 ? width : defaultSize.width;
        int h = height > 0 ? height : defaultSize.height + 10;
        return new Dimension(w, h);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Hiển thị placeholder nếu chưa có văn bản
        if (getPassword().length == 0 && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawString(placeholder, getInsets().left, getHeight() / 2 + getFont().getSize() / 2 - 2);
            g2.dispose();
        }
    }
}
