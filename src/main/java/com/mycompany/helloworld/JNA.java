package com.mycompany.helloworld;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.WString;

// https://www.cnblogs.com/lanxuezaipiao/p/3635556.html

public class JNA {
    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                        CLibrary.class);

        void printf(String format, Object... args);
    }

    public interface Kernel32 extends Library {
        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
        public boolean Beep(int FREQUENCY, int DURATION);
        public void Sleep(int DURATION);
    }

    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        public boolean LockWorkStation();
        public int MessageBoxA(int something, String text, String caption, int flags);
        public int MessageBoxW(int something, WString text, WString caption, int flags);
    }

    public static void main(String[] args) {
        CLibrary.INSTANCE.printf("Hello, World\n");
        for (int i = 0; i < args.length; i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }

        // Kernel32.INSTANCE.Beep(698, 1500);
        // Kernel32.INSTANCE.Sleep(500);
        // User32.INSTANCE.LockWorkStation();
        // User32.INSTANCE.MessageBoxA(0,"MessageBox success!!!","Attention",0);
        User32.INSTANCE.MessageBoxW(0, new WString("MessageBox success!!!"), new WString("Attention"), 0);
    }
}
