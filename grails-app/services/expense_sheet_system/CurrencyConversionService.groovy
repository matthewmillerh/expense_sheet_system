package expense_sheet_system

import grails.converters.JSON
import groovy.json.JsonSlurper
import grails.core.GrailsApplication

/**
 * Service for currency conversion operations.
 * Supports ZAR to USD conversion using Fixer.io API.
 */
class CurrencyConversionService {

    GrailsApplication grailsApplication

    private static final String FIXER_BASE_URL = "http://data.fixer.io/api"

    // --- Caching fields ---
    private static BigDecimal cachedRate = null
    private static Date lastFetch = null
    private static final long CACHE_DURATION_MS = 4 * 60 * 60 * 1000 // 4 hours
    
    /**
     * Convert ZAR amount to USD using current exchange rates from Fixer.io
     * 
     * @param zarAmount The amount in ZAR to convert
     * @return The equivalent amount in USD, or null if conversion fails
     */
    BigDecimal convertZarToUsd(BigDecimal zarAmount) {
        try {
            // Get current exchange rate
            BigDecimal exchangeRate = getZarToUsdRate()
            
            if (exchangeRate) {
                return (zarAmount * exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP)
            }
            
            return null
        } catch (Exception e) {
            log.error("Error converting ZAR to USD: ${e.message}", e)
            return null
        }
    }
    
    /**
     * Get the current ZAR to USD exchange rate from Fixer.io
     * 
     * @return The exchange rate (ZAR per 1 USD), or null if request fails
     */
    private BigDecimal getZarToUsdRate() {
        // Use cached rate if it's valid
        if (cachedRate && lastFetch && (new Date().time - lastFetch.time) < CACHE_DURATION_MS) {
            return cachedRate
        }

        // Fetch new rate from Fixer.io
        try {
            String url = "${FIXER_BASE_URL}/latest?access_key=${getFixerApiKey()}&symbols=ZAR,USD"
            String response = new URL(url).text

            def jsonResponse = new JsonSlurper().parseText(response)

            if (jsonResponse.success) {
                def eurToZar = jsonResponse.rates.ZAR
                def eurToUsd = jsonResponse.rates.USD
                if (eurToZar && eurToUsd) {
                    // ZAR to USD = (EUR to USD) / (EUR to ZAR)
                    // Calculate and cache the rate to avoid frequent API calls
                    cachedRate = (eurToUsd / eurToZar).setScale(4, BigDecimal.ROUND_HALF_UP)
                    lastFetch = new Date()
                    return cachedRate
                }
            } else {
                log.error("Fixer.io API error: ${jsonResponse.error?.toString() ?: "Unknown error"}")
                return null
            }
        } catch (Exception e) {
            log.error("Error fetching exchange rate: ${e.message}", e)
            return null
        }
    }

    /**
     * Get the Fixer API key from configuration or environment variable
     * 
     * @return The Fixer API key
     */
    private String getFixerApiKey() {
        return grailsApplication.config.getProperty('fixer.apiKey', String) ?: System.getenv('FIXER_API_KEY')
    }
}