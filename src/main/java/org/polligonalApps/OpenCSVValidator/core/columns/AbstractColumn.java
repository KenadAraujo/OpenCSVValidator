package org.polligonalApps.OpenCSVValidator.core.columns;

import java.util.regex.Pattern;

public abstract class AbstractColumn {

    private String columnName;
    private Long columnOrder;
    private String columnRegexValidator;

    private String generatedErrorKey;

    public AbstractColumn(String columnName, Long columnOrder, String columnRegexValidator, String generatedErrorKey){
        this.columnName = columnName;
        this.columnOrder = columnOrder;
        this.columnRegexValidator = columnRegexValidator;
        this.generatedErrorKey = generatedErrorKey;
    }

    /**
     *
     * @param value to validate
     * @return True is valid or False is not valid
     */
    public boolean validate(String value){
        Pattern padrao = Pattern.compile(this.columnRegexValidator);
        if(!padrao.matcher(value).matches()){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnRegexValidator() {
        return this.columnRegexValidator;
    }

    public String getGeneratedErrorKey() { return this.generatedErrorKey; }

    @Override
    public boolean equals(Object obj) {
        AbstractColumn ab = (AbstractColumn) obj;
        return columnName.equalsIgnoreCase(ab.getColumnName())
                && columnRegexValidator.equals(ab.getColumnRegexValidator())
                && generatedErrorKey.equals(ab.getGeneratedErrorKey());
    }
}
