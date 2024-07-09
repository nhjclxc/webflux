package com.nhjclxc.webflux.config;

/**
 * @author LuoXianchao
 * @since 2024/07/08 22:30
 */
@FunctionalInterface
public interface FunctionInterface {
    void doSomeThing(int index);
}

class Test {

    public static void main(String[] args) {
        sayHello(new FunctionInterface() {
            @Override
            public void doSomeThing(int index) {
                System.out.println("原始编码方式" + index);
            }
        });


        sayHello((index) -> System.out.println("Lambda编码方式" + index));
    }

    public static void sayHello(FunctionInterface f) {
        f.doSomeThing(5);
    }
}
