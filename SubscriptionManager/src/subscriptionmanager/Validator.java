
package subscriptionmanager;

import java.time.LocalDate;
import java.util.Scanner;


/**
 * The Validator class is a utility class of static methods used to validate
 * user input when the user input may be generic or reusable across one or more
 * use cases, or may benefit the class, in which the method is used, to
 * contain the method here for clarity or readability. 
 */
public class Validator {
    
    private static final Scanner scan = new Scanner(System.in);
    
    /**
     * Requests and validates a customer name and returns it to the calling
     * class in the correct format.
     * First initial and surname have leading and trailing characters or 
     * white space removed, first letters are captialised and then joined either
     * side of a space.
     * 
     * @return Returns the customer name in the correct format.
     */
    public static String validateName() {
        String first;
        String surname;
        String name = null;
        boolean save = false;
        do {
            System.out.print("\nEnter first initial: ");
            first = scan.nextLine();
            first = first.replace(".", "");
            first = first.replace(",", "");
            first = first.trim();

            if(first.isEmpty()) {
                System.out.print("\nInvalid entry, cannot be blank.\n");
                continue;
            } 
            do {
                System.out.print("\nEnter surname: ");
                surname = scan.nextLine();
                surname = surname.replace(".", "");
                surname = surname.replace(",", "");
                surname = surname.trim();
                if(surname.isEmpty()) {
                    System.out.print("\nInvalid entry, cannot be blank.\n");
                } else {
                    break;
                }
            }while(true);
            first = first.toUpperCase();
            first = first.substring(0, 1);

            surname = surname.toLowerCase();
            surname = surname.substring(0, 1).toUpperCase() 
                    + surname.substring(1);

            name = first + " " + surname;
            save = validateConfirmInput("\n" + name + " use this name (Y/N)? ");
        }while(save == false);
        
        return name;        
    }
    
    /**
     * Validates numeric user input, used in conjunction with switch statements
     * in the calling class.
     * 
     * @param prompt is the descriptive message displayed to the user explaining
     *     the options and numbers the user has to select from.
     * @return returns the valid number the user has input.
     */
    public static int validateNumber(String prompt) {
        
        int input;
        do {
            System.out.print(prompt);
            if(scan.hasNextInt()) {
                input = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                System.out.println("\nInvalid input, please enter a number.");
                scan.nextLine();
            }        
        } while(true);
        return input;
    }
    
    /**
     * The requestMonth method gets the month from the user which is to be
     * summarised.
     * The request month method validates the input and modifies it to the
     * correct format, matching the format of the month of the date in the file.
     * The for loop in the method iterates over the String array monthOrder from
     * the Summary base class, which contains all of the months in short format,
     * to check if the modified user input is a match.
     * 
     * @param monthOrder is a String array containing all of the months of the
     * year in short form which is used to check against for a valid month from
     * the user.
     * @return Returns a String of the month in short format.
     */
    public static String validateMonth(String[] monthOrder) {

        String userMonth;
        String shortMonth;
        do {
            System.out.print("\nEnter the month in short form i.e Jan: ");

            userMonth = scan.nextLine();
            userMonth = userMonth.trim();
            userMonth = userMonth.replace(",", "");
            userMonth = userMonth.replace(".", "");
            userMonth = userMonth.replace(" ", "");

        if(userMonth.length() < 3) {
                System.out.println("\nInvalid input, minimum 3 letters.");
                continue;
            }
            userMonth = userMonth.toLowerCase();
            shortMonth = userMonth.substring(0, 1).toUpperCase()
                    + userMonth.substring(1, 3);
            int i = 0;
            for (String m : monthOrder) {
                if(m.equals(shortMonth)) {
                    return shortMonth;
                }
                if(i++ == monthOrder.length - 1) {
                    System.out.println("\nPlease enter valid month!");
                }
            }
        } while(true);   

    }
    
    /**
     * Validates input requesting yes or no responses from the user, for example
     *  can be used to confirm prior inputs are correct, or requesting if the
     * user would like to repeat a process.
     * 
     * @param prompt message to the user explaining why the confirmation is
     *     required.
     * @return returns a boolean which can be used in a conditional in the
     *     calling class.
     */
    public static boolean validateConfirmInput(String prompt) {
    
        String input;
        do {
            System.out.print(prompt);
            input = scan.nextLine();
            input = input.replace(",", "");
            input = input.replace(".", "");
            input = input.trim();

            if(input.isEmpty()) {
                System.out.println("\nInvalid entry, cannot be empty!");
                continue;
            } else {
                input = input.toUpperCase();
            }            
            if(input.length() != 1) {
                System.out.println("\nInvalid entry, enter Y for yes or N for "
                        + "no.");
            }else if(input.equals("Y")) {
                return true;
            } else if(input.equals("N")) {
                return false;
            } else {
                System.out.println("\nInvalid entry, enter Y for yes or"
                        + " N for no.");
            }
        } while(true);
    }

    /**
     * Method to validate the code provided by the user against the time of year
     * the code is valid for and the amount of discount the code provides 
     * against the subscription base cost.
     * The code is validated against length, correct individual characters
     * (either a letter or number), correct char letter representing the half of
     * the year the code is valid for and the int discount amount.
     *
     * @return int amount of discount representing a percentage of discount to
     *     be applied to the subscription.
     */
    public static String validateCode() {
        
        String code = null;
        
        do {
                System.out.print("\nEnter discount code: ");
                code = scan.nextLine();
                code = code.replace(".", "");
                code = code.replace(",", "");
                code = code.trim();

                if(!code.isEmpty()) {
                    break;
                }else{
                    System.out.println("Invalid entry, cannot be empty.");                    
                }
            }while(true);
        code = code.toUpperCase();

        String[] splitCode = code.split("");
        if(splitCode.length != 6) {
            System.out.println("\nCode invalid, incorrect length!");
            return "-";
        }
        
        if (!Character.isLetter(code.charAt(0))) {
            System.out.println("\nCode invalid, first character must be a "
                    + "letter!");
            return "-";
        }
        else if (!Character.isLetter(code.charAt(1))) {
            System.out.println("\nCode invalid, second character must be a "
                    + "letter!");
            return "-";
        }
        else if (!Character.isDigit(code.charAt(2))) {
            System.out.println("\nCode invalid, third character must be a "
                    + "number!");
            return "-";
        }
        else if (!Character.isDigit(code.charAt(3))) {
            System.out.println("\nCode invalid, fourth character must be a "
                    + "number!");
            return "-";
        }
        else if (!Character.isLetter(code.charAt(4))) {
            System.out.println("\nCode invalid, fifth character must be a "
                    + "letter!");
            return "-";   
        }
        else if(!(Character.isDigit(splitCode[5].charAt(0)))){
            System.out.println("\nCode invalid, sixth character must be a "
                    + "number!");
            return "-"; 
        }

        LocalDate date = LocalDate.now();
        int currentYear = date.getYear();
        
        String yearString = "20" + splitCode[2] + splitCode[3];
        int yearCode = Integer.valueOf(yearString);
        
        if(yearCode != currentYear) {
            System.out.println("\nCode invalid, incorrect year!");
            return "-";
        }

        int currentMonth = date.getMonthValue();
        
        char currentMonthChar;
        
        if(currentMonth < 7) {
            currentMonthChar = 'E';
        } else {
            currentMonthChar = 'L';
        }

        if(!splitCode[4].equals(String.valueOf(currentMonthChar))) {
            System.out.println("\nCode invalid, incorrect month code!");
            return "-";
        } 
        
        if(Integer.valueOf(splitCode[5]) < 1 ) {
            System.out.println("\nCode invalid, incorrect discount amount.");
            return "-";
        }else {
            System.out.println("\nCode accepted, " 
                    + Integer.valueOf(splitCode[5]) + "% discount applied!");
            return code;
        }
    }
}
