package lk.ijse.orm.hibernate_project.controllers;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.orm.hibernate_project.bo.custom.RoomBo;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.utils.ValidationUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    @FXML
    private TextField roomTypeId;

    @FXML
    private TextField type;

    @FXML
    private TextField keyMoney;

    @FXML
    private TextField qty;

    @FXML
    private TextField txtId1;

    @FXML
    private TableView<RoomDTO> tableView;

    @FXML
    private TableColumn<RoomDTO, String> colRoomTypeId;

    @FXML
    private TableColumn<RoomDTO, String> colTypeId;

    @FXML
    private TableColumn<RoomDTO, String> colKeyMoneyId;

    @FXML
    private TableColumn<RoomDTO, Integer> colQtyId;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton btnSaveOnAction;

    @FXML
    private JFXButton btnUpdateOnAction;

    @FXML
    private JFXButton btnDeleteOnAction;

    private RoomBo roomBo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("RoomTypeId"));
        colTypeId.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colKeyMoneyId.setCellValueFactory(new PropertyValueFactory<>("KeyMoney"));
        colQtyId.setCellValueFactory(new PropertyValueFactory<>("Qty"));

        // Create an instance of RoomBo
        roomBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.ROOM);

        // Populate data into the TableView
        refreshTableView();

        // Set up double-click event handler for the TableView
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Detect double-click
                RoomDTO selectedRoom = tableView.getSelectionModel().getSelectedItem();
                if (selectedRoom != null) {
                    // Populate fields with selected room data for update or delete
                    roomTypeId.setText(selectedRoom.getRoomTypeId());
                    type.setText(selectedRoom.getType());
                    keyMoney.setText(selectedRoom.getKeyMoney());
                    int roomQty = selectedRoom.getQty();
                    qty.setText(String.valueOf(roomQty));

                    // Disable fields that should not be edited during update
                    roomTypeId.setDisable(true);


                }
            }
        });
    }

    @FXML
    private void btnSaveOnAction() {
        if (roomTypeId.getText().isEmpty() || type.getText().isEmpty() || keyMoney.getText().isEmpty() || qty.getText().isEmpty()) {
            showAlert("Reservation Management", "Fill in all fields", RoomController.SelectType.ERROR);
            return; // Stop processing if any field is empty
        }
        String keyMoneyValue = keyMoney.getText().trim();
        String qtyValue = qty.getText().trim();

        if (!ValidationUtils.isValidKeyMoney(keyMoneyValue)) {
            showAlert("Room Management", "Invalid Key Money!", SelectType.WARNING);
            return;
        }

        if (!ValidationUtils.isValidQuantity(qtyValue)) {
            showAlert("Room Management", "Invalid Quantity!", SelectType.WARNING);
            return;
        }
        roomBo = BoFactory.getBoFactory().getBo(BoFactory.BoType.ROOM);
        int roomQty = Integer.parseInt(qty.getText());
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomTypeId(roomTypeId.getText());
        roomDTO.setType(type.getText());
        roomDTO.setKeyMoney(keyMoney.getText());
        roomDTO.setQty(roomQty);


        boolean saved = roomBo.SaveRoom(roomDTO);

        if (saved) {
            showAlert("Room Management", "Successfully Saved Room Details!", SelectType.INFORMATION);
            setDefault();
        } else {
            showAlert("Room Management", "Failed to Save Room Details!", SelectType.ERROR);
        }

        refreshTableView();
    }

    @FXML
    private void btnUpdateOnAction() {
        String keyMoneyValue = keyMoney.getText().trim();
        String qtyValue = qty.getText().trim();

        if (!ValidationUtils.isValidKeyMoney(keyMoneyValue)) {
            showAlert("Room Management", "Invalid Key Money!", SelectType.WARNING);
            return;
        }

        if (!ValidationUtils.isValidQuantity(qtyValue)) {
            showAlert("Room Management", "Invalid Quantity!", SelectType.WARNING);
            return;
        }

        int roomQty = Integer.parseInt(qty.getText());
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomTypeId(roomTypeId.getText());
        roomDTO.setType(type.getText());
        roomDTO.setKeyMoney(keyMoneyValue);
        roomDTO.setQty(roomQty);

        boolean update = roomBo.UpdateRoom(roomDTO);

        if (update) {
            showAlert("Room Management", "Successfully Updated Room Details!", SelectType.INFORMATION);
            setDefault();
            refreshTableView();
        } else {
            showAlert("Room Management", "Something Went Wrong!", SelectType.ERROR);
        }
        roomTypeId.setDisable(false);
        refreshTableView();

    }

    @FXML
    private void btnDeleteOnAction() {
        // Handle the Delete button action here
        // Retrieve data from the form fields and create a RoomDTO
        // Use roomBo to delete the room
        // Refresh the TableView

        String roomTypeIdValue = roomTypeId.getText();

        // Retrieve the room from the database using the roomTypeId
        RoomDTO roomDTO = roomBo.getRoom(roomTypeIdValue);

        if (roomDTO != null) {
            boolean delete = roomBo.DeleteRoom(roomDTO);

            if (delete) {
                showAlert("Room Management", "Successfully Deleted Room!", SelectType.INFORMATION);
                setDefault();
                refreshTableView();
            } else {
                showAlert("Room Management", "Something Went Wrong!", SelectType.ERROR);
            }
        } else {
            showAlert("Room Management", "Room not found with ID: " + roomTypeIdValue, SelectType.WARNING);
        }
        roomTypeId.setDisable(false);
        refreshTableView();
    }

    @FXML
    private void codeSearchOnAction() {
        // Handle the Search button action here
        String roomTypeIdValue = txtId1.getText().trim(); // Trim to remove leading/trailing spaces

        if (roomTypeIdValue.isEmpty()) {
            // If the input is empty, refresh the TableView to show all rooms
            refreshTableView();
        } else {
            // Use roomBo to fetch the room by ID
            RoomDTO roomDTO = roomBo.getRoom(roomTypeIdValue);

            if (roomDTO != null) {
                // Populate the retrieved data into the form fields
                roomTypeId.setText(roomDTO.getRoomTypeId());
                type.setText(roomDTO.getType());
                keyMoney.setText(roomDTO.getKeyMoney());
                qty.setText(String.valueOf(roomDTO.getQty()));

                // Clear the TableView and add the retrieved room
                tableView.getItems().clear();
                tableView.getItems().add(roomDTO);
            } else {
                // If no result is found, refresh the TableView to show all rooms
                refreshTableView();
                showAlert("Room Management", "Room Not Found!", SelectType.WARNING);
            }
        }
    }


    private void refreshTableView() {
        List<RoomDTO> rooms = roomBo.getAllRooms();
        ObservableList<RoomDTO> roomList = FXCollections.observableArrayList(rooms);
        tableView.setItems(roomList);
    }

    private void setDefault() {
        roomTypeId.clear();
        type.clear();
        keyMoney.clear();
        qty.clear();
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
