package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource


/**
 *
 * abstract - it was at the time all I could think of.
 *
 * I wanted to test the concept of testing across multiple objects through a rest call - with the validation being done through a
 * bean that combines both object types into one validation - obviously that then can be expanded over multiple beans and so on.
 */

@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/patient')
class Patient {

    String firstName
    String lastName
    Date dob

    Map loadValues() {
        Map values=[:]
        values.firstName=this.firstName
        values.lastName=this.lastName
        values.dob=this.dob
        return values

    }


}