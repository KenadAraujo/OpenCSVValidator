package org.polligonalApps.OpenCSVValidator.core.error;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;

import java.util.ResourceBundle;

public abstract class AbstractError {

    private AbstractColumn column;
    private Long row;
    private String keyError;
    private String messageError;

    public AbstractError(Long row,String keyError, AbstractColumn column) {
        this.row = row;
        this.keyError = keyError;
        this.column = column;
    }

    public void setMessageError(String messageError){
        this.messageError = messageError;
    }

    public void setMessageError(ResourceBundle rs){
        this.messageError = rs.getString(keyError);
    }
    public String getMessageError(){
        return this.messageError;
    }

    public String getKeyError(){
        return this.keyError;
    }
}
