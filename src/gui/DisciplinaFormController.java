package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Disciplina;
import model.exceptions.ValidationException;
import model.services.DisciplinaService;

public class DisciplinaFormController implements Initializable{
	private Disciplina entity;
	
	private DisciplinaService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtProfessor;
	@FXML
	private TextField txtCurso;
	@FXML
	private TextField txtVagas;
	@FXML
	private Label labelErrorName;
	
	
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	
	public void setDisciplina(Disciplina entity) {
		this.entity = entity;
	}
	
	public void setDisciplinaService(DisciplinaService service) {
		this.service = service;
	}
	
	
	public void subscribDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
		entity = getFormData();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Disciplina getFormData() {
		Disciplina obj = new Disciplina();
		
		ValidationException exception = new ValidationException("Validation error");
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtNome.getText() == null||txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Field can't be empty");
		}
		obj.setNome(txtNome.getText());
		obj.setProfessor(txtProfessor.getText());
		obj.setCurso(txtCurso.getText());
		obj.setVagas(Utils.tryParseToInt(txtVagas.getText()));
		if(exception.getErrors().size()>0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldDouble(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtProfessor.setText(entity.getProfessor());
		txtCurso.setText(entity.getCurso());
		txtVagas.setText(String.valueOf(entity.getVagas()));
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("nome")) {
			labelErrorName.setText(errors.get("nome"));
		}
	}

}
