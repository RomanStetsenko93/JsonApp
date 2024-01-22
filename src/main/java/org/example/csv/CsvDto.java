package org.example.csv;

import java.util.List;

public class CsvDto {

    private final String header;
    private final List<String> rows;

    public CsvDto(String header, List<String> rows) {
        this.header = header;
        this.rows = rows;
    }

    public String getHeader() {
        return header;
    }

    public List<String> getRows() {
        return rows;
    }

}