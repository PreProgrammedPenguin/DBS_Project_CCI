import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.proteanit.sql.*;

public class BuyerRentals extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	private int buyerID;
	private Product prod;
	private Transactions trs;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SellerProductHistory frame = new SellerProductHistory();
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
	
	public BuyerRentals(Product prod, int bid) {
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
	
	public BuyerRentals() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel);
		
		JLabel lblSearchByProduct = new JLabel("View Rentals");
		lblSearchByProduct.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblSearchByProduct);
		
		JButton searchButton = new JButton("View");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table.setModel(DbUtils.resultSetToTableModel(prod.getRentedProducts(buyerID)));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(searchButton);
		
		JButton btnNewButton = new JButton("Return Selected");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int[] rows = table.getSelectedRows();
					if(rows.length == 0)
						JOptionPane.showMessageDialog(contentPane, "Select something to return.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
					else {
						DefaultTableModel tmpModel = (DefaultTableModel) table.getModel();
						for(int i = 0; i<rows.length; i++)
							trs.updateRentalStatus(Integer.valueOf(tmpModel.getValueAt(rows[i], 0).toString()));
						JOptionPane.showMessageDialog(contentPane, "Returned Successfully.", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
