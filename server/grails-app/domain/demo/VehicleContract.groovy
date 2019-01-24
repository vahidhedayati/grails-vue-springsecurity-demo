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


    static constraints = {
        contractName nullable: false, unique: true
    }

    String toString() {
        return """
           Contract ${contractName}
            Leased by ${driver.name}
            For car: ${vehicle.name}
            For period: ${fromDate.format('dd MMM yyyy')} - ${toDate.format('dd MMM yyyy')}
        """
    }


}
