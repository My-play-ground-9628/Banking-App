import java.util.Scanner;

public class BankingApp {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0m";
        final String clear = "\033[H\033[2J";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        final String DASHBOARD = "Welcome to Smart Banking";
        final String CREATE_NEW_ACCOUNT = "Open new account";
        final String DEPOSIT = "Deposit money";
        final String WITHDRAWS = "Withdraw money";
        final String TRANSFER = "Transfer money";
        final String CHECK_ACCOUNT_BALANCE = "Check Account Balance";
        final String DELETE_ACCOUNT = "Drop existing account";

        String[] names = new String[0];
        int[] deposits = new int[0];

        String screen = DASHBOARD;

        do{
            final String APP_TITLE = String.format("%s%s%s", COLOR_BLUE_BOLD, screen, RESET);
            System.out.println(clear);
            System.out.println("\t" + APP_TITLE + "\n");
            
            switch(screen){
                case DASHBOARD:
                    System.out.println("\t[1]. Open New Account");
                    System.out.println("\t[2]. Deposit Money");
                    System.out.println("\t[3]. Withdraw Money");
                    System.out.println("\t[4]. Transfer Money");
                    System.out.println("\t[5]. Check Account Balance");
                    System.out.println("\t[6]. Drop Existing Account");
                    System.out.println("\t[4]. Exit\n");
                    System.out.print("Enter an Option to Continue > ");

                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        case 1: screen = CREATE_NEW_ACCOUNT; break;
                        case 2: screen = DEPOSIT; break;
                        case 3: screen = WITHDRAWS; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = CHECK_ACCOUNT_BALANCE; break;
                        case 6: screen = DELETE_ACCOUNT; break;
                        case 7: System.out.println(clear); System.exit(0); break;
                    }
                    break;

                case CREATE_NEW_ACCOUNT:
                    String name;
                    int initialDeposit;
                    boolean valid = true;
                    System.out.printf("ID: SDB-%05d \n", (names.length + 1));
                    
                    //Name validation 
                    do{
                        valid = true;
                        System.out.print("\tEnter Customer Name: ");
                        name = scanner.nextLine().toUpperCase();
                        if (name.isBlank()) {
                            System.out.printf(ERROR_MSG , "Name Can't be empty");
                            valid = false;
                            continue;
                        }else {
                            for (int i = 0; i < name.length(); i++) {
                                if (!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))) {
                                    System.out.printf(ERROR_MSG, "Invalid name");
                                    valid = false;
                                    break;
                                }
                            }
                        }

                    }while(!valid);

                    //Deposit validation
                    do{
                        valid = true;
                        System.out.print("\tInitial Deposit: ");
                        initialDeposit = scanner.nextInt();
                        scanner.nextLine();

                        if (initialDeposit < 5000) {
                            System.out.printf(ERROR_MSG, "Insufficient Amount");
                            valid = false;
                            continue;
                        }


                    }while(!valid);

                    
                    String[] newNames = new String[names.length + 1];
                    int[] newDeposits = new int[deposits.length + 1];

                    for (int i = 0; i < names.length; i++) {
                        newNames[i] = names[i];
                        newDeposits[i] = deposits[i];
                    }
                    newDeposits[newDeposits.length - 1] = initialDeposit;
                    newNames[newNames.length - 1] = name;
                    deposits = newDeposits;
                    names = newNames;

                    System.out.println();
                    System.out.printf(SUCCESS_MSG, String.format("SDB-%05d:%s has been saved successfully", (names.length), name));
                    System.out.print("\tDo you want to continue adding? (Y/n)");
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
                    
                case DEPOSIT:
                    String accNum;
                    int index=0;
                    int depositAmount=0;
                    
                    do{
                        valid = true;
                        System.out.print("\tEnter Account Number: ");
                        accNum = scanner.nextLine().toUpperCase().strip();
                        
                        if (accNum.isBlank()) {
                            valid = false;
                            System.out.print("\tDo you want to try again? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else if (!accNum.startsWith("SDB-")) {
                            valid = false;
                            System.out.print("\tDo you want to continue adding? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else if ((Integer.valueOf(accNum.substring(8))) > names.length){
                            valid = false;
                            System.out.print("\tDo you want to continue adding? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }
                        index = Integer.valueOf(accNum.substring(8));
                        System.out.printf("\tCurrent Balance: Rs.%,d.00\n", deposits[index-1]);
                        do{
                            System.out.print("\tDeposit amount: ");
                            depositAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (depositAmount < 500) {
                                System.out.printf(ERROR_MSG, "Insufficient Amount");
                                screen = DASHBOARD;
                                continue;
                            }
                        }while(!valid);

                    }while(!valid);
                    int newBalance = deposits[index-1]+ depositAmount;
                    deposits[index-1] = newBalance; 
                    System.out.printf("\tNew Balance: Rs: %,d.00\n",deposits[index-1]);
                    System.out.print("\tDo you want to continue ? (Y/n)");
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
                    
                case WITHDRAWS:
                    int withdrawtAmount=0;
                    index = 0;
                    
                    do{
                        valid = true;
                        System.out.print("\tEnter Account Number: ");
                        accNum = scanner.nextLine().toUpperCase().strip();
                        
                        if (accNum.isBlank()) {
                            valid = false;
                            System.out.print("\tDo you want to try again? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else if (!accNum.startsWith("SDB-")) {
                            valid = false;
                            System.out.print("\tDo you want to continue adding? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else if ((Integer.valueOf(accNum.substring(8))) > names.length){
                            valid = false;
                            System.out.print("\tDo you want to continue adding? (Y/n)");
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }
                        index = Integer.valueOf(accNum.substring(8));
                        System.out.printf("\tCurrent Balance: Rs.%,d.00\n", deposits[index-1]);
                        do{
                            System.out.print("\tWithdraw amount: ");
                            depositAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (withdrawtAmount < 100) {
                                System.out.printf(ERROR_MSG, "Minimum withdraw amount is Rs.100");
                                screen = DASHBOARD;
                                continue;
                            }else if (deposits[index-1] - withdrawtAmount < 500) {
                                System.out.printf(ERROR_MSG, "Minimum account balance is Rs.500");
                                screen = DASHBOARD;
                                continue;
                            }else{
                                newBalance = deposits[index-1] - withdrawtAmount;
                                deposits[index-1] = newBalance; 
                                System.out.printf("\tNew Balance: Rs: %,d.00\n",deposits[index-1]);
                            }
                                
                    
                        }while(!valid); 
                        
                    System.out.print("\tDo you want to continue ? (Y/n)");
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;    
                    }while(!valid);
                      
                    
                    
                    
                    

                    
                    
                    
            
            }



        }while(true);
    }
}