package demo

import org.apache.catalina.SessionListener
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpSession

class CustomSecurityEventListener implements ApplicationListener<ApplicationEvent> {

    void onApplicationEvent(ApplicationEvent appEvent) {
        if (appEvent instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent
            UserDetails userDetails = (UserDetails) event?.getAuthentication()?.getPrincipal()
            if (userDetails) {
                String username = userDetails.getUsername()
                //session.username=username
                println " username from CustomSecurityEventListener  = ${username}"
            }
        }
    }

    private HttpSession getSession() {
        return RequestContextHolder.currentRequestAttributes().getSession()
    }
}
