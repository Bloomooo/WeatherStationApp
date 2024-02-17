/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import ConnexionHTTP.Callback;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author apeyt
 */
public class DbManager implements Callback {
    Connection con;
    // https://blog.paumard.org/cours/jdbc/chap02-apercu-exemple.html
    // https://learn.microsoft.com/fr-fr/sql/connect/jdbc/step-3-proof-of-concept-connecting-to-sql-using-java?view=sql-server-ver16

    /**
     * @throws java.sql.SQLException
     * @brief Constructor
     *
     *        Constructor sets up connection with db and opens it
     * @param url - absolute path to db file
     */
    public DbManager(String url) throws SQLException {
        con = DriverManager.getConnection(url);
    }

    /**
     * @brief Test if the connexion is open or close
     * @return
     */
    public Boolean isOpen() {
        return con != null;
    }

    /**
     * @brief Creates a new 'pollution' table if it doesn't already exist
     * @return true - 'pollution' table created successfully, false - table not
     *         created
     */
    public Boolean createTable() {
        String query = "CREATE TABLE IF NOT EXISTS pollution(id INTEGER PRIMARY KEY, dt INTEGER, aqi INTEGER, co INTEGER, no INTEGER, so2 INTEGER, pm2_5 INTEGER, pm10 INTEGER, nh3 INTEGER);";
        return executeUpdate(query);
    }

    /**
     * @brief Creates a new 'date' table if it doesn't already exist
     * @return true - 'date' table created successfully, false - table not created
     */
    public Boolean createDateTable() {
        String query = "CREATE TABLE IF NOT EXISTS date(id INTEGER PRIMARY KEY, date_string TEXT);";
        return executeUpdate(query);
    }

    /**
     * @brief Add date string to 'date' table
     * @param dateString - date in string format (e.g., "MM-dd-yyyy")
     * @return true - date added successfully, false - date not added
     */
    public Boolean addDate(String dateString) {
        String query = "INSERT INTO date (date_string) VALUES ('" + dateString + "')";
        return executeUpdate(query);
    }

    /**
     * @brief Add data to db
     * @param dt  - date time
     * @param aqi - air quality indice
     * @return true - data added successfully, false - data not added
     */
    public Boolean addData(int dt, int aqi, double co, double no, double so2, double pm2_5, double pm10, double nh3) {
        if (!entryExists(dt)) {
            String query = "INSERT INTO pollution (dt,aqi, co, no, so2, pm2_5, pm10, nh3) VALUES (" + dt + "," + aqi
                    + "," + co + "," + no + "," + so2 + "," + pm2_5 + "," + pm10 + "," + nh3 + ")";
            return executeUpdate(query);
        }

        return false;
    }

    /**
     * @brief Remove data of dt "dt" from db
     * @param dt - dt of data to remove.
     * @return true - data removed successfully, false - data not removed
     */
    public Boolean removeData(int dt) {
        String query = "DELETE FROM pollution WHERE dt = " + dt;
        return executeUpdate(query);
    }

    /**
     * @brief Print values of all data in db
     */
    public void printAllData() {
        String query = "SELECT * FROM pollution";
        try (Statement statement = con.createStatement();) {

            // Create and execute a SELECT SQL statement.
            ResultSet resultSet = statement.executeQuery(query);

            // Print results from select statement
            while (resultSet.next()) {
                int dt = resultSet.getInt("dt");
                int aqi = resultSet.getInt("aqi");

                System.out.println("===" + dt + " " + aqi);
            }
        } catch (SQLException e) {
            System.out.println("Error : printAllData()" + e.getMessage());
        }
    }

    /**
     * @brief Remove all date strings from the 'date' table
     * @return true - all dates removed successfully, false - not removed
     */
    public Boolean removeAllDates() {
        String query = "DELETE FROM date";
        return executeUpdate(query);
    }

    public List<String> getAllDateEntries() {
        List<String> dateEntries = new ArrayList<>();

        String query = "SELECT date_string FROM date";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String dateString = resultSet.getString("date_string");
                dateEntries.add(dateString);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllDateEntries() " + e.getMessage());
        }

        return dateEntries;
    }

    public List<Integer> getAllAqi() {
        List<Integer> aqi = new ArrayList<>();

        String query = "SELECT aqi FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int aqiez = resultSet.getInt("aqi");
                aqi.add(aqiez);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllaqi() " + e.getMessage());
        }

        return aqi;
    }

    /**
     * @brief Obtient une liste de valeurs CO à partir de la base de données
     * @return Une liste de valeurs CO
     */
    public List<Double> getAllCO() {
        List<Double> coValues = new ArrayList<>();

        String query = "SELECT co FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double co = resultSet.getDouble("co");
                coValues.add(co);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllCO() " + e.getMessage());
        }

        return coValues;
    }

    /**
     * @brief Obtient une liste de valeurs NO à partir de la base de données
     * @return Une liste de valeurs NO
     */
    public List<Double> getAllNO() {
        List<Double> noValues = new ArrayList<>();

        String query = "SELECT no FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double no = resultSet.getDouble("no");
                noValues.add(no);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllNO() " + e.getMessage());
        }

        return noValues;
    }

    /**
     * @brief Obtient une liste de valeurs SO2 à partir de la base de données
     * @return Une liste de valeurs SO2
     */
    public List<Double> getAllSO2() {
        List<Double> so2Values = new ArrayList<>();

        String query = "SELECT so2 FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double so2 = resultSet.getDouble("so2");
                so2Values.add(so2);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllSO2() " + e.getMessage());
        }

        return so2Values;
    }

    /**
     * @brief Obtient une liste de valeurs PM2.5 à partir de la base de données
     * @return Une liste de valeurs PM2.5
     */
    public List<Double> getAllPM2_5() {
        List<Double> pm2_5Values = new ArrayList<>();

        String query = "SELECT pm2_5 FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double pm2_5 = resultSet.getDouble("pm2_5");
                pm2_5Values.add(pm2_5);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllPM2_5() " + e.getMessage());
        }

        return pm2_5Values;
    }

    /**
     * @brief Obtient une liste de valeurs PM10 à partir de la base de données
     * @return Une liste de valeurs PM10
     */
    public List<Double> getAllPM10() {
        List<Double> pm10Values = new ArrayList<>();

        String query = "SELECT pm10 FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double pm10 = resultSet.getDouble("pm10");
                pm10Values.add(pm10);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllPM10() " + e.getMessage());
        }

        return pm10Values;
    }

    /**
     * @brief Obtient une liste de valeurs NH3 à partir de la base de données
     * @return Une liste de valeurs NH3
     */
    public List<Double> getAllNH3() {
        List<Double> nh3Values = new ArrayList<>();

        String query = "SELECT nh3 FROM pollution";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                double nh3 = resultSet.getDouble("nh3");
                nh3Values.add(nh3);
            }
        } catch (SQLException e) {
            System.out.println("Error: getAllNH3() " + e.getMessage());
        }

        return nh3Values;
    }

    /**
     * @brief Check if data of dt "dt" exists in db
     * @param dt - dt of data to to check.
     * @return true - data exists, false - data does not exist
     */
    public Boolean entryExists(int dt) {
        Boolean exists = false;

        String query = "SELECT dt FROM pollution WHERE dt = " + dt;
        try (Statement statement = con.createStatement();) {

            // Create and execute a SELECT SQL statement.
            ResultSet resultSet = statement.executeQuery(query);

            // Print results from select statement
            exists = resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error : entryExists()");
        }

        return exists;
    }

    /**
     * @brief Remove all data from db
     * @return true - all data removed successfully, false - not removed
     */
    public Boolean removeAllData() {
        String query = "DELETE FROM pollution";
        return executeUpdate(query);
    }

    /**
     * @brief Generic function to execute a Query to update the data
     * @return true - all data modified successfully, false - not modified
     */
    private Boolean executeUpdate(String query) {
        Boolean success = false;

        try (Statement statement = con.createStatement();) {

            // Create and execute a SELECT SQL statement.
            int number = statement.executeUpdate(query);

            if (number > 0)
                success = true;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return success;
    }

    public void transformDtToDate() {
        removeAllDates();
        String query = "SELECT dt FROM pollution"; // Utilisez DISTINCT pour obtenir des valeurs uniques
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int dt = resultSet.getInt("dt");
                addDate(convertToDate(dt));
            }
        } catch (SQLException e) {
            System.out.println("Error : transformDtToDate()" + e.getMessage());
        }
    }

    public String convertToDate(int dt) {
        long epoch = ((long) dt) * 1000;
        Date date = new Date(epoch);
        String pattern = "HH";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String timeString = simpleDateFormat.format(date);
        return timeString;
    }

    @Override
    public void onWorkDone(JSONObject JO) throws JSONException {
        removeAllData();
        JSONArray pollution = JO.getJSONArray("list");

        for (int i = 0; i < pollution.length(); i++) {
            JSONObject listJSON = pollution.getJSONObject(i);
            JSONObject componentsJsonObject = listJSON.getJSONObject("components");
            Double co = componentsJsonObject.getDouble("co");
            Double no = componentsJsonObject.getDouble("no");
            Double no2 = componentsJsonObject.getDouble("so2");
            Double pm2_5 = componentsJsonObject.getDouble("pm2_5");
            Double pm10 = componentsJsonObject.getDouble("pm10");
            Double nh3 = componentsJsonObject.getDouble("nh3");

            if (listJSON.has("dt")) {
                int dt = listJSON.getInt("dt");
                JSONObject mainJSON = listJSON.getJSONObject("main");

                if (mainJSON.has("aqi")) {
                    int aqi = mainJSON.getInt("aqi");
                    addData(dt, aqi, co, no, no2, pm2_5, pm10, nh3);
                }
            }
        }

    }

}
