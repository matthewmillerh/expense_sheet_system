package expense_sheet_system

import grails.gorm.transactions.Transactional

/**
 * Controller for managing Expense entities in the expense sheet system.
 * Uses static scaffolding with user session validation and running balance calculation.
 */
class ExpenseController {
    
    /**
     * List expenses for the logged-in user with running balance calculation
     */
    def index() {
        def user = User.get(session.userId)
        def expenses = Expense.findAllByUser(user, [sort: 'date', order: 'asc'])
        
        // Calculate running balance
        BigDecimal currentBalance = user.startingBalance ?: 0.00G
        expenses.each { expense ->
            currentBalance -= expense.amount
            expense.runningBalance = currentBalance
        }
        
        // Reverse for display (newest first)
        expenses = expenses.reverse()
        
        respond expenses, model: [expenseList: expenses, expenseCount: expenses.size()]
    }
}