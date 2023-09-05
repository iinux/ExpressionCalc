package cn.iinux.java.alpha;

import io.javalin.Javalin;

public class JavaLin {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));
    }
}
