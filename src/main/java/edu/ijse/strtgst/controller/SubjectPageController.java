package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.context.AppContext;
import edu.ijse.strtgst.dto.SubjectDto;
import edu.ijse.strtgst.dto.tm.SubjectTM;
import edu.ijse.strtgst.model.SubjectModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.DateUtil;
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
        loadTableData();
        updateDateLabel();
        appContext.setSubjectPageController(this);
        Navigation.navigateTo(ancSubjectContainer, View.DEFAULT_SUBJECT);
    }

    public void addNewSubject(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancSubjectContainer, View.ADD_SUBJECT);
    }

    public void setupTableColumn() {
        columnSubjectName.setCellValueFactory(new PropertyValueFactory<>("subName"));
        columnSubjectDescription.setCellValueFactory(new PropertyValueFactory<>("subDescription"));
        columnSubjectMarks.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        columnSubjectGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        columnSubjectMarks.setCellFactory(c -> new TableCell<>(){
            @Override
            protected void updateItem(String marks, boolean empty) {
                super.updateItem(marks, empty);
                if (empty || marks == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(marks);
                    label.setStyle(getMarksStyle(marks));
                    setGraphic(label);
                    setText(null);
                }
            }
        });

        loadTableData();
    }

    public void loadTableData(){
        try {
            ArrayList<SubjectDto> allSubjects = subjectModel.getAllSubjects();
            tblSubject.setItems(FXCollections.observableArrayList(
                    allSubjects.stream().map(subjectDto -> new SubjectTM(
                                    subjectDto.getSubId(),
                                    subjectDto.getStudId(),
                                    subjectDto.getSubName(),
                                    subjectDto.getSubDescription(),
                                    subjectDto.getTotalMarks(),
                                    calculateGrade(subjectDto.getTotalMarks())
                                    )
                            ).toList()
            ));
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when loading table data");
            e.printStackTrace();
        }
    }

    private String getMarksStyle(String marksStr) {
        try {
            double marks = Double.parseDouble(marksStr);
            if (marks >= 75) {
                return "-fx-background-color: #27ae60; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
            } else if (marks >= 50) {
                return "-fx-background-color: #f39c12; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
            } else {
                return "-fx-background-color: #e74c3c; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
            }
        } catch (NumberFormatException e) {
            return "-fx-background-color: #bdc3c7; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
        }
    }


    public void onClickSubjectTable(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancSubjectContainer, View.ADD_SUBJECT);
        SubjectTM selectedSubject = tblSubject.getSelectionModel().getSelectedItem();
        SubjectFormController subjectFormController = appContext.getSubjectFormController();
        if (selectedSubject != null){
            subjectFormController.populateFormForEdit(selectedSubject);
        }
    }

    private String calculateGrade(String marksStr) {
        try {
            double marks = Double.parseDouble(marksStr);
            if (marks >= 75) return "A";
            else if (marks >= 65) return "B";
            else if (marks >= 55) return "C";
            else if (marks >= 45) return "D";
            else return "F";
        } catch (NumberFormatException e) {
            return "-";
        }
    }

    private void updateDateLabel() {
        labelDate.setText(DateUtil.setDate());
    }
}
