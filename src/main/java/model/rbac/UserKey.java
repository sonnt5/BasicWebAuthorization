/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.rbac;

import model.KeyAttribute;

/**
 *
 * @author sonng
 */
public class UserKey implements KeyAttribute {

    public UserKey(int id) {
        this.id = id;
    }

    public UserKey() {
    }
    
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
