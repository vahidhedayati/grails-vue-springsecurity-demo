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
            values.vehicle=new VehicleHire(name: "Rental Vehicle ${i}", registrationPlate: "REG-${i}",
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


        //https://developers.google.com/public-data/docs/canonical/countries_csv
        //awk -F"\t" '{print "new Country(code:""\""$1"\", name:""\""$4"\").save()" }' countries.txt
        new Country(code:"AD", name:"Andorra").save()
        new Country(code:"AE", name:"United Arab Emirates").save()
        new Country(code:"AF", name:"Afghanistan").save()
        new Country(code:"AG", name:"Antigua and Barbuda").save()
        new Country(code:"AI", name:"Anguilla").save()
        new Country(code:"AL", name:"Albania").save()
        new Country(code:"AM", name:"Armenia").save()
        new Country(code:"AN", name:"Netherlands Antilles").save()
        new Country(code:"AO", name:"Angola").save()
        new Country(code:"AQ", name:"Antarctica").save()
        new Country(code:"AR", name:"Argentina").save()
        new Country(code:"AS", name:"American Samoa").save()
        new Country(code:"AT", name:"Austria").save()
        new Country(code:"AU", name:"Australia").save()
        new Country(code:"AW", name:"Aruba").save()
        new Country(code:"AZ", name:"Azerbaijan").save()
        new Country(code:"BA", name:"Bosnia and Herzegovina").save()
        new Country(code:"BB", name:"Barbados").save()
        new Country(code:"BD", name:"Bangladesh").save()
        new Country(code:"BE", name:"Belgium").save()
        new Country(code:"BF", name:"Burkina Faso").save()
        new Country(code:"BG", name:"Bulgaria").save()
        new Country(code:"BH", name:"Bahrain").save()
        new Country(code:"BI", name:"Burundi").save()
        new Country(code:"BJ", name:"Benin").save()
        new Country(code:"BM", name:"Bermuda").save()
        new Country(code:"BN", name:"Brunei").save()
        new Country(code:"BO", name:"Bolivia").save()
        new Country(code:"BR", name:"Brazil").save()
        new Country(code:"BS", name:"Bahamas").save()
        new Country(code:"BT", name:"Bhutan").save()
        new Country(code:"BV", name:"Bouvet Island").save()
        new Country(code:"BW", name:"Botswana").save()
        new Country(code:"BY", name:"Belarus").save()
        new Country(code:"BZ", name:"Belize").save()
        new Country(code:"CA", name:"Canada").save()
        new Country(code:"CC", name:"Cocos [Keeling] Islands").save()
        new Country(code:"CD", name:"Congo [DRC]").save()
        new Country(code:"CF", name:"Central African Republic").save()
        new Country(code:"CG", name:"Congo [Republic]").save()
        new Country(code:"CH", name:"Switzerland").save()
        new Country(code:"CI", name:"Côte d'Ivoire").save()
        new Country(code:"CK", name:"Cook Islands").save()
        new Country(code:"CL", name:"Chile").save()
        new Country(code:"CM", name:"Cameroon").save()
        new Country(code:"CN", name:"China").save()
        new Country(code:"CO", name:"Colombia").save()
        new Country(code:"CR", name:"Costa Rica").save()
        new Country(code:"CU", name:"Cuba").save()
        new Country(code:"CV", name:"Cape Verde").save()
        new Country(code:"CX", name:"Christmas Island").save()
        new Country(code:"CY", name:"Cyprus").save()
        new Country(code:"CZ", name:"Czech Republic").save()
        new Country(code:"DE", name:"Germany").save()
        new Country(code:"DJ", name:"Djibouti").save()
        new Country(code:"DK", name:"Denmark").save()
        new Country(code:"DM", name:"Dominica").save()
        new Country(code:"DO", name:"Dominican Republic").save()
        new Country(code:"DZ", name:"Algeria").save()
        new Country(code:"EC", name:"Ecuador").save()
        new Country(code:"EE", name:"Estonia").save()
        new Country(code:"EG", name:"Egypt").save()
        new Country(code:"EH", name:"Western Sahara").save()
        new Country(code:"ER", name:"Eritrea").save()
        new Country(code:"ES", name:"Spain").save()
        new Country(code:"ET", name:"Ethiopia").save()
        new Country(code:"FI", name:"Finland").save()
        new Country(code:"FJ", name:"Fiji").save()
        new Country(code:"FK", name:"Falkland Islands [Islas Malvinas]").save()
        new Country(code:"FM", name:"Micronesia").save()
        new Country(code:"FO", name:"Faroe Islands").save()
        new Country(code:"FR", name:"France").save()
        new Country(code:"GA", name:"Gabon").save()
        new Country(code:"GB", name:"United Kingdom").save()
        new Country(code:"GD", name:"Grenada").save()
        new Country(code:"GE", name:"Georgia").save()
        new Country(code:"GF", name:"French Guiana").save()
        new Country(code:"GG", name:"Guernsey").save()
        new Country(code:"GH", name:"Ghana").save()
        new Country(code:"GI", name:"Gibraltar").save()
        new Country(code:"GL", name:"Greenland").save()
        new Country(code:"GM", name:"Gambia").save()
        new Country(code:"GN", name:"Guinea").save()
        new Country(code:"GP", name:"Guadeloupe").save()
        new Country(code:"GQ", name:"Equatorial Guinea").save()
        new Country(code:"GR", name:"Greece").save()
        new Country(code:"GS", name:"South Georgia and the South Sandwich Islands").save()
        new Country(code:"GT", name:"Guatemala").save()
        new Country(code:"GU", name:"Guam").save()
        new Country(code:"GW", name:"Guinea-Bissau").save()
        new Country(code:"GY", name:"Guyana").save()
        new Country(code:"GZ", name:"Gaza Strip").save()
        new Country(code:"HK", name:"Hong Kong").save()
        new Country(code:"HM", name:"Heard Island and McDonald Islands").save()
        new Country(code:"HN", name:"Honduras").save()
        new Country(code:"HR", name:"Croatia").save()
        new Country(code:"HT", name:"Haiti").save()
        new Country(code:"HU", name:"Hungary").save()
        new Country(code:"ID", name:"Indonesia").save()
        new Country(code:"IE", name:"Ireland").save()
        new Country(code:"IL", name:"Israel").save()
        new Country(code:"IM", name:"Isle of Man").save()
        new Country(code:"IN", name:"India").save()
        new Country(code:"IO", name:"British Indian Ocean Territory").save()
        new Country(code:"IQ", name:"Iraq").save()
        new Country(code:"IR", name:"Iran").save()
        new Country(code:"IS", name:"Iceland").save()
        new Country(code:"IT", name:"Italy").save()
        new Country(code:"JE", name:"Jersey").save()
        new Country(code:"JM", name:"Jamaica").save()
        new Country(code:"JO", name:"Jordan").save()
        new Country(code:"JP", name:"Japan").save()
        new Country(code:"KE", name:"Kenya").save()
        new Country(code:"KG", name:"Kyrgyzstan").save()
        new Country(code:"KH", name:"Cambodia").save()
        new Country(code:"KI", name:"Kiribati").save()
        new Country(code:"KM", name:"Comoros").save()
        new Country(code:"KN", name:"Saint Kitts and Nevis").save()
        new Country(code:"KP", name:"North Korea").save()
        new Country(code:"KR", name:"South Korea").save()
        new Country(code:"KW", name:"Kuwait").save()
        new Country(code:"KY", name:"Cayman Islands").save()
        new Country(code:"KZ", name:"Kazakhstan").save()
        new Country(code:"LA", name:"Laos").save()
        new Country(code:"LB", name:"Lebanon").save()
        new Country(code:"LC", name:"Saint Lucia").save()
        new Country(code:"LI", name:"Liechtenstein").save()
        new Country(code:"LK", name:"Sri Lanka").save()
        new Country(code:"LR", name:"Liberia").save()
        new Country(code:"LS", name:"Lesotho").save()
        new Country(code:"LT", name:"Lithuania").save()
        new Country(code:"LU", name:"Luxembourg").save()
        new Country(code:"LV", name:"Latvia").save()
        new Country(code:"LY", name:"Libya").save()
        new Country(code:"MA", name:"Morocco").save()
        new Country(code:"MC", name:"Monaco").save()
        new Country(code:"MD", name:"Moldova").save()
        new Country(code:"ME", name:"Montenegro").save()
        new Country(code:"MG", name:"Madagascar").save()
        new Country(code:"MH", name:"Marshall Islands").save()
        new Country(code:"MK", name:"Macedonia [FYROM]").save()
        new Country(code:"ML", name:"Mali").save()
        new Country(code:"MM", name:"Myanmar [Burma]").save()
        new Country(code:"MN", name:"Mongolia").save()
        new Country(code:"MO", name:"Macau").save()
        new Country(code:"MP", name:"Northern Mariana Islands").save()
        new Country(code:"MQ", name:"Martinique").save()
        new Country(code:"MR", name:"Mauritania").save()
        new Country(code:"MS", name:"Montserrat").save()
        new Country(code:"MT", name:"Malta").save()
        new Country(code:"MU", name:"Mauritius").save()
        new Country(code:"MV", name:"Maldives").save()
        new Country(code:"MW", name:"Malawi").save()
        new Country(code:"MX", name:"Mexico").save()
        new Country(code:"MY", name:"Malaysia").save()
        new Country(code:"MZ", name:"Mozambique").save()
        new Country(code:"NA", name:"Namibia").save()
        new Country(code:"NC", name:"New Caledonia").save()
        new Country(code:"NE", name:"Niger").save()
        new Country(code:"NF", name:"Norfolk Island").save()
        new Country(code:"NG", name:"Nigeria").save()
        new Country(code:"NI", name:"Nicaragua").save()
        new Country(code:"NL", name:"Netherlands").save()
        new Country(code:"NO", name:"Norway").save()
        new Country(code:"NP", name:"Nepal").save()
        new Country(code:"NR", name:"Nauru").save()
        new Country(code:"NU", name:"Niue").save()
        new Country(code:"NZ", name:"New Zealand").save()
        new Country(code:"OM", name:"Oman").save()
        new Country(code:"PA", name:"Panama").save()
        new Country(code:"PE", name:"Peru").save()
        new Country(code:"PF", name:"French Polynesia").save()
        new Country(code:"PG", name:"Papua New Guinea").save()
        new Country(code:"PH", name:"Philippines").save()
        new Country(code:"PK", name:"Pakistan").save()
        new Country(code:"PL", name:"Poland").save()
        new Country(code:"PM", name:"Saint Pierre and Miquelon").save()
        new Country(code:"PN", name:"Pitcairn Islands").save()
        new Country(code:"PR", name:"Puerto Rico").save()
        new Country(code:"PS", name:"Palestinian Territories").save()
        new Country(code:"PT", name:"Portugal").save()
        new Country(code:"PW", name:"Palau").save()
        new Country(code:"PY", name:"Paraguay").save()
        new Country(code:"QA", name:"Qatar").save()
        new Country(code:"RE", name:"Réunion").save()
        new Country(code:"RO", name:"Romania").save()
        new Country(code:"RS", name:"Serbia").save()
        new Country(code:"RU", name:"Russia").save()
        new Country(code:"RW", name:"Rwanda").save()
        new Country(code:"SA", name:"Saudi Arabia").save()
        new Country(code:"SB", name:"Solomon Islands").save()
        new Country(code:"SC", name:"Seychelles").save()
        new Country(code:"SD", name:"Sudan").save()
        new Country(code:"SE", name:"Sweden").save()
        new Country(code:"SG", name:"Singapore").save()
        new Country(code:"SH", name:"Saint Helena").save()
        new Country(code:"SI", name:"Slovenia").save()
        new Country(code:"SJ", name:"Svalbard and Jan Mayen").save()
        new Country(code:"SK", name:"Slovakia").save()
        new Country(code:"SL", name:"Sierra Leone").save()
        new Country(code:"SM", name:"San Marino").save()
        new Country(code:"SN", name:"Senegal").save()
        new Country(code:"SO", name:"Somalia").save()
        new Country(code:"SR", name:"Suriname").save()
        new Country(code:"ST", name:"São Tomé and Príncipe").save()
        new Country(code:"SV", name:"El Salvador").save()
        new Country(code:"SY", name:"Syria").save()
        new Country(code:"SZ", name:"Swaziland").save()
        new Country(code:"TC", name:"Turks and Caicos Islands").save()
        new Country(code:"TD", name:"Chad").save()
        new Country(code:"TF", name:"French Southern Territories").save()
        new Country(code:"TG", name:"Togo").save()
        new Country(code:"TH", name:"Thailand").save()
        new Country(code:"TJ", name:"Tajikistan").save()
        new Country(code:"TK", name:"Tokelau").save()
        new Country(code:"TL", name:"Timor-Leste").save()
        new Country(code:"TM", name:"Turkmenistan").save()
        new Country(code:"TN", name:"Tunisia").save()
        new Country(code:"TO", name:"Tonga").save()
        new Country(code:"TR", name:"Turkey").save()
        new Country(code:"TT", name:"Trinidad and Tobago").save()
        new Country(code:"TV", name:"Tuvalu").save()
        new Country(code:"TW", name:"Taiwan").save()
        new Country(code:"TZ", name:"Tanzania").save()
        new Country(code:"UA", name:"Ukraine").save()
        new Country(code:"UG", name:"Uganda").save()
        new Country(code:"UM", name:"U.S. Minor Outlying Islands").save()
        new Country(code:"US", name:"United States").save()
        new Country(code:"UY", name:"Uruguay").save()
        new Country(code:"UZ", name:"Uzbekistan").save()
        new Country(code:"VA", name:"Vatican City").save()
        new Country(code:"VC", name:"Saint Vincent and the Grenadines").save()
        new Country(code:"VE", name:"Venezuela").save()
        new Country(code:"VG", name:"British Virgin Islands").save()
        new Country(code:"VI", name:"U.S. Virgin Islands").save()
        new Country(code:"VN", name:"Vietnam").save()
        new Country(code:"VU", name:"Vanuatu").save()
        new Country(code:"WF", name:"Wallis and Futuna").save()
        new Country(code:"WS", name:"Samoa").save()
        new Country(code:"XK", name:"Kosovo").save()
        new Country(code:"YE", name:"Yemen").save()
        new Country(code:"YT", name:"Mayotte").save()
        new Country(code:"ZA", name:"South Africa").save()
        new Country(code:"ZM", name:"Zambia").save()
        new Country(code:"ZW", name:"Zimbabwe").save()


        Country.findAll()?.each {
            it.updateUser=driver1
            it.save()
        }


    }
    def destroy = {
    }
}
