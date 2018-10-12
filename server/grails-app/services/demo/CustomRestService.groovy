package demo

class CustomRestService {


    def search(bean) {
        String where=''
        Map whereParams=[:]
        List sorts=['contractName', 'vehicleName', 'makeName', 'modelName' ,'driverName']
        List sorts2=['contractName', 'vehicleName', 'makeName','modelName','driverName']

        def sortChoice=sorts.findIndexOf{it==bean.sort}


        String table = "VehicleContract c join c.vehicle v join v.driver d join v.make m join v.model o"

        /**
         * This is where it cross joins to another table to check the history for return date
         */

        String additionalQuery=''
        if (bean.returnDate) {
            table = "VehicleContract c, VehicleHistory h join c.vehicle v join v.driver d join v.make m join v.model o"

            where=addClause(where,"h.contract.id=c.id")

            where=addClause(where,"h.returnDate<=:returnDate")
            whereParams.returnDate=bean.returnDate

            additionalQuery="h.returnDate as returnDate, "

        }
        String query="""
            select new map(     c.id as id, 
                                v.id as vehicleId,
                                c.contractName as contractName,
                                v.name as vehicleName, 
                                m.name as makeName, 
                                o.name as modelName,
                                ${additionalQuery}
                                d.name as driverName
                          )
                from  ${table}
            """

        if (bean.contractName) {
            where=addClause(where,"lower(c.contractName) like :contractName ")
            whereParams.contractName='%'+bean.contractName.toLowerCase()+'%'
        }
        if (bean.vehicleName) {
            where=addClause(where,"lower(c.vehicle.name) like :vehicleName ")
            whereParams.vehicleName='%'+bean.vehicleName.toLowerCase()+'%'
        }
        if (bean.driver) {
            where=addClause(where,"c.vehicle.driver.id=:driverId")
            whereParams.driverId=bean.driver?.id
        }

        if (bean.make) {
            where=addClause(where,"c.vehicle.make.id=:makeId")
            whereParams.makeId=bean.make?.id
        }

        if (bean.model) {
            where=addClause(where,"c.vehicle.model=:model")
            whereParams.model=bean.model
        }

        query+=where

        def metaParams=[readOnly:true,timeout:15,offset:bean.offset?:0,max:bean.max?:-1]
        if (sortChoice>0) {
            query+=" order by ${sorts2[sortChoice]} $bean.order"
        } else {
            query+=" order by c.dateCreated $bean.order"
        }
        def results=VehicleContract.executeQuery(query,whereParams,metaParams)
        int total=results.size()
        if (total>=metaParams.max) {
            total=VehicleContract.executeQuery("select count(*) from VehicleContract c "+where,whereParams,[readOnly:true,timeout:15,max:1])[0]
        } else {
            total+=metaParams.offset as Long
        }

        println "-- instance = ${results}"
        return [instanceList:results, instanceTotal:total]

    }

    private String addClause(String where,String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }
}
