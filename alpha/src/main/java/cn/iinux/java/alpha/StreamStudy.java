package cn.iinux.java.alpha;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamStudy {
    @Test
    public void TestTrace() {
        Stream.of("beijing","tianjin","shanghai","wuhan")
                .map(String::length)
                .filter(e->e>5)
                .collect(Collectors.toList());
        // StreamTrace
    }
}
