/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sonng
 * @param <K>
 */
public abstract class Entity<K extends KeyAttribute> {
   public K key; 

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
   
}
