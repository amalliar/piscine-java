package com.school21.piscinejava.module01.ex05;

public class Program {
    private static RunProfiles runProfile = RunProfiles.PROD;

    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Error: too many arguments");
            System.exit(1);
        } else if (args.length == 1) {
            if ("--profile=dev".equals(args[0])) {
                runProfile = RunProfiles.DEV;
            } else if ("--profile=prod".equals(args[0])){
            } else {
                System.err.printf("Error: invalid option -- '%s'", args[0]);
                System.exit(1);
            }
        }
        new Menu().run(runProfile);
    }
}
