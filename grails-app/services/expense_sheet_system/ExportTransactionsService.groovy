package expense_sheet_system

import groovy.transform.CompileStatic
import java.text.SimpleDateFormat

/**
 * Service for exporting expense data to CSV format.
 */
@CompileStatic
class ExportTransactionsService {

    /**
     * Export a list of expenses to CSV format.
     * 
     * @param expenses List of expenses to export
     * @return CSV formatted string
     */
    String exportExpensesToCsv(List<Expense> expenses) {
        // Validate input
        if (!expenses) {
            return "Description,Amount (ZAR),Amount (USD),Date,Running Balance (ZAR)\n"
        }

        def writer = new StringWriter()
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        
        // Write CSV header
        writer << "Description,Amount (ZAR),Amount (USD),Date,Running Balance (ZAR)\n"
        
        // Write expense data
        expenses.each { expense ->
            writer << "\"${expense.description}\",${expense.amount},${expense.usdAmount},${dateFormat.format(expense.date)},${expense.runningBalance}\n"
        }
        return writer.toString()
    }
}