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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private TextField keyMoney;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TextField txtId1;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

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
                    Timestamp timestamp = selectedRoom.getOrderDateTime();
                    Instant instant = timestamp.toInstant();
                    LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    String lastDateStr = selectedRoom.getLastDate(); // Assuming lastDateStr is a date string in a compatible format
                    LocalDate localDate2 = LocalDate.parse(lastDateStr);
                    // Populate fields with selected room data for update or delete
                    reservationId.setText(selectedRoom.getReservationId());
                    roomTypeId.setText(selectedRoom.getRoom().ToDto().getRoomTypeId());
                    status.setValue(selectedRoom.getStatus());
                    startDate.setValue(localDate);
                    studentId.setText(selectedRoom.getStudent().getStudentId());
                    lastDate.setValue(localDate2);
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

    @FXML
    private void btnSaveOnAction() {


        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.time.LocalDate selectedDate = startDate.getValue();

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setLastDate(selectedDate.toString());
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
    }

    @FXML
    private void btnUpdateOnAction() {
        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());

        java.time.LocalDate selectedDate = startDate.getValue();

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setLastDate(selectedDate.toString());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setOrderDateTime(Timestamp.valueOf(getCurrentDateTime()));
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
    }

    @FXML
    private void btnDeleteOnAction() {
        ReservationDTO reservationDTO = new ReservationDTO();
        StudentDTO studentDTO = new StudentDTO();
        RoomDTO roomDTO = new RoomDTO();

        StudentDTO student = reservationBo.GetStudentName(studentId.getText());
        RoomDTO room = reservationBo.GetKeyMoney(roomTypeId.getText());

        studentDTO.setStudentId(studentId.getText());
        roomDTO.setRoomTypeId(roomTypeId.getText());


        java.time.LocalDate selectedDate = startDate.getValue();

        reservationDTO.setReservationId(reservationId.getText());
        reservationDTO.setStudent(studentDTO.ToEntity());
        reservationDTO.setRoom(roomDTO.ToEntity());
        reservationDTO.setLastDate(selectedDate.toString());
        reservationDTO.setStatus(status.getValue());
        reservationDTO.setOrderDateTime(Timestamp.valueOf(getCurrentDateTime()));
        reservationDTO.setStudentName(student.getFullName());


        boolean update = reservationBo.DeleteReservationDetails(reservationDTO);

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
        }


        return null;
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
