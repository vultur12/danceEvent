package pl.pragma.danceEvent.model;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static void showSchedule(Map<String, List<DanceClass>> danceClassesMap){
        danceClassesMap.forEach( ( k, v ) -> {
                    System.out.println(k);
                    for (DanceClass danceClass : v) {
                        System.out.println(danceClass.toString());
                    }
                }
        );
    }

    public void printFilteredDanceClass( Predicate<DanceClass> predicate) {
/*        List<DanceClass> danceClasses = danceClassesMap.get("poniedziałek");
        danceClasses = danceClasses.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        danceClasses.forEach(x -> System.out.println(x.toCSV()));
        danceClassesMap.put("poniedziałek",danceClasses);*/


        Set<Map.Entry<String, List<DanceClass>>> entries = danceClassesMap.entrySet();
        HashMap<String, List<DanceClass>> danceClassesMapResult = (HashMap<String, List<DanceClass>>) entries.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        danceClassesMapResult.forEach( ( k, v ) -> {
             List<DanceClass> danceClasses = danceClassesMap.get(k);
             danceClasses = danceClasses.stream()
                     .filter(predicate)
                     .collect(Collectors.toList());
            //danceClasses.forEach(x -> System.out.println(x.toCSV()));
            danceClassesMapResult.put(k, danceClasses);
        });
        danceClassesMapResult.forEach( ( k, v ) -> {
                    System.out.println(k);
                    for (DanceClass danceClass : v) {
                        System.out.println(danceClass.toString());
                    }
                }
        );
    }
}
