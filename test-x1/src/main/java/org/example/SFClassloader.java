package org.example;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @version 1.0
 * @Author cxs
 * @Description
 * @date 2023/8/21
 **/
public class SFClassloader extends URLClassLoader {
    String name;
    public SFClassloader(URL[] urls, ClassLoader parent, String name) {
        super(urls, parent);
        this.name = name;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }
        try {
            Thread.sleep(10);
            System.err.println(this.name + " " + name);
            return findClass(name);
        } catch (Exception e) {
            System.out.println(this.name + " " + name);
            return getParent().loadClass(name);
        }
    }
}

class C1SFClassloader extends SFClassloader {
    String name;

    public C1SFClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent, "c1");
    }
}

class C2SFClassloader extends SFClassloader {
    String name;

    public C2SFClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent, "c2");
        this.name = "c2";
    }
}
