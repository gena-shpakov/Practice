package Work.Practice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@WebFilter(urlPatterns = "/photosessions/*")
public class TokenValidationFilter implements Filter {

    @Value("${admin.username}")
    private String adminUsername;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (path.startsWith("/photosessions")) {
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader == null || authHeader.isBlank()) {
                sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is missing");
                return;
            }

            try {
                String decodedToken = new String(Base64.getDecoder().decode(authHeader), StandardCharsets.UTF_8);
                String[] parts = decodedToken.split(":");

                if (parts.length != 2) {
                    sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Malformed token");
                    return;
                }

                String username = parts[0];
                long expirationTime = Long.parseLong(parts[1]);

                if (!adminUsername.equals(username)) {
                    sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid username");
                    return;
                }

                if (System.currentTimeMillis() > expirationTime) {
                    sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token has expired");
                    return;
                }

            } catch (Exception e) {
                sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Malformed token");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write(message);
    }
}



