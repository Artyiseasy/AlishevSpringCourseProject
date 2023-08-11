package ru.tarasov.springcourse.dao;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.tarasov.springcourse.models.Person;

import javax.naming.Name;
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
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id = ?");
            preparedStatement.setInt(1, id);// указываем  id из контролера
          ResultSet resultSet  = preparedStatement.executeQuery();
          resultSet.next();
          person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
    public void save(Person person){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1,?,?,?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());


            preparedStatement.executeUpdate(); //executeUpdate просто обновляет инфу в бд, ничего не возвращает

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
  public void update(int id, Person updatePerson){
      try {
          PreparedStatement preparedStatement =
                  connection.prepareStatement("UPDATE Person SET name =?,age =?, email =? WHERE id = ?");
          preparedStatement.setString(1, updatePerson.getName());
          preparedStatement.setInt(2, updatePerson.getAge());
          preparedStatement.setString(3, updatePerson.getEmail());
          preparedStatement.setInt(4, id);
          preparedStatement.executeUpdate();

      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }
   public void delete(int id) {
            PreparedStatement preparedStatement = null;
       try {
           preparedStatement = connection.prepareStatement("DELETE from Person where id = ?");
           preparedStatement.setInt(1, id);
           preparedStatement.executeUpdate();

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
    }


