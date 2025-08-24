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

    /**
     * Validates that the provided {@code input} has a length between the given
     * {@code minLength} and {@code maxLength}.
     *
     * <p>A {@code null} input triggers an {@link IllegalArgumentException}. The
     * boundaries are inclusive, meaning an input length equal to
     * {@code minLength} or {@code maxLength} is considered valid.</p>
     *
     * @param input the string to validate
     * @param minLength the minimum acceptable length (inclusive)
     * @param maxLength the maximum acceptable length (inclusive)
     * @return {@code true} if the input length is within the specified bounds
     * @throws IllegalArgumentException if {@code input} is {@code null}
     */
    public static boolean validateStringLength(String input, int minLength, int maxLength) {
        if (input == null) {
            throw new IllegalArgumentException("Input length does not meet the requirement!");
        }
        return input.length() >= minLength && input.length() <= maxLength;
    }
}
