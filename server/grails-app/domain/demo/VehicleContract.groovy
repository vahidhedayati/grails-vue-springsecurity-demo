package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/contract')
class VehicleContract {

    String contractName

    Vehicle vehicle

    Date fromDate
    Date toDate

    Driver driver

    Date dateCreated
    Date lastUpdated

}
