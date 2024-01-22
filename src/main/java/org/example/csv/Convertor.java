package org.example.csv;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Convertor {
    CsvDto convert(String externalApiResponseJson) throws JsonProcessingException;
}