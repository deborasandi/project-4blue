package com.project.crudproject;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.project.crudproject.http.AgeHttp;
import com.project.crudproject.http.PersonHttp;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
@Theme("mytheme")
public class MyUI extends UI {

	final VerticalLayout layout = new VerticalLayout();

	private String URL = "http://localhost:8080/gateway/project/person";
	private String URL_AGE = "http://localhost:8080/gateway/ageservice/age";

	private Person currentPerson;

	private List<Person> data;

	private Grid<Person> grid;

	private TextField name;

	private DateField birthDate;

	private RadioButtonGroup<String> genre;

	private TextField address;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		FormLayout form = new FormLayout();

		final HorizontalLayout buttonPane = new HorizontalLayout();

		Button newButton = new Button("Nova Pessoa");
		newButton.setIcon(VaadinIcons.PLUS);

		newButton.addClickListener(clickEvent -> {
			clearForm();
		});

		Button deleteButton = new Button("Excluir Pessoa");
		deleteButton.setIcon(VaadinIcons.TRASH);

		deleteButton.addClickListener(clickEvent -> {
			if (currentPerson != null) {
				PersonHttp.delete(URL, currentPerson.getId());
				populateGrid();
				clearForm();
			}
		});

		buttonPane.addComponent(newButton);
		buttonPane.addComponent(deleteButton);

		layout.addComponent(buttonPane);

		name = new TextField("Nome");
		name.setIcon(VaadinIcons.USER);
		name.setRequiredIndicatorVisible(true);
		form.addComponent(name);

		birthDate = new DateField("Data de Nascimento");
		birthDate.setIcon(VaadinIcons.CALENDAR);
		birthDate.setValue(LocalDate.now());
		birthDate.setRequiredIndicatorVisible(true);
		form.addComponent(birthDate);

		genre = new RadioButtonGroup<>();
		genre.setItems("Feminino", "Masculino");
		genre.setValue("Feminino");
		genre.setCaption("Sexo");
		genre.setIcon(VaadinIcons.USERS);
		genre.setRequiredIndicatorVisible(true);
		genre.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		form.addComponent(genre);

		address = new TextField("Endereço");
		address.setIcon(VaadinIcons.ROAD);
		address.setRequiredIndicatorVisible(true);
		form.addComponent(address);

		Button saveButton = new Button("Salvar");
		saveButton.setIcon(VaadinIcons.CHECK);
		form.addComponent(saveButton);

		saveButton.addClickListener(clickEvent -> {
			if (currentPerson == null) {
				Person person = new Person();

				person.setName(name.getValue());
				
				int age = AgeHttp.calculate(URL_AGE, birthDate.getValue().toString());
				person.setAge(age);
				
				person.setBirthDate(birthDate.getValue().toString());
				person.setGenre(genre.getValue());
				person.setAddress(address.getValue());

				PersonHttp.create(URL, person);
			}else {
				currentPerson.setName(name.getValue());
				
				int age = AgeHttp.calculate(URL_AGE, birthDate.getValue().toString());
				currentPerson.setAge(age);
				
				currentPerson.setBirthDate(birthDate.getValue().toString());
				currentPerson.setGenre(genre.getValue());
				currentPerson.setAddress(address.getValue());
				
				PersonHttp.update(URL, currentPerson);
			}

			populateGrid();
			clearForm();
		});

		grid = new Grid<>();
		grid.addColumn(Person::getName).setCaption("Nome");
		grid.addColumn(Person::getBirthDate).setCaption("Data de Nascimento");
		grid.addColumn(Person::getAge).setCaption("Idade");
		grid.addColumn(Person::getGenre).setCaption("Sexo");
		grid.addColumn(Person::getAddress).setCaption("Endereço");
		grid.setSizeFull();

		grid.addItemClickListener(l -> {
			currentPerson = l.getItem();

			name.setValue(currentPerson.getName());
			birthDate.setValue(LocalDate.parse(currentPerson.getBirthDate()));
			genre.setValue(currentPerson.getGenre().equals("F") ? "Feminino" : "Masculino");
			address.setValue(currentPerson.getAddress());
		});

		layout.addComponents(form, grid);
		layout.setSizeFull();
		layout.setExpandRatio(grid, 1.0f);

		setContent(layout);

		populateGrid();
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	private void populateGrid() {
		data = PersonHttp.list(URL);

		if (data != null) {
			grid.setItems(data);
		}
	}

	private void clearForm() {
		name.clear();
		birthDate.setValue(LocalDate.now());
		genre.setValue("Feminino");
		address.clear();

		currentPerson = null;
	}
}
