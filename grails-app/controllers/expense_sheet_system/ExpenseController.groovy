package expense_sheet_system

/**
 * Controller for managing Expense entities in the expense sheet system.
 * Uses dynamic scaffolding generated from the domain objects with user session validation.
 */
class ExpenseController {
    static scaffold = Expense
    
    /**
     * Override index to filter expenses by logged-in user and redirect if not authenticated
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