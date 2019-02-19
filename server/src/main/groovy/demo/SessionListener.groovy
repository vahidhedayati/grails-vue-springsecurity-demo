package demo

import grails.util.Holders

import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

class SessionListener implements HttpSessionListener {
    private static List activeUsers = Collections.synchronizedList(new ArrayList())

    static Map sessions = [:].asSynchronized()

    void sessionCreated (HttpSessionEvent se) {
        sessions.put(se.session.id, se.session)
    }

    void sessionDestroyed (HttpSessionEvent se) {
        sessions.remove(se.session.id)
    }

    void invalidateSessions () {
        def httpSessions = sessions.collect { String sessionId, HttpSession session ->
            session
        }
        httpSessions.each { HttpSession session ->
            session.invalidate()
        }
    }
    public static void userReloggedIn(String userName) {
        if (!this.activeUsers.contains(userName)) {
            this.activeUsers.add(userName)
            Holders.grailsApplication.mainContext.loginAuthenticationService.triggerOnline(userName)
        }
        reaAuthUser(userName)
    }




    static boolean isLoggedIn (String user) {
        boolean loggedIn = false
        if (user && this.activeUsers.contains(user)) {
            loggedIn = true
        }
        return loggedIn
    }

    List getAllSessions () {
        def principals = []
        sessions.each { String sessionId, HttpSession session ->
            principals<< session.id
        }
        //principals = principals.findAll {it != null}
        principals
    }

    /*
    def getAllPrincipals () {
        def principals = []
        sessions.each { String sessionId, HttpSession session ->
            SecurityContext securityContext = session[HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY]
            def authentication = securityContext?.authentication
            principals << authentication?.principal
        }
        principals = principals.findAll {it != null}
        principals
    }
    */

    /*

    Old method - now just using above activeUsers
    //This may be expensive an alternative is to re-introduce the CustomSecurityEventListener
    //Tidy up the activeUsers arraylist call and look at our own collection of users ???? unsure to be honest
    // that may have extra cost of adding another list where this already exists....
    boolean isLoggedIn (String user) {
        boolean loggedIn=false
            def principals = []
            sessions.each { String sessionId, HttpSession session ->
                SecurityContext securityContext = session[HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY]
                def authentication = securityContext?.authentication
                principals << authentication?.principal
            }
            principals = principals.findAll {it!=null}
            principals?.each {grails.plugin.springsecurity.userdetails.GrailsUser g->
                if  (g.username==user) {
                    loggedIn=true
                }
            }

        //return principals.findAll { it.username != user }
        }
        return loggedIn
    }
    */

}