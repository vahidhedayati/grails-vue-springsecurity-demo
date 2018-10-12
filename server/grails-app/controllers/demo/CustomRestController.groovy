package demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.util.Holders
import grails.web.databinding.DataBindingUtils
import org.springframework.context.MessageSource

/**
 *
 * This has no ties to any domain classes and will simply interact with a more complex data set to compile
 *
 * the listing / search features sent via rest
 *
 */
@Secured(['ROLE_DRIVER'])
class CustomRestController extends RestfulController {
    MessageSource messageSource

    def customRestService

    /**
     * This binds in with Unused domainClass which is mappedWith=none = no DB mapping
     * So this entire rest controller is on the fly stuff doing custom work
     *
     * Check the URLMappings.groovy for get/post overrides to make all this work
     *
     */
    CustomRestController() {
        super(Unused)
    }

    /**
     * This overrides the default call to this controller
     * @return
     */
    def index(){
        println "-- we are in custom ${params}"
        CustomSearchBean bean = new CustomSearchBean()
        DataBindingUtils.bindObjectToInstance(bean, params)
        bean.validate()
        if (!bean.hasErrors()) {
            def results = customRestService.search(bean)
            results.numberOfPages=(results.instanceTotal/bean.max)+1
            render results as JSON
            //return
        }
    }


    def save() {
        println "WE ARE IN SAVE ${request.JSON}"
        def jsonParams = request.JSON
        CustomSaveBean bean = new CustomSaveBean()
        DataBindingUtils.bindObjectToInstance(bean, params)
        bean.validate()
        if (!bean.hasErrors()) {
            //render vehicleService.save(jsonParams)
        } else {
            //def msg = messageSource.getMessage('my.localized.content', ['Juan', 'lunes'] as Object[], 'Default Message', request.locale)
            def errors = [error: bean.errors.allErrors.collect{it.codes[-2]}]
            println "-- ${errors}"
            render errors as JSON
        }

    }

}
