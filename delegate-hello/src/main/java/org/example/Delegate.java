package org.example;

public class Delegate {

    public static HelloInterface get() {
        return new HelloInterface() {
            @Override
            public HelloModel hello() {
                return new HelloModel();
            }

            @Override
            public void hi(HelloModel model) {
                System.out.println("Delegate");
            }
        };
    }
}