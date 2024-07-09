import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class HomeMenu extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private User usr;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			User tempUser = new User();
			HomeMenu dialog = new HomeMenu(tempUser);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public HomeMenu(User usr) {
		this();
		this.usr = usr;
	}
	public HomeMenu() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("151px"),
				ColumnSpec.decode("137px"),},
			new RowSpec[] {
				RowSpec.decode("84px"),
				RowSpec.decode("21px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("21px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("21px"),}));
		{
			JButton luButton = new JButton("Login as User");
			luButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginUser loginUserDialog = new LoginUser(usr);
					loginUserDialog.setVisible(true);
					loginUserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					setVisible(false);
					dispose();
				}
			});
			contentPanel.add(luButton, "2, 4, fill, top");
		}
		{
			JButton laButton = new JButton("Login as Admin");
			laButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginAdmin loginUserDialog = new LoginAdmin(usr);
					loginUserDialog.setVisible(true);
					loginUserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					setVisible(false);
					dispose();
				}
			});
			contentPanel.add(laButton, "2, 6, fill, top");
		}
	}
}
