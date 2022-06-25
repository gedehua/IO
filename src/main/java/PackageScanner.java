import com.gedehua.Report;
import com.gedehua.t3.BaseT3;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScanner {
    public static void main(String[] args) throws IOException {
        packageScanner("com.gedehua");
    }

    public static void packageScanner(String path){
        String s = PackageScanner.class.getResource("/").getPath()+path.replace('.', '/');
        File dirs = new File(s.substring(1));
        if(dirs.exists()){
            dfs(dirs);
        }
    }

    public  static void dfs(File file){
        if(file == null) {
            return;
        }
        if(file.isFile()){
            if(file.getAbsolutePath().endsWith(".class")){
                dealClass(file);
            }
            return;
        }
        for(File childrenFile:file.listFiles()){
            dfs(childrenFile);
        }
    }

    public static void dealClass(File file){
        String root = PackageScanner.class.getResource("/").getPath().substring(1);
        int begin = root.length();
        int length = file.getAbsolutePath().length();
        String className = file.getAbsolutePath().substring(begin,length-6);
        className =  className.replace('\\','.');
        //System.out.println(className);
        try {
            Class<?> aClass = Class.forName(className);
            Report annotation = aClass.getAnnotation(Report.class);
            if(annotation!=null){
                Constructor<?> constructor = aClass.getConstructor();
                Object o = constructor.newInstance();
                Field[] fields = aClass.getFields();
                for(Field field:fields){
                    if("s".equals(field.getName())){
                        field.set(o,"gedehua1");
                        System.out.println(((BaseT3) o).s);
                    }
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
