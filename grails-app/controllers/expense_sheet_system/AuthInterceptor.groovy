package expense_sheet_system

/**
 * System-wide authentication interceptor.
 * Ensures users are logged in before accessing protected areas.
 */
class AuthInterceptor {

    AuthInterceptor() {
        matchAll()
          .excludes(controller: 'login')    // Allow access to login controller
          .excludes(controller: 'assets')   // Allow access to static assets
    }

    /**
     * Check authentication before each action.
     */
    boolean before() { 
        if (!session.userId) {
            redirect(controller: "login", action: "index")
            return false
        }
        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

}