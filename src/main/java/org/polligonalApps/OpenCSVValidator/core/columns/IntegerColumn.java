package org.polligonalApps.OpenCSVValidator.core.columns;

public class IntegerColumn extends AbstractColumn{

    /**
     * Create integer validate column with size digits
     * @param columnName name of column
     * @param columnOrder order of column
     * @param size digits on column
     */
    public IntegerColumn(String columnName, Long columnOrder, int size) {
        super(columnName, columnOrder, "[0-9]{"+size+"}", "column.number");
    }
}
