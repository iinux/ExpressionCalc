package cn.iinux.java.alpha;

public class JNI {
    private native void hello();

    static{
        String filename = System.getProperty("user.dir") +"/jni/libAtest.so" ;
        System.out.println(filename);
        System.load(filename);
    }

    public static void main(String[] args){
        JNI test = new JNI();
        test.hello();
    }
}
