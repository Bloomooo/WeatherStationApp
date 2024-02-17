/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnexionHTTP;

/**
 *
 * @author apeyt
 */
public class ConnexionManager {

    public static String ville = "lyon";
    public static String lat = "45.75";
    public static String lon = "4.5833";
    public static String URL_Weather = "https://api.openweathermap.org/data/2.5/weather?q=" + ville
            + ",fr&units=metric&lang=fr&appid=c9ae9827454becf601746c0c930a7073";
    public static String URL_Pollution = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + lat
            + "&lon=" + lon + "&units=metric&lang=fr&appid=c9ae9827454becf601746c0c930a7073";
    private static ConnexionManager manager = null;
    private final Callback callWeather;
    private final Callback callPollution;

    private ConnexionManager(Callback callWeather, Callback callPollution) {
        this.callWeather = callWeather;
        this.callPollution = callPollution;
    }

    public static ConnexionManager getConnexionManager(Callback callW, Callback callP) { // Point d'entrée du singleton
                                                                                         // : une seule
        // instance possible
        if (manager == null) {
            manager = new ConnexionManager(callW, callP); // Appel du constructeur privé
        }
        return manager;
    }

    public void loadWeather() {
        ConnexionThread connexionThread = new ConnexionThread(callWeather, URL_Weather);
        connexionThread.start();
        try {
            connexionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadPollution() {
        ConnexionThread connexionThread = new ConnexionThread(callPollution, URL_Pollution);
        connexionThread.start();
        try {
            connexionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeCity(String city) {
        URL_Weather = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toLowerCase()
                + ",fr&units=metric&lang=fr&appid=c9ae9827454becf601746c0c930a7073";
    }

    public void changePollution(String lat, String lon) {
        URL_Pollution = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + lat
                + "&lon=" + lon + "&units=metric&lang=fr&appid=c9ae9827454becf601746c0c930a7073";
    }
}
