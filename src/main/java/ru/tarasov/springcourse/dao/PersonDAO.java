package ru.tarasov.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.tarasov.springcourse.models.Person;

import javax.naming.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {


    private final JdbcTemplate jdbcTemplat;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplat = jdbcTemplate;
    }


    public List<Person> index() {
        //rowmapper - объект, который отображает строки из табилцы в нашей сущности
        // заимствуем  rowMapper
        return jdbcTemplat.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    // возвращает список, но с поиощью стрима, ищется по списку нужный объект и вовращает его или возвращает null
    public Person show(int id) {
        return jdbcTemplat.query("SELECT * FROM Person WHERE id =?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplat.update("INSERT INTO Person VALUES(1,?,?,?)", person.getName(),
                person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplat.update("UPDATE Person SET name =?,age =?, email =? WHERE id = ?", updatePerson.getName(),
                updatePerson.getAge(), updatePerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplat.update("DELETE from Person where id = ?", id);
    }
}


