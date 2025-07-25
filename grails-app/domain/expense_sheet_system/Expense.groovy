package expense_sheet_system

// Represents an expense in the expense sheet system
class Expense {
    String description
    BigDecimal amount
    Date date
    User user

    static constraints = {
        description blank: false, maxSize: 255
        amount nullable: false, min: 0.01G
        date nullable: false
        user nullable: false
    }
}