package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;

import com.trainingproject.model.Token;
import com.trainingproject.model.User;
import com.trainingproject.model.UserAuth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "deleteuser", urlPatterns = "/deleteuser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String token = req.getParameter("token");

        Gson gson = new Gson();
        
        if (Token.tokenExists(token)==true)
        {

        	String username = Token.getUsernameOfToken(token);
        	User.deleteAccount(username);
        	out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_OK));
        }
        else {
        	out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_NOT_FOUND));
        }

    }
}
