package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.context.AppContext;
import edu.ijse.strtgst.dto.SubjectDto;
import edu.ijse.strtgst.dto.tm.SubjectTM;
import edu.ijse.strtgst.model.SubjectModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectPageController implements Initializable {
    public AnchorPane ancSubject;
    public AnchorPane ancSubjectContainer;
    public Label labelDate;
    public TableView<SubjectTM> tblSubject;
    public TableColumn<SubjectTM, String> columnSubjectName;
    public TableColumn<SubjectTM, String> columnSubjectDescription;
    public TableColumn<SubjectTM, String> columnSubjectMarks;
    public TableColumn<SubjectTM, String> columnSubjectGrade;

    private final SubjectModel subjectModel = new SubjectModel();
    private final AppContext appContext = AppContext.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumn();
        appContext.setSubjectPageController(this);
        Navigation.navigateTo(ancSubjectContainer, View.DEFAULT_SUBJECT);
    }

    public void addNewSubject(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancSubjectContainer, View.ADD_SUBJECT);
    }

    public void setupTableColumn() {
        columnSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        columnSubjectDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));
        columnSubjectMarks.setCellValueFactory(new PropertyValueFactory<>("subjectMarks"));
        columnSubjectGrade.setCellValueFactory(new PropertyValueFactory<>("subjectGrade"));

        columnSubjectMarks.setCellFactory(c -> new TableCell<>(){
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if(empty || status == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(status);
                    label.setStyle(getStatusStyle(status));
                    setGraphic(label);
                    setText(null);
                }
            }
        });

        loadTableData();
    }

    private void loadTableData(){
        try {
            ArrayList<SubjectDto> allSubjects = subjectModel.getAllSubjects();
            tblSubject.setItems(FXCollections.observableArrayList(
                    allSubjects.stream().map(subjectDto -> new SubjectTM(
                                    subjectDto.getSubId(),
                                    subjectDto.getStudId(),
                                    subjectDto.getSubName(),
                                    subjectDto.getSubDescription(),
                                    subjectDto.getTotalMarks()
                                    )
                            ).toList()
            ));
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when loading table data");
            e.printStackTrace();
        }
    }

    private String getStatusStyle(String status) {
        return switch (status.toLowerCase()) {
            case "completed" -> "-fx-background-color: #11C759; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            case "pending" -> "-fx-background-color: #f1c40f; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            case "overdue" -> "-fx-background-color: #d90429; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            default -> "-fx-background-color: #bdc3c7; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
        };
    }

    public void onClickSubjectTable(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancSubjectContainer, View.ADD_SUBJECT);
        SubjectTM selectedSubject = tblSubject.getSelectionModel().getSelectedItem();
        SubjectFormController subjectFormController = appContext.getSubjectFormController();
        if (selectedSubject != null){
            subjectFormController.populateFormForEdit(selectedSubject);
        }
    }
}
