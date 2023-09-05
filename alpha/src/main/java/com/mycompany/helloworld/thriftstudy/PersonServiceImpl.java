package com.mycompany.helloworld.thriftstudy;

import com.mycompany.helloworld.thrift.DataException;
import com.mycompany.helloworld.thrift.Person;
import com.mycompany.helloworld.thrift.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("getPersonByUsername " + username);
        Person person = new Person();
        person.setUsername("johann");
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("savePerson");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
        System.out.println(person);
    }
}
