package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
/*from   w w w. j a  v a  2 s  .c om*/
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class Test {

  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JList<CheckListItem> list = new JList<CheckListItem>(new CheckListItem[] { new CheckListItem("apple"),
        new CheckListItem("orange"), new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        
        new CheckListItem("paw paw"), new CheckListItem("banana") });
    list.setCellRenderer(new CheckListRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    List<String> listItem = new ArrayList<String>();
    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        JList list = (JList) event.getSource();
        int index = list.locationToIndex(event.getPoint());// Get index of item
                                                           // clicked
        CheckListItem item = (CheckListItem) list.getModel()
            .getElementAt(index);
        item.setSelected(!item.isSelected()); // Toggle selected state
        if (item.isSelected()) {
        	listItem.add(item.toString());
        } else {
        	listItem.remove(index);
        }
        list.repaint(list.getCellBounds(index, index));// Repaint cell
      }
    });
    JButton btn = new JButton(" ok ");
    btn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(listItem);
		}
	});
    JPanel panel;
	frame.add(panel = new JPanel());
	panel.add(new JScrollPane(list));
	panel.add(btn);
    frame.getContentPane().add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}

class CheckListItem {

  private String label;
  private boolean isSelected = false;

  public CheckListItem(String label) {
    this.label = label;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  @Override
  public String toString() {
    return label;
  }
}

class CheckListRenderer extends JCheckBox implements ListCellRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean hasFocus) {
    setEnabled(list.isEnabled());
    setSelected(((CheckListItem) value).isSelected());
    setFont(list.getFont());
    setBackground(list.getBackground());
    setForeground(list.getForeground());
    setText(value.toString());
    return this;
  }
}
