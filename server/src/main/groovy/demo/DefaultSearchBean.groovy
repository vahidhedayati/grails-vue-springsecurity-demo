package demo

import grails.validation.Validateable

/**
 * This controls all the pagination stuff related to the bean searching away
 * used by CustomSearchBean which extends or becomes this and more much much more
 */
class DefaultSearchBean implements Validateable {

    int offset=0
    int total=0
    String sort
    int max = Math.min(10, 100)
    String order = 'desc'

    static constraints = {
        sort(nullable:true)
    }

    protected def formatSearch() {
        return this
    }

    def getSearch() {
        def search=[
                sort:sort,
                offset:offset,
                order:order,
                max:max,
        ]
        return search
    }

    protected def formatBean(Map input) {
        if (input) {
            max = input?.max ?: 10
            offset = input?.offset ? input?.offset as int : 0
            sort = input?.sort
            order = input?.order ?: 'DESC'
        }
        return this
    }

    protected def formatExport() {
        this.max=-1
        this.offset=0
        exporting=true
    }


    void setOffset(String t) {
        offset=(t as int) ?: 0
    }
    void setTotal(String t) {
        total=(t as int) ?: 0
    }
    void setMax(String t) {
        max=(t as int) ?: 0
    }

    /**
     * form order
     * @param o
     */
    void setOrder(String o) {
        order = o in ['asc', 'desc'] ? o :''
    }

    /**
     * form max
     * @param o
     */
    void setMax(Integer o) {
        max= Math.min(o ? o : 10, 50)
    }

}
