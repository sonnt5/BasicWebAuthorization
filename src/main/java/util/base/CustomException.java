/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.base;

import java.rmi.ServerException;

/**
 *
 * @author sonng
 */
public class CustomException extends ServerException {
    
    public CustomException(String s) {
        super(s);
    }
    
}
