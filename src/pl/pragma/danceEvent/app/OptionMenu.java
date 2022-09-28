package pl.pragma.danceEvent.app;
import pl.pragma.danceEvent.exception.NoSuchOptionException;

public enum OptionMenu {
    EXIT (0, "Wyjście z programu"),
    GET_DANCE_CLASSES_FROM_WEB(1, "Wczytaj zajęcia ze strony"),
    GET_DANCE_CLASSES(2, "Dodaj własne zajęcia"),
    PRINT_DANCE_CLASSES(3, "Wypisanie zajęć tanecznych"),
    FILTER_DANCE_CLASSES(4, "Filtruj zajęcia taneczne");

    private int value;
    private String description;

    OptionMenu(int value, String description) {
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

    static OptionMenu createFromInt(int option) throws NoSuchOptionException {
        try{
            return OptionMenu.values()[option];
        }catch (ArrayIndexOutOfBoundsException e){
            throw new NoSuchOptionException("Brak opcji o ID:"+option);
        }
    }
}
