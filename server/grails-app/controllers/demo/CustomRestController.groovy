package demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import grails.rest.RestfulController
import grails.web.databinding.DataBindingUtils

/**
 *
 * This has no ties to any domain classes and will simply interact with a more complex data set to compile
 *
 * the listing / search features sent via rest
 *
 */
@Secured(['ROLE_DRIVER'])
class CustomRestController extends RestfulController {


    def customRestService

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
    


}
