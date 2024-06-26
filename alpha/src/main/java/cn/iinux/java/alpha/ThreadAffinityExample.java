package cn.iinux.java.alpha;

import net.openhft.affinity.AffinityLock;

public class ThreadAffinityExample {
    public static void main(String[] args) {
        try (AffinityLock affinityLock = AffinityLock.acquireLock(2)) {
            while (true) {

            }
        }
    }
}
