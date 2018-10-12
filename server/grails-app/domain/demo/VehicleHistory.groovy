package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/history')
class VehicleHistory {
    VehicleContract contract
    Date returnDate
    User checkedOutBy
    User checkedInBy
}
