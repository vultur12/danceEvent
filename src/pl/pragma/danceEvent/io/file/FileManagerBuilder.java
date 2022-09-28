package pl.pragma.danceEvent.io.file;

import pl.pragma.danceEvent.exception.NoSuchFileTypeException;
import pl.pragma.danceEvent.io.ConsolPrinter;
import pl.pragma.danceEvent.io.DataReader;

public class FileManagerBuilder {
    private ConsolPrinter consolPrinter;
    private DataReader dataReader;

    public FileManagerBuilder(ConsolPrinter consolPrinter, DataReader dataReader) {
        this.consolPrinter = consolPrinter;
        this.dataReader = dataReader;
    }

    public FileManager build(){
        consolPrinter.printLine("wybierz format danych:");
        FileType fileType = getFileType();
        return switch (fileType) {
            case SERIAL -> new SerializableFileManager();
            case CSV    -> new CSVFileManager();
            default     -> throw new NoSuchFileTypeException("Nie obsługiwany typ danych: " + fileType);
        };
    }

    private FileType getFileType() {
        boolean typeOK = false;
        FileType fileType = null;
        do{
            printTypes();
            String fileTypeString = dataReader.getString().toUpperCase();
            try{
                fileType = FileType.valueOf(fileTypeString);
                typeOK = true;
            } catch (IllegalArgumentException e) {
                consolPrinter.printLine("Nieobsługiwany typ danych, wybierz ponownie.");
            }
        }while (!typeOK);
        return fileType;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            consolPrinter.printLine(value.name());
        }
    }
}
