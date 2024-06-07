/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.customtag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import model.rbac.Feature;
import model.rbac.Role;
import model.rbac.User;

/**
 *
 * @author sonng
 */
public class AuthorizationControlTag extends SimpleTagSupport {

    private String featureid;

    public String getFeatureid() {
        return featureid;
    }

    public void setFeatureid(String featureid) {
        this.featureid = featureid;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        PageContext pageContext = (PageContext) jspContext;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        User user = (User)request.getSession().getAttribute("user");
        boolean isAuthorized = false;
        if(user!=null)
        {
            for (Role role : user.getRoles()) {
                for (Feature feature : role.getFeatures()) {
                    if(featureid.equals(feature.getUrl()))
                    {
                        isAuthorized = true;
                        break;
                    }
                }
                if(isAuthorized)
                    break;
            }
        }
        
        if (isAuthorized) {
            JspFragment jspFragment = getJspBody();
            if (jspFragment != null) {
                jspFragment.invoke(null);
            }
        }
    }
}
