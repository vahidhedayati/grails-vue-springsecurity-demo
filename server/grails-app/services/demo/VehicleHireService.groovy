package demo

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

}
