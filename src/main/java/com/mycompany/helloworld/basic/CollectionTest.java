package com.mycompany.helloworld.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

@Slf4j
public class CollectionTest {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class CertConfig {
        private int flow;

    }

    private static final Random random = ThreadLocalRandom.current();

    @Test
    public void reduce() {
        List<CertConfig> configList = new ArrayList<>();
        configList.add(CertConfig.builder().flow(0).build());
        configList.add(CertConfig.builder().flow(100).build());
        // System.out.println(configList.stream().findFirst());

        configList.sort(Comparator.comparingInt(CertConfig::getFlow));
        Integer totalFactor = configList.stream()
                .map(CertConfig::getFlow)
                .reduce(0, Integer::sum);

        int flow = random.nextInt(totalFactor) + 1;


        log.info("routeSignInfo:{}/{}", flow, totalFactor);

        final CertConfig tmp = new CertConfig();
        Optional<CertConfig> d = configList.stream().filter(c -> {
            boolean result = c.getFlow() + tmp.getFlow() >= flow;
            tmp.setFlow(tmp.getFlow() + c.getFlow());
            return result;
        }).findFirst();
        System.out.println(d);
    }

    @Test
    public void andThen() {

        BiConsumer<Integer, Integer> a = ((BiConsumer<Integer, Integer>) (x, y) -> {
            System.out.println("accept");
            System.out.println(x);
            System.out.println(y);

        }).andThen((x, y) -> {
            System.out.println("andThen");
            System.out.println(x);
            System.out.println(y);
        }).andThen((x, y) -> {
            System.out.println("andThen 2");
            System.out.println(x);
            System.out.println(y);
        });
        a.accept(1, 2);

    }
}
