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

public class PrinicpleChart extends JFrame {

    double nofMin = 0.0, nofMax = 10.0;
    double nomMin = 0.0, nomMax = 10.0;
    double wmcMin = 0.0, wmcMax = 50;
    double ncMin = 0.0, ncMax = 5.0;
    double ditMin = 0.0, ditMax = 5.0;
    double lcomMin = 0.0, lcomMax = 10.0;
    double yalcomMin = -50.0, yalcomMax = 100.0;
    double faninMin = 0.0, faninMax = 5.0;
    double fanoutMin = 0.0, fanoutMax = 5.0;
    String principle;
    InspectedClass inspectedClass;
    InspectedClass inspectedClass2;

    public PrinicpleChart(String principle, InspectedClass inspectedClass) {
        this.principle = principle;
        this.inspectedClass = inspectedClass;

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

        System.out.println("inspectedClass: " + inspectedClass.getName() + " : " + inspectedClass.getFanOut());


        double nof = (double) inspectedClass.getNumberOfFields();
        double nom = (double) inspectedClass.getNumberOfMethods();
        double wmc = (double) inspectedClass.getWmc();
        double nc = (double) inspectedClass.getNumberOfChildren();
        double dit = (double) inspectedClass.getDit();
        double fanin = (double) inspectedClass.getFanIn();
        double fanout = (double) inspectedClass.getFanOut();

        dataset.addValue(normalize("NoF",5.0), "Max", "NoF");
        dataset.addValue(normalize("NoM",5.0), "Max", "NoM");
        dataset.addValue(normalize("WMC",10.0), "Max", "WMC");
        dataset.addValue(normalize("NC",5.0), "Max", "NC");
        dataset.addValue(normalize("DIT",1.0), "Max", "DIT");
        dataset.addValue(normalize("LCOM",2.0), "Max", "LCOM");
        dataset.addValue(normalize("Yalcom",0.5 * 10), "Max", "Yalcom");
        dataset.addValue(normalize("FanIn",3.0), "Max", "FanIn");
        dataset.addValue(normalize("FanOut",3.0), "Max", "FanOut");

        dataset.addValue(normalize("NoF",nof), inspectedClass.getName(), "NoF");
        dataset.addValue(normalize("NoM",nom), inspectedClass.getName(), "NoM");
        dataset.addValue(normalize("WMC",wmc), inspectedClass.getName(), "WMC");
        dataset.addValue(normalize("NC",nc), inspectedClass.getName(), "NC");
        dataset.addValue(normalize("DIT",dit), inspectedClass.getName(), "DIT");
        dataset.addValue(normalize("LCOM",inspectedClass.getLcom4()), inspectedClass.getName(), "LCOM");
        dataset.addValue(normalize("Yalcom",inspectedClass.getYalcom() * 10), inspectedClass.getName(), "Yalcom");
        dataset.addValue(normalize("FanIn",fanin), inspectedClass.getName(), "FanIn");
        dataset.addValue(normalize("FanOut",fanout), inspectedClass.getName(), "FanOut");

        InspectedClass inspectedClass1 = CreatorPrinciple1And3Service.get("Resume2");

        dataset.addValue(normalize("NoF",inspectedClass1.getNumberOfFields()), inspectedClass1.getName(), "NoF");
        dataset.addValue(normalize("NoM",inspectedClass1.getNumberOfMethods()), inspectedClass1.getName(), "NoM");
        dataset.addValue(normalize("WMC",inspectedClass1.getWmc()), inspectedClass1.getName(), "WMC");
        dataset.addValue(normalize("NC",inspectedClass1.getNumberOfChildren()), inspectedClass1.getName(), "NC");
        dataset.addValue(normalize("DIT",inspectedClass1.getDit()), inspectedClass1.getName(), "DIT");
        dataset.addValue(normalize("LCOM",inspectedClass1.getLcom4()), inspectedClass1.getName(), "LCOM");
        dataset.addValue(normalize("Yalcom",inspectedClass1.getYalcom() * 10), inspectedClass1.getName(), "Yalcom");
        dataset.addValue(normalize("FanIn",inspectedClass1.getFanIn()), inspectedClass1.getName(), "FanIn");
        dataset.addValue(normalize("FanOut",inspectedClass1.getFanOut()), inspectedClass1.getName(), "FanOut");


        SpiderWebPlotPatch o = new SpiderWebPlotPatch();
        o.setMetricRanges(ranges);
        SpiderWebPlot plot = o.getPlot(dataset);
        plot.setSeriesPaint(0, Color.GREEN);
        plot.setSeriesPaint(1, Color.BLUE);
        plot.setSeriesPaint(2, Color.MAGENTA);

        inspectedClass.setColor(Color.BLUE);
        inspectedClass1.setColor(Color.MAGENTA);

        plot.setWebFilled(true);

        JFreeChart chart = new JFreeChart(principle + " Principle", new Font(Font.SANS_SERIF, Font.BOLD, 22), plot, true);
        //chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(240, 240, 240));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.PINK);
        chartPanel.setPreferredSize(new Dimension(1200, 500));

        //JPanel infoPanel = createChartInfoPanel(inspectedClass);
        JPanel infoPanel = createHeatmapTablePanel(inspectedClass, inspectedClass1);

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

    private JPanel createChartInfoPanel(InspectedClass inspectedClass) {
        JPanel chartInfoPanel = new JPanel();
        chartInfoPanel.setPreferredSize(new Dimension(1000, 200));
        chartInfoPanel.setLayout(new BorderLayout());
        chartInfoPanel.setBackground(Color.WHITE);
        String information = "Class:\tNom\tNoF\tWMC\tNC\tDIT\tLCOM\tYalcom\tFanIn\tFanOut\n";
        String classMetrics = inspectedClass.getName() + "\t" + inspectedClass.getNumberOfFields() + "\t" + inspectedClass.getNumberOfMethods() + "\t" + inspectedClass.getWmc() + "\t" + inspectedClass.getNumberOfChildren() + "\t" + inspectedClass.getDit() + "\t" + inspectedClass.getYalcom() + "\t" + inspectedClass.getLcom4() + "\t" + inspectedClass.getFanIn()  + "\t" +  inspectedClass.getFanOut();

        String[] columnNames = { "Class", "NoF", "NoM", "WMC", "NC", "DIT", "LCOM", "Yalcom", "FanIn", "FanOut" };
        Object[][] data = {
                {
                        inspectedClass.getName(),
                        inspectedClass.getNumberOfFields(),
                        inspectedClass.getNumberOfMethods(),
                        inspectedClass.getWmc(),
                        inspectedClass.getNumberOfChildren(),
                        inspectedClass.getDit(),
                        inspectedClass.getLcom4(),
                        inspectedClass.getYalcom(),
                        inspectedClass.getFanIn(),
                        inspectedClass.getFanOut()
                }
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Nur Spalte 0 ("Class") linksbündig
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        // Spaltenrenderer setzen
        for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        chartInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                "Metric Information",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Font.SANS_SERIF", Font.BOLD, 14)
        ));

        chartInfoPanel.add(scrollPane, BorderLayout.CENTER);
        return chartInfoPanel;
    }

    private JPanel createHeatmapTablePanel(InspectedClass inspectedClass) {
        //String[] columnNames = {"Metric", "Value"};
        JPanel chartInfoPanel = new JPanel();
        chartInfoPanel.setPreferredSize(new Dimension(1000, 200));
        chartInfoPanel.setLayout(new BorderLayout());
        chartInfoPanel.setBackground(Color.WHITE);
        String[] columnNames = { "Class", "NoF", "NoM", "WMC", "NC", "DIT", "LCOM", "Yalcom", "FanIn", "FanOut" };
        Object[][] data = {
                {
                        inspectedClass.getName(),
                        inspectedClass.getNumberOfFields(),
                        inspectedClass.getNumberOfMethods(),
                        inspectedClass.getWmc(),
                        inspectedClass.getNumberOfChildren(),
                        inspectedClass.getDit(),
                        inspectedClass.getLcom4(),
                        inspectedClass.getYalcom(),
                        inspectedClass.getFanIn(),
                        inspectedClass.getFanOut()
                }
        };

        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                System.out.println(row + " : " + column);
                if (column == 3) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        System.out.println(1 + " : " + value);
                        System.out.println(2 + " : " + v);
                        c.setBackground(heatmapColor(v));
                    }
                }
                if (column >= 1 && column <= 10) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        c.setBackground(heatmapColor(column, v));
                    }
                } else {
                    c.setBackground(Color.WHITE);
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
        return chartInfoPanel;
    }

    private JPanel createHeatmapTablePanel(InspectedClass inspectedClass, InspectedClass inspectedClass1) {
        JPanel chartInfoPanel = new JPanel();
        chartInfoPanel.setPreferredSize(new Dimension(1000, 200));
        chartInfoPanel.setLayout(new BorderLayout());
        chartInfoPanel.setBackground(Color.WHITE);
        String[] columnNames = { "Class", "NoF", "NoM", "WMC", "NC", "DIT", "LCOM", "Yalcom", "FanIn", "FanOut" };

        List<InspectedClass> inspectedClasses = Arrays.asList(inspectedClass, inspectedClass1);

        Object[][] data = {
                {
                        inspectedClass.getName(),
                        inspectedClass.getNumberOfFields(),
                        inspectedClass.getNumberOfMethods(),
                        inspectedClass.getWmc(),
                        inspectedClass.getNumberOfChildren(),
                        inspectedClass.getDit(),
                        inspectedClass.getLcom4(),
                        inspectedClass.getYalcom(),
                        inspectedClass.getFanIn(),
                        inspectedClass.getFanOut()
                },
                {
                        inspectedClass1.getName(),
                        inspectedClass1.getNumberOfFields(),
                        inspectedClass1.getNumberOfMethods(),
                        inspectedClass1.getWmc(),
                        inspectedClass1.getNumberOfChildren(),
                        inspectedClass1.getDit(),
                        inspectedClass1.getLcom4(),
                        inspectedClass1.getYalcom(),
                        inspectedClass1.getFanIn(),
                        inspectedClass1.getFanOut()
                }
        };

        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 3) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        InspectedClass ic = inspectedClasses.get(row);
                        c.setBackground(heatmapColor2(ic));
                    }
                } else if (column >= 1 && column <= 10) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        c.setBackground(heatmapColor(column, v));
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };

        table.setFont(new Font("Font.SANS_SERIF", Font.PLAIN, 16));
        table.setRowHeight(22);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 16));

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

        // Nur Spalte 0 ("Class") linksbündig
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus, int row, int column) {

                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);

                    label.setIcon(new Icon() {
                        private final int size = 10;

                        Color circleColor = inspectedClasses.get(row).getColor();
                        //private final Color circleColor = Color.RED;

                        @Override
                        public void paintIcon(Component c, Graphics g, int x, int y) {
                            g.setColor(circleColor);
                            g.fillOval(x, y, size, size);
                        }

                        @Override
                        public int getIconWidth() {
                            return size;
                        }

                        @Override
                        public int getIconHeight() {
                            return size;
                        }
                    });

                    label.setIconTextGap(8);
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    return label;
                }
            });
        }

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        chartInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                "Metric Information",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Font.SANS_SERIF", Font.BOLD, 20)
        ));

        chartInfoPanel.add(scrollPane, BorderLayout.CENTER);
        return chartInfoPanel;

    }

    // Beispielhafte Heatmap-Farbskala
    private Color heatmapColor(double value) {
        // Erwartete Werte: 0.0 (gut) bis 1.0 (schlecht)
        float normalized = (float) Math.max(0.0, Math.min(1.0, value));
        return new Color(1.0f - normalized, normalized, 0.0f); // grün → gelb → rot
    }

    private Color heatmapColor2(InspectedClass inspectedClass) {
        // inspectedClass.getWmc() > (inspectedClass.getNumberOfConstructors() + inspectedClass.getNumberOfMethods()) * 2
        double wmc = inspectedClass.getWmc();
        int noc = inspectedClass.getNumberOfConstructors();
        int nom = inspectedClass.getNumberOfMethods();

        System.out.println(1 + " wmc = " + wmc + ", noc = " + noc + ", nom = " + nom);

        if(wmc == 0) {
            return new Color(255,255,255);
        } else if(wmc > 14) {
            return new Color(241, 111, 39);
        }
        //else if(wmc > (noc + nom * 2)) {
        //    return new Color(249, 233, 146);
        //}
        else {
            return new Color(254,251,232);
        }
    }

    private Color heatmapColor(int columnNum, double value) {
        double[] max = getMaxValues();

        // System.out.println("...." + columnNum);

        if(columnNum < 1 || columnNum > max.length ) {
            return Color.WHITE;
        }

        if(value == 0.0 || (columnNum == 5 && value == 1.0)) {
            return new Color(255,255,255);
        } else if(value > max[columnNum]) {
            return new Color(241,111,39);
        } else if(value == max[columnNum]) {
            return new Color(249,233,146);
        } else {
            return new Color(254,251,232);
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
            5.0,    // DIT
            2.0,    // LCOM
            0.5,    // YALCOM
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
}
