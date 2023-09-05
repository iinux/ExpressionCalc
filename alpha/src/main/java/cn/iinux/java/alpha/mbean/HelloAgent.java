package cn.iinux.java.alpha.mbean;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class HelloAgent {
    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        ObjectName helloName = new ObjectName("iinux:name=Hello");
        Hello hello = new Hello();
        server.registerMBean(hello, helloName);

        Jack jack = new Jack();
        server.registerMBean(jack, new ObjectName("iinux:name=Jack"));
        jack.addNotificationListener(new HelloListener(), null, hello);

        ObjectName adapterName = new ObjectName("iinux:name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.start();
        server.registerMBean(adapter, adapterName);

        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);

        // jconsole service:jmx:rmi:///jndi/rmi://localhost:1099/iinux
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + "iinux");
        JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
        jmxConnector.start();

        server.invoke(helloName, "helloWorld", new Object[] { "china sf"}, new String[] {"java.lang.String"});

        Thread.sleep(Long.MAX_VALUE);
    }
}