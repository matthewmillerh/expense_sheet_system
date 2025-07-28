package expense_sheet_system

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

/**
 * Controller for managing Expense entities in the expense sheet system.
 * Uses static scaffolding with user session validation and running balance calculation.
 */
class ExpenseController {

    ExpenseService expenseService
    CurrencyConversionService currencyConversionService
    ExportTransactionsService exportTransactionsService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /**
     * List expenses for the logged-in user with running balance calculation
     */
    def index(Integer max) {
        def user = User.get(session.userId)
        def expenses = Expense.findAllByUser(user, [sort: 'date', order: 'asc'])

        // Process expenses to add running balance and USD conversion
        if (expenses) {         
            processExpenseList(expenses, user)
        } 

        // Reverse for display (newest first)
        expenses = expenses.reverse()
        
        respond expenses, model: [expenseList: expenses, expenseCount: expenses.size()]
    }

    def show(Long id) {
        respond expenseService.get(id)
    }

    def create() {
        respond new Expense(params)
    }

    def save(Expense expense) {
        if (expense == null) {
            notFound()
            return
        }

        // Always set the user to the logged-in user
        def user = User.get(session.userId)
        expense.user = user

        // Clear empty user error
        expense.clearErrors()

        // Prevent future dates
        if (expense.date && expense.date > new Date()) {
            expense.errors.rejectValue('date', 'expense.date.future', 'Date cannot be in the future.')
            respond expense.errors, view:'create'
            return
        }

        try {
            expenseService.save(expense)
        } catch (ValidationException e) {
            respond expense.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'expense.label', default: 'Expense'), expense.id])
                redirect expense
            }
            '*' { respond expense, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond expenseService.get(id)
    }

    def update(Expense expense) {
        if (expense == null) {
            notFound()
            return
        }

        // Always set the user to the logged-in user
        def user = User.get(session.userId)
        expense.user = user

        // Prevent future dates
        if (expense.date && expense.date > new Date()) {
            expense.errors.rejectValue('date', 'expense.date.future', 'Date cannot be in the future.')
            respond expense.errors, view:'edit'
            return
        }

        try {
            expenseService.save(expense)
        } catch (ValidationException e) {
            respond expense.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'expense.label', default: 'Expense'), expense.id])
                redirect expense
            }
            '*'{ respond expense, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        expenseService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'expense.label', default: 'Expense'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'expense.label', default: 'Expense'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    /**
     * Convert the specified amount from ZAR to USD.
     *
     * @param zarAmount The amount in ZAR to convert
     * @return The equivalent amount in USD, or null if conversion fails
     */
    private BigDecimal convertZarToUsd(BigDecimal zarAmount) {
        return currencyConversionService.convertZarToUsd(zarAmount)
    }

    /**
     * Adds running balance and USD conversion to the expense list.
     *
     * @param expenses The list of expenses to process
     * @param user The user associated with the expenses
     */
    private void processExpenseList(List expenses, User user){
        // Calculate running balance
        BigDecimal currentBalance = user.startingBalance ?: 0.00G
        expenses.each { expense ->
            currentBalance -= expense.amount
            expense.runningBalance = currentBalance
        }

        // Add the USD conversion for each expense
        expenses.each { expense ->
            if (expense.amount) {
                expense.usdAmount = convertZarToUsd(expense.amount)
            } else {
                expense.usdAmount = null
            }
        }
    }

    /**
     * Export expenses to CSV format.
     */
     def exportCsv() {
        def user = User.get(session.userId)
        def expenses = Expense.findAllByUser(user, [sort: 'date', order: 'asc'])

        // Process expenses to add running balance and USD conversion
        if (expenses) {
            processExpenseList(expenses, user)
        }

        // Reverse for display (newest first)
        expenses = expenses.reverse()

        // Generate CSV from expenses
        String csv = exportTransactionsService.exportExpensesToCsv(expenses)
        response.contentType = 'text/csv'
        response.setHeader("Content-Disposition", "attachment; filename=\"expenses.csv\"")
        response.outputStream << csv
        response.outputStream.flush()
     }
}
