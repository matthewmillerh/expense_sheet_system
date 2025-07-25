package expense_sheet_system

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ExpenseControllerSpec extends Specification implements ControllerUnitTest<ExpenseController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
