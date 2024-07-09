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
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class SellProductSeller extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField prodNameTextField;
	private JComboBox catComboBox;
	private JComboBox sizeComboBox;

	
	private Product prod;
	private int sellerID;
	
	private String[] sizes = {"NA", "XS", "S", "M", "L", "XL"};
	private String[] categories = {"Shirts", "T-Shirts", "Polo", "Jeans", "Pants", "Shorts", "Accessories"};

	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			SellProductSeller dialog = new SellProductSeller();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	
	public SellProductSeller(Product prod, int sid) {
		this();
		this.prod = prod;
		sellerID = sid;
	}
	public SellProductSeller() {
		setBounds(100, 100, 617, 335);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Product Name");
			contentPanel.add(lblNewLabel, "4, 4, right, default");
		}
		{
			prodNameTextField = new JTextField();
			contentPanel.add(prodNameTextField, "6, 4, fill, default");
			prodNameTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Category");
			contentPanel.add(lblNewLabel_1, "4, 6, right, default");
		}
		{
			catComboBox = new JComboBox(categories);
			contentPanel.add(catComboBox, "6, 6, fill, default");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Size");
			contentPanel.add(lblNewLabel_2, "4, 8, right, default");
		}
		{
			sizeComboBox = new JComboBox(sizes);
			contentPanel.add(sizeComboBox, "6, 8, fill, default");
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Type");
			contentPanel.add(lblNewLabel_3, "4, 10, right, default");
		}
		{
			JRadioButton rentButton = new JRadioButton("Rent");
			rentButton.setActionCommand("R");
			buttonGroup.add(rentButton);
			contentPanel.add(rentButton, "6, 10");
		}
		{
			JRadioButton sellButton = new JRadioButton("Sell");
			sellButton.setActionCommand("S");
			buttonGroup.add(sellButton);
			contentPanel.add(sellButton, "6, 12");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Sell");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sellProduct();
						setVisible(false);
						dispose();
						JOptionPane.showMessageDialog(contentPanel, "Sold successfully. Please check your deposit.", "Sold",
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
	
	public void sellProduct() {
		String name = prodNameTextField.getText();
		String cat = catComboBox.getSelectedItem().toString();
		String size = sizeComboBox.getSelectedItem().toString();
		String type = buttonGroup.getSelection().getActionCommand();
		
		try {
			prod.insertProduct(name, cat, size, type, sellerID);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error registering user: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
