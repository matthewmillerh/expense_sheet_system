package expense_sheet_system

/**
 * Domain class representing an individual expense transaction.
 * Each expense belongs to a specific user and tracks spending against their balance.
 */
class Expense {
    String description
    BigDecimal amount
    Date date
    User user

    // Transient properties for running balance calculation and USD conversion
    BigDecimal runningBalance
    BigDecimal usdAmount
    static transients = ['runningBalance', 'usdAmount']

    // Many-to-one relationship with User - each expense belongs to one user
    static belongsTo = [user: User]

    static constraints = {
        description blank: false, maxSize: 255
        amount nullable: false, min: 0.01G  // Minimum expense of 1 cent
        date nullable: false
        user nullable: false  // Every expense must be associated with a user
    }
}