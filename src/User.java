import java.sql.*;
import java.util.Random;

public class User extends DBSConnection{
	
	public User() throws Exception{
		super();
	}
	
	public void insertBuyer(String fn, String ln, String email, String pwd, String pnum) {
		int uid = generateID(); //user_id
		String sql = "insert into user values(" + uid + ",'B',?,?,?,?,?,current_timestamp);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);	
			stmt.setString(1, fn);
			stmt.setString(2, ln);
			stmt.setString(3, pwd);
			stmt.setString(4, pnum);
			stmt.setString(5, email);			
			stmt.executeUpdate();
			
			int bid = generateID(); //buyer_id;
			sql = "insert into buyer values(" + uid + "," + bid + ", (select address_id from address order by rand() limit 1));";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate(sql);
			System.out.println("Inserted successfully.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertSeller(String fn, String ln, String email, String pwd, String pnum) {
		int uid = generateID(); //user_id
		String sql = "insert into user values(" + uid + ",'S',?,?,?,?,?,current_timestamp);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);	
			stmt.setString(1, fn);
			stmt.setString(2, ln);
			stmt.setString(3, pwd);
			stmt.setString(4, pnum);
			stmt.setString(5, email);
			
			stmt.executeUpdate();
			
			int sid = generateID(); //buyer_id;
			sql = "insert into seller values(" + uid + "," + sid + ", (select city_id from city order by rand() limit 1), 0, 'N');";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate(sql);
			System.out.println("Inserted successfully.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertAdmin(String fn, String ln, String email, String pwd, String pnum) {
		int aid = generateID(); //admin_id
		String sql = "insert into admin values(" + aid + ",?,?,?,?,?);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);	
			stmt.setString(1, fn);
			stmt.setString(2, ln);
			stmt.setString(3, pnum);
			stmt.setString(4, email);
			stmt.setString(5, pwd);
			
			stmt.executeUpdate();
			
			System.out.println("Inserted successfully.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkUser(String eid) {
		try{
			String sql = "select * from user where email_id = '" + eid + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkAdmin(String eid) {
		try{
			String sql = "select * from admin where email = '" + eid + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkPass(String pwd) {
		try{
			String sql = "select * from user where password = '" + pwd + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkAdminPass(String pwd) {
		try{
			String sql = "select * from admin where password = '" + pwd + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public char getUserType(String eid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select user_type from user where email_id = '" + eid + "';"); rs.next();
			String type = rs.getString(1);
			return type.charAt(0);
		}catch (Exception e) {
			e.printStackTrace();
			return 'B'; //default
		}
	}
	
	public int getSellerID(String eid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select seller_id from seller s join user u on u.user_id = s.user_id"
					+ " where u.email_id = '" + eid + "';"); rs.next();
			int sid = rs.getInt(1);
			return sid;
		}catch (Exception e) {
			e.printStackTrace();
			return 1; //default
		}
	}
	
	public int getBuyerID(String eid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select buyer_id from buyer b join user u on u.user_id = b.user_id"
					+ " where u.email_id = '" + eid + "';"); rs.next();
			int sid = rs.getInt(1);
			return sid;
		}catch (Exception e) {
			e.printStackTrace();
			return 1; //default
		}
	}
	
}
