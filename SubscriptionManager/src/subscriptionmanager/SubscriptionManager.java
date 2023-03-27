
/**
 * This program has been written using JDK 11 due to the deprecation of JDK 8.
 *  @author Christian Green - K0170764
 */
package subscriptionmanager;

/**
* Subscription manager is the main class for subscription handling and
* allows a user to create, search for and summarise subscriptions.
* Subscriptions are created and written to text files, the text files are then
* read from and the metrics are calculated to generate summaries of the
* subscriptions, or subscriptions are searched for by inputting a
* customers name.
*/
public class SubscriptionManager {
    
    /**
     * The main() method acts as the main menu of the program and is displayed
     * to the user each time the program is used and the user is returned here
     * following each use case.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int userChoice;
        do {
            System.out.println("\nMENU:\n\n1. Create New Subscription\n\n"
                + "2. Display Summary Of Subscriptions\n\n"
                + "3. Display Summary Of Subscriptions By Month\n\n"
                + "4. Search For Subscription To Display\n\n"
                + "0. Exit\n\n");
            
            userChoice = Validator.validateNumber(
                    "\nEnter choice 1-4, or 0 to exit: ");            
            
            switch(userChoice) {
                case 1:
                    Subscription newSub = new Subscription();
                    newSub.fillSubscription();
                    break;
                case 2:
                    Summary sumSubs = new Summary();
                    sumSubs.generateSummary();
                    break;
                case 3:
                    MonthSummary monthSum = new MonthSummary();
                    monthSum.generateSummary();
                    break;
                case 4:
                    SearchSubscription searchSub = new SearchSubscription();
                    searchSub.generateSummary();
                    break;
                case 0:
                    System.out.println("\nGoodbye.");
                    System.exit(0);
                default:
                        System.out.println("\nInvalid choice, please enter a "
                                + "number from the menu options");
            }              
        } while(true);   
    }
}