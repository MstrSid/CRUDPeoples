package by.kos.crudpeoples.dao;


import by.kos.crudpeoples.models.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

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

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return persons;
  }

  public Person show(int id) {
    Person person = null;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "select * from persons where id = ?");
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      resultSet.next();

      person  = new Person();
      person.setId(resultSet.getInt("id"));
      person.setName(resultSet.getString("name"));
      person.setAge(resultSet.getInt("age"));
      person.setEmail(resultSet.getString("email"));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return person;
  }

  public void save(Person person) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("insert into persons values (1, ?, ?, ?)");
      preparedStatement.setString(1, person.getName());
      preparedStatement.setInt(2, person.getAge());
      preparedStatement.setString(3, person.getEmail());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void update(int id, Person person) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("update persons set name = ?, age = ?, email = ? where id = ?");

      preparedStatement.setString(1, person.getName());
      preparedStatement.setInt(2, person.getAge());
      preparedStatement.setString(3, person.getEmail());
      preparedStatement.setInt(4, person.getId());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(int id) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("delete from persons where id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
