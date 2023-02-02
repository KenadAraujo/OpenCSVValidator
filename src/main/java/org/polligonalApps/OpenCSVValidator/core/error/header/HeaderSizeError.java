package org.polligonalApps.OpenCSVValidator.core.error.header;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;
import org.polligonalApps.OpenCSVValidator.core.error.AbstractError;

public class HeaderSizeError extends AbstractError {

    public HeaderSizeError(Long row, String keyError, AbstractColumn column) {
        super(row, keyError, column);
    }
}
