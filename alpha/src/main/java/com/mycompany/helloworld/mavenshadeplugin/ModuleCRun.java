package com.mycompany.helloworld.mavenshadeplugin;

import lombok.extern.slf4j.Slf4j;

// https://www.playpi.org/2019120101.html

@Slf4j
public class ModuleCRun {
    public static void main (String [] args) {
        log.info ("====Hello World!");
        run ();
    }
    public static void run () {
        log.info ("==== 开始执行 module-c 的代码");
        // 这个方法在 v26.0-jre 中有，在 v19.0 中没有
        // log.info ("====[{}]", Strings.lenientFormat ("", "in", "out"));
        log.info ("====module-c 的代码执行完成");
    }
}
