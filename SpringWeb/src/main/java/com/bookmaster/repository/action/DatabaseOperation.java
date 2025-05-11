package com.bookmaster.repository.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseOperation {
	@Autowired
	JdbcTemplate jdbcTemp;

	/**
	 * @param tableName - name of the table
	 * @param colName - COLUMN_NAME, ORDINAL_POSITION, IS_NULLABLE, DATA_TYPE, etc..,
	 * @return List of values
	 */
	public List<String> getInformationSchemas(String tableName, String colName){
		String infoQuery = "Select * From INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = ?";
		
		ArrayList<String> colResults = new ArrayList<String>();
/**   	#WAY - 1
 * This will return only one row
 		jdbcTemp.queryForObject(infoQuery, new Object[] {tableName}, (rs, row)->{
			System.out.println(row + ") " + rs.getString("COLUMN_NAME"));
			colNames.add(rs.getString("COLUMN_NAME"));
			return colNames;
		});		
 */

		/**
		 * This will return multiple result
		 */
		jdbcTemp.query(infoQuery, new Object[] {tableName}, (rs, row)->{
			//System.out.println(row + ") " + rs.getString("COLUMN_NAME"));
			colResults.add(rs.getString(colName));
			return colResults;
		});
		System.out.println("INFORMATION SCHEMA for : "+colName+" Result: " +  colResults);

//		#WAY : 2
//		try ( PreparedStatement pst = con.prepareStatement(infoQuery) ){
//			pst.setString(1, tableName);
//			ResultSet rs = pst.executeQuery();
//			while (rs.next()) {
//				colNames.add(rs.getString("COLUMN_NAME"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return colResults;
	}
}
