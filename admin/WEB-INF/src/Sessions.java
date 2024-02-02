import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Sessions {
    private HttpServletResponse resp;
    private HttpServletRequest req;

    public Sessions(HttpServletRequest req, HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    public boolean sessionExist(){
        return req.getSession(false) != null;
    }

    public boolean redirectIfNotExist(String redirection){
        if(!sessionExist()){
            try {
                resp.sendRedirect(redirection);
                return true;
            } catch (Exception e) {}
        } 
        return false;
    }

    public void setAttribute(String name, String value){
        req.getSession(true).setAttribute(name, value);;
    }

    public String getAttributeIfExistElseRedirect(String redirect, String name){
        redirectIfNotExist(redirect);
        return (String) req.getSession(true).getAttribute(name);
    }
}
