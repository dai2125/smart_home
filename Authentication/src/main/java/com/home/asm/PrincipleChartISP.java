package com.home.asm;

import org.jfree.chart.*;
import org.jfree.chart.plot.SpiderWebPlot;
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

public class PrincipleChartISP extends JFrame {

    /**
     *  isInterface
     *  number of methods
     *  number of interface methods
     *  number of children
     *  depth of inheritance tree
     *  LCOM
     *  Yalcom
     *  unsupported Operation exception
     *  return type null
     *  method body smaller than 100
     */
    
    double noIMMin = 0.0, noIMMax = 2.0;
    double nomMin = 0.0, nomMax = 2.0;
    double isInterfaceFalse = 0.0, isInterfaceTrue = 1.0;
    double ncMin = 0.0, ncMax = 5.0;
    double ditMin = 0.0, ditMax = 5.0;
    double lcomMin = 0.0, lcomMax = 10.0;
    double yalcomMin = -50.0, yalcomMax = 100.0;
    double unsupporteExceptionsMin = 0.0, unsupporteExceptionsMax = 5.0;
    double returnTypeIsNullMin = 0.0, returnTypeIsNullMax = 2.0;
    double methodBodyLengthMin = 0.0, methodBodyLengthMax = 2.0;
    Color[] colors = new Color[] { new Color(1, 31, 75), new Color(3, 57, 108), new Color(0,91, 150), new Color(100, 151, 177), new Color(179, 205, 224), new Color(228, 237, 242), new Color(205, 226,238), new Color(187, 220, 240), new Color(168, 218, 249), new Color(146, 210, 249)};

    String principle;
    DefaultCategoryDataset dataset;

    List<InspectedClass> inspectedClassList;

    public PrincipleChartISP(String principle, List<InspectedClass> inspectedClassList) {
        this.principle = principle;
        this.inspectedClassList = inspectedClassList;

        dataset = new DefaultCategoryDataset();
        createChart();
    }

    private void createChart() {

        Map<String, Double[]> ranges = new HashMap<>();
        ranges.put("NoF", new Double[]{noIMMin, noIMMax});
        ranges.put("NoM", new Double[]{nomMin, nomMax});
        ranges.put("WMC", new Double[]{isInterfaceFalse, isInterfaceTrue});
        ranges.put("NC", new Double[]{ncMin, ncMax});
        ranges.put("DIT", new Double[]{ditMin, ditMax});
        ranges.put("LCOM", new Double[]{lcomMin, lcomMax});
        ranges.put("FanIn", new Double[]{unsupporteExceptionsMin, unsupporteExceptionsMax});
        ranges.put("FanOut", new Double[]{returnTypeIsNullMin, returnTypeIsNullMax});
        ranges.put("Yalcom", new Double[]{yalcomMin, yalcomMax});
        ranges.put("MethodBodyLength", new Double[]{methodBodyLengthMin, methodBodyLengthMax});

        dataset.addValue(normalize("NoIM", 1.0), "Max", "NoIM");
        dataset.addValue(normalize("NoM", 1.0), "Max", "NoM");
        dataset.addValue(normalize("returnsNull", 10.0), "Max", "returnsNull");
        dataset.addValue(normalize("NC", 5.0), "Max", "NC");
        dataset.addValue(normalize("DIT", 1.0), "Max", "DIT");
        dataset.addValue(normalize("LCOM", 2.0), "Max", "LCOM");
        dataset.addValue(normalize("Yalcom", 0.5 * 10), "Max", "Yalcom");
        dataset.addValue(normalize("unsupportedOperationException", 3.0), "Max", "unsupportedOperationException");
        dataset.addValue(normalize("bodyLengthLessThanOneHundred", 3.0), "Max", "bodyLengthLessThanOneHundred");



        initDataset();


        SpiderWebPlotPatch o = new SpiderWebPlotPatch();
        o.setMetricRanges(ranges);
        SpiderWebPlot plot = o.getPlot(dataset);

        plot.setSeriesPaint(0, Color.GREEN);

        for(int i = 0; i < inspectedClassList.size(); i++) {
            plot.setSeriesPaint(i + 1, colors[i]);
            inspectedClassList.get(i).setColor(colors[i]);
        }

        plot.setWebFilled(true);

        JFreeChart chart = new JFreeChart(principle + " Principle", new Font(Font.SANS_SERIF, Font.BOLD, 22), plot, true);
        //chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(240, 240, 240));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.PINK);
        chartPanel.setPreferredSize(new Dimension(1200, 500));

        //JPanel infoPanel = createChartInfoPanel(inspectedClass);
        JPanel infoPanel = createHeatmapTablePanel(inspectedClassList);

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

    private JPanel createHeatmapTablePanel(List<InspectedClass> inspectedClassList) {
        JPanel chartInfoPanel = new JPanel();
        chartInfoPanel.setPreferredSize(new Dimension(1000, 200));
        chartInfoPanel.setLayout(new BorderLayout());
        chartInfoPanel.setBackground(Color.WHITE);


        String[] columnNames = { "Class", "NoIM", "NoM", "Return Null", "NC", "DIT", "LCOM", "Yalcom", "Unsupported", "Body Length" };

        Object[][] data = new Object[inspectedClassList.size()][columnNames.length];

        for(int i = 0; i < inspectedClassList.size(); i++) {
            for(int j = 0; j < columnNames.length; j++) {
                data[i][0] = inspectedClassList.get(i).getName();
                data[i][1] = inspectedClassList.get(i).getInterfaceMethodList().size();
                data[i][2] = inspectedClassList.get(i).getNumberOfMethods();
                data[i][3] = inspectedClassList.get(i).getReturnsNull();
                data[i][4] = inspectedClassList.get(i).getNumberOfChildren();
                data[i][5] = inspectedClassList.get(i).getDit();
                data[i][6] = inspectedClassList.get(i).getLcom4();
                data[i][7] = inspectedClassList.get(i).getYalcom();
                data[i][8] = inspectedClassList.get(i).getUnsupportedExceptions();
                data[i][9] = inspectedClassList.get(i).getBodyLengthLessThanOneHundred();
            }
        }


        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 3) {
                    Object value = getValueAt(row, column);
                    if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        InspectedClass ic = (InspectedClass) inspectedClassList.get(row);
                        // InspectedClass ic = inspectedClasses.get(row);
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

                        Color circleColor = inspectedClassList.get(row).getColor();
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
            case "NoM":     return clamp(value / nomMax);      // max 10
            case "isInterface": if(value == 0) {
                                    return clamp(value / isInterfaceTrue);      // max 50
                                } else {
                                    return clamp(2 / isInterfaceTrue);
                                }
            case "NC":      return clamp(value / ncMax);      // max 10
            case "DIT":     return clamp(value / ditMax);       // max 5
            case "LCOM":    return clamp(value / lcomMax);      // max 10
            case "unsupportedOperationException":   if(value < 1.0) {
                                                        return clamp(1 / unsupporteExceptionsMax);      // max 10
                                                    } else {
                                                        return clamp(2 / unsupporteExceptionsMax);
                                                    }
            case "returnsNull":  if(value < 1.0) return clamp(1 / returnTypeIsNullMax); //return clamp(value / returnTypeIsNullMax);      // max 10
            case "Yalcom":  return clamp((value * 10) / (yalcomMax - 15.0)); // -1.0 → 0.0, 0.9 → 1.0 // TODO für die Darstellung - 15.0 irgendwo wird falsch skaliert
            case "bodyLengthLessThanOneHundred":    if(value < 1.0) {
                                                        return clamp(1 / methodBodyLengthMax);
                                                    } else {
                                                        return clamp(2 / methodBodyLengthMin);
                                                    }
            default:        return 0.0;
        }
    }

    double normalize(String s, double value1, double value2) {
        //System.out.print("s: " + s + ", value: " + value + ", ");
        switch (s) {
            case "NoIM":    if(value1 == value2) {
                                return clamp(1 / noIMMax); //return clamp(value / noIMMax);       // max 5
                            } else {
                                return clamp(2 / noIMMax);
                            }
            default:        return 0.0;
        }
    }

    double clamp(double v) {
        //System.out.println("v: " + v);
        return Math.max(0.0, Math.min(1.0, v));
    }

    void initDataset() {

        for(int i = 0; i < inspectedClassList.size(); i++) {

            /**
             *  isInterface
             *  number of methods
             *  number of interface methods
             *  number of children
             *  depth of inheritance tree
             *  LCOM
             *  Yalcom
             *  unsupported Operation exception
             *  return type null
             *  method body smaller than 100
             */

            double noim = (double) inspectedClassList.get(i).getInterfaceMethodList().size();
            double nom = (double) inspectedClassList.get(i).getNumberOfMethods();
            double nc = (double) inspectedClassList.get(i).getNumberOfChildren();
            double dit = (double) inspectedClassList.get(i).getDit();
            double iFace = 0.0;

            if(inspectedClassList.get(i).getIsInterface()) {
                iFace = 1.0;
            }

            double yalcom = (double) inspectedClassList.get(i).getYalcom();
            double returnsNull = (double) inspectedClassList.get(i).getReturnsNull();
            double unsupportedOperationException = (double) inspectedClassList.get(i).getUnsupportedExceptions();
            double bodyLengthLessThanOneHundred = (double) inspectedClassList.get(i).getBodyLengthLessThanOneHundred();


            dataset.addValue(normalize("NoIM", noim, nom), inspectedClassList.get(i).getName(), "NoIM");
            dataset.addValue(normalize("NoM", nom), inspectedClassList.get(i).getName(), "NoM");
            dataset.addValue(normalize("returnsNull", returnsNull), inspectedClassList.get(i).getName(), "returnsNull");
            dataset.addValue(normalize("NC", nc), inspectedClassList.get(i).getName(), "NC");
            dataset.addValue(normalize("DIT", dit), inspectedClassList.get(i).getName(), "DIT");
            dataset.addValue(normalize("LCOM", inspectedClassList.get(i).getLcom4()), inspectedClassList.get(i).getName(), "LCOM");
            dataset.addValue(normalize("Yalcom", inspectedClassList.get(i).getYalcom() * 10), inspectedClassList.get(i).getName(), "Yalcom");
            dataset.addValue(normalize("unsupportedOperationException", unsupportedOperationException), inspectedClassList.get(i).getName(), "unsupportedOperationException");
            dataset.addValue(normalize("bodyLengthLessThanOneHundred", bodyLengthLessThanOneHundred), inspectedClassList.get(i).getName(), "bodyLengthLessThanOneHundred");


        }



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

