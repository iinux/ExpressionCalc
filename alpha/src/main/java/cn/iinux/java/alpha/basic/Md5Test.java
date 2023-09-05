package cn.iinux.java.alpha.basic;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Md5Test {
    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        String md5Str = DigestUtils.md5DigestAsHex("a".getBytes());
        System.out.println(md5Str);

        String filePath = "pom.xml";
        byte[] allBytes;
        allBytes = FileUtils.readFileToByteArray(new File(filePath));
        System.out.println(DigestUtils.md5DigestAsHex(allBytes));

        allBytes = IOUtils.toByteArray(new FileInputStream(filePath));
        System.out.println(DigestUtils.md5DigestAsHex(allBytes));

        allBytes  = Files.toByteArray(new File(filePath));
        System.out.println(DigestUtils.md5DigestAsHex(allBytes));

        allBytes = ByteStreams.toByteArray(new FileInputStream(filePath));
        System.out.println(DigestUtils.md5DigestAsHex(allBytes));

        allBytes = StreamUtils.copyToByteArray(new FileInputStream(filePath));
        System.out.println(DigestUtils.md5DigestAsHex(allBytes));

        // IOUtils.readFully();
    }
}
