package org.example;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        URL parent = new File("parent-plugins/hello-parent-module-1.0-SNAPSHOT.jar").toURL();
        MyClassLoader parentClassLoader = new ParentClassLoader(new URL[]{parent}, Main.class.getClassLoader());

        URL son = new File("son-plugins/hello-son-module-1.0-SNAPSHOT.jar").toURL();
        MyClassLoader sonClassLoader = new SonClassLoader(new URL[]{son}, parentClassLoader);

        Class<?> helloImplSon = sonClassLoader.loadClass("org.example.HelloImplSon");
        Method helloM = helloImplSon.getMethod("hello");
        Thread.sleep(10);
        helloM.invoke(helloImplSon.newInstance());
        Thread.sleep(10);

    }


}