package demo

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@GrailsCompileStatic
@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/rentaldriver')
class RentalDriver extends User {

    String name


    String toString() {

        return """ 
            Driver name: ${name} -  Username: ${username} 
        """
    }
}
