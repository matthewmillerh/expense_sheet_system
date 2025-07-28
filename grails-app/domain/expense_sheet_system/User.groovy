package expense_sheet_system

/**
 * Domain class representing a user in the expense tracking system.
 * Each user has a unique name and starting balance, and can have multiple expenses.
 */
class User {
    String name
    BigDecimal startingBalance

    // One-to-many relationship - a user can have multiple expenses
    static hasMany = [expenses: Expense]

    // Custom database table mapping to avoid conflict with reserved 'user' keyword
    static mapping = {
        table 'app_user'
    }

    static constraints = {
        name blank: false, unique: true, maxSize: 255  // Unique identifier for each user
        startingBalance nullable: true, min: 0.01G    // Can be null initially, minimum 1 cent when set
    }
}