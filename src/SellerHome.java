import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SellerHome extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private User usr;
	private Product prod;
	private int sellerID;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			User tempUser = new User();
//			SellerHome dialog = new SellerHome(tempUser);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SellerHome(User usr, Product prod, int sid) {
		this();
		this.usr = usr;
		this.prod = prod;
		sellerID = sid;
	}
	
	public SellerHome() {
		setBounds(100, 100, 776, 488);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Sell Product");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellProductSeller sps = new SellProductSeller(prod, sellerID);
				sps.setVisible(true);
				sps.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton.setBounds(150, 205, 137, 21);
		contentPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Product History");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerProductHistory sph = new SellerProductHistory(sellerID);
				sph.setVisible(true);
				sph.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton_1.setBounds(298, 205, 137, 21);
		contentPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Sign Out");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeMenu hm = new HomeMenu(usr);
				hm.setVisible(true);
				hm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				setVisible(false);
				dispose();
			}
		});
		btnNewButton_2.setBounds(445, 205, 137, 21);
		contentPanel.add(btnNewButton_2);
	}
}
