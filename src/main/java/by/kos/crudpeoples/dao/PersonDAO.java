package by.kos.crudpeoples.dao;


import by.kos.crudpeoples.models.Person;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

  private List<Person> create1000persons(int seed) {
    seed = seed == 0 ? 100 : seed;
    List<Person> personList = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      personList.add(new Person(i + seed, String.format("Name_%d", i + seed), i + seed,
          String.format("%d@%d.com", i + seed, i + seed)));
    }
    return personList;
  }

  // Test performance bath update
  public void testMultipleUpdate() {
    List<Person> personList = create1000persons(100);
    long before = System.currentTimeMillis();
    personList.forEach(person -> jdbcTemplate.
        update("insert into persons values (?, ?, ?, ?)",
            person.getId(),
            person.getName(),
            person.getAge(),
            person.getEmail()));
    long after = System.currentTimeMillis();
    System.out.printf("Time without batch update: %dms\n", after - before);
  }

  public void testBatchUpdate() {
    List<Person> personList = create1000persons(200);
    long before = System.currentTimeMillis();
    jdbcTemplate.batchUpdate("insert into persons values (?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, personList.get(i).getId());
            ps.setString(2, personList.get(i).getName());
            ps.setInt(3, personList.get(i).getAge());
            ps.setString(4, personList.get(i).getEmail());
          }

          @Override
          public int getBatchSize() {
            return personList.size();
          }
        });
    long after = System.currentTimeMillis();
    System.out.printf("Time with batch update: %dms\n", after - before);
  }
}
