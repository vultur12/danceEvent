package pl.pragma.danceEvent.app;

import pl.pragma.danceEvent.exception.DataExportException;
import pl.pragma.danceEvent.exception.DataImportException;
import pl.pragma.danceEvent.exception.NoSuchOptionException;
import pl.pragma.danceEvent.io.ConsolPrinter;
import pl.pragma.danceEvent.io.DataReader;
import pl.pragma.danceEvent.io.file.FileManager;
import pl.pragma.danceEvent.io.file.FileManagerBuilder;
import pl.pragma.danceEvent.model.DanceClass;
import pl.pragma.danceEvent.model.Schedule;
import pl.pragma.danceEvent.webscrape.WebScrape;

import java.util.InputMismatchException;

public class EventControl {
    private ConsolPrinter printer = new ConsolPrinter();
    private DataReader dataReader = new DataReader(printer);
    private Schedule schedule;
    private FileManager fileManager;

    public EventControl() {
            fileManager = new FileManagerBuilder(printer, dataReader).build();
            try {
                schedule = fileManager.importData();
                printer.printLine("Zaimportowane dane z pliku");
            } catch (DataImportException e) {
                printer.printLine(e.getMessage());
                schedule = new Schedule();
                printer.printLine("Zainicjowano nową bazę.");
            }

        }

    public void controlLoop(){
        OptionMenu optionMenu = null;
        do{
            optionMenu.printOptions();
            optionMenu = getOption();

            switch (optionMenu) {
                case GET_DANCE_CLASSES_FROM_WEB:
                    downloadDanceClass();
                    break;
                case GET_DANCE_CLASSES:
                    addDanceClass();
                    break;
                case PRINT_DANCE_CLASSES:
                    printDanceClass();
                    break;
                case FILTER_DANCE_CLASSES:
                    printFilteredDanceClass();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.err.println("Podano złą opcję");
            }
        }while(optionMenu != OptionMenu.EXIT);
    }

    private void printFilteredDanceClass() {
        OptionFilter optionFilter;
        /*do{
            printFilteredDanceClass();
        }
        Schedule.showFilteredSchedule();*/

    }

    private void printDanceClass() {
        Schedule.showSchedule();
    }

    private void downloadDanceClass() {
        WebScrape webScrape = new WebScrape();
        webScrape.ReadWeb();
        printer.printLine("Pobrano dane ze strony.");
    }

    private OptionMenu getOption() {
        boolean optionOK = true;
        OptionMenu optionMenu = null;
        while (optionOK){
            try {
                optionMenu = OptionMenu.createFromInt(dataReader.getInt());
                optionOK = false;
            }catch ( NoSuchOptionException e ){
                printer.printLine(e.getMessage());
            }catch ( InputMismatchException e ){
                printer.printLine("Wprowadzono wartość która nie jest liczbą");
            }
        }
        return optionMenu;
    }

    private void exit() {
        try {
            fileManager.exportData(schedule);
            System.out.println("Export zakończony sukcesem ");
        }catch (DataExportException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Dziękuję za skorzystanie z programu: ");
        dataReader.closeScanner();
    }


    private void addDanceClass() {
        try {
            printer.printLine("Podaj dzień tygodnia");
            final String dayOFWeek = dataReader.getString();
            DanceClass danceClass = dataReader.readAndCreateDanceClass();

            schedule.addDanceClass(dayOFWeek, danceClass);
        }catch (InputMismatchException e){
            printer.printLine("Nie udało się dodać zajęć tanecznych ");
        }catch (IndexOutOfBoundsException e){
            printer.printLine("Osiągnięto limit, niemożna dodać zajęć tanecznych");
        }
    }

}