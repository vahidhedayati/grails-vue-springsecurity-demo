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
    /**
     * Return a simple object with metadata about the list
     * @return
     */
    def index(Integer max, Integer offset){
        println "-- vs ${params} ${offset}"
        params.max = Math.min(max ?: 10, 100)
        params.offset = offset ?: 0
        def returnValue = [
                objects: listAllResources(params),
                max: params.max,
                offset: params.offset,
                total: countResources()

        ]
        returnValue.numberOfPages=(countResources()/params.max)+1
        println " number of pages =  ${returnValue.numberOfPages} ${returnValue.total} vs ${returnValue.max} "
        respond returnValue as Object, model: [("${resourceName}Count".toString()): countResources()]
    }


    def export() {
        println "Are we in export ???"
        def allListing = listAllResources([offset:0,max:-1])
        response.setHeader 'Content-type','text/plain; charset=utf-8'
        response.setHeader "Content-disposition", "attachment; filename=index.csv"
        def out = response.outputStream
        out << 'Name,'
        out << "Make,"
        out << 'model,'
        out << "Driver"
        out << '\r'
        allListing?.each{field->
            out << field.name << ','
            out << field.make.name << ','
            out << field.model.name << ','
            out << field.driver.name << ','
            out << '\r'
        }
        out.flush()
        out.close()
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

    /**
     * This appears to conflict with default rest save method - not going to be used in this example
     * @param params
     * @return
     */


    def save2(params) {

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
