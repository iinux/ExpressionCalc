package com.mycompany.helloworld.mtasm;

import javassist.*;

import java.io.IOException;

public class JavassistTest {
    @SuppressWarnings("all")
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        // this will error
        // CtClass cc = cp.get(Base.class.getName());
        CtClass cc = cp.get("com.mycompany.helloworld.mtasm.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("javassist");
        Base h = (Base)c.newInstance();
        h.process();
    }
}
