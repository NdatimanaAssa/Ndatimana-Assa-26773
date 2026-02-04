package auca.rw;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class RedirectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        
        if (query != null && !query.trim().isEmpty()) {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String googleUrl = "https://www.google.com/search?q=" + encodedQuery;
            response.sendRedirect(googleUrl);
        } else {
            response.sendRedirect("search.jsp");
        }
    }
}
