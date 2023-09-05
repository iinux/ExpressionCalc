package cn.iinux.java.alpha.thriftstudy;

import cn.iinux.java.alpha.thrift.Person;
import cn.iinux.java.alpha.thrift.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) throws TException {
        String host = "localhost";
        host = "::1";
        TTransport transport = new TFramedTransport(new TSocket(host, 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();

            Person person = client.getPersonByUsername("zhang san");

            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            Person person1 = new Person();
            person1.setUsername("will");
            person1.setAge(30);
            person1.setMarried(true);
            client.savePerson(person1);
        } finally {
            transport.close();
        }
    }
}
