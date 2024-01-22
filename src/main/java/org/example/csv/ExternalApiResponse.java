package org.example.csv;

import java.util.List;

class ExternalApiResponse {

    private List<Row> rows;
    private List<String> pov;
    private List<List<String>> columns;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<String> getPov() {
        return pov;
    }

    public void setPov(List<String> pov) {
        this.pov = pov;
    }

    public List<List<String>> getColumns() {
        return columns;
    }

    public void setColumns(List<List<String>> columns) {
        this.columns = columns;
    }

}