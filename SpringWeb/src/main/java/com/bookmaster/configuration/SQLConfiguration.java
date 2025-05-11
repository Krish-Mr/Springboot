package com.bookmaster.configuration;

import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.common.utilities.ConfigReader;
import com.project.properties.ProjectPaths;

import jakarta.annotation.PostConstruct;

@Configuration
@Import(ConfigReader.class)
@ComponentScan(basePackages = {"com.bookmaster"})
public class SQLConfiguration {
	@Autowired
	public ConfigReader read;
	
	public static Properties prop;
	private static String serverName;
	private static String hostname;
	private static String defaultPort;
	private static String database;
	private static String userName;
	private static String pass;
	private static String connectionUrl;

	@PostConstruct
	private void init() {
		prop = read.getPropertie(ProjectPaths.mainResourcePath + "Configuration\\Properties\\sql_server_db.properties");
		serverName = prop.getProperty("sql.server.servername");//"DESKTOP-MDT8GGH\\MSSQL2019";
		hostname = prop.getProperty("sql.server.hostname");//"localhost";
		defaultPort = prop.getProperty("sql.server.defaultport");//"14333";
		database = prop.getProperty("sql.server.database");//"BookMaster";
		userName = prop.getProperty("sql.server.username");//"sa";
		pass = prop.getProperty("sql.server.pass");//"mssql2019";
		connectionUrl = "jdbc:sqlserver://"+ hostname + ":"+defaultPort+ " ;instance= " + serverName + ";databaseName="+database + ";encrypt=true; trustServerCertificate=true;";
	}
//		public static Properties prop = read.getPropertie(ProjectPaths.mainResourcePath + "Configuration\\Properties\\sql_server_db.properties");
//		private static String serverName = prop.getProperty("sql.server.servername");//"DESKTOP-MDT8GGH\\MSSQL2019";
//		private static String hostname = prop.getProperty("sql.server.hostname");//"localhost";
//		private static String defaultPort = prop.getProperty("sql.server.defaultport");//"14333";
//		private static String database = prop.getProperty("sql.server.database");//"BookMaster";
//		private static String userName = prop.getProperty("sql.server.username");//"sa";
//		private static String pass = prop.getProperty("sql.server.pass");//"mssql2019";
//		private static String connectionUrl = "jdbc:sqlserver://"+ hostname + ":"+defaultPort+ " ;instance= " + serverName + ";databaseName="+database + ";encrypt=true; trustServerCertificate=true;";
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(connectionUrl, userName, pass);
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return dataSource;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
