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
import java.util.function.Predicate;

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
        OptionMenu optionMenu;
        do{
            printOptionsMenu();
            optionMenu = getOptionMenu();

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
                    filteredDanceClass();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.err.println("Podano złą opcję");
            }
        }while(optionMenu != OptionMenu.EXIT);
    }

    private void filteredDanceClass() {
        OptionFilter optionFilter;

        Predicate<DanceClass> checkIfLevelP = (x) -> {
            return x.getClassName().toLowerCase().contains("p open")
                    || x.getClassName().toLowerCase().contains("p1")
                    || x.getClassName().toLowerCase().contains("p2")
                    || x.getClassName().toLowerCase().contains("p3")
                    || x.getClassName().toLowerCase().contains("p4");
        };

        Predicate<DanceClass> checkIfLevelS = (x) -> {
            return x.getClassName().toLowerCase().contains("s open")
                    || x.getClassName().toLowerCase().contains("s1")
                    || x.getClassName().toLowerCase().contains("s2")
                    || x.getClassName().toLowerCase().contains("s3")
                    || x.getClassName().toLowerCase().contains("s4");
        };

        Predicate<DanceClass> checkIfInstruktor = (x) -> {
            String additionalOption;
            ConsolPrinter consolPrinter = new ConsolPrinter();
            consolPrinter.printLine("Podaj intruktora");
            additionalOption = dataReader.getString();
            return x.getClassName().toLowerCase().contains(additionalOption.toLowerCase());
        };

        Predicate<DanceClass> checkIfDanceStyle = (x) -> {
            String additionalOption;
            ConsolPrinter consolPrinter = new ConsolPrinter();
            consolPrinter.printLine("Podaj styl tańca");
            additionalOption = dataReader.getString();
            return x.getClassName().toLowerCase().contains(additionalOption.toLowerCase());
        };


        do{
            printOptionsFilter();
            optionFilter = getOptionFilter();
            String additionalOption;
            ConsolPrinter consolPrinter = new ConsolPrinter();

            switch (optionFilter) {
                case EXIT :
                    break;
                case INSTRUCTOR:
                    consolPrinter.printLine("Podaj instruktora");
                    additionalOption = dataReader.getString();
                    schedule.printFilteredDanceClass(x -> x.getClassInstructor().toLowerCase().contains(additionalOption.toLowerCase()));
                    break;
                case DANCE_STYLE:
                    consolPrinter.printLine("Podaj styl tańca");
                    additionalOption = dataReader.getString();
                    schedule.printFilteredDanceClass(x -> x.getClassName().toLowerCase().contains(additionalOption.toLowerCase()));
                    break;
                case LEVEL_P:
                    schedule.printFilteredDanceClass(checkIfLevelP);
                    break;
                case LEVEL_S:
                    schedule.printFilteredDanceClass(checkIfLevelS);
                    break;
            }
        }while(optionFilter != OptionFilter.EXIT);
        //Schedule.showFilteredSchedule();*/

    }

    private void printDanceClass() {
        Schedule.showSchedule(Schedule.getDanceClassesMap());
    }

    private void downloadDanceClass() {
        WebScrape webScrape = new WebScrape();
        webScrape.ReadWeb();
        printer.printLine("Pobrano dane ze strony.");
    }

    private OptionMenu getOptionMenu() {
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

    private OptionFilter getOptionFilter() {
        boolean optionOK = true;
        OptionFilter optionMenu = null;
        while (optionOK){
            try {
                optionMenu = OptionFilter.createFromInt(dataReader.getInt());
                optionOK = false;
            }catch ( NoSuchOptionException e ){
                printer.printLine(e.getMessage());
            }catch ( InputMismatchException e ){
                printer.printLine("Wprowadzono wartość która nie jest liczbą");
            }
        }
        return optionMenu;
    }

    public void printOptionsMenu() {
        System.out.println("");
        System.out.println("****************************************************************");
        System.out.println("Dostępne opcje:");
        for (OptionMenu value : OptionMenu.values()) {
            System.out.println(value);
        }
        System.out.println("****************************************************************");
    }

    public void printOptionsFilter() {
        System.out.println("");
        System.out.println("****************************************************************");
        System.out.println("Dostępne opcje:");
        for (OptionFilter value : OptionFilter.values()) {
            System.out.println(value);
        }
        System.out.println("****************************************************************");
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