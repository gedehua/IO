import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PacketScan {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ss/a.properties");
        properties.load(resourceAsStream);
        System.out.println(properties.getProperty("user"));


    }
}
