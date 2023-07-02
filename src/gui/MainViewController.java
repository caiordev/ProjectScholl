package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.AlunoService;
import model.services.CoordenadorService;
import model.services.DisciplinaService;
import model.services.ProfessorService;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemProfessor;
    @FXML
    private MenuItem menuItemAluno;
    @FXML
    private MenuItem menuItemCoordenador;
    @FXML
    private MenuItem menuItemDisciplina;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemProfessorAction() {
    	loadView("/gui/ProfessorList.fxml", (ProfessorListController controller )->{
    		controller.setProfessorService(new ProfessorService());
            controller.updateTableView();
    	});
    }
    @FXML
    public void onMenuItemAlunoAction() {
    	loadView("/gui/AlunoList.fxml", (AlunoListController controller )->{
    		controller.setAlunoService(new AlunoService());
            controller.updateTableView();
    	});
    }
    @FXML
    public void onMenuItemCoordenadorAction() {
    	loadView("/gui/CoordenadorList.fxml", (CoordenadorListController controller )->{
    		controller.setCoordenadorService(new CoordenadorService());
            controller.updateTableView();
    	});
    }
    @FXML
    public void onMenuItemDisciplinaAction() {
    	loadView("/gui/DisciplinaList.fxml", (DisciplinaListController controller )->{
    		controller.setDisciplinaService(new DisciplinaService());
            controller.updateTableView();
    	});
    }
    @FXML
    public void onMenuItemAboutAction() {
    	loadView("/gui/About.fxml", x->{});
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    

}
