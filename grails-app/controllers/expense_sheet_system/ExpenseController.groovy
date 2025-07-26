package expense_sheet_system

/**
 * Controller for managing Expense entities in the expense sheet system.
 * Provides CRUD operations for expenses with user session validation.
 */
class ExpenseController {
    static scaffold = Expense
    
    /**
     * List expenses for the current logged-in user, redirecting to login if not authenticated.
     */
    def index() {
        if (!session.userId) {
            redirect(controller: "login", action: "index")
            return
        }
        
        def user = User.get(session.userId)
        def expenses = Expense.findAllByUser(user)
        respond expenses
    }
}