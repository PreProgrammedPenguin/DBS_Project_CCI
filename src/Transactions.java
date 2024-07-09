import java.sql.*;

public class Transactions extends DBSConnection{
	
	public Transactions() throws Exception{
		super();
	}
	
	public int getNoOfSellerEarnings() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from seller_earnings;"); rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet getSellerEarnings(int sid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select earning_id as Deposit_ID, deposit as Deposit_Amount, deposit_date as Deposit_Date "
					+ "from seller_earnings where seller_id = " + sid + ";");
			return rs;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void purchaseProducts(int bid, int[] pids, String type) {
		float sum = 0;
		if(type.equals("S")) {
			int oid = generateID(); //order ID
			int pid = generateID(); //payment ID
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("insert into orders values(" + oid + "," + bid + ",current_timestamp);");
				for(int i = 0; i<pids.length; i++) {
					sum += getProductPrice(pids[i], type);
					stmt.executeUpdate("insert into ordered_products values(" + oid +"," + pids[i] + ");");			
				}
				stmt.executeUpdate("insert into order_payments values(" + pid + "," + oid + "," + sum + ");");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			int rid = generateID(); //rental ID
			int rpid = generateID(); //rental payment ID
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("insert into rentals(rental_id, buyer_id, rental_date, due_date) "
						+ "values(" + rid + "," + bid + ",current_timestamp, "
						+ "(select date_add(current_timestamp, interval 7 day)));");
				for(int i = 0; i<pids.length; i++) {
					sum += getProductPrice(pids[i], type);
					stmt.executeUpdate("insert into rented_products values(" + rid +"," + pids[i] + ");");
					stmt.executeUpdate("update rent_products set no_of_rentals = no_of_rentals + 1 where product_id = " + pids[i] + ";");
				}
				stmt.executeUpdate("insert into rental_payments values(" + rpid + "," + rid + "," + sum + ");");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateRentalStatus(int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "update rent_products set rented_out = case "
					+ "	when rented_out = 'N' then 'Y' "
					+ "    else 'N' "
					+ "end where product_id = " + pid + ";";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float getProductPrice(int pid, String ptype) {
		try {
			Statement stmt = conn.createStatement();
			if(ptype.equals("S")) {
				ResultSet rs = stmt.executeQuery("select price from sell_products where product_id = " + pid + ";"); rs.next();
				return rs.getFloat(1);			
			}else {
				ResultSet rs = stmt.executeQuery("select rental_rate from rent_products where product_id = " + pid + ";"); rs.next();
				return rs.getFloat(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
