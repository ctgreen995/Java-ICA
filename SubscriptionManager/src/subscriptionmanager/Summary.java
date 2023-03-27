
package subscriptionmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Summary class provides a summary of all the subscriptions contained
 * within a user defined file, it is also the base class for the SummaryByMonth
 * and SearchSubscription classes providing the driver method generateSummary()
 * and file reading functionality provided by readFile().
 * 
 * @see MonthSummary
 */
public class Summary {
    
    protected SimpleDateFormat mft = new SimpleDateFormat("MMM");
    protected String[] monthOrder = new DateFormatSymbols().getShortMonths();

    protected String filename;
    
    protected final ArrayList<Subscription> subs = new ArrayList<>();
    private final HashMap<String, Integer> monthSubs = new HashMap<>();
    
    protected int totalSubs;
    private int aveSubs;
    protected double aveCost;
    protected double percentBronze;
    protected double percentSilver;
    protected double percentGold;
    
    /**
     * The default constructor displays Summary of Subcsriptions banner.
     */
    public Summary() {
        System.out.println("+\n\n=============== Summary of Subscriptions"
                + "===============+\n");
    }
    
    /**
     * Overloaded contstructor which takes a String from the sub class to output
     * the relevant String to the user for that sub-class.
     * 
     * @param output String message to display to user.
     */
    public Summary(String output) {
        System.out.println(output);
    }
    /**
     * Drives the Summary class by calling the methods required to generate the
     * summary, and selecting the file which is to be summarised,it is called
     * from the SubscriptionManager class.
     * This method uses the validateNumber() in the Validator class to get the
     * file from the user. It then uses the boolean returned by the readFile()
     * to check if the user specified file exists, this way if the files were to
     * be changed in the future an error message will be displayed and the
     * program will prompt to select another file, rather than resulting in a
     * program crash. If the file does exist then calculateSummary() is called
     * to calculate the metrics used in the summary toString() is then called
     * to display the summary to the user, if the file is not found the user is
     * prompted to confirm if the want to try another file via the
     * Validator.validateConfirmInput().
     * 
     * @see SubscriptionManager
     * @see #readFile() 
     * @see #calculateSummary() 
     * @see Validator#validateFilename() 
     * @see Validator#validateConfirmInput(java.lang.String) 
     */
    public void generateSummary() {
        
        boolean tryAgain = true;
        do {
        int userChoice = Validator.validateNumber("\nEnter 1 for current.txt or"
                + " 2 for sample.txt: ");
            switch (userChoice) {
                case 1:
                    filename = "current.txt";
                    break;
                case 2:
                    filename = "sample.txt";
                    break;
                default:
                    System.out.println("\nInvalid option, please enter 1"
                            + " or 2.");
                    continue;
            }
            if(readFile() == true) {
                calculateSummary();
                System.out.println(toString());
                break;
            } else {
                tryAgain = Validator.validateConfirmInput(
                        "\nTry another file? ");
            }
        } while(tryAgain == true);
    }
    
    /**
     * The readFile() reads the user specified file and creates an instance of
     * the Subscription class for each line in the file, the subscriptions are
     * then added to an ArrayList to allow for calculating the metrics used in
     * the summaries.
     * This file reader method is used in the sub-classes MonthSummary and 
     * SearchSubscription.
     * 
     * @return Returns a boolean, true, if the file exists so the summary
     * metrics can be calculated.
     * @see Subscription
     * @see MonthSummary
     * @see SearchSubscription
     */
    protected boolean readFile() {
        
        File subFile = new File(filename);
        
        try(BufferedReader reader = new BufferedReader(
                new FileReader(subFile))){
            String current;
            
            while((current = reader.readLine()) != null) {
                                
                String[] subData = current.split("\t");
                
                Subscription sub = new Subscription(subData[0], 
                        PackageType.valueOf(subData[1]),
                        Duration.getTerm(Integer.valueOf(subData[2])),
                        subData[3], PaymentType.valueOf(subData[4]),
                        Integer.valueOf(subData[5]), subData[6]);
                
                subs.add(sub);
            }
        } catch(FileNotFoundException e) {
            System.out.println("\nError, file not found!");
            return false;
        }
        catch(IOException e) {
            System.out.println("\nError reading from file!");
            return false;
        } 
        catch(ParseException e) {
            System.out.println("\nError parsing date!");
            return false;
        }
        return true;
    }
    
    /**
     * The calculateSummary method calculates the metrics used in the summary
     * of all subscriptions contained in the file provided by the user.
     * The method makes use of the ArrayList containing instances of
     * Subscriptions populated in the readFile() method, the relevant getters
     * are then used to get the required values from each Subscription. 
     * <p>
     * The monthSubs hashmap is populated with the months of the year as keys
     * and each value is incremented if a subscription was made in that month,
     * giving the total subscriptions for each month.
     */
    protected void calculateSummary() {
                
        totalSubs = subs.size();

        for(int i = 0; i < monthOrder.length - 1; i++) {
            monthSubs.put(monthOrder[i], 0);
        }

        aveSubs = totalSubs / monthSubs.size();

        double totalCost = 0;
        double bronze = 0;
        double silver = 0;
        double gold = 0;

        int incrementer;
        
        for(Subscription sub: subs) {
            if(monthSubs.containsKey(mft.format(sub.getSubDate()))) {
                incrementer = monthSubs.get(mft.format(sub.getSubDate()));
                incrementer++;
                monthSubs.put(mft.format(sub.getSubDate()), incrementer);
            }
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
        percentBronze = Math.round((bronze / totalSubs * 100) * 10) / 10.0;
        percentSilver = Math.round((silver / totalSubs * 100) * 10) / 10.0;
        percentGold = Math.round((gold / totalSubs * 100) * 10) / 10.0;
        totalCost = totalCost / totalSubs / 100;
        aveCost = Math.round((totalCost) * 100) / 100.0;
    }
    
    /**
     * Displays the Summary in the required format to be displayed to the user.
     * A String[] is populated with the Strings, which is then iterated over to
     * append to a StringBuilder object, which is then displayed to the user.
     * 
     * @return The method returns the StringBuilder object toString().
     */
    @Override
    public String toString() {
        
        String[] summary = new String[10];
        summary[0] = "\n+========== Summary  of subscriptions "
                + "==========+\n";
        summary[1] = "\nTotal subscriptions: " + totalSubs;
        summary[2] = "\nAverage monthly subscriptions: " + aveSubs;
        summary[3] = "\nAverage monthly subscription price: £" + aveCost;
        summary[4] = "\nPercentage of subscriptions: \n";
        summary[5] = "\nBronze: " + percentBronze;
        summary[6] = "\nSilver: " + percentSilver;
        summary[7] = "\nGold: " + percentGold + "\n\n";
        summary[8] = "";
        summary[9] = "";

        for(String month: monthOrder) {
            summary[8] = summary[8] + (String.format("%-5s", month));
        }
        for(String month: monthOrder) {
            if(monthSubs.containsKey(month)) {
                summary[9] = summary[9] + (String.format("%-5s", 
                        monthSubs.get(month)));
            }
        }
        StringBuilder str = new StringBuilder();
        
        for(String line:summary) {
            str.append(line);
            str.append("\n");
        }
        return str.toString();
    }
}
