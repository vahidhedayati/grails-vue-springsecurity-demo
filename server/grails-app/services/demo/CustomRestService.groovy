package demo

import exceptions.ContractNameTakenException
import exceptions.UserTakenException
import exceptions.VehicleException
import grails.gorm.transactions.Transactional
import net.sf.ehcache.transaction.manager.TransactionManagerLookup

import javax.xml.bind.ValidationException

class CustomRestService {


    def search(bean) {
        String where = ''
        Map whereParams = [:]
        List sorts = ['id', 'contractName', 'vehicleName', 'makeName', 'modelName', 'driverName']
        List sorts2 = ['id', 'contractName', 'vehicleName', 'makeName', 'modelName', 'driverName']

        def sortChoice = sorts.findIndexOf { it == bean.sort }


        String table = "VehicleContract c join c.vehicle v join v.driver d join v.make m join v.model o"

        /**
         * This is where it cross joins to another table to check the history for return date
         */

        String additionalQuery = ''
        if (bean.returnDate) {
            table = "VehicleContract c, VehicleHistory h join c.vehicle v join v.driver d join v.make m join v.model o"

            where = addClause(where, "h.contract.id=c.id")

            where = addClause(where, "h.returnDate<=:returnDate")
            whereParams.returnDate = bean.returnDate

            additionalQuery = "h.returnDate as returnDate, "

        }
        String query = """
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
            where = addClause(where, "lower(c.contractName) like :contractName ")
            whereParams.contractName = '%' + bean.contractName.toLowerCase() + '%'
        }
        if (bean.vehicleName) {
            where = addClause(where, "lower(c.vehicle.name) like :vehicleName ")
            whereParams.vehicleName = '%' + bean.vehicleName.toLowerCase() + '%'
        }
        if (bean.driver) {
            where = addClause(where, "c.vehicle.driver.id=:driverId")
            whereParams.driverId = bean.driver?.id
        }

        if (bean.make) {
            where = addClause(where, "c.vehicle.make.id=:makeId")
            whereParams.makeId = bean.make?.id
        }

        if (bean.model) {
            where = addClause(where, "c.vehicle.model=:model")
            whereParams.model = bean.model
        }

        query += where

        def metaParams = [readOnly: true, timeout: 15, offset: bean.offset ?: 0, max: bean.max ?: -1]
        if (sortChoice > 0) {
            query += " order by ${sorts2[sortChoice]} $bean.order"
        } else {
            query += " order by c.dateCreated $bean.order"
        }
        def results = VehicleContract.executeQuery(query, whereParams, metaParams)
        int total = results.size()
        if (total >= metaParams.max) {
            total = VehicleContract.executeQuery("select count(*) from VehicleContract c " + where, whereParams, [readOnly: true, timeout: 15, max: 1])[0]
        } else {
            total += metaParams.offset as Long
        }

        println "-- instance = ${results}"
        return [instanceList: results, instanceTotal: total]

    }

    @Transactional
    CustomSaveBean save(CustomSaveBean bean) {
       // try {
            checkTableSizes()
            saveDriver(bean)
            checkTableSizes()
            if (bean.driver) {

                saveVehicle(bean)
                checkTableSizes()
                if (bean.vehicle) {

                    saveVehicleContract(bean)
                    checkTableSizes()
                    if (bean.contract) {
                        //VehicleHistory history =
                        saveVehicleHistory(bean)
                        checkTableSizes()
                    }
                }
            }
       // } catch (Throwable t) {
        //    println "--- rr "+t
        //}

        return bean
    }

    void checkTableSizes() {
        println "== Drivers: ${Driver.findAll().size()}  Vehicles: ${Vehicle.findAll().size()} - VehicleContract ${VehicleContract.findAll().size()} "
    }

    /**
     *
     * This has a unique username constraint and should fail on saving rest
     * @param driverName
     * @return
     */
    @Transactional //(rollbackFor= UserTakenException.class)
    def saveDriver(CustomSaveBean bean) {

        Driver driver = new Driver()
        driver.name = bean.driverName
        driver.username = bean.driverName
        driver.password = 'password'
        driver.save(flush: true)

        if (!driver.save(flush:true)) {
            bean.errors.rejectValue('driverName','usernameTaken.label')
            throw new UserTakenException()
        }
        bean.driver = driver

    }

    @Transactional//(rollbackFor= UserTakenException.class)
    def saveVehicle(CustomSaveBean bean) {
        //  try {
        Vehicle vehicle = new Vehicle()
        vehicle.name = bean.vehicleName
        vehicle.make = bean.make
        vehicle.model = bean.model
        vehicle.driver = bean.driver
        if (!vehicle.save(flush:true)) {
            bean.errors.rejectValue('vehicleName','vehicleTaken.label')
            throw new VehicleException()

        }
        if (vehicle) {
            bean.vehicle=vehicle
        }

    }

    /**
     *
     * This has a unique constraint and needs to fail any added vehicles drivers
     * @param contractName
     * @param fromDate
     * @param toDate
     * @param vehicle
     * @param driver
     * @return
     */

    @Transactional//(rollbackFor= VehicleException.class)
    def saveVehicleContract(CustomSaveBean bean) {
        // try {
        VehicleContract vehicleContract = new VehicleContract()
        vehicleContract.contractName = bean.contractName
        vehicleContract.fromDate = bean.fromDate
        vehicleContract.toDate = bean.toDate
        vehicleContract.vehicle = bean.vehicle
        vehicleContract.driver = bean.driver

        if (!vehicleContract.save(flush:true)) {
            bean.errors.rejectValue('contractName','contractTaken.label')
            throw new ContractNameTakenException()
        }
        bean.contract=  vehicleContract
    }

    @Transactional
    def saveVehicleHistory(CustomSaveBean bean) {
        VehicleHistory vehicleHistory = new VehicleHistory()
        vehicleHistory.returnDate = bean.returnDate
        vehicleHistory.contract = bean.contract
        bean.history = vehicleHistory.save()

    }

    private String addClause(String where, String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }

}