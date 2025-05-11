package draft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.common.utilities.ConfigReader;
import com.project.properties.ProjectPaths;

public class TestConfigReader {
public static void main(String[] args) throws FileNotFoundException, IOException {
	String fileName = "demotest.properties";
	ConfigReader read = new ConfigReader();
	String filePath = "src\\main\\resources\\" + "Configuration\\Properties\\" + fileName;
	Properties propertie = read.getPropertie(filePath);
	System.out.println(propertie.get("key.test1"));
}
}
