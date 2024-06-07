/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.accesscontrol;

import model.rbac.User;
import java.util.ArrayList;
import java.util.Date;
import model.Entity;

/**
 *
 * @author sonng
 */
public class Resource extends Entity<ResourceKey> {
    private String name;
    private User creator;
    private Date create_time;
    private User updater;
    private Date update_time;
    private String file_url;
    private ArrayList<UserResource> users = new ArrayList<>();

    public ArrayList<UserResource> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserResource> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
    
           
}
