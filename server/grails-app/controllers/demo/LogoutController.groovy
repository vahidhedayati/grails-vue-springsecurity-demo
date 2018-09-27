package demo

import grails.plugin.springsecurity.SpringSecurityUtils

import javax.servlet.http.HttpServletResponse

class LogoutController {

    def index() {

        if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
            response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
            return
        }

        // TODO put any pre-logout code here
        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
    }
}
