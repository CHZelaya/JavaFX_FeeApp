/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */


package org.example.javafx_fees;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.javafx_fees.model.Fee;
import org.example.javafx_fees.model.FeeDb;
import org.example.javafx_fees.utils.Validator;

public class EditFeeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPrice;

    @FXML
    private Label txtTitle;

    String actionFlag;

    @FXML
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert tfDescription != null : "fx:id=\"tfDescription\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert tfPrice != null : "fx:id=\"tfPrice\" was not injected: check your FXML file 'fee-modification.fxml'.";
        assert txtTitle != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'fee-modification.fxml'.";

        btnDelete.setOnMouseClicked(event -> {
            deleteFee();
            closeWindow(event);
        });
        btnSave.setOnMouseClicked(event -> {
            saveFee();
            closeWindow(event);
        });
        btnCancel.setOnMouseClicked(event -> {
            closeWindow(event);
        });
    }


    private void saveFee() {
        int numRows = 0;
        Fee fee = getFeeFromForm();
        String validation = Validator.validateFee(fee);
        if (validation.isEmpty()) {
            if(actionFlag.equalsIgnoreCase("add")) {
                try {
                    numRows = FeeDb.addFee(fee);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                alertUser(Alert.AlertType.CONFIRMATION, "REBUTTAL AGENCY: Fee Added Successfully");

            }else if(actionFlag.equalsIgnoreCase("edit")) {
                try {
                    numRows = FeeDb.updateFee(fee);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                alertUser(Alert.AlertType.ERROR, "REBUTTAL AGENCY: An unexpected error occurred, please try again.");
            }
        }else{
            alertUser(Alert.AlertType.ERROR, "REBUTTAL AGENCY: There was an error adding your fee" + validation);
        }

    }

    private Fee getFeeFromForm() {
        double price = 0;
        String id = tfID.getText();

        try{
            price = Double.parseDouble(tfPrice.getText());
        }catch(NumberFormatException e) {
            alertUser(Alert.AlertType.ERROR, "REBUTTAL AGENCY: Price must be an MUST be a numeric value (0-9)");
        }

        Fee fee = new Fee(
                id,
                tfName.getText(),
                price,
                tfDescription.getText()
        );
        return fee;
    }

    private void deleteFee() {
        int affectedRows = 0;
        String id = tfID.getText();
        try {
            affectedRows = FeeDb.deleteFees(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (affectedRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "REBUTTAL AGENCY: You have successfully deleted your fee");
        }else{
            alertUser(Alert.AlertType.ERROR, "REBUTTAL AGENCY: There was an error with deletion.");
        }
    }



    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
        btnDelete.setDisable(actionFlag.equalsIgnoreCase("add"));
        tfID.setDisable(actionFlag.equalsIgnoreCase("edit"));
        txtTitle.setText("TE: Fees - " + capitalizeFirstLetter(actionFlag) + " mode" );
    }


    private void alertUser(Alert.AlertType type,  String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Travel Experts Fees");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setFeeForm(Fee fee){
        tfID.setText(String.valueOf(fee.getFeeId()));
        tfName.setText(fee.getFeeName());
        tfPrice.setText(String.valueOf(fee.getFeeAmt()));
        tfDescription.setText(fee.getFeeDesc());


    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


}