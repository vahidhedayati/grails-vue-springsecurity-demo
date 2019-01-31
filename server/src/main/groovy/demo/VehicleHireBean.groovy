package demo

import grails.validation.Validateable

class VehicleHireBean implements Validateable {

    //specific vehicle selected from rental screen
    VehicleHire vehicle

    //Will be bound if the current user is existing driver
    Driver driver

    //Otherwise if no driver we capture new driver here
    //When we get rental from a new user - we need to capture their reqirements to sign up
    String username
    String name
    String password



    static constraints = {
        driver(nullable:true)
        username(nullable:true)
        name(nullable:true)
        password(nullable:true)
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
