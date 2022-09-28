package pl.pragma.danceEvent.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.pragma.danceEvent.model.DanceClass;
import pl.pragma.danceEvent.model.Instruktor;
import pl.pragma.danceEvent.model.Schedule;

public class WebScrape {
    private final String DIV_WHOLE_DAY      = "div.fitplan-planning-day";
    private final String DIV_WHOLE_DAY_NAME = "div.fitplan-planning-title";
    private final String DIV_SINGLE_CLASS   = "div.fitplan-planning-item";
    public void ReadWeb() {
        Schedule.getDanceClassesMap().clear();
        final String url = "https://salsaclasica.pl/grafik-szkola-tanca-warszawa-kursy/";
        Document document = null;

        document = getDocumentSalsaClasica(url, document);

        for( Element oneDayClasses : document.select(DIV_WHOLE_DAY) ){                               // Pobranie całego dnia zajęć
            Elements dayName = oneDayClasses.select(DIV_WHOLE_DAY_NAME);                             // Dane jednego dnia tygodnia
            String dayNameString = dayName.text();                                                   //dzień tygodnia
            Elements oneClass = oneDayClasses.select(DIV_SINGLE_CLASS);                              // Pobranie pojedyńczych zajęć
            for (Element danceClass : oneClass) {
                DanceClass newDanceClass = createDanceClass(danceClass);
                Schedule.addDanceClass(dayNameString, newDanceClass);
            }
        }
    }

    private DanceClass createDanceClass( Element danceClass) {
        String danceClassName = danceClass.select("p.fitplan-planning-item-title").text();// Nazwa zajęć
        String startTime = danceClass.select("span.fitplan-planning-modal-hour-start").text();// Godzina rozpoczęcia
        String stopTime =      danceClass.select("span.fitplan-planning-modal-hour-finish").text();    // Godzina zagończenia
        String instruktor = danceClass.select("div.fitplan-planning-modal-coach").text();          // instruktor
        return new DanceClass("Salsa Clasica",
                danceClassName,
                new Instruktor(instruktor),
                startTime,
                stopTime) ;
    }

    private Document getDocumentSalsaClasica(String url, Document document) {
        try{
            document = Jsoup.connect(url).timeout(60 * 1000).get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return document;
    }
}




/*
*
*         document = getDocumentSalsaClasica(url, document);

        for( Element oneDay : document.select("div.fitplan-planning-day") ){                             // Pobranie całego dnia zajęć
            Elements dayName = oneDay.select("div.fitplan-planning-title");                              // Dzień tygodnia
            System.out.println(dayName.text());
            Elements item = oneDay.select("div.fitplan-planning-item");                                  // Pobranie pojedyńczych zajęć
            for (Element element : item) {
                System.out.println(element.select("p.fitplan-planning-item-title").text());              // Nazwa zajęć
                System.out.println(element.select("span.fitplan-planning-modal-hour-start").text());     // Godzina rozpoczęcia
                System.out.println(element.select("span.fitplan-planning-modal-hour-finish").text());    // Godzina zagończenia
                String instruktor = element.select("div.fitplan-planning-modal-coach").text();          // instruktor
                if (!instruktor.equals("")) System.out.println(element.select("div.fitplan-planning-modal-coach").text());
                else System.out.println("dupa");
            }
        }
*
* */