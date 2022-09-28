package pl.pragma.danceEvent.app;

class AppEvents {
    public static final String APP_NAME = "DanceEvents";
    public static final String APP_VERSION = "0.2";

    public static void main(String[] args) {

/*        final String url = "https://salsaclasica.pl/grafik-szkola-tanca-warszawa-kursy/";
        Document document = null;
        try{
            document = Jsoup.connect(url).timeout(120 * 1000).get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(document);*/
        EventControl eventControl = new EventControl();
        eventControl.controlLoop();
        System.out.println(APP_NAME +" version:"+ APP_VERSION);
    }
}
