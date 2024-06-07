/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.rbac;

import java.util.ArrayList;
import model.Entity;

/**
 *
 * @author sonng
 */
public class Feature extends Entity<FeatureKey> {
    private String name;
    private String url;
    private String htppmethod;
    private String param;
    private ArrayList<Role> roles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtppmethod() {
        return htppmethod;
    }

    public void setHtppmethod(String htppmethod) {
        this.htppmethod = htppmethod;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    
}
