/*
 * I use Hashmap to store the temporary result from scaning of the table.
 * I use customer name as the key and rest of the attributes as the corresponding value in computing the first result form. 
 * And I use the combination ofcustomer name and product name as the key and rest of the attributes as the corresponding value in computing the second result form.
 */
/*
 * The program will scan the whole table once and store the specific rows into different hashmap.
 * Use 1.2.6 hashmap to store the first form information and print it by combining together.
 * Use 3.4.5 hashmap to store and calculate the result that the second form need and store it into NO.7 hashmap.Then print it in the standard formation. 
 */

package jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class jdbc {

	public static String left(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		for(int i =0;i<8-s.length();i++) {
			sb.append(" ");
		}
		String a = sb.toString();
		return a;
	}//format the customer to left justifying. The length will be 8.
	
	public static String proleft(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		for(int i =0;i<7-s.length();i++) {
			sb.append(" ");
		}
		String a = sb.toString();
		return a;
	}//format the product to left justifying. The length will be 7.
	
	public static String time(int m) {
		String s = String.valueOf(m);
		StringBuffer sb = new StringBuffer();
		if(s.length()!=2) {
			sb.append("0");
			sb.append(s);
		}else {
			sb.append(s);
		}
		String a = sb.toString();
		return a;
	}//format the date. The length will 10.
	
	public static void main(String[] args)
		{
			String usr ="postgres";
			String pwd ="";
			String url ="jdbc:postgresql://localhost:5432/chen";

			try
			{
				Class.forName("org.postgresql.Driver");
				System.out.println("Success loading Driver!");
			}

			catch(Exception e)
			{
				System.out.println("Fail loading Driver!");
				e.printStackTrace();
			}

			try
			{
				Connection conn = DriverManager.getConnection(url, usr, pwd);
				System.out.println("Success connecting server!");

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
				/*
				Create seven HashMap to store and process the result.
				NO.1,2,6stores the result for the first form
				NO.3,4,5 stores the temporary result for the second form.
				NO.7 stores the final result for the second form
			    */
				Map<String,Object> map1 = new HashMap<String,Object>();
				Map<String,Object> map2 = new HashMap<String,Object>();
				Map<String,Object> map3 = new HashMap<String,Object>(); 
				Map<String,Object> map4 = new HashMap<String,Object>();
				Map<String,Object> map5 = new HashMap<String,Object>();
				Map<String,Object> map6 = new HashMap<String,Object>();
				Map<String,Object> map7 = new HashMap<String,Object>();
				
				// scan all the rows of the sales table. Store the proper rows into the proper hashmap
				while(rs.next()) {
					
					String s = rs.getString("cust")+rs.getString("prod");
					
					if(!map6.containsKey(rs.getString("cust"))){
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						rs1.sum = rs.getInt("quant");
						rs1.fre = 1;
						map6.put(rs.getString("cust"),rs1);
					}else {
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							rs1.sum = ((RS)map6.get(rs.getString("cust"))).sum+rs.getInt("quant");
							rs1.fre = ((RS)map6.get(rs.getString("cust"))).fre+1;
							map6.put(rs.getString("cust"),rs1);
					}
					if(!map1.containsKey(rs.getString("cust"))){
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						map1.put(rs.getString("cust"),rs1);
					}else {
						if(((RS)map1.get(rs.getString("cust"))).quant<rs.getInt("quant")) {
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							map1.put(rs.getString("cust"),rs1);
						}
					}
					if(!map2.containsKey(rs.getString("cust"))) {
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						map2.put(rs.getString("cust"),rs1);
					}else {
						if(((RS)map2.get(rs.getString("cust"))).quant>rs.getInt("quant")) {
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							map2.put(rs.getString("cust"),rs1);
						}
					}
					if(!map3.containsKey(s)&&
							rs.getInt("month")==1&&
							rs.getInt("year")<2006&&
							rs.getInt("year")>1999) {
						
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						map3.put(s,rs1);
					}else {
						if(
							rs.getInt("month")==1&&
							rs.getInt("year")<2006&&
							rs.getInt("year")>1999&&
							((RS)map3.get(s)).quant>rs.getInt("quant")
						) {
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							map3.put(s,rs1);
						}
					}
					if(!map4.containsKey(s)&&
							rs.getInt("month")==2) {
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						map4.put(s, rs1);
					}else {
						if(rs.getInt("month")==2&&
						    ((RS)map4.get(s)).quant<rs.getInt("quant")) {
							
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							map4.put(s, rs1);
						}
					}
					if(!map5.containsKey(s)&&
							rs.getInt("month")==3) {
						RS rs1 = new RS();
						rs1.cust = rs.getString("cust");
						rs1.day = rs.getInt("day");
						rs1.month = rs.getInt("month");
						rs1.year = rs.getInt("year");
						rs1.prod = rs.getString("prod");
						rs1.quant = rs.getInt("quant");
						rs1.state = rs.getString("state");
						map5.put(s, rs1);
					}else {
						if(rs.getInt("month")==3&&
								((RS)map5.get(s)).quant<rs.getInt("quant")) {
							RS rs1 = new RS();
							rs1.cust = rs.getString("cust");
							rs1.day = rs.getInt("day");
							rs1.month = rs.getInt("month");
							rs1.year = rs.getInt("year");
							rs1.prod = rs.getString("prod");
							rs1.quant = rs.getInt("quant");
							rs1.state = rs.getString("state");
							map5.put(s, rs1);
						}
					}			
				}
				
				System.out.println("CUSTOMER MAX_Q PRODUCT DATE       ST MIN_Q PRODUCT DATE       ST AVG_Q");
				System.out.println("======== ===== ======= ========== == ===== ======= ========== == =====");
				Iterator iter = map1.keySet().iterator();
				//combine the map1 and map2 result together and  print all the result that the first form need in the standard formation
				while(iter.hasNext()) {
					
					Object key = iter.next();
					String key1 = (String)key;
					int a = ((RS)map1.get(key)).quant;
					int b = ((RS)map2.get(key)).quant;
					
					System.out.print(left(key1));
					System.out.print(" ");
					System.out.printf("%"+5+"s",((RS)map1.get(key)).quant);
					System.out.print(" ");
					System.out.print(proleft(((RS)map1.get(key)).prod));
					System.out.print(" ");
					System.out.print(time(((RS)map1.get(key)).month)+"/"+time(((RS)map1.get(key)).day)+"/"+((RS)map1.get(key)).year);
					System.out.print(" ");
					System.out.print(((RS)map1.get(key)).state);
					System.out.print(" ");
					System.out.printf("%"+5+"s",((RS)map2.get(key)).quant);
					System.out.print(" ");
					System.out.print(proleft(((RS)map2.get(key)).prod));
					System.out.print(" ");
					System.out.print(time(((RS)map2.get(key)).month)+"/"+time(((RS)map2.get(key)).day)+"/"+((RS)map2.get(key)).year);
					System.out.print(" ");
					System.out.print(((RS)map2.get(key)).state);
					System.out.print(" ");
					System.out.printf("%"+5+"s", ((RS)map6.get(key)).sum/((RS)map6.get(key)).fre);
					System.out.println();
				}
				
				System.out.println();
				System.out.println("CUSTOMER PRODUCT JAN_MAX DATE       FEB_MIN DATE       MAR_MIN DATE      ");
				System.out.println("======== ======= ======= ========== ======= ========== ======= ==========");
				
				//read all the result from map3 and store it into the map7 in the finall formation
				Iterator iter3 = map3.keySet().iterator();
				while(iter3.hasNext()) {
					
					Object key = iter3.next();
					String s = ((RS)map3.get(key)).cust+((RS)map3.get(key)).prod;
					NewRS nrs = new NewRS();
					nrs.cust = ((RS)map3.get(key)).cust;
					nrs.prod = ((RS)map3.get(key)).prod;
					nrs.jday = ((RS)map3.get(key)).day;
					nrs.jmonth = ((RS)map3.get(key)).month;
					nrs.jyear = ((RS)map3.get(key)).year;
					nrs.jquant = ((RS)map3.get(key)).quant;
					nrs.fday = 0;
					nrs.fmonth = 0;
					nrs.fyear = 0;
					nrs.fquant = 0;
					nrs.mday = 0;
					nrs.mmonth = 0;
					nrs.myear = 0;
					nrs.mquant = 0;
					map7.put(s, nrs);
				}
				
				//read all the result from map4 and store it into the map7 in the finall formation	
				Iterator iter4 = map4.keySet().iterator();
				while(iter4.hasNext()) {
					Object key = iter4.next();
					String s = (String)key;
					if(!map7.containsKey(s)) {
						NewRS nrs = new NewRS();
						nrs.cust = ((RS)map4.get(key)).cust;
						nrs.prod = ((RS)map4.get(key)).prod;
						nrs.jday = 0;
						nrs.jmonth = 0;
						nrs.jyear = 0;
						nrs.jquant = 0;
						nrs.fday = ((RS)map4.get(key)).day;
						nrs.fmonth = ((RS)map4.get(key)).month;
						nrs.fyear = ((RS)map4.get(key)).year;
						nrs.fquant = ((RS)map4.get(key)).quant;
						nrs.mday = 0;
						nrs.mmonth = 0;
						nrs.myear = 0;
						nrs.mquant = 0;
						map7.put(s, nrs);
					}else {
						((NewRS)map7.get(key)).fday =((RS)map4.get(key)).day;
						((NewRS)map7.get(key)).fmonth =((RS)map4.get(key)).month;
						((NewRS)map7.get(key)).fyear =((RS)map4.get(key)).year;
						((NewRS)map7.get(key)).fquant =((RS)map4.get(key)).quant;
					}
				}
				
				//read all the result from map5 and store it into the map7 in the finall formation
				Iterator iter5 = map5.keySet().iterator();
				while(iter5.hasNext()) {
					Object key = iter5.next();
					String s = (String)key;
					if(!map7.containsKey(s)) {
						NewRS nrs = new NewRS();
						nrs.cust = ((RS)map5.get(key)).cust;
						nrs.prod = ((RS)map5.get(key)).prod;
						nrs.jday = 0;
						nrs.jmonth = 0;
						nrs.jyear = 0;
						nrs.jquant = 0;
						nrs.fday = 0;
						nrs.fmonth = 0;
						nrs.fyear = 0;
						nrs.fquant = 0;
						nrs.mday = ((RS)map5.get(key)).day;
						nrs.mmonth = ((RS)map5.get(key)).month;
						nrs.myear = ((RS)map5.get(key)).year;
						nrs.mquant = ((RS)map5.get(key)).quant;
						map7.put(s, nrs);
					}else {
						((NewRS)map7.get(key)).mday =((RS)map5.get(key)).day;
						((NewRS)map7.get(key)).mmonth =((RS)map5.get(key)).month;
						((NewRS)map7.get(key)).myear =((RS)map5.get(key)).year;
						((NewRS)map7.get(key)).mquant =((RS)map5.get(key)).quant;
					}
				}
				
				//print all the result that the second form need in the standard formation. The function of map7 is similar with natural full outer join
				Iterator iter7 = map7.keySet().iterator();
				while(iter7.hasNext()) {
					Object key = iter7.next();
					System.out.print(left(((NewRS)map7.get(key)).cust));
					System.out.print(" ");
					System.out.print(proleft(((NewRS)map7.get(key)).prod));
					System.out.print(" ");
					if(((NewRS)map7.get(key)).jquant==0) {
						System.out.printf("%"+7+"s","null");
					}else {
						System.out.printf("%"+7+"s",((NewRS)map7.get(key)).jquant);
					}
					System.out.print(" ");
					if(((NewRS)map7.get(key)).jmonth==0) {
						System.out.printf("%"+10+"s","null");
					}else {
						System.out.printf(time(((NewRS)map7.get(key)).jmonth)+"/"+time(((NewRS)map7.get(key)).jday)+"/"+((NewRS)map7.get(key)).jyear);
					}
					System.out.print(" ");
					if(((NewRS)map7.get(key)).fquant==0) {
						System.out.printf("%"+7+"s","null");
					}else {
						System.out.printf("%"+7+"s",((NewRS)map7.get(key)).fquant);
					}
					System.out.print(" ");
					if(((NewRS)map7.get(key)).fmonth==0) {
						System.out.printf("%"+10+"s","null");
					}else {
						System.out.printf(time(((NewRS)map7.get(key)).fmonth)+"/"+time(((NewRS)map7.get(key)).fday)+"/"+((NewRS)map7.get(key)).fyear);
					}
					System.out.print(" ");
					if(((NewRS)map7.get(key)).mquant==0) {
						System.out.printf("%"+7+"s","null");
					}else {
						System.out.printf("%"+7+"s",((NewRS)map7.get(key)).mquant);
					}
					System.out.print(" ");
					if(((NewRS)map7.get(key)).mmonth==0) {
						System.out.printf("%"+10+"s","null");
					}else {
						System.out.printf(time(((NewRS)map7.get(key)).mmonth)+"/"+time(((NewRS)map7.get(key)).mday)+"/"+((NewRS)map7.get(key)).myear);
					}
					System.out.println();
				}

			}
			
			catch(SQLException e)
			{
				System.out.println("Connection URL or username or password errors!");
				e.printStackTrace();
			}

		}

	}
class RS{
	
	String cust;
	String prod;
	String state;
	int day;
	int month;
	int year;
	int quant;
	
	int sum;
	int fre;
	
} //format a class to store the temporary result.
class NewRS{
	String cust;
	String prod;
	
	int jday;
	int jmonth;
	int jyear;
	int jquant;
	
	int fday;
	int fmonth;
	int fyear;
	int fquant;
	
	int mday;
	int mmonth;
	int myear;
	int mquant;
}// format a class to combine the result together in second form.