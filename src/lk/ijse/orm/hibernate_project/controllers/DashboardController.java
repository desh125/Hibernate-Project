package lk.ijse.orm.hibernate_project.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane holdPane;
    private AnchorPane Pane;

    private void setNode(Node node) {

        holdPane.getChildren().clear();
        holdPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void handleLogout(MouseEvent event) {
    }

    public void roomsManagementController(ActionEvent actionEvent) {

        try {
            Pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/RoomView.fxml")));
            setNode(Pane);

            // Set the preferred width and height of the loaded FXML file to fit the holdPane
            Pane.setPrefWidth(holdPane.getWidth());
            Pane.setPrefHeight(holdPane.getHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReservationManagementController(ActionEvent actionEvent) {

        try {
            Pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReservationView.fxml")));
            setNode(Pane);

            // Set the preferred width and height of the loaded FXML file to fit the holdPane
            Pane.setPrefWidth(holdPane.getWidth());
            Pane.setPrefHeight(holdPane.getHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void studentManagementController(ActionEvent actionEvent) {

        try {
            Pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/StudentView.fxml")));
            setNode(Pane);

            // Set the preferred width and height of the loaded FXML file to fit the holdPane
            Pane.setPrefWidth(holdPane.getWidth());
            Pane.setPrefHeight(holdPane.getHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
