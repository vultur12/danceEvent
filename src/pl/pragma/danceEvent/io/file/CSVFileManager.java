package pl.pragma.danceEvent.io.file;

import pl.pragma.danceEvent.exception.DataExportException;
import pl.pragma.danceEvent.exception.DataImportException;
import pl.pragma.danceEvent.model.DanceClass;
import pl.pragma.danceEvent.model.Instruktor;
import pl.pragma.danceEvent.model.Schedule;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CSVFileManager implements FileManager{
    private static final String FILE_NAME = "Schedule.csv";
    @Override
    public Schedule importData() {
        Schedule schedule = new Schedule();
        try (
                var fileReader = new FileReader(FILE_NAME);
                var bufferedReader = new BufferedReader(fileReader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] split = line.split(";");
                Schedule.addDanceClass(split[0], new DanceClass( "Salsa Clasica", split[1], new Instruktor(split[2]), split[3], split[4]));
            }
            
            return schedule;
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku: " + FILE_NAME + "  error message:" +e.getMessage());
        } catch (IOException e) {
            throw new DataExportException("Błąd odczytu danych z pliku: " + FILE_NAME + "  error message:" +e.getMessage());
        }
    }

    @Override
    public void exportData(Schedule schedule) {
        try (
                var fileWriter = new FileWriter(FILE_NAME);
                var bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            Iterator<Map.Entry<String, List<DanceClass>>> iterator = schedule.getDanceClassesMap().entrySet().iterator();
            StringBuilder stringResult = new StringBuilder();
            while (iterator.hasNext()){
                Map.Entry<String, List<DanceClass>> next = iterator.next();
                for (DanceClass danceClass : next.getValue()) {
                    stringResult = stringResult.append(next.getKey());
                    stringResult = stringResult.append("; ");

                    stringResult = stringResult.append(danceClass.toCSV());
                    bufferedWriter.write( stringResult.toString() );
                    bufferedWriter.newLine();
                    stringResult.setLength(0);
                }
                stringResult.setLength(0);
            }
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku: "+FILE_NAME+"  error message:"+ e.getMessage());
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku: "+FILE_NAME+"  error message:"+ e.getMessage());
        }
    }
}




