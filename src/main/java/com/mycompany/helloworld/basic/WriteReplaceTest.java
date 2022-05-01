package com.mycompany.helloworld.basic;

import java.io.*;
import java.util.ArrayList;

public class WriteReplaceTest {
    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.buf"))) {
            // Person p = new Person("johann", 28);
            Person2 p = new Person2("johann", 28);
            oos.writeObject(p);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.buf"))) {
            // print(((ArrayList)ois.readObject()).toString());
            print(ois.readObject().toString());
        }
    }
}

class Person implements Serializable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }

    private Object writeReplace() throws ObjectStreamException {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(age);
        return list;
    }

    // 错！写了也没用
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ArrayList<Object> list = (ArrayList) in.readObject();
        this.name = (String) list.get(0);
        this.age = (int) list.get(1);
    }

    private Object readResolve() throws ObjectStreamException {
        // 直接替换成一个int的1返回
        return 1;
    }
}

class Person2 implements Externalizable {
    private String name;
    private int age;

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }

    public Person2() {
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(new StringBuffer(name).reverse());
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        this.age = in.readInt();
    }

}
