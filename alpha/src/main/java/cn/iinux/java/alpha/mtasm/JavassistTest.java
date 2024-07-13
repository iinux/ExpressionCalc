package cn.iinux.java.alpha.mtasm;

import javassist.*;
import org.junit.Test;

import java.io.IOException;

public class JavassistTest {
    @SuppressWarnings("all")
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        // this will error
        // CtClass cc = cp.get(Base.class.getName());
        CtClass cc = cp.get("cn.iinux.java.alpha.mtasm.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("javassist");
        Base h = (Base)c.newInstance();
        h.process();
    }

    @Test
    public void makeClass() throws Exception {
        // 创建类池
        ClassPool pool = ClassPool.getDefault();

        // 创建类
        CtClass cc = pool.makeClass("com.example.Person");

        // 添加私有字段
        CtField nameField = CtField.make("private String name;", cc);
        CtField ageField = CtField.make("private int age;", cc);
        cc.addField(nameField);
        cc.addField(ageField);

        // 添加构造函数
        CtConstructor constructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String"), CtClass.intType}, cc);
        constructor.setBody("{this.name = $1; this.age = $2;}");
        cc.addConstructor(constructor);

        // 添加 getter 和 setter 方法
        cc.addMethod(CtNewMethod.setter("setName", nameField));
        cc.addMethod(CtNewMethod.getter("getName", nameField));
        cc.addMethod(CtNewMethod.setter("setAge", ageField));
        cc.addMethod(CtNewMethod.getter("getAge", ageField));

        // 添加自定义方法
        CtMethod introduceMethod = CtNewMethod.make(
                "public String introduce() { return \"My name is \" + name + \" and I am \" + age + \" years old.\"; }",
                cc
        );
        cc.addMethod(introduceMethod);

        // 生成类
        Class<?> personClass = cc.toClass();

        // 使用反射创建对象并调用方法
        Object person = personClass.getConstructor(String.class, int.class).newInstance("Alice", 30);
        System.out.println(personClass.getMethod("introduce").invoke(person));
    }
}
