package cn.iinux.java.alpha.basic;

import java.util.Arrays;

public class ByteArr {
    public static void main(String[] args){
        Byte[] a = {(byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00};
        Byte[] b = {(byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00};
        byte[] aa = {(byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00};
        byte[] bb = {(byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00};

        System.out.println(a);
        System.out.println(b);
        System.out.println(a == b);
        System.out.println(a.equals(b));

        System.out.println(aa);
        System.out.println(bb);
        System.out.println(aa == bb);
        System.out.println(aa.equals(bb));

        System.out.println(Arrays.equals(aa, bb));
    }
}
