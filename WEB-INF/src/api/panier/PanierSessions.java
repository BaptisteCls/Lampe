package api.panier;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionManager;

public class PanierSessions extends SessionManager{

    public PanierSessions(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
    }
    
}
