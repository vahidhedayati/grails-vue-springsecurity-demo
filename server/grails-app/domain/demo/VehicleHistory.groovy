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


    String toString() {
        return """
        History record for 
           Contract ${contract.contractName}
                Driver: ${contract.driver.name}
                Vehicle: ${contract.vehicle.name}
            Checked out by: ${checkedOutBy.username}
            Checked in by: ${checkedInBy.username}
            Returned: ${returnDate.format('dd MMM yyyy')} 
        """
    }
}
