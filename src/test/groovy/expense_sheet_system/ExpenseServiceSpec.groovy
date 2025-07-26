package expense_sheet_system

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ExpenseServiceSpec extends Specification {

    ExpenseService expenseService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Expense(...).save(flush: true, failOnError: true)
        //new Expense(...).save(flush: true, failOnError: true)
        //Expense expense = new Expense(...).save(flush: true, failOnError: true)
        //new Expense(...).save(flush: true, failOnError: true)
        //new Expense(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //expense.id
    }

    void "test get"() {
        setupData()

        expect:
        expenseService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Expense> expenseList = expenseService.list(max: 2, offset: 2)

        then:
        expenseList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        expenseService.count() == 5
    }

    void "test delete"() {
        Long expenseId = setupData()

        expect:
        expenseService.count() == 5

        when:
        expenseService.delete(expenseId)
        sessionFactory.currentSession.flush()

        then:
        expenseService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Expense expense = new Expense()
        expenseService.save(expense)

        then:
        expense.id != null
    }
}
