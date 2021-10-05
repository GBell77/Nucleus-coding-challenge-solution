package bitcoin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;

public class PortfolioPerformance {
	private static final List<Price> PRICES = List.of(
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 5, 0, 0), new BigDecimal("35464.53")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 2, 5, 0, 0), new BigDecimal("35658.76")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 5, 0, 0), new BigDecimal("36080.06")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 13, 0, 0), new BigDecimal("37111.11")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 6, 5, 0, 0), new BigDecimal("38041.47")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 5, 0, 0), new BigDecimal("34029.61")));

    private static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 9, 0, 0), new BigDecimal("0.012")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 15, 0, 0), new BigDecimal("-0.007")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 4, 9, 0, 0), new BigDecimal("0.017")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 5, 9, 0, 0), new BigDecimal("-0.01")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 9, 0, 0), new BigDecimal("0.1")));

    // Complete this method to return a list of daily portfolio values with one record for each day from the 01-09-2021-07-09-2021 in ascending date order
    public static List<DailyPortfolioValue> getDailyPortfolioValues() {
    	
    	List<DailyPortfolioValue> dailyPortfolioValueList = new ArrayList<DailyPortfolioValue>();
    	
    	LocalDate currentDate = LocalDate.of(2021, Month.SEPTEMBER, 1); // beginning of reporting period
    	LocalDate endDate = LocalDate.of(2021, Month.SEPTEMBER, 7); // end of reporting period
    	
    	BigDecimal currentBitcoin = new BigDecimal("0");
    	BigDecimal currentPrice = new BigDecimal("0");
    	int transactionIndex = 0;
    	int priceIndex = 0;
    	
    	// for each day in the reporting period
    	while(currentDate.isBefore(endDate) || currentDate.equals(endDate))
    	{
    		// for each transaction
    		while(transactionIndex < TRANSACTIONS.size())
    		{
    			// check if transaction is on the current day 
    			LocalDate transactionDate = TRANSACTIONS.get(transactionIndex).effectiveDate().toLocalDate();
    			if(transactionDate.equals(currentDate))
    			{
    				// add the transaction amount to the current bitcoin count
    				currentBitcoin = currentBitcoin.add(TRANSACTIONS.get(transactionIndex).numberOfBitcoins());
    				transactionIndex++;
    			}
    			else    			
    				break;    			
    		}
    		
    		// for each price update
    		while(priceIndex < PRICES.size())
    		{
    			// check if price update is on current day
    			LocalDate priceDate = PRICES.get(priceIndex).effectiveDate().toLocalDate();
    			if(priceDate.equals(currentDate))
    			{
    				// update current price
    				currentPrice = PRICES.get(priceIndex).price();
    				priceIndex++;
    			}
    			else    			
    				break;
    		}
    		
    		// create new record and add to list
    		dailyPortfolioValueList.add(new DailyPortfolioValue(currentDate, currentBitcoin.multiply(currentPrice)));
    		
    		currentDate = currentDate.plusDays(1);
    	}
    	
        return dailyPortfolioValueList;
    }

}
