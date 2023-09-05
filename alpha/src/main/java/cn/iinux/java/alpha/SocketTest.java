package cn.iinux.java.alpha;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8888, 2);
        serverSocket.setSoTimeout(0);

        Thread.sleep(15000);

        Socket socket = serverSocket.accept();
        serverSocket.close();
        socket.setKeepAlive(true);
        // socket.setReceiveBufferSize(receiveBufferSize);
        // socket.setSendBufferSize(sendBufferSize);

        Thread ta = new Thread(() -> {
            while (true) {
                try {
                    socket.getOutputStream().write("aaaaaaaaaaaa\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(socket.isClosed());
                    break;
                }
            }
        });

        Thread tb = new Thread(() -> {
            while (true) {
                try {
                    socket.getOutputStream().write("bbbbbbbbbbbb\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(socket.isClosed());
                    break;
                }
            }
        });

        // ta.start();
        // tb.start();

        if (true) {
            System.out.println("before");
            Thread.sleep(10000);
            System.out.println("after");
            socket.shutdownOutput();
            byte[] bytes = new byte[3];
            int r1 = socket.getInputStream().read(bytes, 0, 3);
            System.out.println(bytes[0]);
            System.out.println(bytes[1]);
            System.out.println(bytes[2]);
            System.out.println(r1);
            Thread.sleep(1000000);
        }

        if (false) {
            socket.shutdownInput();
            byte[] bytes = new byte[3];
            int r1 = socket.getInputStream().read(bytes, 0, 3);
            System.out.println(bytes[0]);
            System.out.println(bytes[1]);
            System.out.println(bytes[2]);
            System.out.println(r1);
            Thread.sleep(1000000);
        }


        if (false) {
            while (true) {
                int r = socket.getInputStream().read();
                socket.getOutputStream().write(48);
                System.out.println(r);
            }
        }
    }

    @Test
    public void test1() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8888);
        serverSocket.setSoTimeout(0);

        Thread thread = new Thread(() -> {
            Socket socket = null;
            try {
                System.out.println("start accept");
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        Thread.sleep(10000);

        // thread.interrupt();
        serverSocket.close();

        Thread.sleep(10000);

        System.out.println(thread.getState());

        Thread.sleep(1000000);
    }

    @Test
    public void test2() {
        byte[] bytes = new byte[]{(byte) 0x01, (byte) 0x01};
        System.out.println((bytes[0] & 0xff) * 256 + (bytes[1] & 0xff));

    }
}
