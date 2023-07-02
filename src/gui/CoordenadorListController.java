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
import model.entities.Coordenador;
import model.services.CoordenadorService;

public class CoordenadorListController implements Initializable, DataChangeListener {

	private CoordenadorService service;

	@FXML
	private TableView<Coordenador> tableViewCoordenador;
	@FXML
	private TableColumn<Coordenador, Integer> tableColumnId;
	@FXML
	private TableColumn<Coordenador, String> tableColumnNome;
	@FXML
	private TableColumn<Coordenador, String> tableColumnCpf;
	@FXML
	private TableColumn<Coordenador, String> tableColumnEmail;
	@FXML
	private TableColumn<Coordenador, String> tableColumnDepartamento;

	@FXML
	private TableColumn<Coordenador, Coordenador> tableColumnEDIT;

	@FXML
	private TableColumn<Coordenador, Coordenador> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	private ObservableList<Coordenador> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Coordenador obj = new Coordenador();
		createDialogForm(obj, "/gui/CoordenadorForm.fxml", parentStage);
	}

	public void setCoordenadorService(CoordenadorService service) {
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
		tableColumnDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCoordenador.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Coordenador> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCoordenador.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Coordenador obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CoordenadorFormController controller = loader.getController();
			controller.setCoordenador(obj);
			controller.setCoordenadorService(new CoordenadorService());
			controller.subscribDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do Coordenador");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Coordenador, Coordenador>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Coordenador obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/CoordenadorForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Coordenador, Coordenador>() {
			private final Button button = new Button("remover");

			@Override
			protected void updateItem(Coordenador obj, boolean empty) {
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

	private void removeEntity(Coordenador obj) {
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
