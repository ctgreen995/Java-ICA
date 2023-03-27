
package subscriptionmanager;

import java.util.HashMap;

/**
 * The BasePackage class is used to calculate the base cost for the package type
 * of the subscription.
 * When the method for the package type of the subscription is called the
 * HashMap is populated with the keys and values for that package type, the keys
 * are Duration enum constants and the values are the int values of those enum
 * constants. The Duration of the subscription passed to the BasePackage class
 * when constructed is the key used in the getter called when calculating the
 * base cost of the package.
 * 
 * @see Duration
 */
public class BasePackage {
    
    protected final HashMap<Duration, Integer> packagePricing = new HashMap<>();
    private final Duration duration;
    
    
    /**
     * The default constructor takes the duration of the subscription to use as
     * the key in the getBaseCost().
     * 
     * @param term Is the length of the subscription the customer has chosen.
     * @see #getBaseCost()
     */
    public BasePackage(Duration term) {
        duration = term;
    }    
    
    /**
     * Getter method gets the base cost of the subscription by getting the
     * HashMap value of the Duration.
     * The HashMap value returned  is the base cost of the package for that
     * duration.
     * 
     * @return Returns the int base cost of the subscription.
     * @see Duration
     */
    public int getBaseCost() {
        return packagePricing.get(duration);
    }
    
    /**
     * Populates the HashMap with the bronze package costs.
     */
    public void setBronzePackage() {
        packagePricing.put(Duration.ONE, 600);
        packagePricing.put(Duration.THREE, 500);
        packagePricing.put(Duration.SIX, 400);
        packagePricing.put(Duration.TWELVE, 300);
    }
    
    /**
     * Populates the HashMap with the costs for the silver package.
     */
    public void setSilverPackage() {
    
        packagePricing.put(Duration.ONE, 800);
        packagePricing.put(Duration.THREE, 700);
        packagePricing.put(Duration.SIX, 600);
        packagePricing.put(Duration.TWELVE, 500);
    }    
    
    /**
     * Populates the HashMap with the Gold Package costs.
     */
    public void setGoldPackage() {
        
        packagePricing.put(Duration.ONE, 999);
        packagePricing.put(Duration.THREE, 899);
        packagePricing.put(Duration.SIX, 799);
        packagePricing.put(Duration.TWELVE, 699);
    }
}
