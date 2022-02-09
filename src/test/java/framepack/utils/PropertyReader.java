package framepack.utils;


import reports.ReportTrail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyReader {

    static Properties prop = new Properties();
    static InputStream input = null;

    public static String getProperty(String propertyName) {
        String propertyValue = "";
        try {
            String envFound = GetEnvironment.getEnv();
            ReportTrail.info("Reading the property " + propertyName + " for env " + envFound);
            input = new FileInputStream("config_" + envFound + ".properties");
            prop.load(input);
            propertyValue = prop.get(propertyName).toString();
        } catch (IOException ex) {
            try {
                ReportTrail.info("Encountered error while trying to read the property. Hence reading it from default property file");
                input = new FileInputStream("config_default.properties");
                prop.load(input);
                propertyValue = prop.get(propertyName).toString();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ReportTrail.info("Property value found as " + propertyValue );
        return propertyValue;
    }


}
