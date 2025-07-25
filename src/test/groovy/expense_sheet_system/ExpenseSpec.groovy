package expense_sheet_system

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ExpenseSpec extends Specification implements DomainUnitTest<Expense> {

     void "test domain constraints"() {
        when:
        Expense domain = new Expense()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
