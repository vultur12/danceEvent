package pl.pragma.danceEvent.io;

import pl.pragma.danceEvent.model.DanceClass;
import pl.pragma.danceEvent.model.Instruktor;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolPrinter consolPrinter;

    public DataReader(ConsolPrinter consolPrinter) {
        this.consolPrinter = consolPrinter;
    }

    public void closeScanner(){
        sc.close();
    }

    public DanceClass readAndCreateDanceClass() {
        consolPrinter.printLine("schoolName: ");
        String schoolName = sc.nextLine();
        consolPrinter.printLine("className: ");
        String className = sc.nextLine();
        consolPrinter.printLine("classInstructor: ");
        String classInstructor = sc.nextLine();
        consolPrinter.printLine("classStartHour: ");
        String classStartHour = sc.nextLine();
        consolPrinter.printLine("classEndHour: ");
        String classEndHour = sc.nextLine();

        return new DanceClass( schoolName, className, new Instruktor(classInstructor), classStartHour, classEndHour);
    }

    public int getInt(){
        try {
            return sc.nextInt();
        }finally {
            sc.nextLine();
        }
    }

    public String getString(){
        return sc.nextLine();
    }
}
