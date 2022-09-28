package pl.pragma.danceEvent.model;

import java.io.Serializable;
import java.util.*;

public class Schedule implements Serializable {

    private static Map<String, List<DanceClass>> danceClassesMap = new LinkedHashMap<>();

    public static void addDanceClass(String dayOfWeek, DanceClass danceClass){
        List<DanceClass> danceClassesList = danceClassesMap.getOrDefault(dayOfWeek, new ArrayList<>());
        danceClassesMap.putIfAbsent(dayOfWeek, danceClassesList);
        danceClassesList.add(danceClass);
    }

    public static Map<String, List<DanceClass>> getDanceClassesMap() {
        return danceClassesMap;
    }

    public static void showSchedule(){
        danceClassesMap.forEach( ( k, v ) -> {
                    System.out.println(k);
                    for (DanceClass danceClass : v) {
                        System.out.println(danceClass.toString());
                    }
                }
        );
    }
}
