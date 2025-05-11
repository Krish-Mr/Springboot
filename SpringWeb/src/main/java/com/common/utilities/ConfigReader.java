package com.common.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Configuration
public class ConfigReader {
	/**
	 * 
	 * @param filePath - full name with extension . D:\\filename.ext
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Bean
	public Properties getPropertie(String filePath){
		Properties prop  = new Properties();
		try( FileInputStream fips = new FileInputStream(filePath)){
			prop.load(fips);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
