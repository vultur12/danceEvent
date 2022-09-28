package pl.pragma.danceEvent.model;

import java.io.Serializable;
import java.util.Objects;

public class DanceClass implements Serializable {

    private String schoolName;
    private String className;
    private Instruktor classInstructor;
    private String classStartHour;
    private String classEndHour;


    public DanceClass(String schoolName, String className, Instruktor classInstructor, String classStartHour, String classEndHour) {
        this.schoolName = schoolName;
        this.className = className;
        this.classInstructor = classInstructor;
        this.classStartHour = classStartHour;
        this.classEndHour = classEndHour;
    }

    @Override
    public String toString() {
        return  "  Nazwa zajęć: '" + className + '\'' +
                (classInstructor.toString().isEmpty() ? null : ", Instruktor: " + classInstructor) +
                ", Godzina rozpoczęcia: " + classStartHour +
                ", Godzina zakończenia: " + classEndHour;
    }

    public String toCSV(){
        return className        + ";" +
               classInstructor  + ";" +
               classStartHour   + ";" +
               classEndHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DanceClass that = (DanceClass) o;
        return Objects.equals(schoolName, that.schoolName) && Objects.equals(className, that.className) && Objects.equals(classInstructor, that.classInstructor) && Objects.equals(classStartHour, that.classStartHour) && Objects.equals(classEndHour, that.classEndHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolName, className, classInstructor, classStartHour, classEndHour);
    }
}
