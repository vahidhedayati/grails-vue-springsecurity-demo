package demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.databinding.DataBindingUtils

//@Secured(['ROLE_DRIVER'])
@Secured(['ROLE_DRIVER'])
class VehicleHireController extends RestfulController {
    static responseFormats = ['json']//, 'xml']

    def vehicleHireService

    VehicleHireController() {
        super(VehicleHire)
    }

    def index(){
            params.max = Math.min(params.max ?: 10, 100)
            params.offset = params.offset ?: 0
            def results = vehicleHireService.search(params)
            results.numberOfPages=(results.instanceTotal/params.max).intValue()+1
            println " number of pages =  ${results.numberOfPages} ${results.instanceTotal} vs ${results.max} "
            render results as JSON
            //return
    }

    def search() {
        def jsonResponse = vehicleHireService.search(params)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse
    }


}
