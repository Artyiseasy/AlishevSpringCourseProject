package ru.tarasov.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.tarasov.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    //метод, который будет возвращать объекты из бд и записывать их в объект класса Person
    // не используем, вместо него используем заимствованный rowmapper
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
        return person;
    }
}
