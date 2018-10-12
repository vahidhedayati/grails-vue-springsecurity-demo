package demo

class CustomSearchBean extends DefaultSearchBean {

    String contractName

    Driver driver
    Date fromDate
    Date toDate

    Date returnDate
    User checkedOutBy
    User checkedInBy

    static constraints={
        contractName(nullable: true)
        driver(nullable:true)
        fromDate(nullable:true)
        toDate(nullable:true)
        returnDate(nullable:true)
        checkedOutBy(nullable:true)
        checkedInBy(nullable:true)
    }
}
