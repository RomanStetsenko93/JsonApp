package org.example.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvOutputToFileService implements CsvOutputService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final String FILE_EXTENSION = ".csv";

    @Override
    public void processCsv(CsvDto csvDto) {
        createCSV(csvDto.getHeader(), csvDto.getRows());
    }

    private void createCSV(String header, List<String> rows) {
        File csvOutputFile = new File(generateCsvFileNamePrefix());
        System.out.printf("Preparing CSV file with name: '%s'%n", csvOutputFile.getName());
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(header);
            rows.forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to prepare CSV file" + e.getMessage());
        }
        System.out.println("Path to file: " + csvOutputFile.getAbsolutePath());
    }

    private String generateCsvFileNamePrefix() {
        return String.format("CSV_from_JSON_%s%s", LocalDateTime.now().format(DATE_TIME_FORMATTER), FILE_EXTENSION);
    }

}