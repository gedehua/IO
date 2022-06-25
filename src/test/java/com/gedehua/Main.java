package com.gedehua;


import java.io.Closeable;
import java.lang.reflect.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Throwable {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(proxy);
                System.out.println(method);
                if (method.getName().equals("morning")) {
                    System.out.println("Good morning, " + args[0]);
                }
                return null;
            }
        };
        Hello hello = new HelloDynamicProxy(handler);
        hello.morning("gedehua");
    }
}
class HelloDynamicProxy implements Hello {
    InvocationHandler handler;
    public HelloDynamicProxy(InvocationHandler handler) {
        this.handler = handler;
    }
    public void morning(String name) throws Throwable {
        handler.invoke(
                this,
                Hello.class.getMethod("morning", String.class),
                new Object[] { name });
    }
}
interface Hello {
    void morning(String name) throws Throwable;
}
