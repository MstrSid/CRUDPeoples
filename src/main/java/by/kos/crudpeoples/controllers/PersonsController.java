package by.kos.crudpeoples.controllers;

import by.kos.crudpeoples.dao.PersonDAO;
import by.kos.crudpeoples.models.Person;
import by.kos.crudpeoples.util.PersonValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonsController {

  private final PersonDAO personDAO;
  private final PersonValidator personValidator;

  @Autowired
  public PersonsController(PersonDAO personDAO, PersonValidator personValidator) {
    this.personDAO = personDAO;
    this.personValidator = personValidator;
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

  @GetMapping("/new")
  public String newPerson(Model model) {
    model.addAttribute("person", new Person());
    return "persons/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("person") @Valid Person person,
      BindingResult bindingResult) {
    personValidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "persons/new";
    }
    personDAO.save(person);
    return "redirect:persons";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") int id) {
    model.addAttribute("person", personDAO.show(id));
    return "persons/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
      @PathVariable("id") int id) {
    personValidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "persons/edit";
    }
    personDAO.update(id, person);
    return "redirect:/persons";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    personDAO.delete(id);
    return "redirect:/persons";
  }

}
