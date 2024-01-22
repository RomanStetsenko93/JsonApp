package org.example.csv;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public class CsvProcessor { // tJavaRow_3

    private final CsvOutputService csvOutputService;
    private final Convertor convertor;

    public CsvProcessor(CsvOutputService csvOutputService, Convertor convertor) {
        this.csvOutputService = csvOutputService;
        this.convertor = convertor;
    }

    public void process(String input) throws JsonProcessingException {
        Optional.ofNullable(convertor.convert(input))
                .ifPresent(csvOutputService::processCsv);
    }

}