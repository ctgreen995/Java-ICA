
package subscriptionmanager;

/**
 * The MonthSummary class extends the functionality of the Summary class by
 * allowing a month to be provided by the user then providing the summary of
 * subscriptions for that month. 
 * This method uses the driver method generateSummary() and the file reading
 * method readFile() contained in the Summary class.
 * 
 * @see Summary
 * @see Summary#generateSummary() 
 * @see Summary#readFile() 
 */
public class MonthSummary extends Summary {
    
    private String month;
    
    /**
     * Default constructor uses the super constructor of the base class, passes
     * the relevant String to output to the user.
     */
    public MonthSummary() {
        super("\n\n+=============== Summary of Subscriptions by Month "
                + "===============+\n");
    }

    /**
     * The calculateSummary() method overrides the calculateSummary() method in
     * the Summary class and calculates the metrics needed to produce the
     * summary for the month provided by the user.
     * The requestMonth method is called to get the month to be summarised from
     * the user. The ArrayList of subscriptions is then iterated over and any
     * subscriptions from the month provided by the user are tallied, the
     * package type is tallied and converted to a percentage, and the totalCost
     * of the subscription summed.
     */
    @Override
    protected void calculateSummary() {
        
        month = Validator.validateMonth(monthOrder);
        
        double totalCost = 0.0;
        double bronze = 0.0;
        double silver = 0.0;
        double gold = 0.0;  
        System.out.println(subs.size());
        for(Subscription sub: subs) {             
            if(mft.format(sub.getSubDate()).equals(month)) {
                totalSubs++;
                totalCost = totalCost + sub.getTotalCost();
                switch(sub.getPackageType()) {
                    case B:
                        bronze++;
                        break;
                    case S:
                        silver++;
                        break;
                    case G:
                        gold++;
                        break;
                }
            }
        }
        totalCost = totalCost / totalSubs / 100;
        aveCost = Math.round((totalCost) * 100) / 100.0;

        percentBronze = Math.round((bronze / totalSubs * 100) * 10) / 10.0;
        percentSilver = Math.round((silver / totalSubs * 100) * 10) / 10.0;
        percentGold = Math.round((gold / totalSubs * 100) * 10) / 10.0;
    }
    
    /**
     * Displays the Summary in the required format to be displayed to the user.
     * A String[] is populated with the Strings, which is then iterated over to
     * append to a StringBuilder object, which is then returned to be displayed
     * to the user.
     * 
     * @return The method returns the StringBuilder object toString().
     */
    @Override
    public String toString() {
        
        String[] summary = new String[7];
        summary[0] = "\n+========== Summary of subscriptions for " + month
                + " ==========+\n";
        summary[1] = "\nTotal subscriptions: " + totalSubs;
        summary[2] = String.format("\nAverage monthly subscription price: £"
                + "%.2f", aveCost);
        summary[3] = "\nPercentage of subscriptions: \n";
        summary[4] = "\nBronze: " + percentBronze;
        summary[5] = "\nSilver: " + percentSilver;
        summary[6] = "\nGold: " + percentGold + "\n\n";
                
        StringBuilder str = new StringBuilder();
        
        for(String line:summary) {
            str.append(line);
            str.append("\n");
        }
        return str.toString();
    }    
}
