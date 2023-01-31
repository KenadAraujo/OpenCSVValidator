package org.polligonalApps.OpenCSVValidator.core;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;
import org.polligonalApps.OpenCSVValidator.core.error.AbstractError;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

public class Validator {

    private Set<AbstractError> erros = new HashSet<>();

    public boolean validate(InputStream file, Set<AbstractColumn> columns) {
        erros.clear();
        //TODO Implementar validação
        return Boolean.FALSE;
    }

    /**
     * Translate Erro Key at ResourceBundle message to Natural Language
     * @param rs null for StandartMessage in english or not null for other language
     * @return
     */
    public Set<?> getErrosMessages(ResourceBundle rs) {
        if(rs!=null){
            erros.forEach(e->{
                e.setMessageError(rs);
            });
        }
        return erros;
    }

    public Stream<List<HashMap<String,String>>> extractTo(InputStream file, Set<AbstractColumn> columns) {
        return null;
    }
}
