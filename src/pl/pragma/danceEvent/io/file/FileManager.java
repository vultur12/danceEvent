package pl.pragma.danceEvent.io.file;

import pl.pragma.danceEvent.model.Schedule;

public interface FileManager {
    Schedule importData();
    void exportData(Schedule schedule);
}
