package ru.tarasov.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.tarasov.springcourse.models.Person;

import javax.naming.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Person> show(String email) {
        return jdbcTemplat.query("SELECT * FROM Person WHERE email =?",
                        new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplat.update("INSERT INTO Person(name, age, email, adress) VALUES(?,?,?,?)", person.getName(),
                person.getAge(), person.getEmail(), person.getAdress());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplat.update("UPDATE Person SET name =?,age =?, email =?,adress =? WHERE id = ?", updatePerson.getName(),
                updatePerson.getAge(), updatePerson.getEmail(),updatePerson.getAdress(), id);
    }

    public void delete(int id) {
        jdbcTemplat.update("DELETE from Person where id = ?", id);
    }
    /*
    тестируем производительность пакетной вставки
     */
    public void testMultipleUpdate(){
       List<Person> people = create1000people();
       Long before = System.currentTimeMillis();
       for(Person person : people) {
           jdbcTemplat.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(),
                   person.getName(), person.getAge(),person.getEmail());
       }
       long after = System.currentTimeMillis();
        System.out.println("Time: "+ (after - before));
    }
   private List<Person> create1000people(){
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            people.add(new Person(i,"name"+i, 30, "test" + i + "mail.ru", "some adress"));
        return people;
   }
   public void testBatchUpdate(){
        List<Person> people = create1000people();
        Long before = System.currentTimeMillis();
        jdbcTemplat.batchUpdate("INSERT INTO person values(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                //  i  - указывает на конкретного человека
                ps.setInt(1,people.get(i).getId());
                ps.setString(2, people.get(i).getName());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4,people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        Long after = System.currentTimeMillis();
       System.out.println("Time2 " + (after - before));
   }
}


