package org.example.csv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvertorImpl implements Convertor {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String[] rowDimensions = {"Account", "Operation Type", "Material Type", "Entity", "Customer", "Years", "Scenario", "Version", "Plan Element" };
    private static final String[] povDimensions = {"Currency" };

    @Override
    public CsvDto convert(String externalApiResponseJson) throws JsonProcessingException { //input_row.ResponseContent
        ExternalApiResponse response = mapper.readValue(externalApiResponseJson, new TypeReference<ExternalApiResponse>() {});

        if (response.getColumns() == null || response.getPov() == null || response.getRows() == null) {
            System.out.println("Invalid JSON provided! It must have 'rows', 'columns' and 'pov'.");
            return null;
        }

        return new CsvDto(
                extractHeader(response.getColumns()),
                extractRows(response)
        );
    }

    private static String extractHeader(List<List<String>> columns) {
        return convertStringsToLine(
                true,
                Arrays.stream(rowDimensions), Arrays.stream(povDimensions), columns.stream().flatMap(List::stream)
        );
    }

    @SafeVarargs
    private static String convertStringsToLine(boolean quoted, Stream<String>... strings) {
        return Stream.of(strings)
                .flatMap(Function.identity())
                .collect(Collectors.joining(
                        quoted ? "\",\"" : ",",
                        quoted ? "\"" : "",
                        quoted ? "\"" : ""
                ));
    }

    private static List<String> extractRows(ExternalApiResponse response) {
        long columnSize = response.getColumns().stream().mapToLong(List::size).sum();
        return response.getRows()
                .stream()
                .map(row -> extractRow(row, response.getPov(), columnSize))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static String extractRow(Row row, List<String> pov, long columnSize) {
        List<String> data = row.getData();
        List<String> headers = row.getHeaders();
        if (data.size() != columnSize) {
            System.out.printf(
                    "Unexpected data size: %d in comparison to columns size: %d. Skipping the row. %n",
                    data.size(), columnSize
            );
            return null;
        }
        if (headers.size() != rowDimensions.length) {
            System.out.printf(
                    "Unexpected headers size: %d in comparison to rowDimensions size: %d. Skipping the row. %n",
                    headers.size(), columnSize
            );
            return null;
        }

        return String.join(
                ",",
                convertStringsToLine(true, headers.stream(), pov.stream()),
                convertStringsToLine(false, data.stream())
        );
    }

}