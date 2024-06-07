/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.controller;

/**
 *
 * @author sonng
 */
public class ValidationHelper {
    public static boolean validateStringLength(String input, int minLength, int maxLength) {
        if (input == null) {
            throw new IllegalArgumentException("Length of inpu does not meet the requirement!");
        }
        return input.length() >= minLength && input.length() <= maxLength;
    }
}
