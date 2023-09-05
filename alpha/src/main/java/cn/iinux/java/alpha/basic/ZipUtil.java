package cn.iinux.java.alpha.basic;


import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.*;

public class ZipUtil {
    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        byte[] output;

        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!deflater.finished()) {
                int i = deflater.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        deflater.end();
        return output;
    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @param os   输出流
     */
    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);

        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output;

        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!inflater.finished()) {
                int i = inflater.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        inflater.end();
        return output;
    }

    /**
     * 解压缩
     *
     * @param is 输入流
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];

            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toByteArray();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "hello world";
        System.out.println("原文>>" + data);
        byte[] a = ZipUtil.compress(data.getBytes(StandardCharsets.UTF_8));
        System.out.println("压缩后>>" + Arrays.toString(a));
        byte[] b = ZipUtil.decompress(a);
        System.out.println("解压后>>" + new String(b));
    }

    @Test
    public void a() {
        try {
            String inputString = "hello world";
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            byte[] tmp = new byte[1024];

            Deflater deflater = new Deflater();
            deflater.setInput(input);
            deflater.finish();
            int deflateLen = deflater.deflate(tmp);
            deflater.end();

            byte[] output1 = new byte[deflateLen];
            System.arraycopy(tmp, 0 , output1, 0, deflateLen);

            String str = Base64.getEncoder().encodeToString(output1);
            System.out.println("Deflated String:" + str);

            byte[] output2 = Base64.getDecoder().decode(str);

            Inflater inflater = new Inflater();
            inflater.setInput(output2);
            int inflateLen = inflater.inflate(tmp);
            inflater.end();

            String outputString = new String(tmp, 0, inflateLen, StandardCharsets.UTF_8);
            System.out.println("Deflated String:" + outputString);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException 异常
     */
    public static byte[] compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toByteArray();
    }

    /**
     * 字符串的解压
     *
     * @param b 对字符串解压
     * @return 返回解压缩后的字符串
     */
    public static String unCompress(byte[] b) {
        try {
            if (null == b || b.length <= 0) {
                return null;
            }
            // 创建一个新的 byte 数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
            ByteArrayInputStream in;
            in = new ByteArrayInputStream(b);

            // 使用默认缓冲区大小创建新的输入流
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;
            while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}