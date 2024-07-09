import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import net.proteanit.sql.*;

public class BuyerBrowseProducts extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	
	private User usr;
	private Product prod;
	private int buyerID;
	private String[] sizes = {"None", "NA", "XS", "S", "M", "L", "XL"};
	private String[] categories = {"None", "Shirts", "T-Shirts", "Polo", "Jeans", "Pants", "Shorts", "Accessories"};
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BuyerHome frame = new BuyerHome();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public BuyerBrowseProducts(User usr, Product prod, int bid) {
		this();
		this.usr = usr;
		this.prod = prod;
		buyerID = bid;
	}
	public BuyerBrowseProducts() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(10, 10, 549, 31);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Enter name:");
		panel.add(lblNewLabel);
		
		searchField = new JTextField();
		panel.add(searchField);
		searchField.setColumns(10);
		
		JComboBox catBox = new JComboBox(categories);
		panel.add(catBox);
		
		JComboBox sizeBox = new JComboBox(sizes);
		panel.add(sizeBox);
		
		JRadioButton rentButton = new JRadioButton("Rent");
		rentButton.setActionCommand("R");
		buttonGroup.add(rentButton);
		panel.add(rentButton);
		
		JRadioButton sellButton = new JRadioButton("Buy");
		sellButton.setActionCommand("S");
		buttonGroup.add(sellButton);
		panel.add(sellButton);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchName = searchField.getText();
				String category = catBox.getSelectedItem().toString();
				String size = sizeBox.getSelectedItem().toString();
				if(!rentButton.isSelected() && !sellButton.isSelected())
					JOptionPane.showMessageDialog(contentPane, "Please enter a product type (Rent/Buy).", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				else {
					String type = buttonGroup.getSelection().getActionCommand();
					
					try {
						ResultSet tempRS = prod.getAllProducts(searchName, category, size, type);
						if(tempRS!=null)
							table.setModel(DbUtils.resultSetToTableModel(tempRS));
						else
							JOptionPane.showMessageDialog(contentPane, "Invalid search. Please try again.", "Error",
									JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		
		panel.add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 549, 213);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Add to Wishlist");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				DefaultTableModel tmpModel = (DefaultTableModel) table.getModel();
				for(int i = 0; i<rows.length; i++)
					prod.addToWishlist(buyerID, Integer.valueOf(tmpModel.getValueAt(rows[i], 0).toString()));
			}
		});
		btnNewButton.setBounds(10, 274, 110, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add to Cart");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				DefaultTableModel tmpModel = (DefaultTableModel) table.getModel();
				for(int i = 0; i<rows.length; i++)
					prod.addToCart(buyerID, Integer.valueOf(tmpModel.getValueAt(rows[i], 0).toString()));
			}
		});
		btnNewButton_1.setBounds(130, 274, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cart");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerCart bc = new BuyerCart(buyerID, prod);
				bc.setVisible(true);
				bc.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton_2.setBounds(319, 274, 64, 21);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Wishlist");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerWishlist bw = new BuyerWishlist(buyerID, prod);
				bw.setVisible(true);
				bw.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton_3.setBounds(225, 274, 85, 21);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_5 = new JButton("Rentals");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerRentals br = new BuyerRentals(prod, buyerID);
				br.setVisible(true);
				br.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton_5.setBounds(393, 274, 71, 21);
		contentPane.add(btnNewButton_5);
	}
}
