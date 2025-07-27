package expense_sheet_system

/**
 * HomeController for the Expense Sheet System.
 */
class HomeController {
    def index() {
        def user = User.get(session.userId)
        def startingBalance = user?.startingBalance ?: 0
        // Calculate total expenses for this user
        def totalExpenses = Expense.createCriteria().get {
            eq('user', user)
            projections {
                sum('amount')
            }
        } ?: 0

        // Calculate current balance
        def currentBalance = startingBalance - totalExpenses
        [startingBalance: startingBalance, currentBalance: currentBalance]
    }
}