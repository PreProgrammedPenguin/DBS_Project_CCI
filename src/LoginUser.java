import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField EIDTextField;
	
	private User usr;
	private Product prod;
	private JPasswordField pwdTextField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			User tempUser = new User();
//			LoginUser dialog = new LoginUser(tempUser);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	
	public LoginUser(User usr) {
		this();
		this.usr = usr;
		try {
			prod = new Product();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public LoginUser() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Email ID");
			contentPanel.add(lblNewLabel, "6, 8, right, default");
		}
		{
			EIDTextField = new JTextField();
			contentPanel.add(EIDTextField, "8, 8, fill, default");
			EIDTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Password");
			contentPanel.add(lblNewLabel_1, "6, 10, right, default");
		}
		{
			pwdTextField = new JPasswordField();
			contentPanel.add(pwdTextField, "8, 10, fill, default");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Login");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loginBuyer();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void loginBuyer() {
		String eid = EIDTextField.getText();
		String pwd = new String(pwdTextField.getPassword());
		
		try {
			if(usr.checkUser(eid)) {
				//check if password is right. if not try again
				if(usr.checkPass(pwd)) {
					//do something
					char userType = usr.getUserType(eid);
					if(userType == 'B') {
						int bid = usr.getBuyerID(eid);
						BuyerHome bh = new BuyerHome(usr, prod, bid);
						bh.setVisible(true);
						bh.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						setVisible(false);
						dispose();
					}
					else {
						int sid = usr.getSellerID(eid);
						SellerHome sh = new SellerHome(usr, prod, sid);
						sh.setVisible(true);
						sh.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						setVisible(false);
						dispose();
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPanel, "Incorrect password.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else {
				NotExist notExistDialog = new NotExist(usr);
				notExistDialog.setVisible(true);
				notExistDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error registering user: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
