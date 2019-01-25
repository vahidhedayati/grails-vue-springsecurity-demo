package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.CompileStatic

@CompileStatic
@Secured(['ROLE_DRIVER'])
@Resource(uri = '/api/baseVehicle')
class BaseVehicle {

    String name

    Make make
    Model model



    static constraints = {
        //name unique: true
    }

    String toString() {
        return "Base Vehicle name: ${name} Make: ${make.name} Model: ${model.name}"
    }
}
