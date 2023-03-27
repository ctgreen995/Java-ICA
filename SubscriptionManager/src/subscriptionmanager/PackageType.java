
package subscriptionmanager;
    
    /**
     * The PackageType enum is used for the package type the user has selected
     * for the subscription, both when creating a new subscription and
     * displaying its summary to the user and when reading and writing the
     * subscriptions to text file.
     */
    public enum PackageType {
    
    B("BRONZE"), S("SILVER"), G("GOLD");
    
    public final String type;
    
    /**
     * The constructor is used to assign a String value to the constants, for
     * displaying a String name of the package to the user in the indivdual
     * subscription summary output to the user.
     * @param type String which is displayed to the user with the name of the
     * package selected.
     */
    private PackageType(String type) {
        this.type = type;
    }
}
