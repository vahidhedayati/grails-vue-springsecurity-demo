package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.CompileStatic

import java.math.RoundingMode

@CompileStatic
@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/vehiclehire')
class VehicleHire extends BaseVehicle {

    Double cost
    Double deposit

    int stock

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

    Byte getRating() {
        List<Byte> ratings = VehicleHireRating.executeQuery("""select v.rating from VehicleHireRating v 
        where v.vehicle=:vehicle""",[vehicle:this])
        return (ratings.sum() as Integer) / (ratings.size() as Integer).intValue() as Byte
    }

    void setRating(byte b) {
        println "Creating Vehicle Hire rating "
        VehicleHireRating.withTransaction {
            VehicleHireRating vhr = new VehicleHireRating()
            vhr.vehicle=this
            vhr.rating=b
            vhr.save()
        }

    }
}
