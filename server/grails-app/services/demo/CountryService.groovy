package demo

import grails.transaction.Transactional

class CountryService {

    //This will do both edit / create
    @Transactional
    def save(CountryBean bean)  {
        Country country = Country.bindBean(bean)
        return  country.save(flush:true)
    }

    @Transactional
    def deleteRecord(Long id) {
        println "Delete"

        Country country = Country.load(id)
        if (country) {
            String name= country.name
            country.delete(flush:true)
            return "$name deleted"
        }
    }


    def search(bean) {
        String where = ''
        Map whereParams = [:]
        List sorts = ['id', 'name', 'code', 'lastUpdated', 'updateUser']


        def sortChoice = sorts.findIndexOf { it == bean.sort }


        /**
         * This is where it cross joins to another table to check the history for return date
         */

        String additionalQuery = ''
        if (bean.name) {
            where = addClause(where, "lower(c.name) like :name")
            whereParams.name = '%'+bean.name.toLowerCase()+'%'
        }
        String query = """
            select new map(     c.id as id, 
                                c.lastUpdated as lastUpdated,
                                c.name as name,
                                c.code as code,
                                c.updateUser as updateUser
                                
                                
                          )
                from Country c
            """


        if (bean.id) {
            where = addClause(where, "c.id=:id")
            whereParams.id = bean?.id
        }

        query += where

        def metaParams = [readOnly: true, timeout: 15, offset: bean.offset ?: 0, max: bean.max ?: -1]
        if (sortChoice > 0) {
            query += " order by ${sorts[sortChoice]} $bean.order"
        } else {
            query += " order by c.lastUpdated $bean.order"
        }
        println "-- $query == ${whereParams}"
        def results = VehicleContract.executeQuery(query, whereParams, metaParams)
        int total = results.size()
        if (total >= metaParams.max) {
            total = VehicleContract.executeQuery("select count(*) from  Country c "+where, whereParams, [readOnly: true, timeout: 15, max: 1])[0]
        } else {
            total += metaParams.offset as Long
        }

        return [instanceList: results, instanceTotal: total]

    }

    private String addClause(String where, String clause) {
        return (where ? where + ' and ' : 'where ') + clause
    }
}
