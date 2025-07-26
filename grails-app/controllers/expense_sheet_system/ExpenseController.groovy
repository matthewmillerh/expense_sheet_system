package expense_sheet_system

/**
 * Controller for managing Expense entities in the expense sheet system.
 * Provides CRUD operations for expenses with user session validation.
 */
class ExpenseController {
    static scaffold = Expense
    
    /**
     * Ensure user is logged in before accessing any expense-related actions
     */
    def beforeInterceptor = {
        if (!session.userId) {
            redirect(controller: "login", action: "index")
            return false
        }
    }
}