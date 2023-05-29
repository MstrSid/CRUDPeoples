package by.kos.crudpeoples.dao;


import by.kos.crudpeoples.models.Person;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

  private static int PERSONS_COUNT = 0;
  private List<Person> persons;

  {
    persons = new ArrayList<>();

    persons.add(new Person(++PERSONS_COUNT, "Kyle", 35, "kyle@test.test"));
    persons.add(new Person(++PERSONS_COUNT, "Mike", 25, "mike@test.test"));
    persons.add(new Person(++PERSONS_COUNT, "Bob", 31, "bob@test.test"));
    persons.add(new Person(++PERSONS_COUNT, "Kate", 24, "kate@test.test"));
  }

  public List<Person> index() {
    return persons;
  }

  public Person show(int id) {
    return persons
        .stream()
        .filter(person -> person.getId() == id)
        .findAny()
        .orElse(null);
  }

  public void save(Person person) {
    person.setId(++PERSONS_COUNT);
    persons.add(person);
  }

  public void update(int id, Person person) {
    Person personUpdate = show(id);
    personUpdate.setName(person.getName());
    personUpdate.setAge(person.getAge());
    personUpdate.setEmail(person.getEmail());
  }

  public void delete(int id) {
    persons.removeIf(p -> p.getId() == id);
  }
}
