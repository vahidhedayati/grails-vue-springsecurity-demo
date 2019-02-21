package demo

import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic

@CompileStatic
class VehicleHireRating {

    VehicleHire vehicle

    byte rating

    static constraints = {
    }
}
