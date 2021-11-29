package com.lvcom.java.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final Pattern IP_PATTERN = Pattern.compile("(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}");
    public static final String BLACKLIST_FILE_NAME = "blacklist.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File(BLACKLIST_FILE_NAME);
        long lastModified = file.lastModified();

        Set<String> blacklisted = null;
        blacklisted = loadFile(blacklisted);

        while (true){

            System.out.println("Enter IP address or 'quit' to exit");

            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                 break;
            }

            Matcher matcher = IP_PATTERN.matcher(input);
            if(!matcher.find()){
                System.out.println("Invalid Ip address\n");
                continue;
            }

            if (file.lastModified() != lastModified) {
                blacklisted = loadFile(blacklisted);
            }
            isBlackListed(blacklisted, input);
        }
    }

    private static Set<String> loadFile(Set<String> blacklisted) {
        try {
            blacklisted = new HashSet(Files.readAllLines(Paths.get(BLACKLIST_FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e + "\n");
        }
        return blacklisted;
    }

    private static void isBlackListed(Set<String> blacklisted, String input) {
        boolean isBlackListed = blacklisted.contains(input);
        System.out.println(isBlackListed ? "Access disallowed\n" : "Access allowed\n");
    }
}
