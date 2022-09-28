package pl.pragma.danceEvent.app;
import pl.pragma.danceEvent.exception.NoSuchOptionException;

public enum OptionFilter {
    INSTRUCTOR (0, "Instruktor"),
    DANCE_STYLE(1, "Styl taneczny"),
    LEVEL_P(2, "Poziom zajęć P"),
    LEVEL_S(3, "Poziom zajęć S"),
    DAY_OF_WEEK(4, "Dzień tygodnia");

    private int value;
    private String description;

    OptionFilter(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return value + " " + description;
    }

    static OptionFilter createFromInt(int option) throws NoSuchOptionException {
        try{
            return OptionFilter.values()[option];
        }catch (ArrayIndexOutOfBoundsException e){
            throw new NoSuchOptionException("Brak opcji o ID:"+option);
        }
    }
}
