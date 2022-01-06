import java.sql.*;

public class SQL {
	private Connection conn;
	private Statement stmt;

	// ------------------- CONSTRUCTOR -----------------
	public SQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			conn = DriverManager.getConnection(url, "root", "root");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getStackTrace());
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
	}

	// -------------- CREATE TABLES METHOD -----------------
	public void createTables(String firstTable,String createFirstTable) {
		try{
			// if table already exists, drop it
			// if table already exists, SQL can't override it
			stmt.executeUpdate("DROP TABLE IF EXISTS " + firstTable);
			// create new empty table with given name
			//String createFirstTable = "CREATE TABLE " + firstTable +"(ID int, Date Datetime, Correct Answers int, Final Grade float, )";
			stmt.executeUpdate(createFirstTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------- INSERT INTO TABLE METHOD -----------------
	public void insertIntoTable(String tableName, String insertDetails) throws SQLException{
		//String insertDetails = "INSERT INTO " + tableName + "(ID, Date, CorrectAnswers, FinalGrade, IsOutstanding) VALUES('" + o.getSerialNum() + "','" + o.getSize() + "')";
		stmt.executeUpdate(insertDetails);
	}
	
	// -------------- EXTRACT FROM TABLE METHOD -----------------
	public String extractFromTable(String tableName, int rowNum, String colName){
		ResultSet result = null;
		String returnValue = "";
		String extractDetails = "SELECT * FROM " + tableName;
		try{
			result = stmt.executeQuery(extractDetails);
			for(int i = 1; result.next(); i++){
				if(i == rowNum){
					returnValue = result.getString(colName);
					break;
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getStackTrace());
		}
		return returnValue;
	}
}

