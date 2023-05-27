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

    persons.add(new Person(++PERSONS_COUNT, "Kyle"));
    persons.add(new Person(++PERSONS_COUNT, "Mike"));
    persons.add(new Person(++PERSONS_COUNT, "Bob"));
    persons.add(new Person(++PERSONS_COUNT, "Kate"));
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
}
