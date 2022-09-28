package pl.pragma.danceEvent.io;

import pl.pragma.danceEvent.model.DanceClass;

public class ConsolPrinter {

    public void printDanceClasses(DanceClass[] danceClasses){
        int countDnaceEvent = 0;
        for (DanceClass danceClass : danceClasses) {
            if ( danceClass instanceof DanceClass) {
                countDnaceEvent++;
                printLine(danceClass.toString());
            }
        }
        if ( countDnaceEvent == 0 ){
            printLine("Brak wydarzeń");
        }
    }

    public void printLine(String text){
        System.out.println(text);
    }

}
