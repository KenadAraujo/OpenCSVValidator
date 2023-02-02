package org.polligonalApps.OpenCSVValidator.core.error.constants;

public enum ErrorConstants {
    HEADER_SIZE("header.size"),
    HEADER_COLUMN_NOTFOUND("header.columnNotFound"),
    COLUMNS_SIZE("columns.size");

    private String value;

    private ErrorConstants(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
