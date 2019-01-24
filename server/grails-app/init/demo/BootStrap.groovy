package demo

import demo.*
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter("corsFilterTest",
                SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order - 1)


        def nissan = new Make(name: "Nissan").save()
        def ford = new Make(name: "Ford").save()

        def titan = new Model(name: "Titan").save()
        def leaf = new Model(name: "Leaf").save()
        def windstar = new Model(name: "Windstar").save()

        Role role = new Role(authority: "ROLE_DRIVER").save()
        List makes=[]
        makes << nissan
        makes << ford

        List models=[]
        models << titan
        models << leaf
        models << windstar


        Map bootStrapContent=[:]

        def r = new Random()

        int makesSize=makes.size()

        //Today last year
        Date today = new Date()-365

        Driver driver1= new Driver(name: "Susan", username: "susan", password: "password1").save()
        Driver driver2= new Driver(name: "Pedro", username:  "pedro", password: "password2").save()

        UserRole.create(driver1, role, true)  //<3>
        UserRole.create(driver2, role, true)


        for (int i=1; i < 13; i++) {
            //bootStrapContent << [i,
            Map values=[:]
            values.driver=new Driver(name: "Driver ${i}", username: "driver${i}", password: "password${i}").save()

            UserRole.create(values.driver, role, true)

            values.make=makes.get(r.nextInt(makesSize))

            values.model=models.get(r.nextInt(makesSize))

            values.vehicle=new Vehicle(name: "Vehice ${i}", driver: values.driver, make: values.make, model: values.model).save()

            bootStrapContent."${i}"=values
        }




        List contractsHistory=[]
        /**
         * Iterate through bootStrapContent and append through each key generating a 12 month contract per user/vehicle
         */

            bootStrapContent.each { key, values ->
                println "We have key ${key}: ${values}"

                bootStrapContent.keySet().each { k ->

                    Calendar calendar = today.toCalendar()
                    Calendar endCalendar = today.toCalendar()
                    calendar.add(Calendar.MONTH, k as int)
                    endCalendar.add(Calendar.MONTH, k as int)
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    endCalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                    String period = " ${calendar.getTime().format(' dd MMM yyyy')} - ${endCalendar.getTime().format(' dd MMM yyyy')} "
                    VehicleContract vehicleContract = new VehicleContract(contractName: values.driver.name + " contract for ${period}",
                        vehicle: values.vehicle, fromDate: calendar.getTime(),
                        toDate: endCalendar.getTime(), driver: values.driver).save()

                    VehicleHistory vehicleHistory = new VehicleHistory(contract: vehicleContract, returnDate: endCalendar.getTime(), checkedOutBy: driver1, checkedInBy: driver2).save();

                    contractsHistory<< [contract:vehicleContract,history:vehicleHistory]

            }

        }
        /**
         * this should contain a huge list of 12*12 contracts / history
         */
        contractsHistory?.each { ch ->
            println " Contract ${ch.contract} has history ${ch.history}"
        }



        Vehicle v1 = new Vehicle(name: "Pickup", driver: driver1, make: nissan, model: titan).save()
        Vehicle v2 = new Vehicle(name: "Economy", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v3 = new Vehicle(name: "Kicks", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v4 = new Vehicle(name: "370z", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v5 = new Vehicle(name: "Minivan", driver: driver2, make: ford, model: windstar).save()
        Vehicle v6 = new Vehicle(name: "Focus", driver: driver2, make: ford, model: windstar).save()
        Vehicle v7 = new Vehicle(name: "Fiesta", driver: driver2, make: ford, model: windstar).save()
        Vehicle v8 = new Vehicle(name: "Mondeo", driver: driver2, make: ford, model: windstar).save()
        Vehicle v9 = new Vehicle(name: "Micra", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v10 = new Vehicle(name: "Altima", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v11 = new Vehicle(name: "Frontier", driver: driver1, make: nissan, model: leaf).save()
        Vehicle v12 = new Vehicle(name: "Granada", driver: driver2, make: ford, model: windstar).save()


        VehicleContract vc1  = new VehicleContract(contractName:'aa', vehicle: v1,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        VehicleContract vc2  = new VehicleContract(contractName:'bb',vehicle: v2,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        VehicleContract vc3  = new VehicleContract(contractName:'cc',vehicle: v3,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        VehicleContract vc4  = new VehicleContract(contractName:'dd',vehicle: v4,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        VehicleContract vc5  = new VehicleContract(contractName:'ee', vehicle: v5,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        VehicleContract vc6  = new VehicleContract(contractName:'ff', vehicle: v6,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()

        new VehicleContract(contractName:'gg',vehicle: v7,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        new VehicleContract(contractName:'hh',vehicle: v8,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        new VehicleContract(contractName:'ii',vehicle: v9,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        new VehicleContract(contractName:'jj',vehicle: v10,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()
        new VehicleContract(contractName:'kk',vehicle: v12,fromDate: new Date()-365, toDate: new Date(), driver:driver1).save()


        new VehicleContract(contractName:'qq',vehicle: v1,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'rr',vehicle: v2,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'ss',vehicle: v3,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'tt',vehicle: v4,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'uu',vehicle: v5,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'vv',vehicle: v6,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'ww',vehicle: v7,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'xx',vehicle: v8,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'yy',vehicle: v9,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'zz',vehicle: v10,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()
        new VehicleContract(contractName:'aabb',vehicle: v12,fromDate: new Date()-365, toDate: new Date(), driver:driver2).save()

        VehicleHistory vh1 = new VehicleHistory(contract: vc1,returnDate: new Date(),checkedOutBy: driver1,checkedInBy: driver2).save();
        new VehicleHistory(contract: vc2,returnDate: new Date()-2,checkedOutBy: driver1,checkedInBy: driver2).save();
        new VehicleHistory(contract: vc3,returnDate: new Date()-3,checkedOutBy: driver1,checkedInBy: driver2).save();

        new VehicleHistory(contract: vc4,returnDate: new Date()-4,checkedOutBy: driver2,checkedInBy: driver1).save();
        new VehicleHistory(contract: vc5,returnDate: new Date()-5,checkedOutBy: driver2,checkedInBy: driver1).save();
        new VehicleHistory(contract: vc6,returnDate: new Date()-6,checkedOutBy: driver2,checkedInBy: driver1).save();

        println "---> ${vc1.id} ID of contract 1  + ${vh1?.id}"
    }
    def destroy = {
    }
}
