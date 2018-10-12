package demo

class CustomSearchBean extends DefaultSearchBean {

    String contractName
    String vehicleName

    Driver driver

    Model model
    Make make

    Date fromDate
    Date toDate

    Date returnDate
    User checkedOutBy
    User checkedInBy

    static constraints={
        contractName(nullable: true)
        vehicleName(nullable:true)
        driver(nullable:true)
        model(nullable:true)
        make(nullable:true)
        fromDate(nullable:true)
        toDate(nullable:true)
        returnDate(nullable:true)
        checkedOutBy(nullable:true)
        checkedInBy(nullable:true)
    }
}
