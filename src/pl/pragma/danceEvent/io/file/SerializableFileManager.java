package pl.pragma.danceEvent.io.file;

import pl.pragma.danceEvent.exception.DataExportException;
import pl.pragma.danceEvent.exception.DataImportException;
import pl.pragma.danceEvent.model.Schedule;

import java.io.*;

public class SerializableFileManager implements FileManager{
    private static final String FILE_NAME = "Schedule.o";
    @Override
    public Schedule importData() {
        Schedule schedule = new Schedule();
        try (
                FileInputStream fis = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            schedule = (Schedule) ois.readObject();
            System.out.println("importData"+schedule.getDanceClassesMap().size() );
            return schedule;
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku: " + FILE_NAME + "  error message:" +e.getMessage());
        } catch (IOException e) {
            throw new DataExportException("Błąd odczytu danych z pliku: " + FILE_NAME + "  error message:" +e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new DataExportException("Nie odnaleziono klasy do zapisu. error message:" + e.getMessage() );
        }
    }

    @Override
    public void exportData(Schedule schedule) {
        try (
                FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            System.out.println("exportData"+schedule.getDanceClassesMap().size() );
            oos.writeObject(schedule);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku: "+ FILE_NAME +"  error message:"+ e.getMessage());
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku: " +FILE_NAME+ "  error message:"+ e.getMessage());
        }
    }
}




