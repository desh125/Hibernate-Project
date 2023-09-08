package lk.ijse.orm.hibernate_project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.orm.hibernate_project.bo.BoFactory;
import lk.ijse.orm.hibernate_project.bo.custom.StudentBo;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;
import lk.ijse.orm.hibernate_project.utils.ValidationUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtId1;

    @FXML
    private TableView<StudentDTO> tableView;

    @FXML
    private TableColumn<StudentDTO, String> colId;

    @FXML
    private TableColumn<StudentDTO, String> colName;

    @FXML
    private TableColumn<StudentDTO, String> colAddress;

    @FXML
    private TableColumn<StudentDTO, String> colNumber;

    @FXML
    private TableColumn<StudentDTO, String> colDob;

    @FXML
    private TableColumn<StudentDTO, String> colGender;

    private StudentBo studentBo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        colName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        // Create an instance of StudentBo
        studentBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.STUDENT);

        // Populate data into the TableView
        refreshTableView();

        // Set up double-click event handler for the TableView
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Detect double-click
                StudentDTO selectedStudent = tableView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    // Populate fields with selected student data for update or delete
                    txtId.setText(selectedStudent.getStudentId());
                    txtName.setText(selectedStudent.getFullName());
                    txtDOB.setText(selectedStudent.getDateOfBirth());
                    txtAddress.setText(selectedStudent.getAddress());
                    txtNumber.setText(selectedStudent.getContactNumber());
                    txtGender.setText(selectedStudent.getGender());


                    txtId.setDisable(true);


                }
            }
        });
    }

    @FXML
    private void btnSaveOnAction() {

        studentBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.STUDENT);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(txtId.getText());
        studentDTO.setFullName(txtName.getText());
        studentDTO.setAddress(txtAddress.getText());
        studentDTO.setContactNumber(txtNumber.getText());
        studentDTO.setDateOfBirth(txtDOB.getText());
        studentDTO.setGender(txtGender.getText());

        if (!ValidationUtils.validatePhoneNumber(txtNumber.getText())) {
            showAlert("Room Management", "Invalid Phone Number!", StudentController.SelectType.WARNING);
            return;
        }
        if (!ValidationUtils.isValidString(txtName.getText())) {
            showAlert("Room Management", "Invalid Name!", StudentController.SelectType.WARNING);
            return;
        }


        boolean saved = studentBo.SaveStudent(studentDTO);

        if (saved) {
            showAlert("Student Management", "Successfully Saved Student Details!", SelectType.INFORMATION);
            setDefault();
        } else {
            showAlert("Student Management", "Failed to Save Student Details!", SelectType.ERROR);
        }

        refreshTableView();
    }

    @FXML
    private void btnUpdateOnAction() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(txtId.getText());
        studentDTO.setFullName(txtName.getText());
        studentDTO.setAddress(txtAddress.getText());
        studentDTO.setContactNumber(txtNumber.getText());
        studentDTO.setDateOfBirth(txtDOB.getText());
        studentDTO.setGender(txtGender.getText());

        if (!ValidationUtils.validatePhoneNumber(txtNumber.getText())) {
            showAlert("Room Management", "Invalid Phone Number!", StudentController.SelectType.WARNING);
            return;
        }
        if (!ValidationUtils.isValidString(txtName.getText())) {
            showAlert("Room Management", "Invalid Name!", StudentController.SelectType.WARNING);
            return;
        }


        boolean update = studentBo.UpdateStudent(studentDTO);

        if (update) {
            showAlert("Student Management", "Successfully Updated Student Details!", SelectType.INFORMATION);
            setDefault();
            refreshTableView();
        } else {
            showAlert("Student Management", "Something Went Wrong!", SelectType.ERROR);
        }
        refreshTableView();
        txtId.setDisable(false);
    }

    @FXML
    private void btnDeleteOnAction() {


        String studentId = txtId.getText();

        // Retrieve the student from the database using the studentId
        StudentDTO studentDTO = studentBo.getStudent(studentId);

        if (studentDTO != null) {
            boolean delete = studentBo.DeleteStudent(studentDTO);

            if (delete) {
                showAlert("Student Management", "Successfully Deleted Student!", SelectType.INFORMATION);
                setDefault();
                refreshTableView();
            } else {
                showAlert("Student Management", "Something Went Wrong!", SelectType.ERROR);
            }
        } else {
            showAlert("Student Management", "Student not found with ID: " + studentId, SelectType.WARNING);
        }
        refreshTableView();
        txtId.setDisable(false);
    }


    @FXML
    private void codeSearchOnAction() {
        // Handle the Search button action here
        String studentIdValue = txtId1.getText().trim(); // Trim to remove leading/trailing spaces

        if (studentIdValue.isEmpty()) {
            // If the input is empty, refresh the TableView to show all students
            refreshTableView();
        } else {
            // Use studentBo to fetch the student by ID
            StudentDTO studentDTO = studentBo.getStudent(studentIdValue);

            if (studentDTO != null) {
                // Populate the retrieved data into the form fields
                txtId.setText(studentDTO.getStudentId());
                txtName.setText(studentDTO.getFullName());
                txtDOB.setText(studentDTO.getDateOfBirth());
                txtAddress.setText(studentDTO.getAddress());
                txtNumber.setText(studentDTO.getContactNumber());
                txtGender.setText(studentDTO.getGender());

                // Clear the TableView and add the retrieved student
                tableView.getItems().clear();
                tableView.getItems().add(studentDTO);
            } else {
                // If no result is found, refresh the TableView to show all students
                refreshTableView();
                showAlert("Student Management", "Student Not Found!", SelectType.WARNING);
            }
        }
    }


    private void refreshTableView() {
        List<StudentDTO> students = studentBo.getAllStudents();
        ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(students);
        tableView.setItems(studentList);
    }


    private void setDefault() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtNumber.clear();
        txtGender.clear();
        txtDOB.clear();
        txtId1.clear();
    }

    private void showAlert(String title, String content, SelectType type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (type == SelectType.INFORMATION) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else if (type == SelectType.WARNING) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else if (type == SelectType.ERROR) {
            alert = new Alert(Alert.AlertType.ERROR);
        }

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private enum SelectType {
        INFORMATION, WARNING, ERROR
    }
}
