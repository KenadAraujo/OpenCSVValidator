package org.polligonalApps.OpenCSVValidator.core.error.header;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;
import org.polligonalApps.OpenCSVValidator.core.error.AbstractError;

public class HeaderColumnNotFoundError extends AbstractError {

    private String columnNotFoundName;

    public HeaderColumnNotFoundError(Long row, String keyError, String columnNotFoundName) {
        super(row, keyError, null);
        this.columnNotFoundName = columnNotFoundName;
    }

    public String getColumnNotFoundName() {
        return this.columnNotFoundName;
    }
}
