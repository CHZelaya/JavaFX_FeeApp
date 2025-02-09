package org.example.javafx_fees;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.javafx_fees.model.Fee;
import org.example.javafx_fees.model.FeeDb;

public class FeesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn<Fee, String> colDesc;

    @FXML
    private TableColumn<Fee, String> colId;

    @FXML
    private TableColumn<Fee, String> colName;

    @FXML
    private TableColumn<Fee, Double> colPrice;

    @FXML
    private TableView<Fee> tvFeeList;

    // global list of agents
    private ObservableList<Fee> data = FXCollections.observableArrayList();

    String actionFlag;

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert colDesc != null : "fx:id=\"colDesc\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert colPrice != null : "fx:id=\"colPrice\" was not injected: check your FXML file 'fees-view.fxml'.";
        assert tvFeeList != null : "fx:id=\"tvFeeList\" was not injected: check your FXML file 'fees-view.fxml'.";

        populateFeeTable();
        displayFeeData();
        btnAdd.setOnAction((ActionEvent event) -> {
            actionFlag = "add";
            launchFeeEditWindow(null, actionFlag);
        });

        tvFeeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Fee>(){

            @Override
            public void changed(ObservableValue<? extends Fee> observableValue, Fee oldValue, Fee newValue) {
                int index = tvFeeList.getSelectionModel().getSelectedIndex();
                if(tvFeeList.getSelectionModel().isSelected(index)){
                    Platform.runLater(() -> {
                        actionFlag = "edit";
                        launchFeeEditWindow(newValue, actionFlag);
                    });
                }
            }
        });
}

    private void displayFeeData() {
        data.clear();
        try{
            data.addAll(FeeDb.getFees()); // Add all items to the ObservableList
            tvFeeList.setItems(data); // Update the TableView with the data

        } catch (SQLException e) {
            throw new RuntimeException("Error getting fees", e);
        }
    }

    private void populateFeeTable() {
        colId.setCellValueFactory((new PropertyValueFactory<>("feeId")));
        colName.setCellValueFactory((new PropertyValueFactory<>("feeName")));
        colPrice.setCellValueFactory((new PropertyValueFactory<>("feeAmt")));
        colDesc.setCellValueFactory((new PropertyValueFactory<>("feeDesc")));
    }


    public void launchFeeEditWindow(Fee fee, String actionFlag) {
        FXMLLoader fxmlLoader = new FXMLLoader(FeesApplication.class.getResource("edit-fee-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EditFeeController controller = fxmlLoader.getController();
        controller.setActionFlag(actionFlag);
        // if editing, pass the agent to the new form. gets the controller for
        if(actionFlag.equalsIgnoreCase("edit")) {
            controller.setFeeForm(fee);
        }
        if(actionFlag.equalsIgnoreCase("add")) {
            controller.setActionFlag("add");

        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Fee Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayFees();
    }

    private void displayFees() {
        data.clear();
        try {
            data = FeeDb.getFees();

        } catch (SQLException e) {
            throw new RuntimeException("Couldnt Load the Fee List", e);
        }
        tvFeeList.setItems(data);
    }

    public void btnCloseOnAction(ActionEvent event) {
        Platform.exit();
    }




}//class
