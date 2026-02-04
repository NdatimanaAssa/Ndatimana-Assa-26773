package auca.rw;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String message;
        if (password.length() < 8) {
            message = "Hello " + username + ", your password is weak. Try a strong one.";
        } else {
            message = "Welcome " + username;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Result</title>");
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    height: 100vh;");
        out.println("    margin: 0;");
        out.println("    background-color: #f0f0f0;");
        out.println("}");
        out.println(".message-container {");
        out.println("    background: white;");
        out.println("    padding: 40px;");
        out.println("    border-radius: 8px;");
        out.println("    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);");
        out.println("    text-align: center;");
        out.println("}");
        out.println(".message-container p {");
        out.println("    font-size: 18px;");
        out.println("    color: #333;");
        out.println("    margin: 0;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='message-container'>");
        out.println("<p>" + message + "</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
