package cn.iinux.java.alpha.asm;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if ("sample.HelloWorld".equals(name)) {
            byte[] bytes = HelloWorldDump.dump();
            return defineClass(name, bytes, 0, bytes.length);
        }

        throw new ClassNotFoundException("Class Not Found: " + name);
    }
}
