package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;

import com.trainingproject.model.Token;
import com.trainingproject.model.UserAuth;


@WebServlet(name = "login", urlPatterns = "/login")
public class LogInServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean responseFlag;
        Gson gson = new Gson();
        String json = "";

        if(UserAuth.newAccountAuthorization(username)==false)
        {
                responseFlag =UserAuth.userAuthenticated(username, password);

                if(responseFlag == true )
                {

                    Token token = new Token(username, Token.nextToken());
                    token.addTokenToDataBase();
                    json = gson.toJson(token);
                    out.print(json);
                }
                else
                {
                    json = gson.toJson(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
                    out.print(json);
                }
        }
        else
        {
            json = gson.toJson(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
            out.print(json);
        }

    }

}
