package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @version 1.0
 * @Author cxs
 * @Description
 * @date 2023/8/21
 **/
public class MyClassLoader extends URLClassLoader {

    public String jarPath;

    private String name;

    public MyClassLoader(URL[] urls, ClassLoader parent, String name) {
        super(urls, parent);
        this.name = name;
    }

    public static byte[] getClassBytesFromJar(String jarPath, String className) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            String classEntryName = className.replace('.', '/') + ".class";
            JarEntry jarEntry = jarFile.getJarEntry(classEntryName);

            if (jarEntry == null) {
                return null;  // Class not found in the JAR
            }

            try (InputStream is = jarFile.getInputStream(jarEntry)) {
                byte[] classBytes = new byte[(int) jarEntry.getSize()];
                int bytesRead = 0;
                int offset = 0;

                while (offset < classBytes.length) {
                    bytesRead = is.read(classBytes, offset, classBytes.length - offset);
                    if (bytesRead == -1) {
                        break;
                    }
                    offset += bytesRead;
                }

                if (offset != classBytes.length) {
                    throw new IOException("Failed to read the entire class bytes: " + classEntryName);
                }
                return classBytes;
            }
        }
    }


    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.jarPath = urls[0].getPath();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (clazz != null) {
            return clazz;
        }
        try {
            System.out.println(this.name + name + " start, ");
            Class<?> aClass = findClass(name);
            System.err.println(this.name + name + " end, " + aClass.getClassLoader());
            return aClass;
        } catch (Exception e) {
            System.err.println(name);
            return getParent().loadClass(name);
        }
    }
}

class ParentClassLoader extends MyClassLoader {

    public ParentClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent, "parent ");
    }
}

class SonClassLoader extends MyClassLoader {

    public SonClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent, "son ");
    }
}