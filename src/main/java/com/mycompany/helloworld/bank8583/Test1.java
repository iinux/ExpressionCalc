package com.mycompany.helloworld.bank8583;

import com.imohsenb.ISO8583.builders.ISOMessageBuilder;
import com.imohsenb.ISO8583.entities.ISOMessage;
import com.imohsenb.ISO8583.enums.FIELDS;
import com.imohsenb.ISO8583.enums.MESSAGE_FUNCTION;
import com.imohsenb.ISO8583.enums.MESSAGE_ORIGIN;
import com.imohsenb.ISO8583.enums.VERSION;
import com.imohsenb.ISO8583.exceptions.ISOException;

public class Test1 {
    public static void main(String[] args) throws ISOException {
        ISOMessage isoMessage = ISOMessageBuilder.Packer(VERSION.V1987)
                .networkManagement()
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setField(FIELDS.F11_STAN,  "1")
                .setField(FIELDS.F12_LocalTime,  "023120")
                .setField(FIELDS.F13_LocalDate,  "0332")
                .setField(FIELDS.F24_NII_FunctionCode,  "333")
                .setHeader("1002230000")
                .build();
        byte[] body = isoMessage.getBody();
        byte[] trace = isoMessage.getField(11);
        String trace2 = isoMessage.getStringField(FIELDS.F11_STAN);
        System.out.println(bytesToHex(body));
        System.out.println(bytesToHex(trace));
        System.out.println(trace2);
        System.out.println(bytesToHex(isoMessage.getHeader()));
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }
}
