package org.example;

/**
 * @version 1.0
 * @Author cxs
 * @Description
 * @date 2023/8/21
 **/
public class Wrapper {

    Class<?> aClass1;
    Class<?> aClass2;

    Object object1;
    Object object2;

    public Wrapper(Class<?> aClass1, Class<?> aClass2, Object object1, Object object2) {
        this.aClass1 = aClass1;
        this.aClass2 = aClass2;
        this.object1 = object1;
        this.object2 = object2;
    }
}
