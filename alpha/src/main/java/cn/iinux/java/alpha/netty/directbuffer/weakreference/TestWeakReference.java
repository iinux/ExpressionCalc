package cn.iinux.java.alpha.netty.directbuffer.weakreference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestWeakReference {
    public static void main(String[] args) throws InterruptedException {

        Car car = new Car(22000, "silver");
        WeakReference<Car> weakCar = new WeakReference<Car>(car);
        int i = 0;
        while (true) {
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }

        Car car2 = new Car(22000, "silver");
        SoftReference<Car> softReference = new SoftReference<>(car2);
        i = 0;
        while (true) {
            if (softReference.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + softReference);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
            Thread.sleep(1000);
        }

    }
}
