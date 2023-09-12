package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class MultithreadingDemo extends Thread {
	
	
	
	Map<List<String>, List<String>> hm;
	String root;
	String Password;
	
    public MultithreadingDemo(Map<List<String>, List<String>> hm, String root, String Password) 
    {
		this.hm=hm;
		this.root=root;
		this.Password=Password;
	}
    int c=0;

    
    
	public void run() 
	{

	
		try
		{

                
//	       System.out.println("Connected");  
//	       Class.forName("com.mysql.cj.jdbc.Driver");
//	       Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/dw","root","1122");
//	       Statement stmt1 = con1.createStatement();
       
	       String dw = "jdbc:mysql://localhost:3306/dw";
	       
	       Class.forName("com.mysql.cj.jdbc.Driver");
		   Connection con1 = DriverManager.getConnection(dw,root,Password);
		   Statement stmt1 = con1.createStatement(); 
	       
	    for (Map.Entry<List<String>, List<String> >entry : hm.entrySet()) 
	    {	
		  
		    	List<String> value = entry.getValue();
		    	
		      //Insert into customers
		      String sql_insert3 = "INSERT IGNORE INTO customers (CUSTOMER_ID,CUSTOMER_NAME) VALUES(\"" + value.get(2) + "\",\"" + value.get(13) + "\")";  	
		      stmt1.executeUpdate(sql_insert3);
		    	
		      //Products
			  String sql_insert1 = "INSERT IGNORE INTO products (PRODUCT_ID,PRODUCT_NAME,PRICE) VALUES(\"" + value.get(1) + "\",\"" + value.get(8)  + "\",\"" + value.get(11)  + "\")";  	        			
			  stmt1.executeUpdate(sql_insert1);
			  
		      //Supplier
			  String sql_insert2 = "INSERT IGNORE INTO supplier(SUPPLIER_ID,SUPPLIER_NAME) VALUES(\"" + value.get(9) + "\",\"" + value.get(10) + "\")";  	        			
			  stmt1.executeUpdate(sql_insert2);
		//    	
		//      Store
		    	String sql_insert4 = "INSERT IGNORE INTO store(STORE_ID,STORE_NAME) VALUES(\"" + value.get(4) + "\",\"" + value.get(5) + "\")";  	        			
		    	stmt1.executeUpdate(sql_insert4);
		//    	
		    	//	  Time
		    	PreparedStatement statement = con1.prepareStatement("INSERT IGNORE INTO time_d(TIME_ID,T_DATE,T_DAY,T_MONTH,T_QUARTER,T_YEAR) VALUES (?, ?, ?, ?, ?, ?)");
		    	Format a = new SimpleDateFormat("EEEE");
		    	String str_d = a.format(Date.valueOf(value.get(6)));
//		    	System.out.println(str_d);
		    	a = new SimpleDateFormat("MMMM");
		    	String str_m= a.format(Date.valueOf(value.get(6)));			 
		    	statement.setString(1, value.get(3));
		    	statement.setDate(2, Date.valueOf(value.get(6)));
		    	statement.setString(3, str_d);
		    	statement.setString(4, str_m);
		    	statement.setInt(5, Date.valueOf(value.get(6)).getMonth()/3 + 1);
		    	statement.setInt(6, Date.valueOf(value.get(6)).getYear()+1900);
		    	statement.executeUpdate();
		    	
//				String sql_insert = "INSERT INTO TRANSACTIONS (TRANSACTION_ID,PRODUCT_ID,CUSTOMER_ID,TIME_ID, STORE_ID, SUPPLIER_ID,QUANTITY,SALE) VALUES(\"" + 
//															Double.valueOf(value.get(0)) + "\",\"" + value.get(1)  + "\",\"" + value.get(2)  + "\",\"" + value.get(3) +"\",\"" +
//															value.get(4)  + "\",\"" + value.get(9)  + "\",\"" + value.get(7)+ "\",\"" + value.get(12)   + "\")";  	        			
		    	
		    	statement = con1.prepareStatement("INSERT IGNORE INTO TRANSACTIONS (TRANSACTION_ID,PRODUCT_ID,CUSTOMER_ID,TIME_ID, STORE_ID, SUPPLIER_ID,QUANTITY,SALE) VALUES (?, ?, ?, ?, ?, ?,?,?)");
		    	statement.setDouble(1, Double.valueOf(value.get(0)));
		    	statement.setString(2, value.get(1));
		    	statement.setString(3, value.get(2));
		    	statement.setString(4, value.get(3));
		    	statement.setString(5, value.get(4));
		    	statement.setString(6, value.get(9));
		    	statement.setDouble(7, Double.valueOf(value.get(7)));
		    	statement.setDouble(8, Double.valueOf(value.get(12)));
		    	statement.executeUpdate();

//		    	stmt1.executeUpdate(sql_insert);
				System.out.println("Insertion done: "+ c);
				c++;    	        	
		//    	List<String> key = entry.getKey();
		//    	System.out.println(entry);
				
				
		
			    }



		}
		catch(Exception e)
	    {
	        System.out.println(e);
	    }
	}
}


public class meshjoin {

	public static void main(String[] args) {
			
			String db = "jdbc:mysql://localhost:3306/db";
			 String dw = "jdbc:mysql://localhost:3306/dw";
			 String root;
			 String password;
		  
		  System.out.println("Please enter Username");

	      Scanner d = new Scanner(System.in);
	      root = d.nextLine();

	      System.out.println("Please enter the Password");

	      Scanner e = new Scanner(System.in);
	      password = e.nextLine();   
		  
	    try {
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      Connection con = DriverManager.getConnection(db,root,password);
	      Statement stmt = con.createStatement();      
			
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      Connection con1 = DriverManager.getConnection(dw,root,password);
	      Statement stmt1 = con.createStatement(); 
					
	           
	           
	           String sql5 = "SELECT count(TRANSACTION_ID) FROM db.transactions ;";
	           PreparedStatement st5 = con.prepareStatement( sql5,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
	           ResultSet rs5 = st5.executeQuery();
	           int index=0;
	           int size = 0;
				if(rs5.next())
				{
					   size = rs5.getInt(1);
				}
		        int partition=size/5;
		        int i=0;
	           int j=0;
		        
		        
				while(index<size)
				{
					Map<List<String>, List<String>> hm = new HashMap<List<String>, List<String>>();
					String sql = "select * from transactions LIMIT " + index + "," + partition + ";";
					PreparedStatement st1 = con.prepareStatement( sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
					ResultSet rs1 = st1.executeQuery();
					
			           
			           String sql3 = "SELECT * FROM db.customers;";
			           PreparedStatement st3 = con.prepareStatement( sql3,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
			           ResultSet rs3 = st3.executeQuery(sql3);
			           
			           
			           String sql2 = "SELECT * FROM db.products;";
			           PreparedStatement st2 = con.prepareStatement( sql2,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
			           ResultSet rs2 = st2.executeQuery(sql2);
			           		           
					
			           
			           while(rs1.next()) {
			               //Retrieve by column name

			               Double Trans_Id  = rs1.getDouble("TRANSACTION_ID");
			               String Prod_Id = rs1.getString("PRODUCT_ID");
			               String Cust_Id = rs1.getString("CUSTOMER_ID");
			               String St_Id = rs1.getString("STORE_ID");
			               String St_Name = rs1.getString("STORE_NAME");
			               String Time_Id = rs1.getString("TIME_ID");
			               Date Date_Id = rs1.getDate("T_DATE");
			               Double Quantity = rs1.getDouble("Quantity");

			               
			               List<String> values = new ArrayList<String>();
			               values.add(Double.toString(Trans_Id));
			               values.add(Prod_Id);
			               values.add(Cust_Id);
			               values.add(Time_Id);
			               values.add(St_Id);
			               values.add(St_Name);
			               values.add(Date_Id.toString());
			               values.add(Double.toString(Quantity));
			               
			               List<String> keys = new ArrayList<String>();
			               keys.add(Double.toString(Trans_Id));
			               keys.add(Prod_Id);
			               keys.add(Cust_Id);
			               
			               hm.put(keys, values);
			               // to get the array list
//			               System.out.println(hm.get(keys));
			               
			              		            	   
			            	   while(rs2.next())
			            	   {
			            		   
			            		   if ((keys.get(1)).equals(rs2.getString("PRODUCT_ID") ))
			            		   {
			            			   List <String> value = hm.get(keys);
			            			   
			            			   value.add(rs2.getString("PRODUCT_NAME"));
			            			   value.add(rs2.getString("SUPPLIER_ID"));
			            			   value.add(rs2.getString("SUPPLIER_NAME"));
			            			   value.add(rs2.getString("PRICE"));        			   
//			            	           System.out.println(hm.get(key));
			            			   
//			                    	   System.out.println(value.get(6));
			            			   double Quantity1 = Double.parseDouble(value.get(7));
			            	           double Price = Double.parseDouble(rs2.getString("PRICE"));
			            	           double SALE = Quantity1 * Price;
//			                    	   System.out.println(Quantity);
//			                    	   System.out.println(Price);
//			                    	   System.out.println(SALE);
			            			   value.add(Double.toString(SALE));        			   
			                    	   
			            		   }
			            	   
			            	   }
			            	   rs2.beforeFirst();
			            	   
			            	   while(rs3.next() )
			            	   {
			            		   if ((keys.get(2)).equals(rs3.getString("CUSTOMER_ID") ))
			            		   {
	          
			            			   List <String> value = hm.get(keys);
			            			   
			            			   value.add(rs3.getString("CUSTOMER_NAME"));
			            		   }
			            	   }
			            		   rs3.beforeFirst();
					        
			            		   System.out.println(hm.get(keys));
			        
				}	        
		        

			           MultithreadingDemo object = new MultithreadingDemo(hm,root,password);       
			           Thread.sleep(300);
			           object.start();
			           index+=partition;    
				}
				
		
	    
		
	    }
	    
		catch(Exception e1)
	    {
	        System.out.println(e1);
	    }
	}
	}


