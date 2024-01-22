package org.example.csv;

public class CsvOutputToConsoleService implements CsvOutputService {

    @Override
    public void processCsv(CsvDto csvDto) {
        System.out.println(csvDto.getHeader());
        csvDto.getRows().forEach(System.out::println);
    }

}