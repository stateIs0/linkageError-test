package org.example;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {
        URL u = new File("c1/").toURI().toURL();
        C1SFClassloader c1SFClassloader = new C1SFClassloader(new URL[]{u}, Thread.currentThread().getContextClassLoader());

        URL u2 = new File("c2/").toURI().toURL();
        C2SFClassloader c2SFClassloader = new C2SFClassloader(new URL[]{u2}, c1SFClassloader);
        Class<?> aClass = c2SFClassloader.loadClass("org.example.C2");
        Object o = aClass.newInstance();
        Method get = aClass.getMethod("get");

        Object invoke = get.invoke(o);
        System.out.println(invoke.getClass().getClassLoader());

    }
}