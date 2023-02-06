package org.polligonalApps.OpenCSVValidator.core;

import org.polligonalApps.OpenCSVValidator.core.columns.AbstractColumn;
import org.polligonalApps.OpenCSVValidator.core.error.AbstractError;
import org.polligonalApps.OpenCSVValidator.core.error.header.HeaderColumnNotFoundError;
import org.polligonalApps.OpenCSVValidator.core.error.constants.ErrorConstants;
import org.polligonalApps.OpenCSVValidator.core.error.header.HeaderSizeError;
import org.polligonalApps.OpenCSVValidator.core.error.row.ColumnError;
import org.polligonalApps.OpenCSVValidator.core.error.row.RowSizeError;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validator {

    private long lineIndex = 0L;
    private int columnIndex = 0;

    private List<AbstractError> erros = new ArrayList<>();
    private List<AbstractColumn> columns = new ArrayList<>();
    private char separator;

    private boolean validate = Boolean.FALSE;

    public Validator(char separator){
        this.separator = separator;
    }

    /**
     * Validate file with Rules on columns
     * @param file to validate
     * @param columns from file with validation rules
     * @param stopFirstLineError true at stop first error or false at check all file
     * @return true is valid or false is not valid
     */
    public boolean validate(String file, Set<AbstractColumn> columns, boolean stopFirstLineError) {
        erros.clear();
        this.lineIndex = 1L;

        try(Stream<String> lines = Files.lines(Paths.get(file), Charset.defaultCharset())){
            String firstLine = String.valueOf(lines.findFirst());
            List<AbstractError> errorsHeader = processFirstLine(firstLine, columns);
            if (!errorsHeader.isEmpty()) {
                this.erros.addAll(errorsHeader);
                if (stopFirstLineError) this.validate = false;
            }else {
                lines.iterator().next();
            }

            return !lines.anyMatch(currentLine -> {
                List<AbstractError> errorsRow = processOtherLine(lineIndex,currentLine);
                if(!errorsRow.isEmpty()) {
                    this.erros.addAll(errorsRow);
                    if(stopFirstLineError) {
                        return true;
                    }else{
                        return false;
                    }
                }
                lineIndex ++;
                return false;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<AbstractError> processFirstLine(String firstLine, Set<AbstractColumn> columns) {
        List<AbstractError> erros = new ArrayList<>();
        String[] values = firstLine.split(String.valueOf(this.separator));
        if(values.length!=columns.size()){
            erros.add(new HeaderSizeError(0L, ErrorConstants.HEADER_SIZE.getValue(), null));
        }
        Arrays.stream(values).forEachOrdered(e->{
            List<AbstractColumn> colSpecific = columns.stream().filter(col-> col.getColumnName().equals(e)).collect(Collectors.toList());
            if(colSpecific.isEmpty()){
                erros.add(new HeaderColumnNotFoundError(0L,ErrorConstants.HEADER_COLUMN_NOTFOUND.getValue(),e));
            }else{
                this.columns.add(colSpecific.get(0));
            }
        });
        return erros;
    }

    private List<AbstractError> processOtherLine(long row,String line){
        List<AbstractError> erros = new ArrayList<>();
        String[] values = line.split(String.valueOf(this.separator));
        if(values.length!=this.columns.size()){
            erros.add(new RowSizeError(row,ErrorConstants.COLUMNS_SIZE.getValue(), null));
        }else{
            final int[] col = {0};
            columns.forEach(e->{
                if(!e.validate(values[col[0]])){
                    erros.add(new ColumnError(Long.valueOf(col[0]),e.getGeneratedErrorKey(),e));
                    col[0] +=1;
                }
            });
        }
        return erros;
    }

    /**
     * Translate Erro Key at ResourceBundle message to Natural Language
     * @param rs null for StandartMessage in english or not null for other language
     * @return
     */
    public List<AbstractError> getErrosMessages(ResourceBundle rs) {
        if(rs!=null){
            erros.forEach(e->{
                e.setMessageError("["+e.getColumn()+"]"+rs.getString(e.getKeyError()));
            });
        }
        return erros;
    }

    /**
     * Extract data from CSV file. DANGER: This method may use a lot of RAM memory.
     * @param file
     * @param columns
     * @return
     */
    public Stream<HashMap<String,String>> extract(String file, Set<AbstractColumn> columns) throws IOException {
        Stream.Builder<HashMap<String,String>> data = Stream.builder();
        try(Stream<String> lines = Files.lines(Paths.get(file), Charset.defaultCharset())) {
            List<String> header = extractToHeader(lines.findFirst().get(),columns);

            while(!lines.anyMatch(currentLine -> {
                List<AbstractError> errorsRow = processOtherLine(lineIndex,currentLine);
                if(!errorsRow.isEmpty()) {
                    this.erros.addAll(errorsRow);
                    return true;
                }
                data.add(extractToLine(currentLine,header));
                lineIndex ++;
                return false;
            }));
        }
        return data.build();
    }

    private List<String> extractToHeader(String firstLine, Set<AbstractColumn> columns) throws IOException {
        if(!processFirstLine(firstLine,columns).isEmpty()){
            throw new IOException("File with header invalid!");
        }
        return this.columns.stream().map(e->e.getColumnName()).collect(Collectors.toList());
    }
    private HashMap<String, String> extractToLine(String line, List<String> header) {
        HashMap<String,String> values = new HashMap<String,String>();
        this.columnIndex = 0;
        header.forEach(e->{
            String data = line.split(String.valueOf(this.separator))[columnIndex];
            columnIndex++;
            values.put(e,data);
        });
        return values;
    }
}
