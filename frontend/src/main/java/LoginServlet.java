import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet(urlPatterns = "/login")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = { "users" },
        transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("username", req.getUserPrincipal().getName());
        req.getRequestDispatcher("taskBoard.html").forward(req, res);
    }
}
