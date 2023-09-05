package lk.ijse.orm.hibernate_project.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.orm.hibernate_project.bo.BoFactory;
import lk.ijse.orm.hibernate_project.bo.custom.ReservationBo;
import lk.ijse.orm.hibernate_project.bo.custom.RoomBo;
import lk.ijse.orm.hibernate_project.bo.custom.StudentBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController {

    String reservationId = IdGeneratorUtil.generateReservationId();
    @FXML
    private TextField studentId;
    @FXML
    private TextField roomTypeId;
    @FXML
    private TextField txtId1;
    @FXML
    private JFXComboBox<String> status;
    @FXML
    private DatePicker date;
    @FXML
    private TableView<ReservationDTO> tableView;
    @FXML
    private TableColumn<ReservationDTO, String> colResId;
    @FXML
    private TableColumn<ReservationDTO, String> colRoomTypeId;
    @FXML
    private TableColumn<ReservationDTO, String> colStatusId;
    @FXML
    private TableColumn<ReservationDTO, String> colDateId;
    @FXML
    private TableColumn<ReservationDTO, String> colStudentId;
    private StudentBo studentBo;
    private RoomBo roomBo;
    private List<String> dataList = new ArrayList<>();
    private ReservationBo reservationBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.RESERVATION);

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize your TableView and bind columns here
        colResId.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("RoomTypeId"));
        colStatusId.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colDateId.setCellValueFactory(new PropertyValueFactory<>("OrderDateTime"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));

        // Populate data into the TableView
        refreshTableView();


        // Add a double-click event handler to the TableView
        tableView.setRowFactory(tv -> {
            TableRow<ReservationDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    // Double-click detected
                    ReservationDTO rowData = row.getItem();
                    populateFieldsWithData(rowData); // Populate the data into form fields
                }
            });
            return row;
        });
    }

    private void populateFieldsWithData(ReservationDTO rowData) {
        if (rowData != null) {
            studentId.setText(rowData.getStudent().getStudentId());
            roomTypeId.setText(rowData.getRoom().getRoomTypeId());
            date.setValue(LocalDate.parse(rowData.getLastDate()));
            status.setValue(rowData.getStatus());
            reservationId = rowData.getReservationId(); // Update reservationId for updates
        }
    }

    private void refreshTableView() {
        // Fetch data from your Bo and populate the TableView
        List<ReservationDTO> reservations = reservationBo.getAllReservations();
        ObservableList<ReservationDTO> reservationList = FXCollections.observableArrayList(reservations);
        tableView.setItems(reservationList);
    }

    @FXML
    private void btnSaveOnAction() {
        // Handle the Save button action here
        // Retrieve data from the form fields and create a ReservationDTO
        // Use reservationBo to save the reservation
        // Refresh the TableView

        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.time.LocalDate selectedDate = date.getValue();

        reservationDTO.setReservationId(reservationId);
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setLastDate(selectedDate.toString());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setStudentName(student.getFullName());
        reservationDTO.setKeyMoney(room.getKeyMoney());

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
    }

    @FXML
    private void btnUpdateOnAction() {
        // Handle the Update button action here
        // Retrieve data from the form fields and create a ReservationDTO
        // Use reservationBo to update the reservation
        // Refresh the TableView

        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.time.LocalDate selectedDate = date.getValue();

        reservationDTO.setReservationId(reservationId);
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setLastDate(selectedDate.toString());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setOrderDateTime(Timestamp.valueOf(getCurrentDateTime()));
        reservationDTO.setStudentName(student.getFullName());
        reservationDTO.setKeyMoney(room.getKeyMoney());

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
            //  reservationId.setDisable(false);
        } catch (Exception e) {
            throw e;
        }
    }

    @FXML
    private void btnDeleteOnAction() {
        // Handle the Delete button action here
        // Retrieve data from the form fields and create a ReservationDTO
        // Use reservationBo to delete the reservation
        // Refresh the TableView
    }

    @FXML
    private void codeSearchOnAction() {
        // Handle the Search button action here
        String reservationId = txtId1.getText();

        // Use reservationBo to fetch the reservation by ID
        ReservationDTO reservationDTO = reservationBo.getReservationDetails(reservationId);

        // Populate the retrieved data into the form fields
        // You may need to convert and format the data as needed
    }

    @FXML
    private void reservationudtxtonAction(ActionEvent actionEvent) {
        try {
            if (reservationId != null & reservationId != ("")) {
                try {
                    ReservationDTO reserversiondetails = reservationBo.getReservationDetails(reservationId);
                    studentId.setText(reserversiondetails.getStudent().getStudentId());
                    roomTypeId.setText(reserversiondetails.getRoom().getRoomTypeId());
                    date.setValue(LocalDate.parse(reserversiondetails.getLastDate()));
                    status.setValue(reserversiondetails.getStatus());

                    // reservationidtxt.setDisable(true);
                } catch (Exception e) {
                    setDefault();
                    showAlert("Reservation Management"
                            , "Something Wrong !" + "\n" + "Please Check & Enter Valid Reservation ID."
                            , SelectType.ERROR);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void setDataForComboBox() {
        dataList.add("PAID");
        dataList.add("PENDING");

        ObservableList<String> observableList = FXCollections.observableArrayList(dataList);
        status.setItems(observableList);
        dataList.clear();
    }

    private void setDefault() {
        // reservationId.clear();
        studentId.clear();
        date.setValue(null);
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
            ReservationDTO reservationDetails = reservationBo.getReservationDetails(reservationId);
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

                // Add a return statement here if needed
                return roomDTOUpdate; // You might want to return the updated roomDTO

                // If you don't need to return anything here, you can add a return statement for a default value or null
                // return null; // Or any other default value you want to return
            }
        }

        // Add a default return statement outside of the if-else block
        return null; // Or any other default value you want to return
    }

    private enum SelectType {
        INFORMATION, WARNING, ERROR
    }

    private enum SelectTypeForCalculateRoom {
        SAVE, UPDATE, DELETE
    }

}
