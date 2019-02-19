package demo

import grails.validation.Validateable

class VehicleHireReturnBean implements Validateable {

    //specific vehicle selected from rental screen
    VehicleContract contract
    Date returnDate

    byte rating

    User user

    static constraints = {

    }


}
