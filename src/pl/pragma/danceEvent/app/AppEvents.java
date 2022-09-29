package pl.pragma.danceEvent.app;

class AppEvents {
    public static final String APP_NAME = "DanceEvents";
    public static final String APP_VERSION = "1.2";

    public static void main(String[] args) {

        EventControl eventControl = new EventControl();
        eventControl.controlLoop();
        System.out.println(APP_NAME +" version:"+ APP_VERSION);
    }
}
