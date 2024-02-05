package utils;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import jakarta.servlet.http.*;

public class SessionManager {
    private HttpServletResponse resp;
    private HttpServletRequest req;

    public SessionManager(HttpServletRequest req, HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    public void initUserId(){
        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(999999999);
        Object userId = session.getAttribute("userId");
        if(userId == null){
            try (Connection con = Database.getConnection("website")) {
                long newId = new Date().getTime();
                req.getSession(false).setAttribute("userId", "" + newId);
            } catch (Exception e) {}
        }
    }

    public long getUserId(){
        initUserId();
        try {
            return Long.parseLong(getAttribute("userId"));
        } catch (Exception e) {}
        return -1;
    }

    public boolean sessionExist(){
        return req.getSession(false) != null;
    }

    public boolean redirectIfNotExist(String redirection) throws IOException{
        if(!sessionExist()){
            resp.sendRedirect(redirection);
            return true;
        }
        return false;
    }

    public void setAttribute(String name, String value){
        req.getSession(true).setAttribute(name, value);;
    }

    public String getAttributeIfExistElseRedirect(String redirect, String name) throws IOException{
        redirectIfNotExist(redirect);
        return (String) req.getSession(true).getAttribute(name);
    }

    public String getAttribute(String name) throws IOException{
        return (String) req.getSession(false).getAttribute(name);
    }

}
