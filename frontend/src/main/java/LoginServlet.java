import com.ibm.websphere.security.jwt.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = "/login")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = { "user" },
        transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getRemoteUser();
        var roles = getRoles(req);
        HttpSession ses = req.getSession();
        if (ses == null)
            System.out.println("Session timed out");
        else
            ses.setAttribute("jwt", buildJwt(username, roles));
        req.getRequestDispatcher("taskBoard.html").forward(req, res);
    }

    private String buildJwt(String username, Set<String> roles) {
        try {
            return JwtBuilder.create("jwtFrontEndBuilder")
                    .claim(Claims.SUBJECT, username)
                    .claim("upn", username)
                    .claim("groups", roles.toArray(new String[0]))
                    .claim("aud", "frontend")
                    .buildJwt()
                    .compact();
        } catch (JwtException | InvalidClaimException | InvalidBuilderException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getRoles(HttpServletRequest request) {
        Set<String> roles = new HashSet<>();
        if (request.isUserInRole("user"))
            roles.add("user");
        return roles;
    }
}
