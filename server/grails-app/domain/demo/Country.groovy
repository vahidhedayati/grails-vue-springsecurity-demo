package demo
//@CompileStatic

//Rather than making a restful domain class since we need to sort by column names etc we will just make a new rest controller
//to control the country file manually instead
//@Resource(uri = '/api/country')

class Country {

    String name

    String code

    Date dateCreated
    Date lastUpdated
    User updateUser


    static constraints = {
        code (unique: true, maxSize: 2,minSize: 2)
        updateUser(nullable:true)
    }

    /**
     * Used when saving a new record / or binding edit record
     * @param c
     * @return
     */
    static Country bindBean(CountryBean c) {
        Country current
        if (c.id) {
            current=Country.load(c.id)
        } else {
            current=new Country()
        }
        current.name=c.name
        current.code=c.code
        current.updateUser=c.updateUser
        return current
    }


    Map loadCountry() {
        Map m = [:]
        m.name=this.name
        m.code=this.code
        m.updateUser=this.updateUser.loadUser()
        m.lastUpdated=this.lastUpdated.format('dd MMM yyyy hh:mm:ss')
        return m
    }
    String toString() {
        return "Country name: ${name} code: ${code} updated By: ${updateUser.username} at ${lastUpdated.format('dd MMM yyyy hh:mm')}"
    }
}
