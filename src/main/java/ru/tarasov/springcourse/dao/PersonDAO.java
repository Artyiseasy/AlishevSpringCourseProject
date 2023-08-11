package ru.tarasov.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.tarasov.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

        private static int PEOPLE_COUNT;
        private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
        private static final String USERNAME = "postgres";
        private static final String PASSWORD = "123";

        private static Connection connection; // создаем для соеденения с бд

        static { // убеждаемся, что у нас подключен драйвер постгреса
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // подключаемся к бд
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public List<Person> index(){
        // создаем лист в который будем класть считаных и бд людей
        List <Person> people = new ArrayList<>();
        // это тот объект, который содержит в себе sql запрос
        // на этом объекте connection(на нашем соеденение) создаем объект запрос к бд
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            // resultSet - объект который инкапсулирует результат sql запроса
            ResultSet resultSet =  statement.executeQuery(SQL); //statement выполняет SQL запрос
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }


    public Person show (int id){
   //    return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }
    public void save(Person person){
        //person.setId(++PEOPLE_COUNT);
      //  people.add(person);
        try {
            Statement statement = connection.createStatement();

            //так делать нельзя, это временно
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName()+
                    "', " + person. getAge() + ",'" + person.getEmail() + "')";
            statement.executeUpdate(SQL); // executeUpdate просто обновляет данные в базе, ничего не возвращает
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//  public void update(int id, Person updatePerson){
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatePerson.getName());
//        personToBeUpdated.setAge(updatePerson.getAge());
//        personToBeUpdated.setEmail(updatePerson.getEmail());
//    }
   // public void delete(int id){people.removeIf(p -> p.getId() == id);
    }


