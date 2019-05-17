package outer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DB extends AbstractGetOrdDate {

	private HashMap<Integer,String>fieldMap=new HashMap<Integer, String>(); 
	private ArrayList<ArrayList<String>>ordDate;
	

	public DB() {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>>date=new ArrayList<ArrayList<String>>();
		ArrayList<String>temp=new ArrayList<String>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=Date_mining";
			Connection con=DriverManager.getConnection(url, "sa", "LZ1998723");
			String sql="select * from ord_date";
			PreparedStatement preparedStatement=con.prepareStatement(sql);
			ResultSetMetaData metaData=preparedStatement.getMetaData();
			ResultSet rs=preparedStatement.executeQuery();
			for(int i=0;i<metaData.getColumnCount();i++) {
				fieldMap.put(i, metaData.getColumnName(i+1));
			}
//			System.out.println(fieldMap);
			while(rs.next()) {
				temp=new ArrayList<String>();
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
//					String string=rs.getString(i);
					temp.add(rs.getString(i));
//					System.out.println(rs.getString(i));
				}
//				System.out.println(temp);
				date.add(temp);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ordDate=date;
	}

	@Override
	public HashMap<Integer, String> getFieldMap() {
		return fieldMap;
	}

	@Override
	public ArrayList<ArrayList<String>> getOrdDate() {
		return ordDate;
	}

}
