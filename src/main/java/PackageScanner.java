import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScanner {
    public static void main(String[] args) throws IOException {
        System.out.println(PackageScanner.class.getResource("/").getPath());
        packageScanner("com.gedehua");
    }

    public static void packageScanner(String path){
        String s = PackageScanner.class.getResource("/").getPath()+path.replace('.', '/');
        System.out.println(s);
        File file = new File(s.substring(1));
        System.out.println(file.exists());
        dfs(file);
    }

    public  static void dfs(File file){
        if(file == null) {
            return;
        }
        if(file.isFile()){
            System.out.println(file.getAbsolutePath());
            return;
        }
        for(File childrenFile:file.listFiles()){
            dfs(childrenFile);
        }
    }
    private void dealJarPackage(URL url) {
        try {
            JarURLConnection connection = (JarURLConnection) url.openConnection();
            JarFile jarFile = connection.getJarFile();
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jar = jarEntries.nextElement();
                if(jar.isDirectory() || !jar.getName().endsWith(".class")) {
                    continue;
                }
                String jarName = jar.getName();
                jarName = jarName.replace(".class", "");
                jarName = jarName.replace("/", ".");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
