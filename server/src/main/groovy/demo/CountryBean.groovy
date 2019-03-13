package demo

import grails.validation.Validateable

//@CompileStatic

//Rather than making a restful domain class since we need to sort by column names etc we will just make a new rest controller
//to control the country file manually instead
//@Resource(uri = '/api/country')

class CountryBean implements  Validateable{
    Long id
    String name

    String code

    Date dateCreated
    Date lastUpdated
    User updateUser


    static constraints = {
        //name unique: true
        id(nullable:true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        updateUser(nullable: true)
        code(blank:true,minSize:2,maxSize:2,matches:/^[a-zA-Z]{2}$/)
    }

    CountryBean bindBean(Country c) {
        this.id=c.id
        this.name=c.name
        this.code=c.code
        this.dateCreated=c.dateCreated
        this.lastUpdated=c.lastUpdated
        this.updateUser=c.updateUser
        return this
    }


    String toString() {
        return "Country name: ${name} code: ${code} updated By: ${updateUser?.username} at ${lastUpdated?.format('dd MMM yyyy hh:mm')}"
    }
}
