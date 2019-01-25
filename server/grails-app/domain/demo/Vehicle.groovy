package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.CompileStatic

@CompileStatic
@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/vehicle')
class Vehicle extends BaseVehicle {

    static belongsTo = [driver: Driver]


    String toString() {
        return "Vehicle name: ${name} Make: ${make.name} Model: ${model.name} Driver: ${driver.name}"
    }
}
