package subscriptionmanager;

import java.util.Scanner;

/**
 * The SearchSubscription class extends the Summary class and allows the user to
 * search for all of the subscriptions for a given customer contained in a given
 * file, by entering a customers name, a summary of the subscription will then
 * be displayed. 
 * The file is read and an ArrayList of subscriptions is populated
 * in the Summary class, the ArrayList is then iterated over in the searchSubs()
 * method contained within this SearchSubscription class and the Summary of any
 * subscription matching the customers name is displayed via the matching
 * Subscription.toString().
 *
 * @see Summary
 * @see Subscription#toString()
 * @see #searchSubs()
 */
public class SearchSubscription extends Summary {
    
    private final Scanner scan = new Scanner(System.in);
    private String customer;

    /**
     * The default constructor uses the super constructor of the base class but
     * passes in the relevant output for the sub class.
     */
    public SearchSubscription() {
        super("\n\n+=============== Search for subscriptions "
                + "===============+\n");
    }

    /**
     * Overrides the calculateSummary() method of the base class and finds any
     * subscriptions matching the customers name and displays the
     * summary display box for that subscription to the user and also how many
     * matches were found, the user is then asked if they would like to search
     * again. 
     * The ArrayList which was populated in the Summary base class
     * readFile() is iterated over and both the customer name for each
     * subscription and the provided customer name are both modified to
     * lowercase. If there is any partial match of the customer name of a
     * subscription, the Subscription toString() is called. If the
     * Validator.validateConfirmInput() returns true the user will be asked to
     * provide another customer name.
     *
     * @see Summary
     * @see Summary#calculateSummary()
     * @see Subscription#toString()
     * @see Validator#validateConfirmInput(java.lang.String)
     */
    @Override
    protected void calculateSummary() {

        int matches = 0;
        boolean searchAgain;
        do {
            System.out.print("\nCustomer name to search for i.e J Smith"
                    + "\n\nPartial matches are accepted. i.e 'J', Sm' or 'ith'"
                    + "\n\nEnter name: ");
            customer = scan.nextLine();

            for (Subscription sub : subs) {
                if (sub.getCustomer().toLowerCase()
                        .contains(customer.toLowerCase())) {
                    System.out.println(sub.toString());
                    matches++;
                }
            }
            System.out.println("\nNumber of matches: " + matches);

            matches = 0;
            searchAgain = Validator.validateConfirmInput("\nSearch again? ");

        } while (searchAgain == true);
    }
}






































