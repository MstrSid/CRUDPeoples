package by.kos.crudpeoples.dao;


import by.kos.crudpeoples.models.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

  private static int PERSONS_COUNT = 0;

  private static final String URL = "jdbc:postgresql://localhost:5432/persons_db";
  private static final String USER_NAME = "postgres";
  private static final String PASSWORD = "postgres";

  private static Connection connection;

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Person> index() {
    List<Person> persons = new ArrayList<>();

    try {
      Statement statement = connection.createStatement();
      String SQL = "select * from persons";
      ResultSet resultSet = statement.executeQuery(SQL);

      while (resultSet.next()) {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
        persons.add(person);
      }
      statement.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return persons;
  }

  public Person show(int id) {
//    return persons
//        .stream()
//        .filter(person -> person.getId() == id)
//        .findAny()
//        .orElse(null);
    return null;
  }

  public void save(Person person) {
    try {
      Statement statement = connection.createStatement();
      String SQL = String.format("insert into persons values (%d, '%s', %d, '%s')",
          ++PERSONS_COUNT,
          person.getName(),
          person.getAge(),
          person.getEmail());
      statement.executeUpdate(SQL);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
//    person.setId(++PERSONS_COUNT);
//    persons.add(person);
  }

  public void update(int id, Person person) {
//    Person personUpdate = show(id);
//    personUpdate.setName(person.getName());
//    personUpdate.setAge(person.getAge());
//    personUpdate.setEmail(person.getEmail());
  }

  public void delete(int id) {
    //   persons.removeIf(p -> p.getId() == id);
  }
}
