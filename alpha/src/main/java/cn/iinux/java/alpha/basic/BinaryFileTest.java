package cn.iinux.java.alpha.basic;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Conversion;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BinaryFileTest {

    public static void main(String[] args) throws IOException {
        String shortName = FilenameUtils.getName("a/bbc中国");
        System.out.println(shortName);

        byte[] shortNameBytes = shortName.getBytes(StandardCharsets.UTF_8);

        byte[] shortNameLenBytes = new byte[4];
        Conversion.intToByteArray(shortNameBytes.length, 0, shortNameLenBytes, 0, 4);

        // 这种方式是针对大头端的
        // byte[] shortNameLenBytes = Ints.toByteArray(shortNameBytes.length);
        // 这个也是大头端的
        // shortNameLenBytes = toBytes(shortNameBytes.length);
        // 也是大头端
        // shortNameLenBytes = intToBytesD(shortNameBytes.length);
        // shortNameLenBytes = intToBytes(shortNameBytes.length);

        // 这种方法生成的只有1个字节，非想要的4个字节
        // shortNameLenBytes = BigInteger.valueOf(shortNameBytes.length).toByteArray();

        byte[] allBytes = mergeBytes(shortNameLenBytes, shortNameBytes);
        // allBytes = concat(shortNameLenBytes, shortNameBytes);
        // allBytes = ArrayUtils.addAll(shortNameLenBytes, shortNameBytes);

        int len = Conversion.byteArrayToInt(allBytes, 0, 0, 0, 4);

        byte[] contentBytes = new byte[len];
        System.arraycopy(allBytes, 4, contentBytes, 0, len);
        System.out.println(new String(contentBytes));
    }

    public static byte[] concat(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }

    private static byte[] mergeBytes(byte[] shortNameLenBytes, byte[] shortNameBytes) {
        byte[] allByteArray = new byte[shortNameLenBytes.length + shortNameBytes.length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(shortNameLenBytes);
        buff.put(shortNameBytes);

        byte[] allBytes = allByteArray;//buff.array();
        return allBytes;
    }

    public static byte[] toBytes(final int intVal, final int... intArray) {
        if (intArray == null || (intArray.length == 0)) {
            return ByteBuffer.allocate(4).putInt(intVal).array();
        } else {
            final ByteBuffer bb = ByteBuffer.allocate(4 + (intArray.length * 4)).putInt(intVal);
            for (final int val : intArray) {
                bb.putInt(val);
            }
            return bb.array();
        }
    }

    public static byte[] intToBytesD(int x) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bos);
        out.writeInt(x);
        out.close();
        byte[] int_bytes = bos.toByteArray();
        bos.close();
        return int_bytes;
    }

    public static byte[] intToBytes(int my_int) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeInt(my_int);
        out.close();
        byte[] int_bytes = bos.toByteArray();
        bos.close();
        return int_bytes;
    }

    public static int bytesToInt(byte[] int_bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(int_bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        int my_int = ois.readInt();
        ois.close();
        return my_int;
    }
}
