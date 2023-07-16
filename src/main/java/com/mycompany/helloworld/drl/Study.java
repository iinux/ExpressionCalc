package com.mycompany.helloworld.drl;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Study {
    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        KieSession statefulKieSession = container.newKieSession("myAgeSession");

        User user = new User("duval yang", 12);
        statefulKieSession.insert(user);
        statefulKieSession.fireAllRules();
        statefulKieSession.dispose();


    }
}
