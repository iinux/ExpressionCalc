package cn.iinux.java.alpha.netty.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.time.Duration;
import java.time.Instant;

public class NewIOClient {

    private void connect() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        socketChannel.configureBlocking(true);

        String bigFilePath = "/usr/home/qzhang/Downloads/winxp.iso"; // 300MB
        FileChannel fileChannel = new FileInputStream(bigFilePath).getChannel();

        Instant start = Instant.now();

        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        Instant end = Instant.now();

        System.out.println("send bytes: " + transferCount + ", take time: " +
                                   Duration.between(start, end)
                                           .toMillis());
        fileChannel.close();
        socketChannel.close();
    }

    public static void main(String[] args) throws Exception {
        new NewIOClient().connect();
    }
}
