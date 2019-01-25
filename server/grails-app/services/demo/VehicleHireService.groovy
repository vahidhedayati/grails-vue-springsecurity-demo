package demo

class VehicleHireService {


    def search(params) {
        Map wp=[:]
        String where=''
        String query="""
           from VehicleHire rv  
        """
        if (params.cost) {
            where=addClause(where,"rv.cost >= :cost")
            wp.cost=new BigDecimal(params.cost)
        }
        if (params.deposit) {
            where=addClause(where,"rv.deposit <= :deposit")
            wp.deposit=new BigDecimal(params.deposit)
        }
        if (params.make) {
            where=addClause(where,"rv.make.id <= :make")
            wp.make=params.make as Long
        }
        if (params.model) {
            where=addClause(where,"rv.model.id <= :model")
            wp.model=params.model as Long
        }
        def metaParams=[readOnly:true,timeout:15,offset:params.offset?:0,max:params.max?:-1]
        query=query+where
        def results=VehicleHire.executeQuery(query,wp,metaParams)
        int total=results.size()
        if (total>=metaParams.max) {
            total=EmailQueue.executeQuery("select count(*) fromRentalVehicle rv "+where,wp,[readOnly:true,timeout:15,max:1])[0]
        } else {
            total+=metaParams.offset as Long
        }
        return [instanceTotal:total, instanceList:results]
    }

    private String addClause(String where,String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }

}
