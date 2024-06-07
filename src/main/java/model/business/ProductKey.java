/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.business;

import model.KeyAttribute;

/**
 *
 * @author sonng
 */
public class ProductKey implements KeyAttribute {
    private int id;
 
    public ProductKey(int id) {
        this.id = id;
    }

    public ProductKey() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
