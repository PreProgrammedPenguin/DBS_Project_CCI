import java.sql.*;

public class Product extends DBSConnection{
	
	private static String[] sizes = {"XS", "S", "M", "L", "XL"};
	private static String[] categories = {"Shirts", "T-Shirts", "Polo", "Jeans", "Pants", "Shorts", "Accessories"};
	
	public Product() throws Exception{
		super();
	}
	
	public void insertProduct(String name, String cat, String size, String type, int sid) {
		int pid = generateID(); //user_id
		int wid = getWarehouseID(sid);
		String sql = "insert into products (product_id, prod_name, warehouse_id, product_type,"
				+ "category, size, seller_id) values(" + pid + ",?," + wid + ",?,?,?,?);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);	
			stmt.setString(1, name);
			stmt.setString(2, type);
			stmt.setString(3, cat);
			stmt.setString(4, size);
			stmt.setInt(5, sid);			
			stmt.executeUpdate();
			
			float price = getPrice(type, cat);
			if(type.equals("R")) {
				sql = "insert into rent_products values(" + pid + ", 'N',"  + price + ",10, 0);";
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate(sql);
			}
			else {
				sql = "insert into sell_products values(" + pid + ","  + price + ",0);";
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate(sql);
			}
			System.out.println("Inserted successfully.");
			
			updateSellerEarnings(price, sid);
			System.out.println("Earnings updated.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateSellerEarnings(float price, int sid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "update seller set sales_no = sales_no + 1 "
					+ "where seller_id = " + sid + ";";
			stmt.executeUpdate(sql);
			
			int earnings_id = generateID();
			sql = "insert into seller_earnings values(" + sid + "," + earnings_id + "," + price + ",current_timestamp);";
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addToWishlist(int bid, int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into wishlist values(" + bid + ", " + pid + ");";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addToCart(int bid, int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into cart values(" + bid + ", " + pid + ");";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromWishlist(int bid, int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from wishlist where product_id = " + pid + " and buyer_id = " + bid + ";";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromCart(int bid, int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from cart where product_id = " + pid + " and buyer_id = " + bid + ";";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromProducts(int pid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from sell_products where product_id = " + pid + ";";
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getAllProducts(String name, String cat, String size, String type) {
		if(name != null && name.trim().length() > 0)
			name += "%";
		else
			name = null;
		
		if(type.equals("R")) {
			String sql = "select distinct p.product_id as 'ID', prod_name as Name, product_type as Type, category as Category, "
					+ "size as Size, rented_out as 'Out', rental_rate as Rate, "
					+ "rental_period as Duration, no_of_rentals as No_of_Rentals from products p "
					+ "join rent_products rp on rp.product_id = p.product_id where "
					+ "prod_name like coalesce(?, prod_name) "
					+ "and product_type = coalesce(?, product_type) "
					+ "and category like case "
					+ "	when ? not like 'None' then ? "
					+ "    else category "
					+ "    end "
					+ "and size like case "
					+ "	when ? not like 'None' then ? "
					+ "    else size "
					+ "    end;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, type);
				stmt.setString(3, cat); stmt.setString(4, cat);
				stmt.setString(5, size); stmt.setString(6, size);
				
				ResultSet rs = stmt.executeQuery();
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}else if(type.equals("S")) {
			String sql = "select distinct p.product_ID as 'ID', prod_name as 'Name', product_type as 'Type', category as Category, "
					+ "size as Size, price as Price, discount as Discount from products p "
					+ "join sell_products sp on sp.product_id = p.product_id where "
					+ "prod_name like coalesce(?, prod_name) "
					+ "and product_type = coalesce(?, product_type) "
					+ "and category like case "
					+ "	when ? not like 'None' then ? "
					+ "    else category "
					+ "    end "
					+ "and size like case "
					+ "	when ? not like 'None' then ? "
					+ "    else size "
					+ "    end;";	
			
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, type);
				stmt.setString(3, cat); stmt.setString(4, cat);
				stmt.setString(5, size); stmt.setString(6, size);
				
				ResultSet rs = stmt.executeQuery();
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
	}
	
	public ResultSet getWishlistProducts(int bid, String ptype) {
		try {
			Statement stmt = conn.createStatement();
			if(ptype.equals("S")) {
				String sql = "select p.product_id as 'ID', prod_name as 'Name', product_type as 'Type', category as 'Category', "
						+ "size as 'Size', price as 'Price', discount as 'Discount' from products p join sell_products sp on sp.product_id = p.product_id "
						+ "join wishlist w on w.buyer_id = " + bid + " and w.product_id = sp.product_id;";
				ResultSet rs= stmt.executeQuery(sql);
				return rs;
			}else if(ptype.equals("R")) {
				String sql = "select p.product_id as 'ID', prod_name as Name, product_type as Type, category as Category, "
						+ "size as Size, rented_out as 'Out', rental_rate as Rate, "
						+ "rental_period as Duration, no_of_rentals as No_of_Rentals from products p "
						+ "join rent_products rp on rp.product_id = p.product_id join wishlist w on w.buyer_id = " + bid + " and "
								+ "w.product_id = rp.product_id;";
				ResultSet rs = stmt.executeQuery(sql);
				return rs;
			}
			else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getCartProducts(int bid, String ptype) {
		try {
			Statement stmt = conn.createStatement();
			if(ptype.equals("S")) {
				String sql = "select p.product_id as 'ID', prod_name as 'Name', product_type as 'Type', category as 'Category', "
						+ "size as 'Size', price as 'Price', discount as 'Discount' from products p join sell_products sp on sp.product_id = p.product_id "
						+ "join cart c on c.buyer_id = " + bid + " and c.product_id = sp.product_id;";
				ResultSet rs= stmt.executeQuery(sql);
				return rs;
			}else if(ptype.equals("R")) {
				String sql = "select p.product_id as 'ID', prod_name as Name, product_type as Type, category as Category, "
						+ "size as Size, rented_out as 'Out', rental_rate as Rate, "
						+ "rental_period as Duration, no_of_rentals as No_of_Rentals from products p "
						+ "join rent_products rp on rp.product_id = p.product_id join cart c on c.buyer_id = " + bid + " and "
								+ "c.product_id = rp.product_id;";
				ResultSet rs = stmt.executeQuery(sql);
				return rs;
			}
			else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getRentedProducts(int bid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select p.product_id as 'ID', prod_name as Name, product_type as Type, category as Category, "
					+ "size as Size, rented_out as 'Out', rental_rate as Rate, "
					+ "rental_period as Duration, no_of_rentals as No_of_Rentals from products p "
					+ "join rent_products rpo on rpo.product_id = p.product_id "
					+ "join rented_products rp on rp.product_id = p.product_id join rentals r on r.rental_id = rp.rental_id where "
					+ "r.buyer_id = " + bid + " and rpo.rented_out = 'Y';";
			ResultSet rs= stmt.executeQuery(sql);
			return rs;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getOrderHistory(int bid, String ptype) {
		try {
			Statement stmt = conn.createStatement();
			if(ptype.equals("S")) {
				String sql = "select o.order_id as Order_ID, order_date as Order_Date, payment_amount as Amount, "
						+ "p.product_id as 'ID', prod_name as 'Name', product_type as 'Type', category as 'Category', "
						+ "size as 'Size' from orders o join order_payments op on op.order_id = o.order_id "
						+ "join ordered_products opo on opo.order_id = o.order_id join products p on p.product_id = opo.product_id "
						+ "join sell_products sp on sp.product_id = p.product_id "
						+ "where o.buyer_id = " + bid + ";";
				ResultSet rs= stmt.executeQuery(sql);
				return rs;
			}else if(ptype.equals("R")) {
				String sql = "select r.rental_id as Rental_ID, r.rental_date as Rental_Date, rental_amount as Amount, "
						+ "p.product_id as 'ID', prod_name as Name, product_type as Type, category as Category, "
						+ "size as Size from rentals r join rental_payments rpy on rpy.rental_id = r.rental_id "
						+ "join rented_products rpo on rpo.rental_id = r.rental_id join products p on p.product_id = rpo.product_id "
						+ "join rent_products rp on rp.product_id = p.product_id "
						+ "where r.buyer_id = " + bid + ";";
				ResultSet rs = stmt.executeQuery(sql);
				return rs;
			}
			else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getWarehouseID(int sid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select warehouse_id from warehouse w join "
					+ "address a on w.address_id = a.address_id join "
					+ "seller s on a.city_id = s.city_id "
					+ "where s.seller_id = " + sid + ";"); rs.next();
			int wid = rs.getInt(1);
			return wid;
		}catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public String getProductType(int pid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select product_type from products where product_id = " + pid + ";"); rs.next();
			return rs.getString(1);
		}catch(Exception e) {
			e.printStackTrace();
			return "S";
		}
	}
	
	public float getPrice(String type, String cat) {
		float price = 0;
		if(cat.equals(categories[0]))
			price = 2000;
		else if(cat.equals(categories[1]))
			price = 1000;
		else if(cat.equals(categories[2]))
			price = 1300;
		else if(cat.equals(categories[3]))
			price = 2000;
		else if(cat.equals(categories[4]))
			price = 2000;
		else if(cat.equals(categories[5]))
			price = 800;
		else if(cat.equals(categories[6]))
			price = 600;
		
		if(type.equals("R"))
			price *= 0.5;
		
		return price;
	}
}


