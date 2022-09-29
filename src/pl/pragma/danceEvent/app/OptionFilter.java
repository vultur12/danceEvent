package pl.pragma.danceEvent.app;
import pl.pragma.danceEvent.exception.NoSuchOptionException;

public enum OptionFilter {
    EXIT (0, "Wyjście z filtrowania"),
    INSTRUCTOR (1, "Instruktor"),
    DANCE_STYLE(2, "Styl taneczny"),
    LEVEL_P(3, "Poziom zajęć P"),
    LEVEL_S(4, "Poziom zajęć S");

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
