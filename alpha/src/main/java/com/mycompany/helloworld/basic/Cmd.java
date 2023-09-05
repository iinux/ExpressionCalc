package com.mycompany.helloworld.basic;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mycompany.helloworld.sensitive.User;
import groovy.lang.Tuple2;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

import java.io.*;
import java.lang.management.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
public class Cmd implements Serializable {
    public static String[] args;

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cmd.args = args;

        String action = "input";
        Objects.requireNonNull(action, "error");
        if (args.length >= 2) {
            action = args[1];
        }

        Cmd cmd = new Cmd();
        Method m = cmd.getClass().getDeclaredMethod(action);
        m.invoke(cmd);

        try {
            return;
        } finally {
            System.out.println("end");
        }
    }


    @Test
    public void abnormalEqual() throws NoSuchFieldException, IllegalAccessException {
        Class<?> cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        // array[129] is 1
        array[130] = array[129];
        // Set 2 to be 1
        array[131] = array[129];
        // Set 3 to be 1
        Integer a = 1;
        if (a == (Integer) 1 && a == (Integer) 2 && a == (Integer) 3) {
            System.out.println("Success");
        }
    }

    @Test
    public void cast() {
        System.out.println((Map<String, String>) null);
    }

    @Test
    public void reg() {
        String s = "{hello}{hi}";
        String pStr = "\\{([\\w.]*)\\}";
        Pattern pattern = Pattern.compile(pStr);
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            System.out.println(m.group(0) + " => " + m.group(1));
            s = s.replace(m.group(0), m.group(1));
        }
        System.out.println(s);
    }

    @Test
    public void andSoOn() {
        andSoOnSub(1, 2, 3);
    }

    protected void andSoOnSub(int... values) {
        System.out.println(values.getClass().getName());
        System.out.println(values);
    }

    @Test
    public void instanceofTest() {
        String x = null;
        if (x instanceof String) {
            System.out.println("instanceof true");
        } else {
            System.out.println("instanceof false");
        }
    }

    @Test
    public void runSh() throws IOException, InterruptedException {
        Runtime ec = Runtime.getRuntime();
        File file = new File("input/hello.sh");
        Process process = ec.exec(file.getAbsolutePath(), null, file.getParentFile());
        System.out.println(IOUtils.toString(process.getInputStream(), "UTF8"));
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
        int waitForRet = process.waitFor();
        System.out.println("exec return " + waitForRet);
    }

    @Test
    public void list() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        // will error
        // list.remove(4);

        System.out.println(Collections.min(Arrays.asList(11, 2)));

        log.error("a={}", list);


        List<String> list2 = Arrays.asList("1", "2", "3");
        list2 = list2.stream().limit(2).collect(Collectors.toList());
        System.out.println(list2.size());
    }

    @Test
    public void log() {

        Exception e = new Exception("i am error msg");
        log.info("exception:{}", e.getMessage(), e);
    }

    @Test
    public void springVersion() {
        System.out.println(SpringVersion.getVersion());
        System.out.println(SpringBootVersion.getVersion());
    }

    public enum Strategy {
        FAST {
            @Override
            void run() {
                System.out.println("i am fast");
            }
        },
        NORMAL {
            @Override
            void run() {
                System.out.println("i am normal");
            }
        };

        abstract void run();
    }

    @Test
    public void enumTest() {
        Strategy s = Strategy.valueOf("FAST");
        s.run();
        s = Strategy.NORMAL;
        s.run();
    }

    @Test
    public void concurrentTime() throws InterruptedException {
        int c1 = 2;
        switch (c1) {
            case 1:
                System.out.println(SystemClock.now());
                Thread.sleep(1);
                System.out.println(SystemClock.now());
                Thread.sleep(10);
                System.out.println(SystemClock.now());
                Thread.sleep(100);
                System.out.println(SystemClock.now());
                Thread.sleep(1000);
                System.out.println(SystemClock.now());
                break;
            case 2:
                System.out.println(System.currentTimeMillis());
                for (int i = 0; i < 10000; i++) {
                    System.currentTimeMillis();
                }
                System.out.println(System.currentTimeMillis());
                for (int i = 0; i < 10000; i++) {
                    SystemClock.now();
                }
                System.out.println(System.currentTimeMillis());
                break;
        }
    }

    @Test
    public void future() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        Future<Integer> f = es.submit(() -> {
            // 长时间的异步计算
            // ……
            // 然后返回结果
            Thread.sleep(10000);
            return 100;
        });
//        while(!f.isDone())
//            ;
        System.out.println(f.get());

    }

    @Test
    public void date() throws ParseException {
        Date now = new Date();
        System.out.println(now);
        System.out.println("----------");

        String dateStr = "Wed Mar 18 10:00:00 2020";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        System.out.println(dateTime);
        System.out.println("----------");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(20200323));

        // will error
        // System.out.println(sdf.parse("20200323"));
        // System.out.println(sdf.parse("2020-03"));

        System.out.println("----------");

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println(now);
        System.out.println("----------");

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse("1986-05-04 00:30:00"));
        System.out.println("----------");
    }

    static class Fruit {

    }

    static class Plate<T> {
        private T item;

        public Plate() {
        }

        public Plate(T t) {
            item = t;
        }

        public void set(T t) {
            item = t;
        }

        public T get() {
            return item;
        }
    }

    @Test
    public void plate() {
        Plate<?> p;
        p = new Plate<Fruit>(new Fruit());
        Object f = p.get();
        System.out.println(p.get().getClass());
    }

    @Test
    public void mapOp() {
        Map<Long, String> m = new HashMap<>();
        m.put(new Long(10), "aa");
        System.out.println(m.get(new Long(10)));
    }

    @Test
    public void conMap() {
        Map<String, Integer> result = new ConcurrentHashMap<>();
        result.put("a", 1);
        result.put("b", 2);
        result.put("c", 3);
        System.out.println(result);

        // Map<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        IntStream.iterate(1, i -> i + 1).limit(200_000).mapToObj(String::valueOf).parallel()
                .forEach((k) -> map.put(k, "String" + k));
        System.out.println(map.size());

    }

    @Test
    public void numberFormat() {
        double d = 123114.145;
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        nf.setGroupingUsed(false);
        System.out.println(nf.format(d));

        DecimalFormat df = new DecimalFormat("######0.00");
        System.out.println(df.format(11.11));

    }

    @Test
    public void jsonParse() {
        String content2 = "[{\"id\":\"2789b3787c67e4b7c5665462f1c4691d\",\"gmv\":\"1129115\"}]";
        List<Map> a2 = JSON.parseArray(content2, Map.class);
        List<Map<String, String>> newList = new ArrayList<>();
        a2.forEach(newList::add);
        System.out.println(a2);
        System.out.println(newList);

        new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept("name");
        filterProvider.addFilter("fieldFilter", fieldFilter);
        new ObjectMapper().setFilterProvider(filterProvider);

        // @JsonIgnore
    }

    @Test
    public void printStrArr() {
        System.out.println(ArrayUtils.toString(new String[]{"111", "22"}));
    }

    @Test
    public void tuple() {
        Map<Tuple2<String, String>, List<String>> map = new HashMap<>();
        map.put(new Tuple2<>("a", "b"), Arrays.asList("hello"));
        System.out.println(map.get(new Tuple2<>("a", "b")));

        // @Data
        // @EqualsAndHashCode
        @AllArgsConstructor
        class MyTuple {
            String a;
            Integer b;
            String c;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                MyTuple myTuple = (MyTuple) o;
                return Objects.equals(a, myTuple.a) && Objects.equals(b, myTuple.b);
            }

            @Override
            public int hashCode() {
                return Objects.hash(a, b);
            }
        }

        Map<MyTuple, String> map2 = new HashMap<>();
        map2.put(new MyTuple("a", 1, "c"), "hello");
        map2.put(new MyTuple("a", 1, "d"), "hi");
        System.out.println(map2.get(new MyTuple("a", 1, "e")));
    }

    public void input() throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        //PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int a, b;
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            a = (int) in.nval;
            in.nextToken();
            b = (int) in.nval;
            //out.println(a + b);
            System.out.println("a + b = " + (a + b));
        }
        //out.flush();
    }

    protected void graphSql() {
        /*
        String schema = "type Query{hello: String} schema{query: Query}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = new RuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(executionResult.getData().toString());

         */
        // Prints: {hello=world}
    }


    @Test
    public void sqlWrapper() {
        QueryWrapper<Object> abstractWrapper = new QueryWrapper<>();
        abstractWrapper.eq("a", "b");
        abstractWrapper.select("abc");
        System.out.println(abstractWrapper.getSqlSegment() + " " + abstractWrapper.getSqlSelect());
        System.out.println(abstractWrapper.getTargetSql());

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "a");
        System.out.println(wrapper.getTargetSql());
    }

    @Test
    public void ConcurrentModificationException() {
        // https://www.jianshu.com/p/c5b52927a61a
        List<String> list = new ArrayList<>();
        // List<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        list.add("c");
        list.add("d");

        int j = 4;
        switch (j) {
            case 1:
                for (String s : list) {
                    if (s.equals("B")) {
                        list.remove(s);
                    }
                }
                break;
            case 2:
                list.removeIf(s -> s.equals("B"));
                break;
            case 3:
                new Thread() {
                    public void run() {
                        Iterator<String> iterator = list.iterator();

                        while (iterator.hasNext()) {
                            System.out.println(Thread.currentThread().getName() + ":1:" + iterator.next());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                new Thread() {
                    public synchronized void run() {
                        Iterator<String> iterator = list.iterator();

                        while (iterator.hasNext()) {
                            String element = iterator.next();
                            System.out.println(Thread.currentThread().getName() + ":2:" + element);
                            if (element.equals("c")) {
                                iterator.remove();
                            }
                        }
                    }
                }.start();
                break;
            case 4:
                new Thread() {
                    public void run() {
                        Iterator<String> iterator = list.iterator();

                        synchronized (list) {
                            while (iterator.hasNext()) {
                                System.out.println(Thread.currentThread().getName() + ":1:" + iterator.next());
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }.start();

                new Thread() {
                    public synchronized void run() {
                        Iterator<String> iterator = list.iterator();

                        synchronized (list) {
                            while (iterator.hasNext()) {
                                String element = iterator.next();
                                System.out.println(Thread.currentThread().getName() + ":2:" + element);
                                if (element.equals("c")) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }.start();
                break;
        }

        System.out.println(list);
    }

    @Test
    public void test() {
        Result result = JUnitCore.runClasses(SystemClockTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    @Test
    public void myClone() {
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);// 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Cmd cmd = (Cmd) ois.readObject();
            System.out.println(cmd);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private final List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

    @Test
    public void testCollect2() {
        Iterator<?> iterator = this.memoryPoolMXBeans.iterator();

        while (iterator.hasNext()) {
            MemoryPoolMXBean mxBean = (MemoryPoolMXBean) iterator.next();
            String memoryPoolName = mxBean.getName();
            MemoryUsage memoryUsage = mxBean.getUsage();
            System.out.printf("JVM.MemoryPool." + memoryPoolName + ".Init=%d%n", memoryUsage.getInit());
            System.out.printf("JVM.MemoryPool." + memoryPoolName + ".Used=%d%n", memoryUsage.getUsed());
            System.out.printf("JVM.MemoryPool." + memoryPoolName + ".Committed=%d%n", memoryUsage.getCommitted());
            System.out.printf("JVM.MemoryPool." + memoryPoolName + ".Max=%d%n%n", memoryUsage.getMax());
        }

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println("JVM.Memory.ObjectPendingFinalizationCount:" + memoryMXBean.getObjectPendingFinalizationCount());
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("JVM.Memory.Heap.Init:" + heapMemoryUsage.getInit());
        System.out.println("JVM.Memory.Heap.Max:" + heapMemoryUsage.getMax());
        System.out.println("JVM.Memory.Heap.Used:" + heapMemoryUsage.getUsed());
        System.out.println("JVM.Memory.Heap.Committed:" + heapMemoryUsage.getCommitted());
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        System.out.println("JVM.Memory.NonHeap.Init:" + nonHeapMemoryUsage.getInit());
        System.out.println("JVM.Memory.NonHeap.Max:" + nonHeapMemoryUsage.getMax());
        System.out.println("JVM.Memory.NonHeap.Used:" + nonHeapMemoryUsage.getUsed());
        System.out.println("JVM.Memory.NonHeap.Committed:" + nonHeapMemoryUsage.getCommitted());

        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("JVM.ClassLoading.LoadedClassCount:" + classLoadingMXBean.getLoadedClassCount());
        System.out.println("JVM.ClassLoading.TotalLoadedClassCount:" + classLoadingMXBean.getTotalLoadedClassCount());
        System.out.println("JVM.ClassLoading.UnloadedClassCount:" + classLoadingMXBean.getUnloadedClassCount());

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        System.out.println(garbageCollectorMXBeans.size());
        Iterator<?> iterator1 = garbageCollectorMXBeans.iterator();

        while (iterator1.hasNext()) {
            GarbageCollectorMXBean mxBean = (GarbageCollectorMXBean) iterator1.next();
            System.out.println(mxBean.getName());
            System.out.println(mxBean.getCollectionCount());
            System.out.println(mxBean.getCollectionTime());
        }
    }

    @Test
    public void testCountDownLatch() {
        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        try {
            System.out.println("before await");
            latch.await();
            System.out.println("after await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLombokSneaky() {
        var c = new SneakyThrowsStudy();
        c.run();
    }

    @Builder
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    static class Blog {
        int id;
        String title;
        String content;
        double rate;
    }

    @Test
    public void testLombokBuilder() {
        var b = new Blog(1, "2", "3", 4.0);
        var b2 = new Blog(1, "2", "3", 4.0);
        System.out.println(b.equals(b2));
        var blog = Blog.builder()
                .id(1)
                .title("hello")
                .build();
        System.out.println(blog);
    }

    @Test
    public void testAssert() {
        int i = 10_0000;
        assert i > 10 : "bad i";
    }

    public static class GT<T> {
        public static int var = 0;

        public void nothing(T x) {
        }
    }

    @Test
    public void testGenericStatic() {
        System.out.println(GT.var);

        GT<Integer> gti = new GT<>();
        gti.var = 1;
        GT<String> gts = new GT<>();
        gts.var = 2;
        System.out.println(gti.var);
    }

    @Data
    static
    class Student {
        int id;
    }

    @Test
    public void testFor() {
        List<Student> students = new ArrayList<>();
        Student si;
        si = new Student();
        si.setId(1);
        students.add(si);
        si = new Student();
        si.setId(2);
        students.add(si);
        si = new Student();
        si.setId(3);
        students.add(si);
        si = new Student();
        si.setId(4);
        students.add(si);

        for (Student stu : students) {
            System.out.println(stu.getId());
            if (stu.getId() == 2) {
                students.remove(stu);
            }
        }
    }
}
