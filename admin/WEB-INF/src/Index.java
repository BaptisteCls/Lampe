import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/index")
public class Index extends HttpServlet{
    private static String top = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
        <title>Nom du site | Gestion du site</title>
    
        <link rel="stylesheet" href="./css/header.css">
        <link rel="stylesheet" href="./css/body.css">
        <link rel="stylesheet" href="./css/main.css">
    
        <script src="https://kit.fontawesome.com/c2507bb805.js" crossorigin="anonymous"></script>
        <script src="./js/open.js" defer></script>
    </head>
    <body>
        <header>
            <p>Administration</p>
            <img src="./img/logo.jpg" alt="logo">
            <p><i class="fa-solid fa-right-from-bracket"></i>Deconnexion</p>
        </header>
        <main>
            <nav>        
    """;

    private static String middle = """
        </nav>
        <div id="content">
    """;

    private static String bottom = """
        </div>
        </main>
    </body>
    </html>
    """;
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        StringBuilder nav = new StringBuilder();
        StringBuilder content = new StringBuilder();

        String title = "Annonces";
        nav.append(generateNav(title));
        nav.append(generateContent(title, getAnnonces()));

        out.println(top);
        out.println(nav);
        out.println(middle);
        out.println(content);
        out.println(bottom);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    private String generateNav(String title){ return "<p>"+title+"</p>";}

    private String generateContent(String name, String content){ return "<div id=\""+name+"\">"+content+"</div>";}

    private String getAnnonces(){
        StringBuilder res = new StringBuilder();
        try (Connection con = Database.getConnection("website");) {
            Statement stmt = con.createStatement();
            String query = "Select * from annonces";
            ResultSet rs = stmt.executeQuery(query);

            whi

        } catch (Exception e) {}
        return res.toString();
    }
}
