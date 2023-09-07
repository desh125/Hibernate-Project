package lk.ijse.orm.hibernate_project.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.orm.hibernate_project.bo.BoFactory;
import lk.ijse.orm.hibernate_project.bo.custom.ReservationBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindRemainController implements Initializable {
    @FXML
    private TextField txtId1;

    @FXML
    private TableView<ReservationDTO> tableView;

    @FXML
    private TableColumn<ReservationDTO, String> colResId;

    @FXML
    private TableColumn<ReservationDTO, String> colRoomTypeId;

    @FXML
    private TableColumn<ReservationDTO, String> colStudentNameId;

    @FXML
    private TableColumn<ReservationDTO, String> colStatusId;

    @FXML
    private TableColumn<ReservationDTO, String> colStudentId;

    private ReservationBo reservationBo;

    public FindRemainController() {
        reservationBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.RESERVATION);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colStudentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getStudentId()));
        colStudentNameId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        colStatusId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));


        reservationBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.RESERVATION);

        // Fetch and display unpaid students
        fetchUnpaidStudents();
    }

    private void fetchUnpaidStudents() {
        try {
            List<ReservationDTO> unpaidStudents = reservationBo.getUnpaidStudentsWithJoinQuery();
            ObservableList<ReservationDTO> studentList = FXCollections.observableArrayList(unpaidStudents);
            tableView.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @FXML
    private void codeSearchOnAction() {
        try {
            String studentId = txtId1.getText();
            List<ReservationDTO> reservations = reservationBo.getUnpaidReservationsByStudentId(studentId);

            if (reservations != null && !reservations.isEmpty()) {
                populateTableView(reservations);
            } else {
                clearTableView();
                // Handle the case where no unpaid reservations are found for the student.
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            // Handle exceptions
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void populateTableView(List<ReservationDTO> reservations) {
        // Clear previous data in the TableView
        tableView.getItems().clear();

        // Add the retrieved reservations to the TableView
        tableView.getItems().addAll(reservations);
    }

    private void clearTableView() {
        // Clear all data from the TableView
        tableView.getItems().clear();
    }
}
