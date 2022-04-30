package com.mycompany.helloworld.springboot;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ENCTest {
    @Autowired
    StringEncryptor stringEncryptor;

    // will error see https://stackoverflow.com/questions/47487609/unable-to-find-a-springbootconfiguration-you-need-to-use-contextconfiguration

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("password");
        System.out.println("==================");
        System.out.println(result);//密文
        System.out.println("==================");
    }
}
