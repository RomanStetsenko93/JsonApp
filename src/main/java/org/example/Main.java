package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.example.csv.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String OPTIONS_MESSAGE = "Type '1' to write output to console, type '2' to write output to file";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Json_to_CSV application!");
        System.out.println(OPTIONS_MESSAGE);

        CsvProcessor processor = initProcessor(scanner);

        System.out.println("Input your JSON or press ENTER to proceed with the default JSON from the JavaJacksonExample_TalendJobVersion.docx");
        System.out.println("Your JSON have to be minified to one line, it can be done via following link: https://codebeautify.org/json-to-one-line\n");
        try {
            processor.process(getJson(scanner));
        } catch (JsonProcessingException e) {
            System.out.println("Provided JSON can not be processed: " + e.getMessage());
        }
        System.out.println("\nWork finished");
    }

    private static String getJson(Scanner scanner) {
        String userJson = scanner.nextLine();
        if (StringUtils.isBlank(userJson)) {
            System.out.println("\nDefault JSON from the doc is going to be used.");
            userJson = Constants.DEFAULT_INPUT;
        } else {
            System.out.println("\nThanks for your input.");
        }
        System.out.println("Processing target JSON...\n");
        makePause();
        return userJson;
    }

    private static void makePause() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static CsvProcessor initProcessor(Scanner scanner) {
        CsvOutputService outputService;
        do {
            String mode = scanner.nextLine();
            outputService = getOutputService(mode);
        } while (outputService == null);

        return new CsvProcessor(outputService, new ConvertorImpl());
    }

    private static CsvOutputService getOutputService(String mode) {
        switch (mode) {
            case "1":
                System.out.println("Output will be displayed in the console");
                return new CsvOutputToConsoleService();
            case "2":
                System.out.println("Output will be saved to file");
                return new CsvOutputToFileService();
            default:
                System.out.printf("Input value '%s' is not correct. %s%n", mode, OPTIONS_MESSAGE);
                return null;
        }
    }

}