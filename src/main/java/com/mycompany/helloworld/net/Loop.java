package com.mycompany.helloworld.net;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

import java.io.IOException;

public class Loop {

    private static final String COUNT_KEY = Loop.class.getName() + ".count";
    private static final int COUNT = Integer.getInteger(COUNT_KEY, 5);
    private static final String READ_TIMEOUT_KEY = Loop.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]
    private static final String SNAPLEN_KEY = Loop.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    private Loop() {}

    public static void main(String[] args) throws PcapNativeException, NotOpenException {
        String filter = "ip and tcp and (dst host 127.0.0.1 and dst port 80)"; // 设置过滤的字符串

        // 设置要抓包的网卡
        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface(); // 这个方法提供用户输入网卡的序号
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (nif == null) {
            return;
        }

        // 实例化一个捕获报文的对象，设置抓包参数：长度，混杂模式，超时时间等
        final PcapHandle handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        // 设置过滤器
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);

        // 观察者模式，抓到报文回调gotPacket方法处理报文内容
        PacketListener listener =
                new PacketListener() {
                    @Override
                    public void gotPacket(Packet packet) {
                        // 抓到报文走这里...
                        System.out.println(handle.getTimestamp());
                        System.out.println(packet);
                    }
                };

        // 直接使用loop无限循环处理包
        try {
            handle.loop(COUNT, listener); // COUNT设置为抓包个数，当为-1时无限抓包
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handle.close();
    }
}
