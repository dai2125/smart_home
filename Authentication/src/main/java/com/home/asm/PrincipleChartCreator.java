package com.home.asm;

import org.jfree.chart.*;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PrincipleChartCreator extends JFrame {

    double nofMin = 0.0, nofMax = 10.0;
    double nomMin = 0.0, nomMax = 10.0;
    double wmcMin = 0.0, wmcMax = 50;
    double ncMin = 0.0, ncMax = 5.0;
    double ditMin = 0.0, ditMax = 5.0;
    double lcomMin = 0.0, lcomMax = 10.0;
    double yalcomMin = -50.0, yalcomMax = 100.0;
    double faninMin = 0.0, faninMax = 5.0;
    double fanoutMin = 0.0, fanoutMax = 5.0;
    String principle = "Creator";

    public PrincipleChartCreator() {

        createChart();
    }

    private void createChart() {

        Map<String, Double[]> ranges = new HashMap<>();
        ranges.put("NoF", new Double[]{nofMin, nofMax});
        ranges.put("NoM", new Double[]{nomMin, nomMax});
        ranges.put("WMC", new Double[]{wmcMin, wmcMax});
        ranges.put("NC", new Double[]{ncMin, ncMax});
        ranges.put("DIT", new Double[]{ditMin, ditMax});
        ranges.put("LCOM", new Double[]{lcomMin, lcomMax});
        ranges.put("FanIn", new Double[]{faninMin, faninMax});
        ranges.put("FanOut", new Double[]{fanoutMin, fanoutMax});
        ranges.put("Yalcom", new Double[]{yalcomMin, yalcomMax});

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();




        dataset.addValue(normalize("NoF",5.0), "Max", "NoF");
        dataset.addValue(normalize("NoM",5.0), "Max", "NoM");
        dataset.addValue(normalize("WMC",10.0), "Max", "WMC");
        dataset.addValue(normalize("NC",5.0), "Max", "NC");
        dataset.addValue(normalize("DIT",1.0), "Max", "DIT");
        dataset.addValue(normalize("LCOM",2.0), "Max", "LCOM");
        dataset.addValue(normalize("Yalcom",0.5 * 10), "Max", "Yalcom");
        dataset.addValue(normalize("FanIn",3.0), "Max", "FanIn");
        dataset.addValue(normalize("FanOut",3.0), "Max", "FanOut");


        //InspectedClass inspectedClass1 = CreatorPrinciple1And3Service.get("Calculator");

        dataset.addValue(normalize("NoF",2), "example", "NoF");
        dataset.addValue(normalize("NoM",2), "example", "NoM");
        dataset.addValue(normalize("WMC",2), "example", "WMC");
        dataset.addValue(normalize("NC",2), "example", "NC");
        dataset.addValue(normalize("DIT",2), "example", "DIT");
        dataset.addValue(normalize("LCOM",2.0), "example", "LCOM");
        dataset.addValue(normalize("Yalcom",2.0), "example", "Yalcom");
        dataset.addValue(normalize("FanIn",2), "example", "FanIn");
        dataset.addValue(normalize("FanOut",2), "example", "FanOut");


        SpiderWebPlotPatch o = new SpiderWebPlotPatch();
        o.setMetricRanges(ranges);
        SpiderWebPlot plot = o.getPlot(dataset);
        plot.setSeriesPaint(0, Color.GREEN);
        plot.setSeriesPaint(1, Color.BLUE);
        plot.setSeriesPaint(2, Color.MAGENTA);

        plot.setWebFilled(true);

        JFreeChart chart = new JFreeChart(principle, new Font(Font.SANS_SERIF, Font.BOLD, 22), plot, true);
        //chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(240, 240, 240));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.PINK);
        chartPanel.setPreferredSize(new Dimension(1200, 100));

        //JPanel infoPanel = createChartInfoPanel(inspectedClass);
        JPanel infoPanel = createHeatmapTablePanel();

        setLayout(new BorderLayout());

        //add(legendPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setBackground(Color.GREEN);
        setTitle("Principle Diagram");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

//        paramLegend.setPosition(RectangleEdge.BOTTOM);
//        chart.addSubtitle(paramLegend);
//
//
//        ChartPanel panel = new ChartPanel(chart);
//        setBackground(Color.BLUE);
//        setContentPane(panel);
    }

    private JPanel createHeatmapTablePanel() {
        //String[] columnNames = {"Metric", "Value"};
        JPanel chartInfoPanel = new JPanel();
        chartInfoPanel.setPreferredSize(new Dimension(1000, 380));
        chartInfoPanel.setLayout(new BorderLayout());
        chartInfoPanel.setBackground(Color.WHITE);
        String[] columnNames = { "Class", "NoUF", "NoF", "NoM", "LCOM", "Yalcom", "Cohesion LCOM", "Cohesion Yalcom" };
        Object[][] data = {
                {
                    "Example",
                        4,
                        7,
                        6,
                        7.0,
                        0.67,
                        "False",
                        "False",
                },
                {
                        "Example1",
                        0,
                        4,
                        4,
                        2.0,
                        0.50,
                        "False",
                        "True",
                },
                {
                        "Example2",
                        0,
                        2,
                        5,
                        2.0,
                        0.33,
                        "False",
                        "True",
                },
                {
                        "Example3",
                        0,
                        4,
                        5,
                        3.0,
                        0.60,
                        "False",
                        "False",
                },
                {
                        "Example4",
                        0,
                        4,
                        7,
                        5.0,
                        0.71,
                        "False",
                        "False",
                },
                {
                        "Example5",
                        0,
                        4,
                        7,
                        3.0,
                        0.43,
                        "False",
                        "True",
                },
                {
                        "Example6",
                        4,
                        8,
                        5,
                        7.0,
                        0.60,
                        "False",
                        "False",
                },
                {
                        "Example7",
                        0,
                        4,
                        9,
                        7.0,
                        0.78,
                        "False",
                        "False",
                },
                {
                        "Example8",
                        0,
                        4,
                        9,
                        6.0,
                        0.67,
                        "False",
                        "False",
                },
                {
                        "Example9",
                        0,
                        4,
                        9,
                        1.0,
                        0.00,
                        "True",
                        "True",
                },
                {
                        "Example10",
                        0,
                        2,
                        5,
                        2.0,
                        0.40,
                        "False",
                        "True",
                },
                {
                        "Example11",
                        0,
                        2,
                        5,
                        1.0,
                        0.00,
                        "True",
                        "True",
                },
                {
                        "Example12",
                        0,
                        0,
                        7,
                        1.0,
                        -1.0,
                        "True",
                        "False",
                },
                {
                        "Example13",
                        4,
                        7,
                        6,
                        7.0,
                        0.83,
                        "False",
                        "False",
                },
        };

        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (column >= 4 && column <= 5) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        c.setBackground(heatmapColor(column, v));
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
                if(column == 6 || column == 7) {
                    Object value = getValueAt(row, column);
                    System.out.println("value: "  + value);
                    if(value instanceof String) {
                        if(value.equals("True")) {
                            c.setBackground(new Color(110,255,96));
                        } else {
                            c.setBackground(new Color(241,111,39));
                        }
                    }
                }
                return c;
            }
        };

        table.setFont(new Font("Font.SANS_SERIF", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chartInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Metric Heatmap",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Font.SANS_SERIF", Font.BOLD, 14)
        ));
        chartInfoPanel.add(scrollPane, BorderLayout.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        chartInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                "Metric Information",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Font.SANS_SERIF", Font.BOLD, 14)
        ));

        chartInfoPanel.add(scrollPane, BorderLayout.CENTER);
        // Tabellenzellen zentrieren
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

// Spaltenüberschriften fett formatieren
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        return chartInfoPanel;
    }

    private Color heatmapColor(int columnNum, double value) {
        double[] max = getMaxValues();

        // System.out.println("...." + columnNum);

        if(columnNum < 1 || columnNum > max.length ) {
            return Color.WHITE;
        }

        if(value == 0.0 || (columnNum == 5 && value == 1.0)) {
            return new Color(110,255,96);
        } else if(value > max[columnNum]) {
            return new Color(241,111,39);
        } else if(value == -1.0) {
            return new Color(241, 111, 39);
        } else if(value == max[columnNum]) {
            return new Color(249,233,146);
        } else {
            return new Color(110,255,96);
        }
    }


    private double[] getMaxValues() {
        switch(principle) {
            case "Single Responsibilty Principle":
                return srpMax;
            case "Cohesion":
                return cohesionMax;
            case "Coopling":
                return cooplingMax;
            case "Creator":
                return creatorMax;
            case "Indirection":
                return indirectionMax;
            default:
                return srpMax;
        }
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBackground(Color.WHITE);

        JTextArea legendText = new JTextArea("Legend:\n1. Class 1\nClass 2\nMax Value");
        legendText.setFont(new Font("Arial", Font.PLAIN, 16));
        legendText.setBackground(Color.RED);
        legendText.setEditable(false);
        legendPanel.add(legendText);

        return legendPanel;
    }

    double normalize(String s, double value) {
        //System.out.print("s: " + s + ", value: " + value + ", ");
        switch (s) {
            case "NoF":     return clamp(value / nofMax);       // max 5
            case "NoM":     return clamp(value / nomMax);      // max 10
            case "WMC":     return clamp(value / wmcMax);      // max 50
            case "NC":      return clamp(value / ncMax);      // max 10
            case "DIT":     return clamp(value / ditMax);       // max 5
            case "LCOM":    return clamp(value / lcomMax);      // max 10
            case "FanIn":   return clamp(value / faninMax);      // max 10
            case "FanOut":  return clamp(value / fanoutMax);      // max 10
            case "Yalcom":  return clamp((value * 10) / (yalcomMax - 15.0)); // -1.0 → 0.0, 0.9 → 1.0 // TODO für die Darstellung - 15.0 irgendwo wird falsch skaliert
            //case "Yalcom":  return clamp((value + 1.0) / (0.9 + 1.0)); // -1.0 → 0.0, 0.9 → 1.0
            default:        return 0.0;
        }
    }

    double clamp(double v) {
        //System.out.println("v: " + v);
        return Math.max(0.0, Math.min(1.0, v));
    }




    double[] srpMax = {
            0.0,    // Dummy Value
            10.0,   // NoF
            5.0,   // NoM
            50.0,   // WMC
            5.0,    // NC
            5.0,    // DIT
            2.0,    // LCOM
            0.5,    // YALCOM
            5.0,    // FANIN
            3.0     // FANOUT
    };

    double[] cohesionMax = {
            0.0,    // Dummy Value
            10.0,   // NoF
            10.0,   // NoM
            50.0,   // WMC
            5.0,    // NC
            5.0,    // DIT
            2.0,    // LCOM
            0.5,    // YALCOM
            5.0,    // FANIN
            5.0     // FANOUT
    };

    double[] cooplingMax = {
            10.0,   // NoF
            10.0,   // NoM
            50.0,   // WMC
            5.0,    // NC
            5.0,    // DIT
            2.0,    // LCOM
            0.5,    // YALCOM
            5.0,    // FANIN
            5.0     // FANOUT
    };

    double[] creatorMax = {
            10.0,   // NoF
            10.0,   // NoM
            50.0,   // WMC
            5.0,    // NC
            1.1,    // DIT
            0.1,    // LCOM
            0.1,    // YALCOM
            5.0,    // FANIN
            5.0     // FANOUT
    };

    double[] indirectionMax = {
            10.0,   // NoF
            10.0,   // NoM
            50.0,   // WMC
            5.0,    // NC
            5.0,    // DIT
            2.0,    // LCOM
            0.5,    // YALCOM
            5.0,    // FANIN
            5.0     // FANOUT
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrincipleChartCreator example = new PrincipleChartCreator();
            example.setSize(1200, 1200);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
