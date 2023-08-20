package ru.tarasov.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tarasov.springcourse.dao.PersonDAO;
import ru.tarasov.springcourse.models.Person;

//validator используется только на одной сущности, для которой он преднозначен
@Component
public class PersonValidator implements Validator{



    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO){
        this.personDAO = personDAO;

    }
    @Override
    public boolean supports(Class<?> clazz) {
        //true если класс, который передается в качестве аргумента = классу Person
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //посмотерть есть ли человек с таким же email в БД
        if (personDAO.show(person.getEmail()).isPresent())
            errors.rejectValue("email", "",
                    "This email is already taken");
    }
}
