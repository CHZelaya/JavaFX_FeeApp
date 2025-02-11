/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */

package org.example.javafx_fees.utils;

import org.example.javafx_fees.model.Fee;

public class Validator {

    public static String validateFee(Fee fee) {
        String errorMessage = "";
        if (!fee.getFeeId().matches("^[a-zA-Z0-9 ]{1,10}$")) {
            errorMessage += " Fee ID must contain a maximum of 10 Characters. No Special Characters please\n";
        }

        if (!fee.getFeeName().matches("^[a-zA-Z0-9 ]{1,50}$")) {
            errorMessage += " Fee name must contain only letters and numbers. No Special characters.\n";
        }

        if (fee.getFeeAmt() < 0) {
            errorMessage += " Fee amount must not be negative.\n";
        }

        if (!fee.getFeeDesc().matches("^[a-zA-Z0-9 .,!?()\"'-]{0,100}$")) {
            errorMessage += " Fee description must contain only letters and numbers. \n";
        }

        return errorMessage;
    }
}