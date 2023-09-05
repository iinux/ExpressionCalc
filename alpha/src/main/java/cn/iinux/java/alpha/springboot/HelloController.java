package cn.iinux.java.alpha.springboot;

import cn.iinux.java.alpha.springboot.apiversion.ApiVersion;
import cn.iinux.java.alpha.basic.ZipUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@RestController
// @CrossOrigin // 用filter 实现也可以
public class HelloController implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MyFactoryBean myFactoryBean;

    @Autowired
    private HelloService helloService;

    @GetMapping("/allBeans")
    public String[] allBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        return beans;
    }

    @GetMapping("/newUser")
    public String newUser() throws Exception {
        helloService.asyncNewUser();
        return "success";
    }

    @ApiVersion("1.0")
    @GetMapping("/apiVersion")
    public String apiVersion() {
        return "api version 1";
    }

    @ApiVersion("2.0")
    @GetMapping("/apiVersion")
    public String apiVersion2() {
        return "api version 2";
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.getClass() + " CommandLineRunner");
        System.out.println(myFactoryBean);
    }

    // @Qualifier("haha")


    @Test
    public void test1() {
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Component.class));
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Test.class));
    }

    @Autowired
    private HttpServletRequest autowiredRequest;

    @GetMapping("/method1")
    public String method1(HttpServletRequest request) {
        System.out.println("Request URI: " + request.getRequestURI());
        return "Invoke HttpServletRequest by method param.";
    }

    @GetMapping("/method2")
    public String method2() {
        System.out.println("Request URI: " + autowiredRequest.getRequestURI());
        return "Invoke HttpServletRequest by @Autowired.";
    }

    @GetMapping("/method3")
    public String method3() {
        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        System.out.println("Request URI: " + request.getRequestURI());
        return "Invoke HttpServletRequest by ServletRequestAttributes.";
    }

    @GetMapping("/pako")
    public String pako() throws IOException {
        String input = "jsonString";
        String a;
        a = Base64.getEncoder().encodeToString(ZipUtil.compress(input.getBytes(StandardCharsets.UTF_8)));
        a = Base64.getEncoder().encodeToString(ZipUtil.compress(input));
        return a;
    }

    @RequestMapping(value = "/pako2")
    @ResponseBody
    public String pako2(@RequestParam String data) {
        byte[] clientBytes = toBytes(data);
        byte[] bytes = ZipUtil.decompress(clientBytes);
        System.out.println("data decompress = " + new String(bytes));
        byte[] encodingStr = ZipUtil.compress(bytes);
        return fromBytes(encodingStr);
    }

    /**
     * 整数逗号分隔字符串转字节数组
     * @param arrInt 形如： 123,2,09
     *               字符串是由无符号整数构成，逗号分隔
     */
    public static byte[] toBytes(String arrInt) {
        String[] a = arrInt.split(",");
        byte[] clientBytes = new byte[a.length];
        int i = 0;
        for (String e : a) {
            clientBytes[i++] = Integer.valueOf(e).byteValue();
        }
        return clientBytes;
    }

    /**
     * 字节数组转整数逗号分隔字符串
     * @param bytes 字节数组
     * @return String 整数逗号分隔字符串
     */
    public static String fromBytes(byte[] bytes) {
        String[] ints = new String[bytes.length];
        int j = 0;
        for (byte e : bytes) {
            int t = e;
            if (t < 0) {
                t = 256 + t;
            }
            ints[j++] = String.valueOf(t);

        }

        return String.join(",", ints);
    }
}
