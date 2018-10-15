package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFDataFormat
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Font

//@Secured(['ROLE_DRIVER'])
@Secured(['ROLE_DRIVER'])
class VehicleController extends RestfulController {
    static responseFormats = ['json']//, 'xml']

    def vehicleService

    VehicleController() {
        super(Vehicle)
    }
    /**
     * Return a simple object with metadata about the list
     * @return
     */
    def index(Integer max, Integer offset){
        println "-- vs ${params} ${offset}"
        params.max = Math.min(max ?: 10, 100)
        params.offset = offset ?: 0
        def returnValue = [
                objects: listAllResources(params),
                max: params.max,
                offset: params.offset,
                total: countResources()

        ]
        returnValue.numberOfPages=(countResources()/params.max).intValue()+1
        println " number of pages =  ${returnValue.numberOfPages} ${returnValue.total} vs ${returnValue.max} "
        respond returnValue as Object, model: [("${resourceName}Count".toString()): countResources()]
    }


    def export() {
        println "Are we in export ???"
        def allListing = listAllResources([offset:0,max:-1])
        response.setHeader 'Content-type','text/plain; charset=utf-8'
        response.setHeader "Content-disposition", "attachment; filename=index.csv"
        def out = response.outputStream
        out << 'Name,'
        out << "Make,"
        out << 'model,'
        out << "Driver"
        out << '\r'
        allListing?.each{field->
            out << field.name << ','
            out << field.make.name << ','
            out << field.model.name << ','
            out << field.driver.name << ','
            out << '\r'
        }
        out.flush()
        out.close()
    }


    def exportXls() {
        def queryResults = listAllResources([offset:0,max:-1])
        String filename = 'Excel.xls'
        HSSFWorkbook wb = new HSSFWorkbook()
        HSSFSheet sheet = wb.createSheet()
        wb.setMissingCellPolicy(HSSFRow.RETURN_NULL_AND_BLANK)
        def heading=headerList
        int counter=0
        int firstRowCounter
        HSSFCellStyle standard = wb.createCellStyle()
        standard.setVerticalAlignment(CellStyle.VERTICAL_TOP)
        HSSFCellStyle headingStyle = wb.createCellStyle()
        headingStyle.cloneStyleFrom(standard)
        Font headerFont = wb.createFont()
        headingStyle.setAlignment(CellStyle.ALIGN_CENTER)
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD)
        headingStyle.setFont(headerFont)
        HSSFCellStyle  number = wb.createCellStyle()
        number.cloneStyleFrom(standard)
        number.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"))
        heading.eachWithIndex{element, i ->
            HSSFRow row = sheet.createRow(i)
            row.createCell(0).setCellValue("${element}")
            counter=i
        }
        counter=counter+2
        firstRowCounter=counter+1
        HSSFRow row=sheet.createRow(counter)
        def firstRow=headingRowList
        firstRow?.eachWithIndex { cell, i ->
            Cell cell1 = row.createCell(i)
            cell1.setCellValue("${cell}")
            cell1.setCellStyle(headingStyle)
        }
        queryResults.each{ instance ->
            counter++
            row=sheet.createRow(counter)
            def rowItem = excelRow(instance)
            rowItem?.eachWithIndex {qr,i ->
                Cell cell = row.createCell(i)
                cell.setCellValue(qr)
                cell.setCellStyle(standard)
            }
        }

        try {

            // When copying your method over to your new Service
            // as already mentioed out is already provided by plugin
            // the below 4 lines should not be provided in the new service call
            // everything else is identical
            response.setContentType("application/ms-excel")
            response.setHeader("Expires:", "0") // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=$filename")
            OutputStream out = response.outputStream
            // End of no longer required - when converted to plugin service method

            wb.write(out)
            out.close()
        } catch (Exception e) {
        }
    }

    /**
     * used by EXCEL report to print top heading
     * @param bean
     * @return a list of headings
     */
    private List getHeaderList() {
        def out=[]
        out << "some Execel report"
        out << 'Report Date: '+new Date().format('dd MMM yyyy HH:mm:ss')
        return out
    }

    /**
     * used by EXCEL report top  row i.e. field headings
     * @return as list
     */
    private List getHeadingRowList() {
        def out =[]
        out << 'Name'
        out << 'Make'
        out << 'model,'
        out << "Driver"
        return out
    }

    /**
     * iterate through each instanceList returned from queryResults.
     * @param instance
     * @return
     */
    private List excelRow(field) {
        def out = []
        out << field.name
        out << field.make.name
        out << field.model.name
        out << field.driver.name
        return out
    }

    def search() {
     //   println "- search triggered  ${params}"
        def jsonResponse = vehicleService.search(params)
        println "-=-- json response =  "+jsonResponse
        render jsonResponse
    }

    /**
     * Dabbling around this is some custom stuff trying to make it do more complex save based on additional
     * params etc
     * @return
     */

    /**
     * This appears to conflict with default rest save method - not going to be used in this example
     * @param params
     * @return
     */


    def save2(params) {

        println "WE ARE IN SAVE ${request.JSON}"
        def jsonParams = request.JSON
        if (jsonParams.funkyForm) {
            render vehicleService.save(jsonParams)
            return
        } else {
            return super.save()
        }

    }

}
