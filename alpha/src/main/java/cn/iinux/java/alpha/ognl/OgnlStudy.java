package cn.iinux.java.alpha.ognl;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Setter;
import ognl.*;
import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
class BDemo extends ADemo {
    private String address;
}

@Data
class PrintDemo {
    private String prefix;
    private ADemo aDemo;

    public void sayHello(String name, int age) {
        System.out.println("name: " + name + " age: " + age);
    }

    private void print(ADemo a) {
        System.out.println(prefix + " => " + a);
    }

    public <T> T print(String str, Class<T> clz) {
        T obj = JSON.parseObject(str, clz);
        System.out.println("class: " + obj);
        return obj;
    }

    public void print(String str, String clz) {
        System.out.println("str2a: " + str + " clz: " + clz);
    }

    public void print(String str, OgnlEnum ognlEnum) {
        System.out.println("enum: " + str + ":" + ognlEnum);
    }

    public void print(String str, ADemo a) {
        System.out.println("obj: " + str + ":" + a);
    }

    public void show(Class clz) {
        System.out.println(clz.getName());
    }

    public void print(List<Integer> args) {
        System.out.println(args);
    }

    public void print(Map<String, Integer> args) {
        System.out.println(args);
    }
}

class StaticDemo {
    private static int num = (int) (Math.random() * 100);

    public static int showDemo(int a) {
        System.out.println("static show demo: " + a);
        return a;
    }
}

enum OgnlEnum {
    CONSOLE, FILE;
}

@Setter
class DefaultMemberAccess implements MemberAccess {
    private boolean allowPrivateAccess = false;
    private boolean allowProtectedAccess = false;
    private boolean allowPackageProtectedAccess = false;

    public DefaultMemberAccess(boolean allowAllAccess) {
        this(allowAllAccess, allowAllAccess, allowAllAccess);
    }

    public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess,
                               boolean allowPackageProtectedAccess) {
        super();
        this.allowPrivateAccess = allowPrivateAccess;
        this.allowProtectedAccess = allowProtectedAccess;
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }

    @Override
    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;

        if (isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject) member;

            if (!accessible.isAccessible()) {
                result = Boolean.TRUE;
                accessible.setAccessible(true);
            }
        }
        return result;
    }

    @Override
    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
        if (state != null) {
            ((AccessibleObject) member).setAccessible((Boolean) state);
        }
    }

    /**
     * Returns true if the given member is accessible or can be made accessible by this object.
     */
    @Override
    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        int modifiers = member.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return true;
        } else if (Modifier.isPrivate(modifiers)) {
            return this.allowPrivateAccess;
        } else if (Modifier.isProtected(modifiers)) {
            return this.allowProtectedAccess;
        } else {
            return this.allowPackageProtectedAccess;
        }
    }
}

public class OgnlStudy {
    @Test
    public void test1() throws OgnlException {
        ADemo a = new ADemo();
        a.setName("yi hui");
        a.setAge(10);

        BDemo b = new BDemo();
        b.setName("b name");
        b.setAge(20);
        b.setAddress("测试ing");

        PrintDemo print = new PrintDemo();
        print.setPrefix("ognl");
        print.setADemo(a);

        // 构建一个OgnlContext对象
        // 扩展，支持传入class类型的参数
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this,
                new DefaultMemberAccess(true), new DefaultClassResolver(), new DefaultTypeConverter());
        context.setRoot(print);
        context.put("print", print);
        context.put("a", a);
        context.put("b", b);

        // ognl表达式执行
        Object expression = Ognl.parseExpression("#a.name");
        Object result = Ognl.getValue(expression, context, context.getRoot());
        System.out.println(result);

        Object ans = Ognl.getValue(Ognl.parseExpression("#print.sayHello(\"一灰灰blog\", 18)"),
                context, context.getRoot());
        System.out.println("实例方法执行： " + ans);

        ans = Ognl.getValue(Ognl.parseExpression("#a.name=\"一灰灰Blog\""), context, context.getRoot());
        System.out.println("实例属性设置： " + ans);

        ans = Ognl.getValue(Ognl.parseExpression("#a.name"), context, context.getRoot());
        System.out.println("实例属性访问： " + ans);

        ans = Ognl.getValue(Ognl.parseExpression("#b.name"), context, context.getRoot());
        System.out.println("实例父类属性访问：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression("@cn.iinux.java.alpha.ognl.StaticDemo@showDemo(20)"), context,
                context.getRoot());
        System.out.println("静态类方法执行：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression("@cn.iinux.java.alpha.ognl.StaticDemo@num"), context,
                context.getRoot());
        System.out.println("静态类成员访问：" + ans);

        //ans = Ognl.getValue(Ognl.parseExpression("@cn.iinux.java.alpha.ognl.StaticDemo@num=1314"), context,
        //        context.getRoot());
        ans = Ognl.getValue(Ognl.parseExpression("#field=@cn.iinux.java.alpha.ognl.StaticDemo@class.getDeclaredField(\"num\"), #field.setAccessible(true), #field.set(null,1234)"),
                context, context.getRoot());
        System.out.println("静态类成员设置：" + ans);

        System.out.println(context.get("field"));

        ans = Ognl.getValue(Ognl.parseExpression("@cn.iinux.java.alpha.ognl.StaticDemo@num"), context,
                context.getRoot());
        System.out.println("静态类成员访问：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression(
                "#print.print(\"{'name':'xx', 'age': 20}\", @cn.iinux.java.alpha.ognl.ADemo@class)"), context,
                context.getRoot());
        System.out.println("class 参数方法执行：" + ans);

        // class传参
        ans = Ognl.getValue(Ognl.parseExpression("#print.print(\"{'name':'haha', 'age': 10}\", #a.getClass())"),
                context, context.getRoot());
        System.out.println("class 参数方法执行：" + ans);

        ans = Ognl.getValue(
                Ognl.parseExpression("#print.print(\"print enum\", @cn.iinux.java.alpha.ognl.OgnlEnum@CONSOLE)"),
                context, context.getRoot());
        System.out.println("枚举参数方法执行：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression("#print.print(null)"), context, context.getRoot());
        System.out.println("null 传参：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression("#print.print(null, null)"), context, context.getRoot());
        System.out.println("null 传参：" + ans);

        expression = Ognl.parseExpression("#print.print(\"对象构建\", new cn.iinux.java.alpha.ognl.ADemo(\"test\", 20))");
        ans = Ognl.getValue(expression, context, context.getRoot());
        System.out.println("对象传参：" + ans);

        // 这里有个坑，我们创建的这个属性会丢到OgnlContext上下文中，所以这种操作非常有可能导致我们自己创建的临时对象覆盖了原有的对象
        expression = Ognl.parseExpression("#print.print(\"对象构建\", (#demo=new cn.iinux.java.alpha.ognl.ADemo(), #demo.setName(\"一灰灰\"), #demo))");
        ans = Ognl.getValue(expression, context, context.getRoot());
        System.out.println("对象传参：" + ans);

        expression = Ognl.parseExpression("#print.print({1, 3, 5})");
        ans = Ognl.getValue(expression, context, context.getRoot());
        System.out.println("List传参：" + ans);

        expression = Ognl.parseExpression("#print.print(#{\"A\": 1, \"b\": 3, \"c\": 5})");
        ans = Ognl.getValue(expression, context, context.getRoot());
        System.out.println("Map传参：" + ans);

        ans = Ognl.getValue(Ognl.parseExpression("1 + 3 + 4"), context, context.getRoot());
        System.out.println("表达式执行: " + ans);

        // 阶乘
        ans = Ognl.getValue(Ognl.parseExpression("#fact = :[#this<=1? 1 : #this*#fact(#this-1)], #fact(3)"),
                context, context.getRoot());
        System.out.println("lambda执行: " + ans);

        for (String s : Arrays.asList(
                "new int[] {1,2,3}",
                "\"name\".toCharArray()[0]",
                "\"name\".length() % 2 == 0 ? \"偶数长度\" : \"奇数长度\"",
                "\"name\" in {\"name\", \"hello\"}",
                // 遍历集合，获取所有的偶数
                "{1,2,3,4,5,6}.{? #this % 2 == 0}",
                // 遍历集合，获取第一个满足条件的元素
                "{1,2,3,4,5,6}.{^ #this % 2 == 0}",
                // 遍历集合，获取最后一个满足条件的元素
                "{1,2,3,4,5,6}.{$ #this % 2 == 0}",
                "#value1=@System@getProperty(\"java.home\"), #value2=@System@getProperty(\"java.runtime.name\"), {#value1, #value2}"
        )) {
            ans = Ognl.getValue(Ognl.parseExpression(s),
                    context, context.getRoot());
            System.out.println(ans);
        }
    }
}
