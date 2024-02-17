package Controller;

import Model.DbManager;

public class ControllerPollution implements AbstractController {
    private DbManager dbManager;
    private java.util.List<String> listDate;
    private java.util.List<Integer> listAqi;

    public ControllerPollution(DbManager dbManager) {
        this.dbManager = dbManager;
        listDate = new java.util.ArrayList<String>();
        listAqi = new java.util.ArrayList<>();

    }

    @Override
    public void control() {
        dbManager.transformDtToDate();
        listDate = dbManager.getAllDateEntries();
        listAqi = dbManager.getAllAqi();
    }

    public java.util.List<String> getAllDate() {
        return this.listDate;
    }

    public java.util.List<Integer> getAllAqi() {
        return this.listAqi;
    }

    public java.util.List<Double> getAllCO() {
        return this.dbManager.getAllCO();
    }

    public java.util.List<Double> getAllNO() {
        return this.dbManager.getAllNO();
    }

    public java.util.List<Double> getAllSO2() {
        return this.dbManager.getAllSO2();
    }

    public java.util.List<Double> getAllPM2_5() {
        return this.dbManager.getAllPM2_5();
    }

    public java.util.List<Double> getAllPM10() {
        return this.dbManager.getAllPM10();
    }

    public java.util.List<Double> getAllNH3() {
        return this.dbManager.getAllNH3();
    }

}
