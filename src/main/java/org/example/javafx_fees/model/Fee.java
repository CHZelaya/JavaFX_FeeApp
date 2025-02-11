/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */

package org.example.javafx_fees.model;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Fee {

    //SimpleProperty types for observable nature
    //? Properties

    private final SimpleStringProperty feeId;
    private final SimpleStringProperty feeName;
    private final SimpleDoubleProperty feeAmt;
    private final SimpleStringProperty feeDesc;

    //? Constructor


    public Fee(String feeId, String feeName, double feeAmt, String feeDesc) {
        this.feeId = new SimpleStringProperty(feeId);
        this.feeName = new SimpleStringProperty(feeName);
        this.feeAmt = new SimpleDoubleProperty(feeAmt);
        this.feeDesc = new SimpleStringProperty(feeDesc);
    }

    //? Getters/Setters
    public String getFeeId() {
        return feeId.get();
    }

    public SimpleStringProperty feeIdProperty() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId.set(feeId);
    }

    public String getFeeName() {
        return feeName.get();
    }

    public SimpleStringProperty feeNameProperty() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName.set(feeName);
    }

    public double getFeeAmt() {
        return feeAmt.get();
    }

    public SimpleDoubleProperty feeAmtProperty() {
        return feeAmt;
    }

    public void setFeeAmt(double feeAmt) {
        this.feeAmt.set(feeAmt);
    }

    public String getFeeDesc() {
        return feeDesc.get();
    }

    public SimpleStringProperty feeDescProperty() {
        return feeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        this.feeDesc.set(feeDesc);
    }







}//class
