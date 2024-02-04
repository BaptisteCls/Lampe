package api.panier;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Sessions;

public class PanierSessions extends Sessions{

    public PanierSessions(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
    }
    
}
