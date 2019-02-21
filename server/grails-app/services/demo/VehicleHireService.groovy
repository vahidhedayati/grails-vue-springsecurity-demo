package demo

import grails.gorm.transactions.Transactional

class VehicleHireService {


    def listDefaults(Map params) {
        Map wp=[:]
        String where=''
        String query="""
           select new map(o.id as modelId, o.name as modelName, m.name as makeName, m.id as makeId)
           from VehicleHire rv join rv.make m join rv.model o group by m, o
        """
        List instanceList=[]
        List groupedHires = VehicleHire.executeQuery(query,[:],[readOnly: true])
        String query2="""
                select new map(
                      Round(min(rv.cost), 6) as minCost,
                      Round(max(rv.cost), 6) as maxCost,
                      Round(min(rv.deposit), 6) as minDeposit,
                      Round(max(rv.deposit), 6) as maxDeposit,
                      Round(min(rv.stock), 6) as minStock,
                      Round(max(rv.stock), 6) as maxStock)
                      from VehicleHire rv join rv.make m join rv.model o where o.id=:oid and m.id=:mid
            """
        groupedHires.each {
            //println " >>> ${it} "
            it << VehicleHire.executeQuery(query2,[oid:it.modelId, mid:it.makeId],[readOnly: true])

        }

        return groupedHires

    }

    def search(bean) {
        Map wp=[:]
        String where=''
        String query="""
          select new map (rv.id as id, rv.stock as stock, rv.deposit as deposit,
            rv.cost as cost,rv.name as vehicleName, 
                                m.name as makeName, 
                                o.name as modelName,
                                (select sum(v.rating)/count(v.rating) from VehicleHireRating v 
        where v.vehicle=rv) as rating, rv.registrationPlate as regPlate,
            (select count(*) from VehicleContract vc where vc.vehicle.id = rv.id and vc.returnDate is null) as onHire,  
            (coalesce(rv.stock - (select count(*) from VehicleContract vc where vc.vehicle.id = rv.id and vc.returnDate is null),0) ) as inStock,    
            rv.make as make, rv.model as model) from VehicleHire rv  join rv.make m join rv.model o
        """
        if (bean.cost) {
            where=addClause(where,"rv.cost >= :cost")
            wp.cost=bean.cost
        }
        if (bean.deposit) {
            where=addClause(where,"rv.deposit <= :deposit")
            wp.deposit=bean.deposit
        }
        println " -- make : ${bean?.make}"
        if (bean.make) {
            where = addClause(where, "m.id=:makeId")
            wp.makeId = bean.make as Long
        }

        if (bean.model) {
            where = addClause(where, "o.id=:model")
            wp.model = bean.model as Long
        }
        def metaParams=[readOnly:true,timeout:15,offset:bean.offset?:0,max:bean.max?:-1]
        query=query+where
        println " = $query $wp"
        def results=VehicleHire.executeQuery(query,wp,metaParams)
        int total=results.size()
        if (total>=metaParams.max) {
            total=VehicleHire.executeQuery("select count(*) from VehicleHire rv join rv.make m join rv.model o "+where,wp,[readOnly:true,timeout:15,max:1])[0]
        } else {
            total+=metaParams.offset as Long
        }
        return [instanceTotal:total, instanceList:results]
    }

    private String addClause(String where,String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }

    @Transactional
    def save(VehicleHireBean bean) {
        println "saving"
        if (!bean.driver) {
            println "no user found creating a new authenticating user"

            //we know this is a guest user so lets add the user and set the bean's driver details
            Role role =Role.findByAuthority('ROLE_DRIVER')
            if (!role) {
                role = new Role(authority: "ROLE_DRIVER").save()
            }
            bean.driver= new RentalDriver(name: bean.name, username: bean.username, password: bean.password).save()
            UserRole.create( bean.driver, role, true)
        } else {
            println "Driver was already logged in "
        }

        VehicleContract vc = new VehicleContract()
        vc.vehicle=bean.vehicle
        //Can't figure out why it saves properly but custom listing can't bind to driver object - really unusual
        vc.driver=bean.driver
        vc.fromDate=bean.fromDate
        vc.toDate=bean.toDate
        vc.contractName="""${bean.vehicle.name} hired to ${bean.driver.name} 
                (${bean.fromDate.format('dd-MMM-yyyy')}/${bean.toDate.format('dd-MMM-yyyy')}) """
        if (!vc.save(flush:true)) {
            println "vcerrors: ${vc.errors}"
        }
        VehicleHistory vh = new VehicleHistory()
        vh.contract=vc
        vh.checkedOutBy=bean.driver
        vh.save()
        println " vc = ${vc.id} ${vc.driver}"
        return vc
    }

    @Transactional
    def saveReturn(VehicleHireReturnBean bean) {

        ///bean.contract.vehicle.rating=bean.rating

        VehicleHireRating vhr = new VehicleHireRating()
        vhr.vehicle=bean.contract.vehicle
        vhr.rating=bean.rating
        vhr.save()

        bean.contract.returnDate=bean.returnDate

        bean.contract.save()

        VehicleHistory vh = VehicleHistory.findByContract(bean.contract)
        if (vh) {
            vh.returnDate=bean.returnDate
            vh.checkedInBy=bean.user
            vh.save()
        }

    }

}
