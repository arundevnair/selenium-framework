package framepack.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    static Properties prop = new Properties();
    static InputStream input = null;
    static int counter=0;

    public static String getProperty(String s)  {
        String str ="";
        try {
            if(!prop.isEmpty()&&counter<=1){
                input = new FileInputStream("config_"+GetEnvironment.getEnv()+".properties");
            }else if(counter==0){
                input = new FileInputStream("config_default.properties");
            }
            counter++;
            if(counter<=2){
                prop.load(input);
            }
            str = prop.get(s).toString();

        } catch (IOException ex) {
            try {
                input = new FileInputStream("config_default.properties");
                prop.load(input);
                str = prop.get(s).toString();}
            catch(Exception e2)
            {e2.printStackTrace();}
        } finally {
            if (input != null) {
                try {
                    input.close();}
                catch (IOException e) {
                    e.printStackTrace();}
            }
        }
        return str;
    }


}
