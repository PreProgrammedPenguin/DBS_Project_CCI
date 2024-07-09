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

public class RegisterSeller extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField fnTextField;
	private JTextField lnTextField;
	private JTextField eidTextField;
	private JTextField pnTextField;

	private User usr;
	private JPasswordField pwdTextField;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			User tempUser = new User();
//			RegisterSeller dialog = new RegisterSeller(tempUser);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public RegisterSeller(User usr) {
		this();
		this.usr = usr;
	}

	/**
	 * Create the dialog.
	 */
	public RegisterSeller() {
		setTitle("Register as Seller");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
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
			JLabel lblNewLabel = new JLabel("First Name");
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			fnTextField = new JTextField();
			contentPanel.add(fnTextField, "4, 2, fill, default");
			fnTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Last Name");
			contentPanel.add(lblNewLabel_1, "2, 4, right, default");
		}
		{
			lnTextField = new JTextField();
			contentPanel.add(lnTextField, "4, 4, fill, default");
			lnTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email ID");
			contentPanel.add(lblNewLabel_2, "2, 6, right, default");
		}
		{
			eidTextField = new JTextField();
			contentPanel.add(eidTextField, "4, 6, fill, default");
			eidTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Password");
			contentPanel.add(lblNewLabel_3, "2, 8, right, default");
		}
		{
			pwdTextField = new JPasswordField();
			contentPanel.add(pwdTextField, "4, 8, fill, default");
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Phone Number");
			contentPanel.add(lblNewLabel_4, "2, 10, right, default");
		}
		{
			pnTextField = new JTextField();
			contentPanel.add(pnTextField, "4, 10, fill, default");
			pnTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Register");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addSeller();
						
						setVisible(false);
						dispose();
						JOptionPane.showMessageDialog(contentPanel, "Registered successfully.", "Registered",
								JOptionPane.INFORMATION_MESSAGE);
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
	
	public void addSeller() {
		String fn = fnTextField.getText();	
		String ln = lnTextField.getText();
		String email = eidTextField.getText();
		String pwd = new String(pwdTextField.getPassword());
		String pnum = pnTextField.getText();
		
		
		try {
			usr.insertSeller(fn, ln, email, pwd, pnum);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error registering user: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
