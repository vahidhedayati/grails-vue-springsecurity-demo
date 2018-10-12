package demo

import grails.validation.Validateable

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CustomSaveBean implements Validateable {

    String contractName
    String vehicleName

    Driver driver

    Model model
    Make make

    Date fromDate
    Date toDate

    Date returnDate


    static constraints = {
        contractName(nullable:false)
        vehicleName(nullable:false)
        returnDate(validator: this.checkReturnDate)
    }

    static def checkReturnDate={val,obj,errors->
        if (val && val < obj.fromDate) {
            errors.rejectValue(propertyName, "returnedBeforeStart.error", [val.format('dd MMM yyyy'),obj.fromDate.format('dd MMM yyyy')] as Object[], '')
        }
    }

}