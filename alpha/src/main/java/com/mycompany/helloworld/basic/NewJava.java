package com.mycompany.helloworld.basic;

import java.io.FileNotFoundException;

// Run with option --add-modules jdk.incubator.foreign
// import jdk.incubator.foreign.MemoryAddress;
// import jdk.incubator.foreign.MemorySegment;

public class NewJava {
    // public record UserDTO(String id,String nickname,String homepage) { };

    public static void main(String[] args) throws FileNotFoundException {
        /*
        List<Integer> numbers = new ArrayList<>() {

        };

        System.out.println(numbers);

         */


        /*
        FileInputStream fileInputStream = new FileInputStream("/home/user/net.sh");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader0 = new BufferedReader(inputStreamReader);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader);
        try (bufferedReader0; bufferedReader1) {
            System.out.println(bufferedReader0.readLine() + bufferedReader1.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        /*
        var message = "Hello, Java 10";
        System.out.println(message);

         */

        /*
        int dayOfWeek = 1;
        var typeOfDay = switch (dayOfWeek) {
            case 1 -> "Working Day";
            case 2 -> "Day Off";
            default -> "default";
        };
        System.out.println(typeOfDay);

         */

        /*
        Object obj = "Hello Java 12!";
        if (obj instanceof String str) {
            int length = str.length();
            System.out.println(length);
        }

         */

        /*
        int dayOfWeek = 2;
        var typeOfDay = switch (dayOfWeek) {
            case 1 -> {
                // do sth...
                yield "Working Day";
            }
            case 2 -> "Day Off";
            default -> "default";
        };
        System.out.println(typeOfDay);

         */

        /*
        String json = """
                {
                    "id":"1697301681936888",
                    "nickname":"空无",
                    "homepage":"https://juejin.cn/user/1697301681936888"
                }
                """;
        System.out.println(json);

         */

        /*
        UserDTO user = new UserDTO("1697301681936888","空无","https://juejin.cn/user/1697301681936888");
        System.out.println(user.id);
        System.out.println(user.nickname);
        System.out.println(user.id);

         */

        /*
        Map<String, Map<String,Boolean>> wrapMap = new HashMap<>();
        wrapMap.put("innerMap",new HashMap<>());

        boolean effected = wrapMap.get("innerMap").get("effected");

         */

        /*
        // 分配 200B 堆外内存
        MemorySegment memorySegment = MemorySegment.allocateNative(200);

        // 用 ByteBuffer 分配，然后包装为 MemorySegment
        MemorySegment memorySegment = MemorySegment.ofByteBuffer(ByteBuffer.allocateDirect(200));

        // MMAP 当然也可以
        MemorySegment memorySegment = MemorySegment.mapFromPath(
                Path.of("/tmp/memory.txt"), 200, FileChannel.MapMode.READ_WRITE);

        // 获取堆外内存地址
        MemoryAddress address = MemorySegment.allocateNative(100).baseAddress();

        // 组合拳，堆外分配，堆外赋值
        long value = 10;
        MemoryAddress memoryAddress = MemorySegment.allocateNative(8).baseAddress();
        // 获取句柄
        VarHandle varHandle = MemoryHandles.varHandle(long.class, ByteOrder.nativeOrder());
        varHandle.set(memoryAddress, value);

        // 释放就这么简单，想想 DirectByteBuffer 的释放……多奇怪
        memorySegment.close();

         */
    }
}

/*
sealed interface Service permits Car, Truck {

    int getMaxServiceIntervalInMonths();

    default int getMaxDistanceBetweenServicesInKilometers() {
        return 100000;
    }

}


 */
