package com.mycompany.helloworld.basic;

public class TryClose implements AutoCloseable {
    public static void main(String[] args) {
        try (TryClose tryClose = newIns()) {
            System.out.println("in try");
        } catch (Exception e) {
            System.out.println("in catch");
            e.printStackTrace();
        }

    }

    public static TryClose newIns() throws Exception {
        System.out.println("new ins");
        // throw new Exception("error");
        return new TryClose();
    }

    @Override
    public void close() throws Exception {
        System.out.println("close");
    }
}
