package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Disciplina;
import model.services.DisciplinaService;

public class DisciplinaListController implements Initializable, DataChangeListener {

	private DisciplinaService service;

	@FXML
	private TableView<Disciplina> tableViewDisciplina;
	@FXML
	private TableColumn<Disciplina, Integer> tableColumnId;
	@FXML
	private TableColumn<Disciplina, String> tableColumnNome;
	@FXML
	private TableColumn<Disciplina, String> tableColumnProfessor;
	@FXML
	private TableColumn<Disciplina, String> tableColumnCurso;
	@FXML
	private TableColumn<Disciplina, Integer> tableColumnVagas;

	@FXML
	private TableColumn<Disciplina, Disciplina> tableColumnEDIT;

	@FXML
	private TableColumn<Disciplina, Disciplina> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Disciplina> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Disciplina obj = new Disciplina();
		createDialogForm(obj, "/gui/DisciplinaForm.fxml", parentStage);
	}

	public void setDisciplinaService(DisciplinaService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

//iniciar o comprotamento das colunas.
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnProfessor.setCellValueFactory(new PropertyValueFactory<>("professor"));
		tableColumnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		tableColumnVagas.setCellValueFactory(new PropertyValueFactory<>("vagas"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDisciplina.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Disciplina> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDisciplina.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Disciplina obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DisciplinaFormController controller = loader.getController();
			controller.setDisciplina(obj);
			controller.setDisciplinaService(new DisciplinaService());
			controller.subscribDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do Disciplina");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Disciplina, Disciplina>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Disciplina obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DisciplinaForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Disciplina, Disciplina>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Disciplina obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Disciplina obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("confirmar", "Tem certeza?");
		
		if(result.get()== ButtonType.OK){ {
			if(service==null) {
				throw new IllegalStateException("service was null");

			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Error ao remover", null, e.getMessage(), AlertType.ERROR);
			}
		}
		}
	}

}
