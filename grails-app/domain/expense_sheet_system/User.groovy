package expense_sheet_system

// Represents a user in the expense sheet system
class User {
    String name
    BigDecimal startingBalance
    Boolean isFirstTime

    static constraints = {
        name blank: false, unique: true, maxSize: 255
        startingBalance nullable: false, min: 0.00G
        isFirstTime nullable: false
    }
}