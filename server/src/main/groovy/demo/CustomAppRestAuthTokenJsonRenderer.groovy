package demo

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority

/**
 * Created by Prakash Thete on 17/04/2018
 */
class CustomAppRestAuthTokenJsonRenderer implements AccessTokenJsonRenderer  {

    @Override
    String generateJson(AccessToken accessToken){

        // Add extra custom parameters if you want in this map to be rendered in login response
        Map response = [
                id           : accessToken.principal.id,
                username     : accessToken.principal.username,
                access_token : accessToken.accessToken,
                token_type   : "Bearer",
                refresh_token: accessToken.refreshToken,
                roles        : accessToken.authorities.collect { GrantedAuthority role -> role.authority }
        ]

        return new JsonBuilder( response ).toPrettyString()
    }
}