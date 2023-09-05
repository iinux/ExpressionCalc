package cn.iinux.java.alpha.basic;

public class EnumValueTest {
    public static void main(String[] args) {
        System.out.println(EnumValue.A.getValue());
    }
}

enum EnumValue {
    A(1),
    B(2),
    C(3);

    Integer value;

    EnumValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
