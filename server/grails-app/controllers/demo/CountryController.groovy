package demo

import grails.converters.JSON
import grails.web.databinding.DataBindingUtils
import org.springframework.context.MessageSource

/**
 *
 * Customised rest controller - defined in UrlMappings.groovy
 * and application.groovy in conf to override security
 *
 */

class CountryController {

    def countryService

    def utilService


    MessageSource messageSource

    def index(){
        println "index hit"
        CountrySearchBean bean = new CountrySearchBean()
        DataBindingUtils.bindObjectToInstance(bean, params)
        bean.validate()
        if (!bean.hasErrors()) {
            def results = countryService.search(bean)
            results.numberOfPages=(results.instanceTotal/bean.max).intValue()+1
            println " number of pages =  ${results.numberOfPages} ${results.instanceTotal} vs ${results.max} "
            render results as JSON
            //return
        }
    }

    def save() {
        def jsonParams = request.JSON
        CountryBean bean = new CountryBean()
        DataBindingUtils.bindObjectToInstance(bean, jsonParams)
        if (!bean.code) {
            bean.code=utilService.mapCode(Country.class.simpleName,2,bean.name)
        }
        if (!bean.updateUser) {
            bean.updateUser=utilService.adminUser
        }
        bean.validate()
        //
        try {
            if (!bean.hasErrors()) {
                bean=countryService.save(bean)
                def done = [success:true]
                render done as JSON
                return
            }
        } catch (Throwable e ){
            //   bean.errors=e
        }

        def errors = [error: bean.errors.fieldErrors.collect{messageSource.getMessage(it, request.locale)}]
        render errors as JSON, status: 409
    }

    def delete() {
        println "params = $params"
        String deleted = countryService.delete(params.id as Long)
        if (!deleted) {
            //throw error
            def errors = [error: message(code:'deleteFailed.label')]
            render errors as JSON, status: 409
        } else {
            def success = [success:true]
            render success as JSON, status: response.SC_OK
        }
    }
}
