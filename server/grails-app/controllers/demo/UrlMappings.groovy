package demo

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        "/api/vehicle"(resources:'vehicle')
        "/api/driver"(resources:'driver')
        "/api/vehiclehire"(resources:'vehicleHire')
        "/api/searchUser"(controller:'driver', action: 'username')
        "/api/exportVehicle"(controller:'vehicle', action: 'export')
        "/api/exportXls"(controller:'vehicle', action: 'exportXls')
        get "/api/customRest"(controller:'customRest', action:'index')
        post "/api/customRest"(controller:'customRest',action:'save')


        //Country config
        delete "/guest/countries/$id(.$format)?"(controller:'country',action:"delete")
        get "/guest/countries"(controller:'country', action:'index')
        get "/guest/countries/$id(.$format)?"(controller:'country',action:"show")
        post "/guest/countries(.$format)?"(controller:'country',action:"save")
        put "/guest/countries/$id(.$format)?"(controller:'country',action:"update")
        patch "/guest/countries/$id(.$format)?"(controller:'country',action:"patch")
        //END Country config

        get "/guest/availableHires"(controller:'vehicleRental', action:'listDefaults')
        get "/guest/hireVehicle"(controller:'vehicleRental', action:'hireVehicle')
        // post "/guest/saveHire"(controller:'vehicleRental',action:'save')

        get "/guest/rental"(controller:'vehicleRental', action:'index')
        post "/guest/rental"(controller:'vehicleRental',action:'save')
        post "/guest/returnRental"(controller:'vehicleRental',action:'saveReturn')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
