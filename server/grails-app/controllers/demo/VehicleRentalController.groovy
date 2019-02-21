package demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.web.databinding.DataBindingUtils
import org.springframework.context.MessageSource

@Secured(['permitAll'])
class VehicleRentalController {

    static responseFormats = ['json']

    def vehicleHireService
    MessageSource messageSource
    def springSecurityService

    def index(){
        println "rental index ${params}"
        CustomSearchBean bean = new CustomSearchBean()
        DataBindingUtils.bindObjectToInstance(bean, params)
        bean.validate()
        if (!bean.hasErrors()) {
            def results = vehicleHireService.search(params)
            results.numberOfPages = (results.instanceTotal / bean.max).intValue() + 1
            println " number of pages =  ${results.numberOfPages} ${results.instanceTotal} vs ${results.max} "
            render results as JSON
        }
    }

    def listDefaults() {
        render vehicleHireService.listDefaults(params) as JSON
    }

    def hireVehicle(VehicleHireBean bean) {
        println "kkkkkk"

        println "hireVehicle--  ${bean.vehicle?.name} -- ${bean?.driver} ${bean?.driver?.name}"
        render

    }
    def search() {
        println "params = ${params}"
        def jsonResponse = vehicleHireService.search(params)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse
    }
    def internalSearch(Map paramsMap) {
        println "params = ${paramsMap}"
        def jsonResponse = vehicleHireService.search(paramsMap)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse as JSON
    }
    /**
     * This is the pop up dialog offering either a form for a guest user to provide username/name/password and rental from/toDates
     * or it is an existing user and they provide from / to dates
     * both types also provide vehicle.id which is what they clicked
     * @return
     */
    def save() {
        def jsonParams = request.JSON
        println "saving jsonParams = ${jsonParams}"
        VehicleHireBean bean = new VehicleHireBean()
        DataBindingUtils.bindObjectToInstance(bean, jsonParams)
        bean.validate()
        try {
            if (!bean.hasErrors()) {
                println "all done"

                def  r = vehicleHireService.save(bean)

               def paramsMap=[make:r.vehicle.make.id,model:r.vehicle.model.id]
               return internalSearch(paramsMap)

               // def done = [success: true]
               // render done as JSON
               // return
            }
        } catch (Throwable e ){
        //   bean.errors=e
            println "--- ${e} 0000000000000"

        }
        println "-------- ${bean.errors}"
        //def msg = messageSource.getMessage('my.localized.content', ['Juan', 'lunes'] as Object[], 'Default Message', request.locale)
        def errors = [error: bean.errors.fieldErrors.collect{messageSource.getMessage(it, request.locale)}]
        println "-- ${errors}"
        render errors as JSON, status: 409
    }



    def saveReturn() {
        def jsonParams = request.JSON
        //def user = springSecurityService.currentUser
        //println " user = ${user} ${springSecurityService.principal} vs ${session.username}"
        //println "saving jsonParams = ${jsonParams}"
        VehicleHireReturnBean bean = new VehicleHireReturnBean()
        DataBindingUtils.bindObjectToInstance(bean, jsonParams)
        bean.validate()
        println "update user = ${bean.user}"
        try {
            if (!bean.hasErrors()) {
                println "all done"

                vehicleHireService.saveReturn(bean)
                def done = [success: true]
                render done as JSON
                return
            }
        } catch (Throwable e ){
            //   bean.errors=e
            println "--- ${e} 0000000000000"

        }
        println "-------- ${bean.errors}"
        //def msg = messageSource.getMessage('my.localized.content', ['Juan', 'lunes'] as Object[], 'Default Message', request.locale)
        def errors = [error: bean.errors.fieldErrors.collect{messageSource.getMessage(it, request.locale)}]
        println "-- ${errors}"
        render errors as JSON, status: 409
    }
}
