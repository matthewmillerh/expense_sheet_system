package expense_sheet_system

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

/**
 * Unit tests for CurrencyConversionService.
 */
class CurrencyConversionServiceSpec extends Specification implements ServiceUnitTest<CurrencyConversionService> {

      // Test for converting ZAR to USD with a fixed exchange rate
      void "test currency conversion from ZAR to USD"() {
            given:
            // Mock the exchange rate method to return a fixed value
            service.metaClass.getZarToUsdRate = { -> 0.055G } // 1 ZAR = 0.055 USD

            when:
            def result = service.convertZarToUsd(100G)

            then:
            result.compareTo(5.50G) == 0 // Assuming the conversion rate is 0.055 USD for 1 ZAR
      }

      // Test for converting null ZAR amount
      void "test currency conversion with null input"() {
            given:
            // Mock the exchange rate method to return null
            service.metaClass.getZarToUsdRate = { -> null }

            when:
            def result = service.convertZarToUsd(null)

            then:
            result == null // Should return null for null input
      }

      // Test for converting zero ZAR amount
      void "test currency conversion with zero ZAR amount"() {
         given:
         service.metaClass.getZarToUsdRate = { -> 0.055G }

         when:
         def result = service.convertZarToUsd(0G)

         then:
         result == 0G
      }

      // Test for converting when exchange rate is null but ZAR amount is provided
      void "test currency conversion returns null if exchange rate is null"() {
         given:
         service.metaClass.getZarToUsdRate = { -> null }

         when:
         def result = service.convertZarToUsd(100G)

         then:
         result == null
      }

      // Test for converting negative ZAR amount
      void "test currency conversion with negative ZAR amount"() {
         given:
         service.metaClass.getZarToUsdRate = { -> 0.055G }

         when:
         def result = service.convertZarToUsd(-100G)

         then:
         result.compareTo(-5.50G) == 0
      }

      // Test for converting large ZAR amount
      void "test currency conversion with large ZAR amount"() {
         given:
         service.metaClass.getZarToUsdRate = { -> 0.055G }

         when:
         def result = service.convertZarToUsd(1000000G)

         then:
         result.compareTo(55000G) == 0
      }
   }
