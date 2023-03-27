
package subscriptionmanager;

/**
 * This enum is used for the payment terms which the user selects for a
 * subscription, it is used when making a new subscription and writing to file
 * or when reading from files to summarise the subscriptions.
 * The constants are written to or read from the text files, the values are
 * displayed to the user in the summary outputs of individual subscriptions.
 * 
 */
public enum PaymentType {
    O("ONE-OFF"), M("MONTHLY");
    
    public final String payment;
    
    /**
     * The constructor is used to assign values to the constants, it is required
     * to display the payment term to the user in the individual subscription
     * summary output.
     * @param payment  String which is displayed in the individual subscription
     * summary output to the user.
     */
    private PaymentType(String payment) {
        this.payment = payment;
    }   
}
