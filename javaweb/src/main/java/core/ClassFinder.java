package core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public final class ClassFinder {

    private final static char DOT = '.';
    private final static char SLASH = '/';
    private final static String CLASS_SUFFIX = ".class";
    private final static String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the given '%s' package exists?";

    public final static List<Class<?>> find(final String scannedPackage) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String scannedPath = scannedPackage.replace(DOT, SLASH);
        final Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(scannedPath);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage), e);
        }
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        while (resources.hasMoreElements())
        {
            final File file = new File(resources.nextElement().getFile());
            classes.addAll(find(file, scannedPackage, ""));
        }
        return classes;
    }

    private final static List<Class<?>> find(final File file,  final String scannedPackage, String folder) {
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        String resource = scannedPackage.equals("") ? file.getName() : (scannedPackage + DOT) + file.getName();
        
        
        //폴더 or 파일
        if (file.isDirectory()) {
        	
        	//폴더 == 패키지 --> 패키지 이름을 나열 한다.
        	if(folder.equals(""))
            	folder = file.getName();
        	else
        		folder += "." + file.getName();
        
        	//폴더 안에 있는 파일(폴더 포함) 다시 재귀를 해서 찾는다.
            for (File nestedFile : file.listFiles()) {
                classes.addAll(find(nestedFile, scannedPackage, folder));
            }
            
        } else if (resource.endsWith(CLASS_SUFFIX)) {
        	//확장자가 .class인지 확인  .class이면 이 파일은 class파일이다.
        	
            final int beginIndex = 0;
            final int endIndex = resource.length() - CLASS_SUFFIX.length();
            final String packageName = folder.replace("classes.", "");
            //classes폴더가 포함되어있으니 classes폴더를 먼저 제거한다.
            
            String className = resource.substring(beginIndex, endIndex);
            //className 에서 ???.class => ??? .class 접미사를 제거.
            
            className = packageName.equals("") ? className : packageName + "." + className;
            //className 풀네임으로 만들어준다.
            
            try {
            	//className 풀네임으로 클래스를 찾는다.
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

}


/*
 [펌] http://stackoverflow.com/questions/15519626/how-to-get-all-classes-names-in-a-package
*/