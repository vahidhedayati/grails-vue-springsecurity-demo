package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.CompileStatic

@CompileStatic
@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/vehiclehire')
class VehicleHire extends BaseVehicle {

    Double cost
    Double deposit

    int stock

    byte rating
    String registrationPlate

    static constraints = {
        //name unique: true
    }

    int getOnHire() {
        String query="""select count(*) from VehicleContract where vehicle.id=:currentId and returnDate is null"""
        return (VehicleContract.executeQuery(query,[currentId:this.id])[0])
    }

    String toString() {
        return """Rental Vehicle name: ${name} Make: ${make.name} Model: ${model.name} cost: ${cost} 
                per day Deposit: ${deposit}  stock total: ${stock} hired out ${onHire} Left: ${stock-onHire}"""
    }
}
