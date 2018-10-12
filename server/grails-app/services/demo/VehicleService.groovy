package demo

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.databinding.DataBindingUtils

class VehicleService {

    def search(params) {

        //println "Service params inside service is ${params}"
        return (params as JSON)
    }



    @Transactional
    def save(params) {
        if (params.funkyForm) {
            CustomSaveBean bean = new CustomSaveBean()
            DataBindingUtils.bindObjectToInstance(bean, params)
            bean.validate()
            println "-- ${bean.errors.allErrors.collect{it.codes[-1]}}"
            if (!bean.hasErrors()) {
                Hospital hospital = bean.getHospital(new Hospital()).save()
                Patient patient = bean.getPatient(new Patient()).save()
                return ([patient:patient, hospital:hospital] as JSON)
            }
            Map err=[:]
            bean.errors.allErrors.collect{err."${it.code}"=true}
            //println "${aa.getClass()} ${aa as JSON} vs ${aa}"
            println err as JSON
            return [error: err] as JSON
            //return aa as JSON


        }
    }
}