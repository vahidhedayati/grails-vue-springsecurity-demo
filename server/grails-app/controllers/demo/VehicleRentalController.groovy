package demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.web.databinding.DataBindingUtils

@Secured(['ROLE_DRIVER'])
class VehicleRentalController {

    static responseFormats = ['json']

    def vehicleHireService


    def index(){
        println "rental index ${params}"
        CustomSearchBean bean = new CustomSearchBean()
        DataBindingUtils.bindObjectToInstance(bean, params)
        bean.validate()
        println " dd"
        if (!bean.hasErrors()) {
            def results = vehicleHireService.search(bean)
            results.numberOfPages = (results.instanceTotal / bean.max).intValue() + 1
            println " number of pages =  ${results.numberOfPages} ${results.instanceTotal} vs ${results.max} "
            render results as JSON
        }
    }

    def search() {
        def jsonResponse = vehicleHireService.search(params)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse
    }

    def save() {
        //TODO
    }


}
