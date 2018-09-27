package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

//@Secured(['ROLE_DRIVER'])
@Secured(['ROLE_DRIVER'])
class VehicleController extends RestfulController {
    static responseFormats = ['json']//, 'xml']

    def vehicleService

    VehicleController() {
        super(Vehicle)
    }

    def search() {
     //   println "- search triggered  ${params}"
        def jsonResponse = vehicleService.search(params)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse
    }

    /**
     * Dabbling around this is some custom stuff trying to make it do more complex save based on additional
     * params etc
     * @return
     */

    def save(params) {

        println "WE ARE IN SAVE ${request.JSON}"
        def jsonParams = request.JSON
        if (jsonParams.funkyForm) {
            render vehicleService.save(jsonParams)
            return
        } else {
            return super.save()
        }

    }

}
