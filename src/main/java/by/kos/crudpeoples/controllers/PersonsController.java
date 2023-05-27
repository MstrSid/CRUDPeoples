package by.kos.crudpeoples.controllers;

import by.kos.crudpeoples.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonsController {

  private final PersonDAO personDAO;

  @Autowired
  public PersonsController(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  @GetMapping()
  public String index(Model model) {
    //Получим всех людей из DAO и передадим на отображение в представление
    model.addAttribute("persons", personDAO.index());
    return "persons/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model) {
    // Получим одного человека по его id из DAO
    // и передадим этого человека на отображение в представление
    model.addAttribute("person", personDAO.show(id));
    return "persons/show";
  }

}
