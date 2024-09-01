import java.sql.*;
import java.util.Random;

public class DBSConnection {
	Connection conn = null;
	
	public DBSConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "<password>");	
			System.out.println("Connected to DB.");
		} catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
	int generateID() {
		int sum = 0;
		Random rand = new Random();
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select extract(hour from current_timestamp);"); rs.next();
            sum += (rs.getInt(1)*rand.nextInt(10001));
            rs = stmt.executeQuery("select extract(minute from current_timestamp);"); rs.next();
            sum += (rs.getInt(1)*rand.nextInt(10001));
            rs = stmt.executeQuery("select extract(second from current_timestamp);"); rs.next();
            sum += (rs.getInt(1)*rand.nextInt(10001));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

}
