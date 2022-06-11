package com.school21.piscinejava.module01.ex05;

import com.school21.piscinejava.module01.ex05.Transaction.TransferCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class Menu {
    private static final String ansiRed = "\u001B[31m";
    private static final String ansiReset = "\u001B[0m";
    private static final String ps1 = "-> ";
    private final TransactionsService transactionsService = new TransactionsService();
    private final Scanner scanner = new Scanner(System.in);

    interface MenuAction {
        void execute();
        String getDescription();
    }

    public void run(RunProfiles runProfile) {
        List<MenuAction> menuActions = new ArrayList<>(List.of(
                new MenuAction() { public void execute() { addUserAction(); } public String getDescription() { return "Add a user"; }},
                new MenuAction() { public void execute() { viewUserBalanceAction(); } public String getDescription() { return "View user balances"; }},
                new MenuAction() { public void execute() { performTransferAction(); } public String getDescription() { return "Perform a transfer"; }},
                new MenuAction() { public void execute() { viewUserTransactionsAction(); } public String getDescription() { return "View all transactions for a specific user"; }}
        ));
        if (runProfile == RunProfiles.DEV) {
            menuActions.addAll(List.of(
                    new MenuAction() { public void execute() { removeTransferByIdAction(); } public String getDescription() { return "DEV - remove a transfer by ID"; }},
                    new MenuAction() { public void execute() { checkTransferValidity(); } public String getDescription() { return "DEV - check transfer validity"; }}
            ));
        }
        menuActions.add(new MenuAction() { public void execute() { finishExecutionAction(); } public String getDescription() { return "Finish execution"; }});
        for (;;) {
            try {
                System.out.println("---------------------------------------------------------");
                for (int i = 0; i < menuActions.size(); ++i) {
                    System.out.printf("%d. %s%n", i + 1, menuActions.get(i).getDescription());
                }
                System.out.print(ps1);
                String[] args = extractArgs(scanner.nextLine());
                if (args.length != 1) {
                    throw new MenuActionException(format("Error: too %s arguments", (args.length < 1) ? "few" : "many"));
                }
                int idx = Integer.parseInt(args[0]);
                if (idx < 1 || idx > menuActions.size()) {
                    throw new MenuActionException("Error: invalid action number");
                }
                menuActions.get(idx - 1).execute();
            }catch (NumberFormatException ex) {
                System.out.printf("%s%s%s%n", ansiRed, "Error: invalid number format", ansiReset);
            } catch (Exception ex) {
                System.out.printf("%s%s%s%n", ansiRed, ex.getMessage(), ansiReset);
            }
        }
    }

    public void addUserAction() {
        System.out.printf("Enter a user name and a balance%n%s", ps1);
        String[] args = extractArgs(scanner.nextLine());
        if (args.length != 2) {
            throw new MenuActionException(format("Error: too %s arguments", (args.length < 2) ? "few" : "many"));
        }
        int userId = transactionsService.addUser(args[0], Integer.parseInt(args[1]));
        System.out.printf("User with id = %d is added%n", userId);
    }

    public void viewUserBalanceAction() {
        System.out.printf("Enter a user ID%n%s", ps1);
        String[] args = extractArgs(scanner.nextLine());
        if (args.length != 1) {
            throw new MenuActionException(format("Error: too %s arguments", (args.length < 1) ? "few" : "many"));
        }
        int userId = Integer.parseInt(args[0]);
        String userName = transactionsService.getUserName(userId);
        int userBalance = transactionsService.getUserBalance(userId);
        System.out.printf("%s - %d%n", userName, userBalance);
    }

    public void performTransferAction() {
        System.out.printf("Enter a sender ID, a recipient ID, and a transfer amount%n%s", ps1);
        String[] args = extractArgs(scanner.nextLine());
        if (args.length != 3) {
            throw new MenuActionException(format("Error: too %s arguments", (args.length < 3) ? "few" : "many"));
        }
        int senderId = Integer.parseInt(args[0]);
        int recipientId = Integer.parseInt(args[1]);
        int transferAmount = Integer.parseInt(args[2]);
        transactionsService.makeTransaction(recipientId, senderId, transferAmount);
        System.out.println("The transfer is completed");
    }

    public void viewUserTransactionsAction() {
        System.out.printf("Enter a user ID%n%s", ps1);
        String[] args = extractArgs(scanner.nextLine());
        if (args.length != 1) {
            throw new MenuActionException(format("Error: too %s arguments", (args.length < 1) ? "few" : "many"));
        }
        int userId = Integer.parseInt(args[0]);
        Transaction[] userTransactions = transactionsService.getUserTransactions(userId);
        for (Transaction tr : userTransactions) {
            System.out.printf("%s with id = %s%n", tr, tr.getId());
        }
    }

    public void removeTransferByIdAction() {
        System.out.printf("Enter a user ID and a transfer ID%n%s", ps1);
        String[] args = extractArgs(scanner.nextLine());
        if (args.length != 2) {
            throw new MenuActionException(format("Error: too %s arguments", (args.length < 2) ? "few" : "many"));
        }
        int userId = Integer.parseInt(args[0]);
        String transactionId = args[1];
        String transfer = transactionsService.removeTransaction(userId, transactionId);
        System.out.printf("Transfer %s removed%n", transfer);
    }

    public void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] unpairedTransactions = transactionsService.getUnpairedTransactions();
        if (unpairedTransactions.length != 0) {
            for (Transaction tr : unpairedTransactions) {
                System.out.printf("%s has an unacknowledged transfer id = %s %s %s for %d%n",
                        (tr.getTransferCategory() == TransferCategory.CREDIT) ? tr.getSender() : tr.getRecipient(),
                        tr.getId(),
                        (tr.getTransferCategory() == TransferCategory.CREDIT) ? "to" : "from",
                        (tr.getTransferCategory() == TransferCategory.CREDIT) ? tr.getRecipient() : tr.getSender(),
                        tr.getTransferAmount()
                );
            }
        } else {
            System.out.println("No unacknowledged transfers were found");
        }
    }

    public void finishExecutionAction() {
        System.exit(0);
    }

    private static String[] extractArgs(String input) {
        return Arrays.stream(input.split("\\s")).filter(s -> !s.isBlank()).toArray(String[]::new);
    }
}
