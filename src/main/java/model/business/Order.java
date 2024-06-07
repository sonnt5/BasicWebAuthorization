/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.business;

import java.util.ArrayList;
import java.util.Date;
import model.Entity;
import model.rbac.User;

/**
 *
 * @author sonng
 */
public class Order extends Entity<OrderKey> {
    private Date orderdate;
    private User customer;
    private int status;
    public static final int NEW = 0;
    public static final int APPROVED = 1;
    public static final int REJECTED = 2;
    
    private User sale;
    private Date processdate;
    private float total;

    public float getTotal() {
        total = 0;
        for (OrderLine line : lines) {
            total += (float)line.getQuantity() * line.getPrice();
        }
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    
    private ArrayList<OrderLine> lines = new ArrayList<>();

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<OrderLine> lines) {
        this.lines = lines;
    }
    

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getSale() {
        return sale;
    }

    public void setSale(User sale) {
        this.sale = sale;
    }

    public Date getProcessdate() {
        return processdate;
    }

    public void setProcessdate(Date processdate) {
        this.processdate = processdate;
    }
    
    
}
