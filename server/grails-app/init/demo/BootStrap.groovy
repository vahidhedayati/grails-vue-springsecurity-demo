package demo


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

        Date yesterday = new Date()-1
        Double cost=new Double(120.50)
        Double deposit=new Double(500)
        for (int i=1; i < 13; i++) {
            //bootStrapContent << [i,
            Map values=[:]
            values.driver=new RentalDriver(name: "Driver ${i}", username: "driver${i}", password: "password${i}").save()

            UserRole.create(values.driver, role, true)

            values.make=makes.get(r.nextInt(makesSize))
            //println " make is --------------------------------------------------------------------  ${values.make}"

            values.model=models.get(r.nextInt(makesSize))

           // values.vehicle=new Vehicle(name: "Vehice ${i}", driver: values.driver, make: values.make, model: values.model).save()
            cost=cost+(i*5)
            deposit=deposit-(i*2)
            values.vehicle=new VehicleHire(name: "Rental Vehice ${i}", registrationPlate: "REG-${i}",
                    rating:r.nextInt(5)  as byte,
                    make: values.make, model: values.model, cost: cost, deposit: deposit, stock: 10).save()

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
                        vehicle: values.vehicle, fromDate: calendar.getTime(),returnDate: yesterday,
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

    }
    def destroy = {
    }
}
