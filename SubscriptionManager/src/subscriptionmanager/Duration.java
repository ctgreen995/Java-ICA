
package subscriptionmanager;

/**
 * Duration of a subscription, used in calculating base cost of a package and
 * displaying the duration in a subscription's individual summary.
 * The int values are used in the cost calculation in the BasePackage class and,
 * when reading from a file, the int in the file is passed as an argument to the
 * getTerm() which retrieves the corresponding constant when creating an
 * instance of the Subscription.
 * 
 * @see #getTerm(int) 
 */
public enum Duration {
    
    ONE(1), THREE(3), SIX(6), TWELVE(12);
    
    public final int term;
    
    /**
     * The class constructor is used in reading and writing subscriptions to
     * file and calculating their base costs.
     * @param term is the int written to or read from the text files.
     */
    private Duration(int term) {
        this.term = term;
    }
    
    /**
     * Gets the enum constant when reading a subscription from file.
     * Iterates over the values and if the value matches the int term argument
     * returns the constant associated with that value.
     * @param term int which is read from the file is compared to the values.
     * @return returns the constant associated with the matching value.
     */
    public static Duration getTerm(int term) {
        for(Duration enumTerm: values()) {
            if(enumTerm.term == term) {
                return enumTerm;
            }
        }
        return null;
    }
}
