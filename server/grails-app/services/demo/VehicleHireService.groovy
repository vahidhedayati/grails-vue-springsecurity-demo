package demo

class VehicleHireService {


    def search(CustomSearchBean bean) {
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
        if (bean.make) {
            where = addClause(where, "c.vehicle.make.id=:makeId")
            whereParams.makeId = bean.make?.id
        }

        if (bean.model) {
            where = addClause(where, "c.vehicle.model=:model")
            whereParams.model = bean.model
        }
        def metaParams=[readOnly:true,timeout:15,offset:bean.offset?:0,max:bean.max?:-1]
        query=query+where
        println " -- ${query}"
        println " -0- ${wp}"
        def results=VehicleHire.executeQuery(query,wp,metaParams)
        int total=results.size()
        if (total>=metaParams.max) {
            total=VehicleHire.executeQuery("select count(*) from VehicleContract rv "+where,wp,[readOnly:true,timeout:15,max:1])[0]
        } else {
            total+=metaParams.offset as Long
        }

        return [instanceTotal:total, instanceList:results]
    }

    private String addClause(String where,String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }

}
