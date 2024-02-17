/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import ConnexionHTTP.Callback;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author apeyt
 */
public class WeatherReport implements Callback {
    String main, description, ville, country;
    double temp, temp_min, temp_max, humidite, pressure, sunset, sunrise;
    double lon, lat;
    ImageIcon icon;
    String url = "jdbc:sqlite:data/pollution.sqlite";
    DbManager bDbManager;

    public WeatherReport() throws SQLException {
        temp = 0.0;
        temp_min = 0.0;
        temp_max = 0.0;
        lon = 0.0;
        lat = 0.0;
        humidite = 0.0;
        pressure = 0.0;
        main = new String();
        description = new String();
        ville = new String();
        country = new String();
        icon = null;
        bDbManager = new DbManager(url);
    }

    @Override
    public String toString() {
        return "WeatherReport{" + "main=" + main + ", description=" + description + ", temp=" + temp + ", temp_min="
                + temp_min + ", temp_max=" + temp_max + ", lon=" + lon + ", lat=" + lat + '}';
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getVille() {
        return ville;
    }

    public double getHumidity() {
        return humidite;
    }

    public double getPressure() {
        return pressure;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public void onWorkDone(JSONObject JO) throws JSONException {
        try {
            JSONArray weather = (JSONArray) JO.getJSONArray("weather");
            main = weather.getJSONObject(0).getString("main");
            description = weather.getJSONObject(0).getString("description");
            String image = "https://openweathermap.org/img/wn/" + weather.getJSONObject(0).getString("icon")
                    + "@2x.png";
            try {
                ImageIcon originalIcon = new ImageIcon(new URL(image));
                int newWidth = 50;
                int newHeight = 50;
                // Redimensionnez l'image
                Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
                icon = new ImageIcon(scaledImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject mainJSON = JO.getJSONObject("main");
            temp_min = mainJSON.getDouble("temp_min");
            temp_max = mainJSON.getDouble("temp_max");
            temp = mainJSON.getDouble("temp");
            humidite = mainJSON.getDouble("humidity");
            pressure = mainJSON.getDouble("pressure");
            JSONObject coordJSON = JO.getJSONObject("coord");
            lat = coordJSON.getDouble("lat");
            lon = coordJSON.getDouble("lon");
            ville = JO.getString("name");

            JSONObject sysJSON = JO.getJSONObject("sys");
            country = sysJSON.getString("country");
            sunset = sysJSON.getDouble("sunset");
            sunrise = sysJSON.getDouble("sunrise");
            String ggez = bDbManager.convertToDate((int) sunrise);
            String ez = bDbManager.convertToDate((int) sunset);
            System.out.println("sunset : " + ez);
            System.out.println("sunrise : " + ggez);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
