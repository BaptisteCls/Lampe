package api.link;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Error404Handler",urlPatterns = "/error404")
public class Redirection extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Test");

        String[] splits = req.getPathInfo().split("/");

        resp.getWriter().println("Test");

        if(splits.length == 1 && getAllHtmlFiles().contains(splits[0]) ){
            /* 
            RequestDispatcher dispatcher = req.getRequestDispatcher(splits[0]+".html");
            dispatcher.forward(req, resp);*/
        }
        else{
            /* 
            RequestDispatcher dispatcher = req.getRequestDispatcher(req.getPathInfo());
            dispatcher.forward(req, resp);*/
        }
    }

    private List<String>getAllHtmlFiles(){
        File racine = new File("..");
        List<String> res = new ArrayList<>();
        for(File file : racine.listFiles()){
            String fileName = file.getName();
            if(isHTML(fileName))
                res.add(removeExtension(fileName));
        }
        return res;
    }

    private boolean isHTML(String fileName){
        return fileName.endsWith(".html");
    }

    private String removeExtension(String fileName){
        int start = 0;
        int end = fileName.lastIndexOf('.');
        return fileName.substring(start, end);
    }

}
