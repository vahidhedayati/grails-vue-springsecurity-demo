package demo

import grails.validation.Validateable

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CustomSaveBean implements Validateable {

    String firstName
    String lastName
    Date dob

    String name
    String address1

    static constraints = {
        firstName(nullable:false, maxSize:10)
        dob(nullable:true, validator: verifyDob)
    }

    static def verifyDob={val,obj,errors->
        if (val) {
            Date d = val as Date
            LocalDate today = LocalDate.now()
            LocalDate BirthDay= new java.sql.Date(d.getTime()).toLocalDate()
            def age = ChronoUnit.YEARS.between(BirthDay, today)
            if (age < 15) {
                errors.rejectValue(propertyName, "invalid.young.dob", ["${age as String}","15"] as Object[], '')
            }
            if (age > 50) {
                errors.rejectValue(propertyName, "invalid.old.dob", ["${age as String}","50"] as Object[], '')
            }
        } else {
            return errors.rejectValue(propertyName, 'invalid.dob')
        }
    }
    Hospital getHospital(Hospital hosp) {
        hosp.name=this.name
        hosp.address1=this.address1
        return hosp
    }

    Patient getPatient(Patient patient) {
        patient.dob=this.dob
        patient.firstName=this.firstName
        patient.lastName=this.lastName
        return patient
    }


}