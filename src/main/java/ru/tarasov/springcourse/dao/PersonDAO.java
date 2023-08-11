package ru.tarasov.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.tarasov.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    //этот класс будет общаться со списком(БД), извлекать людей из списка, находить по ID, обновлять, добавлять6 удалять

        private List<Person> people;
        private static int PEOPLE_COUNT;


    {
        //блок инициализации
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT,"Bob", 25, "bob@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Tom", 44, "tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Igor", 32, "igor@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT,"Mike",62, "mike@mail.ru"));

    }
    public List<Person> index(){
        return people;
    }
    public Person show (int id){
       return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    public void update(int id, Person updatePerson){
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }
    public void delete(int id){people.removeIf(p -> p.getId() == id);
    }
}


