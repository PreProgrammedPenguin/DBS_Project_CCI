import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import net.proteanit.sql.*;
import javax.swing.JScrollPane;

public class BuyerCart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private Product prod;
	private Transactions trs;
	private int buyerID;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BuyerWishlist frame = new BuyerWishlist();
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
	
	public BuyerCart(int bid, Product prod) {
		this();
		this.prod = prod;
		buyerID = bid;
		try {
			trs = new Transactions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BuyerCart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 677, 35);
		contentPane.add(panel_1);
		
		JRadioButton rentButton = new JRadioButton("Rent");
		rentButton.setActionCommand("R");
		buttonGroup.add(rentButton);
		panel_1.add(rentButton);
		
		JRadioButton buyButton = new JRadioButton("Buy");
		buyButton.setActionCommand("S");
		buttonGroup.add(buyButton);
		panel_1.add(buyButton);
		
		JButton btnNewButton_1 = new JButton("View");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!rentButton.isSelected() && !buyButton.isSelected())
					JOptionPane.showMessageDialog(contentPane, "Please enter a product type (Rent/Buy).", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				else {
					String type = buttonGroup.getSelection().getActionCommand();
					
					try {
						ResultSet tempRS = prod.getCartProducts(buyerID, type);
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
		panel_1.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 677, 339);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Payment");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!rentButton.isSelected() && !buyButton.isSelected())
					JOptionPane.showMessageDialog(contentPane, "Please enter a product type (Rent/Buy).", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				else {
					String type = buttonGroup.getSelection().getActionCommand();
					
					try {
						int[] rows = table.getSelectedRows();
						int[] pids = new int[rows.length];
						DefaultTableModel tmpModel = (DefaultTableModel) table.getModel();
						for(int i = 0; i<rows.length; i++) {
							pids[i] = Integer.valueOf(tmpModel.getValueAt(rows[i], 0).toString());
							prod.removeFromCart(buyerID, pids[i]);
						}
						trs.purchaseProducts(buyerID, pids, type);
						//Commented for testing
						for(int i = 0; i<rows.length; i++){
							if(prod.getProductType(pids[i]).equals("S"))
								prod.removeFromProducts(pids[i]);
							else
								trs.updateRentalStatus(pids[i]);
						}
						JOptionPane.showMessageDialog(contentPane, "Payment Successful. Check your order history.", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(306, 400, 85, 21);
		contentPane.add(btnNewButton);
	}
}
