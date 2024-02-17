/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import Controller.ControllerPollution;
import Model.DbManager;
import Observer.Observable;

/**
 *
 * @author apeyt
 */
public class ViewPollution extends JPanel implements Observable {

    ChartPanel chartPanel;
    ControllerPollution cp;
    DbManager dbManager;

    public ViewPollution(DbManager dbManager, ControllerPollution cp) {
        initChart(dbManager, cp);
        this.dbManager = dbManager;
        validate();
        repaint();
    }

    private void initChart(DbManager dbManager, ControllerPollution cp) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Pollution chart",
                "Date",
                "Air Quality Indice",
                createDataset(dbManager, cp),
                PlotOrientation.VERTICAL,
                true, true, false);

        chartPanel = new ChartPanel(lineChart);

        this.add(chartPanel);

    }

    @Override
    public Dimension getPreferredSize() {

        Dimension dim = this.size();
        chartPanel.setPreferredSize(new Dimension(800, 600));
        return new Dimension(800, 600);
    }

    private CategoryDataset createDataset(DbManager dbManager, ControllerPollution cp) {

        return loadData(dbManager, cp);
    }

    private DefaultCategoryDataset loadData(DbManager dbManager, ControllerPollution cp) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        java.util.List<String> dates = cp.getAllDate();
        java.util.List<Integer> aqiValues = cp.getAllAqi();
        java.util.List<Double> coValues = cp.getAllCO();
        java.util.List<Double> noValues = cp.getAllNO();
        java.util.List<Double> so2Values = cp.getAllSO2();
        java.util.List<Double> pm2_5Values = cp.getAllPM2_5();
        java.util.List<Double> pm10Values = cp.getAllPM10();
        java.util.List<Double> nh3Values = cp.getAllNH3();

        if (dates.size() == aqiValues.size()) {
            if (!aqiValues.isEmpty()) {
                for (int i = 0; i < dates.size(); i++) {
                    String date = dates.get(i);
                    int aqi = aqiValues.get(i);
                    dataset.addValue(aqi, "AQI", date);
                    dataset.addValue(noValues.get(i), "NO", date);
                    dataset.addValue(so2Values.get(i), "SO2", date);
                    dataset.addValue(pm2_5Values.get(i), "PM2.5", date);
                    dataset.addValue(pm10Values.get(i), "PM10", date);
                    dataset.addValue(nh3Values.get(i), "NH3", date);
                }
            } else {
                System.out.println("ez");
            }
        }

        return dataset;
    }

    @Override
    public void update(ControllerPollution caP) {
        DefaultCategoryDataset newDataset = loadData(dbManager, caP);
        JFreeChart chart = chartPanel.getChart();
        chart.getCategoryPlot().setDataset(newDataset);

        chartPanel.repaint();
    }

}
