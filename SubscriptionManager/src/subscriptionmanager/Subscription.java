
package subscriptionmanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Fills the new subscription from a customer, writes it to file then displays
 * a summary.
 */
public class Subscription {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    
    private final Date subDate;  
    private String customer;
    private PackageType packageType;
    private Duration subLength;
    private String discountCode;
    private int discount;
    private PaymentType paymentType;
    private int totalCost;
    
    /**
     * Default constructor used when filling new subscriptions, gets the current
     * date for the subscription when instantiating the class.
     */
    public Subscription() {
        System.out.print("\n+=============== Create a new subscription "
        + "===============+\n");
        subDate = new Date();
    }

    /**
     *Overloaded constructor used for creating instances of a subscription when
     * reading from files.
     * The date is a String in the file, the constructor parses this to a date
     * object.
     * 
     * @param subDate String date of subscription in the file.
     * @param userPackage PackageEnum for the package type.
     * @param subLength Duration for the duration of the subscription.
     * @param discountCode String for the discount code.
     * @param paymentType PaymentType for the payment terms.
     * @param totalCost int total cost for the subscription after any discounts.
     * @param customer String customer name, i.e. J Smith.
     * @throws ParseException if date in incorrect format in file.
     */
    public Subscription(String subDate, PackageType userPackage, 
            Duration subLength, String discountCode, PaymentType paymentType, 
            int totalCost, String customer) throws ParseException {
        
        this.subDate = sdf.parse(subDate);
        this.customer = customer;
        this.packageType = userPackage;
        this.subLength = subLength;
        this.discountCode = discountCode;
        this.paymentType = paymentType;
        this.totalCost = totalCost;
    }
    
    /**
     * Driver method to fill the Subscription by calling the required methods
     * within this class.
     */
    public void fillSubscription() {

        setCustomer();
        setPackage();
        setSubLength();
        setDiscount();
        setPayment(); 
        setTotalCost();
        writeSubscription();
        System.out.println(toString());
    }
    
    /**
     * The formatSubDate() method converts date object to String format in order
     * to be written to file.
     * 
     * @return String formatted date.
     */
    private String formatSubDate() {
        
        Calendar cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(cal.getTime());
    }

    /**
     * Method to get the date of the subscription, used in generating the 
     * summaries for all subscriptions and subscriptions by month.
     * 
     * @return returns the date of the subscription.
     */
    public Date getSubDate() {
        return subDate;
    }

    /**
     * Method to get the customer name from the user, uses the validateName() in 
     * the Validator class where the name is validated and modified into the
     * correct format to be written to file.
     * setCustomer() also checks that the formatted name is max 25 characters to
     * ensure the format is correct to be written to file.
     * 
     * @see Validator#validateName()
     */
    private void setCustomer() {

        boolean save = false;  
        do {
            System.out.println("\nName of the customer i.e J Smith");
            customer = Validator.validateName();

            if(customer.length() > 25){
                System.out.println("\nName invalid, too many characters.");
            } else {
                System.out.println("\nName accepted.");
                save = true;
            }
        } while(save == false);
    }

    public String getCustomer() {
        return customer;
    }

    /**
     * Requests the user to select which package type for the subscription then
     * displays confirmation message of that particular package type.
     * Stores the package type as a PackageType enum constant which is the
     * correct format to be written to file.
     * 
     * @see PackageType
     */
    private void setPackage() {
       
        int userPackage;

        do {
            userPackage = Validator.validateNumber("\nSelect package type, "
                    + "1. Bronze, 2. Silver or 3. Gold\n\nEnter (1, 2 or 3): ");
            
            switch (userPackage) {
                case 1:
                    System.out.println("\nBronze package selected.");
                    packageType = PackageType.B;
                    break;
                case 2:
                    System.out.println("\nSilver package selected.");
                    packageType = PackageType.S;
                    break;
                case 3:
                    System.out.println("\nGold package selected.");
                    packageType = PackageType.G;
                    break;
                default:
                    System.out.println("\nInvalid choice, please choose from "
                        + "the options provided");
                    userPackage = 0;
            }
        } while(userPackage == 0);   
    }

    /**
     * This method gets the type of package for the subscription, it is used in
     * the percentages of package types in the metrics displayed to the user
     * when generating a summary of subscriptions. 
     * 
     * @return the type of package for the subscription.
     */
    public PackageType getPackageType() {
        return packageType;
    }

    /**
     * Requests the subscription duration from the user, then displays a
     * confirmation message confirming the duration to the user.
     * Sets subLength as a Duration enum constant which is the correct format
     * when displayed in the subscription toString() to the user.
     * 
     * @see Duration
     */
    private void setSubLength() {
        
        int userLength;
        do {
            userLength = Validator.validateNumber("\nEnter subscription length,"
                    + " 1, 3, 6 or 12 months: ");

            switch (userLength) {
                case 1:
                    System.out.println("\n1 month term selected.");
                    subLength = Duration.ONE;
                    break;
                case 3:
                    System.out.println("\n3 month term selected.");
                    subLength = Duration.THREE;
                    break;
                case 6:
                    System.out.println("\n6 month term selected.");
                    subLength = Duration.SIX;
                    break;
                case 12:
                    System.out.println("\n12 month term selected.");
                    subLength = Duration.TWELVE;
                    break;
                default:
                    System.out.println("\nInvalid subscription length, please "
                        + "choose from available options.");
                    userLength = 0;
            }
        } while (userLength == 0);
    }
    
    /**
     * Requests a discount code string from the user and validates the code, the
     * code is then modified to the correct format to be written to file.
     * This method uses validateCode() in the Validator class to validate the 
     * code if the user has supplied one. If a valid code is supplied it is
     * modified to all uppercase characters. If the code is invalid, the method
     * uses the validateInput() in the Validator class to check if the user
     * wants to proceed without a discount code, if no valid code is supplied a
     * '-' is stored in place of a code.
     * 
     * @see Validator#validateCode(java.lang.String) 
     * @see Validator#validateConfirmInput(java.lang.String) 
     */
    private void setDiscount() {
        
        boolean proceed = false;
        boolean enterCode;
        do {
            enterCode = Validator.validateConfirmInput("\nDo you have a"
                    + " discount code to enter (Y/N)? ");           
            if(enterCode == false) {
                System.out.println("\nConfirmed, no discount code applied.");
                discountCode = "-";
                discount = 0;
                break;               
            }

            discountCode = Validator.validateCode();

            if(!discountCode.equals("-")) {
                discount = Character.getNumericValue(discountCode.charAt(5));
                break;
            }
        } while (proceed == false);
    }

    /**
     * Requests the type of payment the customer wishes to use, one-off or
     * monthly, then displays a confirmation message to the user stating the
     * payment type chosen.
     * The payment type is stored as a PaymentType enum constant, which is the
     * correct format to be written to file.
     * 
     * @see PaymentType
     */
    private void setPayment() {
        
        int userPayment;
        do {
            userPayment = Validator.validateNumber("\nPay in full today to "
                    + "receive a 5% discount!\n\nTo pay in full enter (1), "
                    + "to pay monthly enter (2): ");
                    
                switch (userPayment) {
                    case 1:
                        paymentType = PaymentType.O;
                        System.out.println("\nPayment to be made in full.\n");
                        break;
                    case 2:
                        paymentType = PaymentType.M;
                        System.out.println("\nPayment to be made monthly.\n");
                        break;
                    default:
                        System.out.println("\nInvalid choice please choose from"
                                + " options available.");
                        userPayment = 0;
                }
        } while (userPayment == 0);
    }

    /**
     * Method to get the total cost of the subscription, this is used when
     * reading from the file for generating the metrics used in the summaries.
     * 
     * @return returns an int total cost of the subscription
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Calculates the total cost of the subscription, by getting the
     * base cost from the BasePackage class then subtracting any discounts.
     * This method creates an instance of the BasePackage class, which contains
     * the methods to calculate the base costs of each package type. The base
     * cost then has the percentages of the dicounts subtracted from it and if
     * the customer is paying a one off fee the base cost is multiplied by the
     * length of subscription.
     * 
     * @see BasePackage
     */
    private void setTotalCost() {
       
        int baseCost = 0;
        BasePackage base = new BasePackage(subLength);

        
        switch(packageType) {
            case B:
                base.setBronzePackage();
                baseCost = base.getBaseCost();
                break;
            case S:
                base.setSilverPackage();
                baseCost = base.getBaseCost();
                break;
            case G:
                base.setGoldPackage();
                baseCost = base.getBaseCost();
                break;
        }
        double afterDiscount;
        if(discount != 0) {
            afterDiscount = baseCost - ((((double)discount) / 100) * baseCost);
        } else {
            afterDiscount = baseCost;
        }
        if(paymentType == PaymentType.O) {
            afterDiscount = afterDiscount - ((((double)5) / 100) 
                    * afterDiscount);
        afterDiscount = afterDiscount * subLength.term;
        }
        totalCost = (int)Math.round(afterDiscount);
    }

    /**
     * Writes the subscriptions to the subscriptions.txt file, in the
     * tabbed format.
     */
    private void writeSubscription() {
        
        try {
            File subFile = new File("subscriptions.txt");
       
            if(!subFile.exists()) {
                System.out.println(subFile.createNewFile());
            }
            try(BufferedWriter writeSub = new BufferedWriter(
                    new FileWriter(subFile, true))) {
                writeSub.append(formatSubDate() + "\t" 
                        + packageType + "\t" 
                        + subLength.term + "\t" + discountCode + "\t" 
                        + paymentType + "\t" + totalCost + "\t" + customer);
                writeSub.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error writing new subscription.");
        }
    }

    /**
     * The toString() displays the summary of the subscription to the user in 
     * a display box, this is also the method used to display each matching
     * subscription when the user searches for subscriptions using the
     * SearchSubscription class.
     * This method uses the centreAlign() and dualAlign() methods to align the
     * Strings in the subscription box display.
     * 
     * @return returns the subscription summary String in the correct format
     * @see SearchSubscription
     * @see #centreAlign(java.lang.String, int) centreAlign()
     * @see #dualAlign(java.lang.String, java.lang.String, int) 
     */
    @Override
    public String toString() {
        
        int lineLength = 50;
        
        String[] summary = new String[11];
        summary[0] = "+" + "-".repeat(lineLength - 2) + "+";
        
        String line1 = "Customer: " + customer;
        summary[1] = centreAlign(line1, lineLength);
        
        summary[2] = String.format("|%s|", " ".repeat(lineLength - 2));
        String line3left = "Date: " + sdf.format(subDate);
        String line3right = "Discount Code: " + discountCode;
        summary[3] = dualAlign(line3left, line3right, lineLength);
        
        summary[4] = summary[2];
        
        String line5left = "Package: " + packageType.type;
        
        String line5right = "Duration: " 
                + subLength;
        summary[5] = dualAlign(line5left, line5right, lineLength);
        
        summary[6] = summary[2];
        
        String line7 = "Payment terms: " + paymentType.payment;
        summary[7] = centreAlign(line7, lineLength);
        
        summary[8] = summary[2];
        
        String line9 = String.format("Subscription price: £%.2f", 
                    ((double) totalCost / 100));
        summary[9] = centreAlign(line9, lineLength);
        
        summary[10] = summary[0];
        
        StringBuilder str = new StringBuilder();
        for(String line:summary) {
            str.append(line);
            str.append("\n");
        }
        
        return str.toString();
    }
    
    /**
     * Centre aligns a single String in a given line in the toString() display
     * box.
     * The method subtracts string length from the total line length, divide the
     * remainder by 2 and adds a pad of the correct length either side.
     * 
     * @param lineData String which is to be centre aligned
     * @param lineLength int length of the line in the summary display
     * @return complete formatted string with pads and edge characters added
     */
    private String centreAlign(String lineData, int lineLength) {
        
        int padAmount = (lineLength - lineData.length()) / 2;

        String pad = (" ").repeat(padAmount);
        
        String builtString = String.format("|%s%s%s|", pad, lineData, pad);
        
        int padExtraAmount;
        if((builtString.length() - lineLength) < 0) {
            padExtraAmount = padAmount + (builtString.length() - lineLength);
        }
        if((builtString.length() -lineLength ) > 0) {
            padExtraAmount = padAmount - (builtString.length() - lineLength);
        } else {
            padExtraAmount = padAmount;
        }
        String padExtra = (" ").repeat(padExtraAmount);
        
        builtString = String.format("|%s%s%s|", pad, lineData, padExtra);
        
        return builtString;
    }
    
    /**
     * Aligns two strings evenly on the same line in the subscription toString()
     * display box.
     * The length of the two Strings are added together then subtracted from the
     * line length, the remainder is divided by 3 and the correct pad length 
     * added to the left, centre and right of the two strings.
     * 
     * @param leftString String to be aligned to left of the summary display
     * @param rightString String to be aligned to right of the summary display
     * @param lineLength int length of the line in the summary display
     * @return complete formatted String with pads and edge characters added
     */
    private String dualAlign(String leftString, String rightString, 
            int lineLength) {
        
        int padAmount = (lineLength - (leftString.length() + 
                rightString.length())) / 3;

        String pad = (" ").repeat(padAmount);
        
        String builtString = String.format("|%s%s%s%s%s|", pad, leftString, pad,
                rightString, pad);
        
        int padExtraAmount;
        if((builtString.length() - lineLength) < 0) {
            padExtraAmount = padAmount + (builtString.length() - lineLength);
        }
        if((builtString.length() -lineLength ) > 0) {
            padExtraAmount = padAmount - (builtString.length() - lineLength);
        } else {
            padExtraAmount = padAmount;
        }
        String padExtra = (" ").repeat(padExtraAmount);
        
        builtString = String.format("|%s%s%s%s%s|", pad, leftString, pad,
                rightString, padExtra);
        
        return builtString;
    }
}
