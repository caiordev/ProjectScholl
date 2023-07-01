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
import model.entities.Professor;
import model.services.ProfessorService;

public class ProfessorListController implements Initializable, DataChangeListener {

	private ProfessorService service;

	@FXML
	private TableView<Professor> tableViewProfessor;
	@FXML
	private TableColumn<Professor, Integer> tableColumnId;
	@FXML
	private TableColumn<Professor, String> tableColumnNome;
	@FXML
	private TableColumn<Professor, String> tableColumnCpf;
	@FXML
	private TableColumn<Professor, String> tableColumnEmail;
	@FXML
	private TableColumn<Professor, Integer> tableColumnNumero;
	@FXML
	private TableColumn<Professor, Integer> tableColumnSiaep;

	@FXML
	private TableColumn<Professor, Professor> tableColumnEDIT;

	@FXML
	private TableColumn<Professor, Professor> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Professor> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Professor obj = new Professor();
		createDialogForm(obj, "/gui/ProfessorForm.fxml", parentStage);
	}

	public void setProfessorService(ProfessorService service) {
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
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnSiaep.setCellValueFactory(new PropertyValueFactory<>("siaep"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProfessor.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Professor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProfessor.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Professor obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProfessorFormController controller = loader.getController();
			controller.setProfessor(obj);
			controller.setProfessorService(new ProfessorService());
			controller.subscribDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do professor");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Professor, Professor>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ProfessorForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Professor, Professor>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
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

	private void removeEntity(Professor obj) {
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
