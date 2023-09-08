package lk.ijse.orm.hibernate_project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.orm.hibernate_project.bo.BoFactory;
import lk.ijse.orm.hibernate_project.bo.custom.ReservationBo;
import lk.ijse.orm.hibernate_project.bo.custom.RoomBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private TextField reservationId;

    @FXML
    private TextField studentId;

    @FXML
    private TextField roomTypeId;

    @FXML
    private JFXComboBox<String> status;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker lastDate;

    @FXML
    private TextField txtId1;

    @FXML
    private JFXButton searchButton;


    @FXML
    private ComboBox<String> keyMoneyStatus;

    @FXML
    private TableView<ReservationDTO> tableView;

    @FXML
    private TableColumn<ReservationDTO, String> colResId;

    @FXML
    private TableColumn<ReservationDTO, String> colRoomTypeId;

    @FXML
    private TableColumn<ReservationDTO, String> colStatusId;

    @FXML
    private TableColumn<ReservationDTO, String> colStartDateId;

    @FXML
    private TableColumn<ReservationDTO, String> colStudentId;

    @FXML
    private TableColumn<ReservationDTO, String> colLastDateId;


    private List<String> dataList = new ArrayList<>();
    private ReservationBo reservationBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.RESERVATION);

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        setDataForComboBox();
        setDataForComboBox2();
        colResId.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
        colStatusId.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colStartDateId.setCellValueFactory(new PropertyValueFactory<>("OrderDateTime"));
        colLastDateId.setCellValueFactory(new PropertyValueFactory<>("LastDate"));
        colRoomTypeId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomTypeId()));
        colStudentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getStudentId()));


        reservationBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.RESERVATION);
        refreshTableView();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Detect double-click
                ReservationDTO selectedRoom = tableView.getSelectionModel().getSelectedItem();
                if (selectedRoom != null) {
                    reservationId.setText(selectedRoom.getReservationId());
                    roomTypeId.setText(selectedRoom.getRoom().ToDto().getRoomTypeId());
                    status.setValue(selectedRoom.getStatus());
                    startDate.setValue(selectedRoom.getOrderDateTime().toLocalDate());
                    studentId.setText(selectedRoom.getStudent().getStudentId());
                    lastDate.setValue(selectedRoom.getLastDate().toLocalDate());
                    // Disable fields that should not be edited during update
                    reservationId.setDisable(true);


                }
            }
        });
    }


    private void refreshTableView() {
        // Fetch data from your Bo and populate the TableView
        List<ReservationDTO> reservations = reservationBo.getAllReservations();
        ObservableList<ReservationDTO> reservationList = FXCollections.observableArrayList(reservations);
        tableView.setItems(reservationList);

    }

    private void clearFields() {
        reservationId.clear();
        studentId.clear();
        roomTypeId.clear();
        status.setValue(null);
        startDate.setValue(null);
        lastDate.setValue(null);
    }


    @FXML
    private void btnSaveOnAction() {
        String studentIdText = studentId.getText().trim();
        String roomTypeIdText = roomTypeId.getText().trim();
        LocalDate localLastDate = lastDate.getValue();
        LocalDate localStartDate = startDate.getValue();

        // Check if any of the fields is empty
        if (studentIdText.isEmpty() || roomTypeIdText.isEmpty() || localLastDate == null || localStartDate == null) {
            showAlert("Reservation Management", "Fill in all fields", SelectType.ERROR);
            return; // Stop processing if any field is empty
        }

        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        if (student == null) {
            showAlert("Reservation Management", "Invalid Student ID", SelectType.ERROR);
            return; //
        }

        if (room == null) {
            showAlert("Reservation Management", "Invalid Room Type ID", SelectType.ERROR);
            return; //
        }
        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.sql.Date sqlLastDate = java.sql.Date.valueOf(localLastDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setLastDate(sqlLastDate);

        java.sql.Date sqlStartDate = java.sql.Date.valueOf(localStartDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setOrderDateTime(sqlStartDate);

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setStudentName(student.getFullName());

        RoomDTO roomDTOAvailable = CalculateAvailableRoom(room, SelectTypeForCalculateRoom.SAVE);
        String save = reservationBo.SaveReservationDetails(reservationDTO, roomDTOAvailable);

        if (save != ("-1")) {
            showAlert("Reservation Management"
                    , "Successfully Saved Reservation Details !"
                    , SelectType.INFORMATION);
            setDefault();
        } else {
            setDefault();
            showAlert("Reservation Management"
                    , "Something Wrong !" + "\n" + "Duplicate ID Entry"
                    , SelectType.WARNING);
        }
        refreshTableView();
        clearFields();
    }

    @FXML
    private void btnUpdateOnAction() {
        String studentIdText = studentId.getText().trim();
        String roomTypeIdText = roomTypeId.getText().trim();
        LocalDate localLastDate = lastDate.getValue();
        LocalDate localStartDate = startDate.getValue();

        // Check if any of the fields is empty
        if (studentIdText.isEmpty() || roomTypeIdText.isEmpty() || localLastDate == null || localStartDate == null) {
            showAlert("Reservation Management", "Fill in all fields", SelectType.ERROR);
            return; // Stop processing if any field is empty
        }

        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());
        if (student == null) {
            showAlert("Reservation Management", "Invalid Student ID", SelectType.ERROR);
            return; //
        }

        if (room == null) {
            showAlert("Reservation Management", "Invalid Room Type ID", SelectType.ERROR);
            return; //
        }
        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.sql.Date sqlLastDate = java.sql.Date.valueOf(localLastDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setLastDate(sqlLastDate);
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(localStartDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setOrderDateTime(sqlStartDate);

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setStudentName(student.getFullName());

        try {
            RoomDTO roomDTOAvailable = CalculateAvailableRoom(room, SelectTypeForCalculateRoom.UPDATE);
            boolean update = reservationBo.UpdateReservationDetails(reservationDTO, roomDTOAvailable);

            if (update) {
                showAlert("Reservation Management"
                        , "Successfully Updated Reservation Details !"
                        , SelectType.INFORMATION);
                setDefault();
            } else {
                setDefault();
                showAlert("Reservation Management"
                        , "Something Wrong !"
                        , SelectType.ERROR);
            }

        } catch (Exception e) {
            throw e;
        }
        refreshTableView();
        clearFields();
        reservationId.setDisable(false);
    }

    @FXML
    private void btnDeleteOnAction() {

        String studentIdText = studentId.getText().trim();
        String roomTypeIdText = roomTypeId.getText().trim();
        LocalDate localLastDate = lastDate.getValue();
        LocalDate localStartDate = startDate.getValue();

        // Check if any of the fields is empty
        if (studentIdText.isEmpty() || roomTypeIdText.isEmpty() || localLastDate == null || localStartDate == null) {
            showAlert("Reservation Management", "Fill in all fields", SelectType.ERROR);
            return; // Stop processing if any field is empty
        }

        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());
        if (student == null) {
            showAlert("Reservation Management", "Invalid Student ID", SelectType.ERROR);
            return; //
        }

        if (room == null) {
            showAlert("Reservation Management", "Invalid Room Type ID", SelectType.ERROR);
            return; //
        }
        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());
        java.sql.Date sqlLastDate = java.sql.Date.valueOf(localLastDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setLastDate(sqlLastDate);
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(localStartDate); // Convert LocalDate to java.sql.Date
        reservationDTO.setOrderDateTime(sqlStartDate);

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setStudentName(student.getFullName());

        RoomDTO roomDTOAvailable = CalculateAvailableRoom(room, SelectTypeForCalculateRoom.DELETE);
        boolean update = reservationBo.DeleteReservationDetails(reservationDTO, roomDTOAvailable);

        if (update) {
            showAlert("Reservation Management"
                    , "Successfully Deleted Reservation Details !"
                    , SelectType.INFORMATION);
            setDefault();
        } else {
            setDefault();
            showAlert("Reservation Management"
                    , "Something Wrong !"
                    , SelectType.ERROR);
        }
        //    reservationidtxt.setDisable(false);
        refreshTableView();
        clearFields();
        reservationId.setDisable(false);
    }

    @FXML
    private void codeSearchOnAction() {
        // Handle the Search button action here
        String reservationId = txtId1.getText();

        // Use reservationBo to fetch the reservation by ID
        ReservationDTO reservationDTO = reservationBo.getReservationDetails(reservationId);


    }


    private void setDataForComboBox() {
        dataList.add("PAID");
        dataList.add("PENDING");

        ObservableList<String> observableList = FXCollections.observableArrayList(dataList);
        status.setItems(observableList);
        dataList.clear();
    }

    private void setDataForComboBox2() {
        dataList.add("PAID");
        dataList.add("PENDING");

        ObservableList<String> observableList = FXCollections.observableArrayList(dataList);
        keyMoneyStatus.setItems(observableList);
        dataList.clear();
    }

    private void setDefault() {
        // reservationId.clear();
        studentId.clear();
        // date.setValue(null);
        roomTypeId.clear();
        status.setValue(null);
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


    private String getCurrentDateTime() {
        String formattedTimestamp;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (formattedTimestamp = dateFormat.format(timestamp));
    }

    private RoomDTO CalculateAvailableRoom(RoomDTO room, SelectTypeForCalculateRoom select) {
        if (select == SelectTypeForCalculateRoom.SAVE) {
            RoomDTO roomDTO = new RoomDTO();

            int total = room.getQty();
            total = (total - 1);

            roomDTO.setRoomTypeId(room.getRoomTypeId());
            roomDTO.setType(room.getType());
            roomDTO.setKeyMoney(room.getKeyMoney());
            roomDTO.setQty(total);

            return roomDTO;
        } else if (select == SelectTypeForCalculateRoom.UPDATE) {
            ReservationDTO reservationDetails = reservationBo.getReservationDetails(reservationId.getText());
            if (roomTypeId.getText().equals(reservationDetails.getRoom().getRoomTypeId())) {
                RoomDTO roomDTO = new RoomDTO();

                roomDTO.setRoomTypeId(room.getRoomTypeId());
                roomDTO.setType(room.getType());
                roomDTO.setKeyMoney(room.getKeyMoney());
                roomDTO.setQty(room.getQty());

                return roomDTO;
            } else {
                RoomBo roomBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.ROOM);
                RoomDTO roomDTOUpdate = new RoomDTO();

                int total = room.getQty();
                total = (total - 1);

                roomDTOUpdate.setRoomTypeId(room.getRoomTypeId());
                roomDTOUpdate.setType(room.getType());
                roomDTOUpdate.setKeyMoney(room.getKeyMoney());
                roomDTOUpdate.setQty(total);

                roomBo.UpdateRoom(roomDTOUpdate);


                return roomDTOUpdate;


            }
        } else if (select == SelectTypeForCalculateRoom.DELETE) {
            ReservationDTO reserversiondetails = reservationBo.getReservationDetails(reservationId.getText());

            RoomDTO roomDTOReturn = new RoomDTO();

            roomDTOReturn.setRoomTypeId(reserversiondetails.getRoom().getRoomTypeId());
            roomDTOReturn.setKeyMoney(reserversiondetails.getRoom().getKeyMoney());
            roomDTOReturn.setQty((reserversiondetails.getRoom().getQty() + 1));
            roomDTOReturn.setType(reserversiondetails.getRoom().getType());

            return roomDTOReturn;
        } else {
            return null;
        }

    }

    public void codeSearchOnStatusAction(javafx.event.ActionEvent actionEvent) {
        String selectedStatus = keyMoneyStatus.getValue(); // Get the selected status from the ComboBox

        if (selectedStatus != null) {
            List<ReservationDTO> filteredReservations = reservationBo.getReservationsByStatus(selectedStatus);

            // Clear the table and add the filtered reservations
            tableView.getItems().clear();
            tableView.getItems().addAll(filteredReservations);
        } else {
            showAlert("Reservation Management", "Please select a status to filter by.", SelectType.WARNING);
        }
    }

    private enum SelectType {
        INFORMATION, WARNING, ERROR
    }

    private enum SelectTypeForCalculateRoom {
        SAVE, UPDATE, DELETE
    }

}
