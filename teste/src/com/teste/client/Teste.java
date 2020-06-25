package com.teste.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Teste implements EntryPoint {
	private CellTable<Person> cellTable;
	private HorizontalPanel horizontalPanel;
	private List<Person> persons;

	private Label nameLabel = new Label("Nome:");
	private final TextBox nameBox = new TextBox();

	private Label birthLabel = new Label("Data de Nascimento:");
	private final DateBox birthDate = new DateBox();

	private Label genreLabel = new Label("Sexo:");
	private final RadioButton genreF = new RadioButton("group", "F");
	private final RadioButton genreM = new RadioButton("group", "M");

	private Label addressLabel = new Label("Endereço:");
	private final TextBox addressBox = new TextBox();

	private Person currentPerson;

	public void onModuleLoad() {
		persons = new ArrayList<Person>();
		currentPerson = null;

		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(20);

		createForm();
		createCellTable();

		RootPanel.get("container").add(horizontalPanel);
	}

	private void createForm() {
		genreF.setText("Feminino");
		genreM.setText("Masculino");

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(genreF);
		hPanel.add(genreM);

		Button button = new Button("Salvar");

		Grid grid = new Grid(5, 2);

		grid.setWidget(0, 0, nameLabel);
		grid.setWidget(0, 1, nameBox);
		grid.setWidget(1, 0, birthLabel);
		grid.setWidget(1, 1, birthDate);
		grid.setWidget(3, 0, genreLabel);
		grid.setWidget(3, 1, hPanel);
		grid.setWidget(2, 0, addressLabel);
		grid.setWidget(2, 1, addressBox);
		grid.setWidget(4, 1, button);

		HorizontalPanel formpanel = new HorizontalPanel();
		formpanel.addStyleName("form");

		formpanel.add(grid);

		horizontalPanel.add(formpanel);

		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentPerson == null) {
					Person newPerson = new Person();
					newPerson.setName(nameBox.getValue());
					newPerson.setAddress(addressBox.getValue());
					newPerson.setBirthDate(birthDate.getValue());
					newPerson.setGenre(genreF.getValue() ? "F" : "M");

					persons.add(newPerson);
					
					Window.alert("Dados Cadastrados!");
				}else {
					for (Person p : persons) {
						if(p.getId() == currentPerson.getId()) {
							currentPerson.setName(nameBox.getValue());
							currentPerson.setAddress(addressBox.getValue());
							currentPerson.setBirthDate(birthDate.getValue());
							currentPerson.setGenre(genreF.getValue() ? "F" : "M");
							
							Window.alert("Dados Atualizados!");
							break;
						}
					}
				}

				cellTable.setRowCount(persons.size(), true);
				cellTable.setRowData(0, persons);
				cellTable.redraw();
			}
		});
	}

	private void createCellTable() {
		cellTable = new CellTable<Person>();
		// The policy that determines how keyboard selection will work. Keyboard
		// selection is enabled.
		cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a text columns to show the details.
		TextColumn<Person> colName = new TextColumn<Person>() {
			@Override
			public String getValue(Person object) {
				return object.getName();
			}
		};
		cellTable.addColumn(colName, "Nome");

		TextColumn<Person> colBirthDate = new TextColumn<Person>() {
			@Override
			public String getValue(Person object) {
				return DateTimeFormat.getFormat("dd/MM/yyyy").format(object.getBirthDate());
			}
		};
		cellTable.addColumn(colBirthDate, "Data de Nascimento");

		TextColumn<Person> colAge = new TextColumn<Person>() {
			@Override
			public String getValue(Person object) {
				return object.getAge() + "";
			}
		};
		cellTable.addColumn(colAge, "Idade");

		TextColumn<Person> colGenre = new TextColumn<Person>() {
			@Override
			public String getValue(Person object) {
				return object.getGenre();
			}
		};
		cellTable.addColumn(colGenre, "Sexo");

		TextColumn<Person> colAddress = new TextColumn<Person>() {
			@Override
			public String getValue(Person object) {
				return object.getAddress();
			}
		};
		cellTable.addColumn(colAddress, "Endereço");

		final SingleSelectionModel<Person> selectionModel = new SingleSelectionModel<Person>();

		cellTable.setSelectionModel(selectionModel);

		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			public void onSelectionChange(SelectionChangeEvent event) {

				currentPerson = selectionModel.getSelectedObject();
				if (currentPerson != null) {
					nameBox.setText(currentPerson.getName());
					addressBox.setText(currentPerson.getAddress());
					birthDate.setValue(currentPerson.getBirthDate());

					if (currentPerson.getGenre().equals("F")) {
						genreF.setValue(true);
					} else {
						genreM.setValue(true);
					}
				}
			}
		});

		persons.add(new Person((long) 1, "Débora", DateTimeFormat.getFormat("yyyy-MM-dd").parse("1993-04-01"), 27, "F",
				"Rua Rezala Simão"));
		persons.add(new Person((long) 2, "Yago", DateTimeFormat.getFormat("yyyy-MM-dd").parse("1992-11-17"), 27, "M",
				"Rua Rezala Simão"));

		cellTable.setRowCount(persons.size(), true);
		cellTable.setRowData(0, persons);

		VerticalPanel vp = new VerticalPanel();
		vp.setBorderWidth(1);
		vp.add(cellTable);

		horizontalPanel.add(vp);
	}

	private void metodo() {
		String url = "http://localhost:8080/gateway/project/person";

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			builder.sendRequest(null, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					exception.printStackTrace();

					// teste.setText("Deu erro");
				}

				public void onResponseReceived(Request request, Response response) {

					// teste.setText(response.getStatusCode() + "");
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
}
