package draft;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.bookmaster.db.SQLConnection;
import com.bookmaster.repository.action.DatabaseOperation;
import com.bookmaster.repository.model.BookDetails;
import com.common.utilities.ConfigReader;
import com.project.properties.ProjectPaths;
/**
 *  Dynamic Query formation using JDBC connection
 */
public class SqlQueryDemo {
	SqlQueryDemo(){}
	private static SqlQueryDemo obj = new SqlQueryDemo();
	public static ConfigReader read = new ConfigReader();
	public static Properties prop = read.getPropertie(ProjectPaths.mainResourcePath + "Configuration\\Properties\\sql_server_db.properties");
	
	private static String serverName = prop.getProperty("sql.server.servername");//"DESKTOP-MDT8GGH\\MSSQL2019";
	private static String hostname = prop.getProperty("sql.server.hostname");//"localhost";
	private static String defaultPort = prop.getProperty("sql.server.defaultport");//"14333";
	private static String database = prop.getProperty("sql.server.database");//"BookMaster";
	private static String userName = prop.getProperty("sql.server.username");//"sa";
	private static String pass = prop.getProperty("sql.server.pass");//"mssql2019";
	private static String connectionUrl = "jdbc:sqlserver://"+ hostname + ":"+defaultPort+ " ;instance= " + serverName + ";databaseName="+database + ";encrypt=true; trustServerCertificate=true;";

	private static JdbcTemplate jdbcTemp = new JdbcTemplate(new DriverManagerDataSource(connectionUrl, userName, pass));
	private static DatabaseOperation databaseOp = new DatabaseOperation();
	
	
	public static void main(String[] args) {

		try (Connection dbCon = SQLConnection.getDBConection(connectionUrl, userName, pass)){
			if(dbCon==null)
				return;
			String tableName = "BookDetails";
			List<String> colConstr  = Arrays.asList("Id INT PRIMARY KEY", "Title VARCHAR(100)", "Author VARCHAR(100)", "Release_Date Date", "Prize NUMERIC(3,2)");
			//obj.createTable(dbCon, tableName, colConstr);
//			obj.createBookDetailsRecord(dbCon);
//			obj.getBookRecords(dbCon, tableName);
//			databaseOp.getInformationSchemas(tableName, "COLUMN_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getBookRecords(Connection con, String tableName) {
		System.out.println("Getting Book Records");
		String query = "SELECT * FROM "+tableName;
		try(Statement stmt = con.createStatement()){
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				BookDetails bookPojo = new BookDetails();
				bookPojo.setId(res.getInt(res.findColumn("id")));
				bookPojo.setTitle(res.getString(res.findColumn("Title")));
				bookPojo.setAuthor(res.getString("Author"));
				bookPojo.setRelease_date(res.getDate(res.findColumn("Release_date")));
				bookPojo.setPrize(res.getDouble("Prize"));
				System.out.println(bookPojo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void createTable(Connection con, String tableName, List<String> colNameWithTypes) {
		String query = "Create Table "+ tableName + "("+String.join(", ",colNameWithTypes)+");";
		try (Statement stmt = con.createStatement()){
			stmt.executeUpdate(query);
			System.out.println( "Table Creation");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createBookDetailsRecord(Connection con) {
		String query = "INSERT INTO BookDetails (Id, Title, Author, Release_Date, Prize ) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setInt(1, 1001);
			stmt.setString(2, "Money Makes Everything");
			stmt.setString(3, "Robert");
			stmt.setDate(4, Date.valueOf("2011-05-20"));
			stmt.setDouble(5, 190.96);
			int executeStatus = stmt.executeUpdate();
			System.out.println("Records inserted - " + (executeStatus==0?false:true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

