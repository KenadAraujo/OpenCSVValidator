package org.polligonalApps.OpenCSVValidator.core.error.row;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;
import org.polligonalApps.OpenCSVValidator.core.error.AbstractError;

public class RowSizeError extends AbstractError {

    public RowSizeError(Long row, String keyError, AbstractColumn column) {
        super(row, keyError, column);
    }
}
