package demo

import grails.validation.Validateable

class VehicleHireBean implements Validateable {

    //specific vehicle selected from rental screen
    VehicleHire vehicle

    //Will be bound if the current user is existing driver
    RentalDriver driver

    //Otherwise if no driver we capture new driver here
    //When we get rental from a new user - we need to capture their reqirements to sign up
    String username
    String name
    String password

    Date fromDate
    Date toDate


    static constraints = {
        driver(nullable:true, validator:checkDriver)
        username(nullable:true,size:5..15, matches: "[a-zA-Z0-9]+")
        name(nullable:true,minSize: 2,maxSize: 200)
        password(nullable:true,size:5..15)
        fromDate(nullable:true)
        toDate(nullable:true, validator:checkDates)

    }



    static def checkDates= { val, obj, errors ->
        if (obj.fromDate && val) {
            if (obj.fromDate >val) {
                errors.rejectValue(propertyName, "invalid.fromDate")
            } else {

                //This calculates the end of the month based on fromDate and
                //if toDate is greater than end of month it fails
                Calendar cal = Calendar.getInstance()
                cal.setTime(obj.fromDate)
                int endDay  = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                cal.set(Calendar.DAY_OF_MONTH,endDay)
                if (val > cal.getTime()) {
                    errors.rejectValue(propertyName, "longerThanMonth.label")
                }
            }
        }
    }

    static def checkDriver= { val, obj, errors ->
        if (!val &&  (!obj.username ||!obj.name ||!obj.password)) {
            errors.rejectValue(propertyName, "invalid.driver")
        }
    }

    VehicleHireBean() {}

    VehicleHireBean(Long id) {
        this.vehicle=VehicleHire.load(id)
    }

    VehicleHireBean(Driver driver,Long id) {
        this.driver=driver
        this.vehicle=VehicleHire.load(id)
    }
}
