package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/contract')
class VehicleContract {

    String contractName

    BaseVehicle vehicle

    Date fromDate
    Date toDate

    User driver

    Date dateCreated
    Date lastUpdated

    Date returnDate

    static constraints = {
        contractName nullable: false, unique: true
        returnDate(nullable:true)
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
