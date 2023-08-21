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
        // 在 c2 里访问 c1, c1 会重新加载 c0(c2 已经加载过 c0),从而触发
        // loader constraint violation: loader (instance of org/example/C1SFClassloader) previously initiated loading for a different type with name "org/example/C0"
        Method get = aClass.getMethod("get");

        Object invoke = get.invoke(o);
        System.out.println(invoke.getClass().getClassLoader());

    }
}