package demo
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        if (req.getMethod() == "OPTIONS") {
            resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            resp.addHeader("Access-Control-Max-Age", "3600")
            resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200")
            resp.addHeader("Access-Control-Allow-Credentials", "true")
            resp.status = 200
        } else {
            chain.doFilter(req, resp)
        }
    }
}