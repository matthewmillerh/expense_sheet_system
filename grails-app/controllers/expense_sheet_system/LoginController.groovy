package expense_sheet_system

import grails.gorm.transactions.Transactional

/**
 * Handles user authentication and initial setup for the expense tracking system.
 * Manages user login/creation and starting balance setup workflow.
 */
class LoginController {
    
    /**
     * Display the login form
     */
    def index() {}

    /**
     * Process user login/registration
     * Creates new user if doesn't exist, then redirects to balance setup or expense page
     */
    @Transactional
    def login() {
        def user = User.findByName(params.name)
        
        // Create new user if they don't exist
        if (!user) {
            user = new User(name: params.name)
            user.save(flush: true)
        }
        
        // Store user in session for subsequent requests
        session.userId = user.id
        
        // Redirect to balance setup if not configured, otherwise to home page
        if (!user.startingBalance) {
            redirect(action: "setupBalance")
        } else {
            redirect(uri: "/")
        }
    }

    /**
     * Display the starting balance setup form
     */
    def setupBalance() {
        // Ensure the user is logged in
        if (!session.userId) {
            redirect(controller: 'login', action: 'index')
            return
        }

        // Retrieve the current user for balance setup
        def user = User.get(session.userId)

        // Add cache-control headers to prevent browser caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
        response.setHeader("Pragma", "no-cache")

        // Redirect to home page if user already has a starting balance
        if (user.startingBalance) {
            redirect(uri: "/")
            return
        }
        [user: user]
    }

    /**
     * Save the user's starting balance and proceed to expense tracking
     */
    @Transactional
    def saveBalance() {
        def user = User.get(session.userId)
        // Prevent setting balance again if already set
        if (user.startingBalance) {
            redirect(uri: "/")
            return
        }

        user.startingBalance = params.startingBalance as BigDecimal
        
        if (user.save(flush: true)) {
            redirect(uri: "/")
        } else {
            // Display validation error if starting balance is invalid (e.g., <= 0)
            flash.message = "Starting balance must be greater than 0"
            render(view: "setupBalance", model: [user: user])
        }
    }

    /**
     * Clear user session and return to login page
     */
    def logout() {
        session.userId = null
        redirect(action: "index")
    }
}