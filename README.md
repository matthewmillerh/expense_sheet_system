# Expense Sheet System

A Grails-based expense tracking application that allows users to manage their personal expenses with real-time currency conversion and CSV export functionality.

## Features

- **User Authentication**: Simple name-based login system with session management
- **Expense Management**: Create, view, edit, and delete personal expenses
- **Running Balance Calculation**: Track your balance over time with each transaction
- **Currency Conversion**: Real-time ZAR to USD conversion using Fixer.io API
- **CSV Export**: Export all expenses to CSV format with USD values
- **Responsive Design**: Bootstrap-styled interface that works on all devices

## Tech Stack

- **Framework**: Grails 6.2.4
- **Language**: Groovy/Java
- **Database**: H2 (embedded, file-based)
- **Frontend**: GSP (Grails Server Pages) with Bootstrap
- **Testing**: Spock Framework
- **Build Tool**: Gradle

## Prerequisites

## Prerequisites

- Java Development Kit 17 installed
- Git installed
- [Grails 6.2.4](https://grails.org/download.html) installed
- Set the `JAVA_HOME` environment variable to your JDK installation path
- Set the `GRAILS_HOME` environment variable to your Grails installation path
- Add the Grails `bin` directory to your system `PATH` so the `grails` command is available

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/matthewmillerh/expense_sheet_system.git
cd expense_sheet_system
```

### 2. Set Up Environment Variables

The application requires a Fixer.io API key for currency conversion:

**On Windows (PowerShell):**

```powershell
$env:FIXER_API_KEY="your_fixer_api_key_here"
```

**On Windows (Command Prompt):**

```cmd
set FIXER_API_KEY=your_fixer_api_key_here
```

**On Linux/macOS:**

```bash
export FIXER_API_KEY=your_fixer_api_key_here
```

### 3. Run the Application

```bash
# On Windows
.\gradlew.bat bootRun

# On Linux/macOS
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 4. First Time Setup

1. Navigate to `http://localhost:8080`
2. Enter your name to create an account
3. Click continue
4. Set your starting balance
5. Click Save and Continue
6. Start tracking your expenses

## Testing

### Run All Tests

```bash
# On Windows
.\gradlew.bat test

# On Linux/macOS
./gradlew test
```

### Run Specific Test Class

```bash
# On Windows
.\gradlew.bat test --tests expense_sheet_system.CurrencyConversionServiceSpec

# On Linux/macOS
./gradlew test --tests expense_sheet_system.CurrencyConversionServiceSpec
```

### View Test Reports

Test results are available at: `build/reports/tests/test/index.html`

## API Key Setup

This project uses the Fixer.io API for currency conversion. To get an API key:

1. Sign up at [fixer.io](https://fixer.io/) or contact me privately for my key
2. Get your free API key
3. Set it as an environment variable (see Getting Started section)

**Note**: The free plan has rate limits (100 requests/month). The application includes caching to minimize API calls.

## Project Structure

```
grails-app/
├── controllers/          # Request handling logic
├── domain/              # Data models (User, Expense)
├── services/            # Business logic (CurrencyConversion, Export)
├── views/               # GSP templates
└── conf/                # Configuration files
src/
├── test/groovy/         # Unit tests
└── integration-test/    # Integration tests
```

## Key Components

- **User Domain**: Manages user accounts and starting balances
- **Expense Domain**: Tracks individual expenses with running balance calculation
- **CurrencyConversionService**: Handles ZAR to USD conversion with caching
- **ExportTransactionsService**: Generates CSV exports
- **AuthInterceptor**: Ensures users are authenticated before accessing protected areas

## Database

The application uses H2 embedded database with the following schema:

- `app_user`: User accounts with starting balance
- `expense`: Individual expense records linked to users

By default, the development database is in-memory and will be cleared on each restart.
To persist data between restarts, update your application.yml to use a file-based H2 database.

## Performance and Scalability Considerations

Optimisation considerations at 10k concurrent users:

- Using a more robust database like PostgreSQL or MySQL instead of H2 for production.
- Making sure the most common database queries are efficient (for example, by adding indexes where needed).
- Running more than one instance of the app behind a load balancer if needed.
- Caching exchange rates to reduce the number of external API calls (already implemented).
- The CurrencyConversionService could be modified to use @CompileStatic for improved performance and type safety.

## License

This project is for demonstration purposes as part of a job application assignment.

---

## Grails Documentation References

- [User Guide](https://docs.grails.org/6.2.3/guide/index.html)
- [API Reference](https://docs.grails.org/6.2.3/api/index.html)
- [Grails Guides](https://guides.grails.org/index.html)
