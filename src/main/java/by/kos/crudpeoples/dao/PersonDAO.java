package by.kos.crudpeoples.dao;


import by.kos.crudpeoples.models.Person;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    return jdbcTemplate.query("select * from persons", new BeanPropertyRowMapper<>(Person.class));
  }

  public Person show(int id) {
    return jdbcTemplate.queryForObject("select * from persons where id = ?",
        new BeanPropertyRowMapper<>(Person.class), id);
  }

  public void save(Person person) {
    jdbcTemplate.update("insert into persons values (1, ?, ?, ?)",
        person.getName(),
        person.getAge(),
        person.getEmail());
  }

  public void update(int id, Person person) {
    jdbcTemplate.update("update persons set name = ?, age = ?, email = ? where id = ?",
        person.getName(),
        person.getAge(),
        person.getEmail(),
        id);
  }

  public void delete(int id) {
    jdbcTemplate.update("delete from persons where id = ?", id);
  }
}
