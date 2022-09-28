package pl.pragma.danceEvent.model;

public class Instruktor {
    private String name;

    public Instruktor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String resultString = name.replace("By", "").trim().replace("," , " oraz");

        return resultString;
    }
}
