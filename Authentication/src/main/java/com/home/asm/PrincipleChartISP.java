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
    double ncMin = 0.0, ncMax = 10.0;
    double ditMin = 0.0, ditMax = 10.0;
    double lcomMin = 0.0, lcomMax = 5.0;
    double yalcomMin = -50.0, yalcomMax = 100.0;
    double unsupporteExceptionsMin = 0.0, unsupporteExceptionsMax = 2.0;
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

        // dataset.addValue(normalize("NoIM", 1.0), "Max", "NoIM");
        dataset.addValue(normalize("Methods", 1.0), "Max", "Methods");
        dataset.addValue(normalize("returnsNull", 1.0), "Max", "returnsNull");
        dataset.addValue(normalize("NC", 3.0), "Max", "NC");
        dataset.addValue(normalize("DIT", 4.0), "Max", "DIT");
        dataset.addValue(normalize("LCOM", 2.0), "Max", "LCOM");
        dataset.addValue(normalize("Yalcom", 0.75), "Max", "Yalcom");
        dataset.addValue(normalize("unsupportedOperationException", 1.0), "Max", "unsupportedOperationException");
        dataset.addValue(normalize("bodyLengthLessThanOneHundred", 1.0), "Max", "bodyLengthLessThanOneHundred");

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

        JFreeChart chart = new JFreeChart(principle, new Font(Font.SANS_SERIF, Font.BOLD, 22), plot, true);
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


        String[] columnNames = { "Class", "Interface", "Methods", "NC", "DIT", "LCOM", "Yalcom", "Unsupported", "Return Null" };

        Object[][] data = new Object[inspectedClassList.size()][columnNames.length];

        boolean isMainClass = false;

        for(int i = 0; i < inspectedClassList.size(); i++) {
            containsMainMethod(inspectedClassList.get(i));
        }


        for (int i = 0; i < inspectedClassList.size(); i++) {
            // System.out.println("1 Collections.sort(): " + inspectedClassList.get(i).getName());
        }

        Collections.sort(inspectedClassList, new BooleanComparator());

        for (int i = 0; i < inspectedClassList.size(); i++) {
            // System.out.println("2 Collections.sort(): " + inspectedClassList.get(i).getName());
        }


        for(int i = 0; i < inspectedClassList.size(); i++) {
            for(int j = 0; j < columnNames.length; j++) {
                if(inspectedClassList.get(i).getIsInterface()) {
                    data[i][0] = inspectedClassList.get(i).getName();
                    //data[i][1] = inspectedClassList.get(i).getInterfaceMethodList().size();
                    data[i][1] = inspectedClassList.get(i).getIsInterface() ? "True" : "False";
//                    data[i][2] = "(" + inspectedClassList.get(i).getNumberOfMethods() + "/" + inspectedClassList.get(i).getInterfaceMethodList().size() + ")";
//                    data[i][3] = inspectedClassList.get(i).getReturnsNull();
//                    data[i][4] = inspectedClassList.get(i).getNumberOfChildren();
//                    data[i][5] = inspectedClassList.get(i).getDit();
//                    data[i][6] = inspectedClassList.get(i).getLcom4();
//                    data[i][7] = inspectedClassList.get(i).getYalcom();
//                    data[i][8] = inspectedClassList.get(i).getUnsupportedExceptions();
//                    data[i][9] = inspectedClassList.get(i).getBodyLengthLessThanOneHundred();
                } else if(inspectedClassList.get(i).isMainClass()) {
                    data[i][0] = inspectedClassList.get(i).getName();
                    data[i][1] = "Main Class";
                } else {
                    data[i][0] = inspectedClassList.get(i).getName();
                    //data[i][1] = inspectedClassList.get(i).getInterfaceMethodList().size();
                    data[i][1] = inspectedClassList.get(i).getIsInterface() ? "True" : "False";// String.valueOf(inspectedClassList.get(i).getIsInterface()).toUpperCase();
                    data[i][2] = "(" + inspectedClassList.get(i).getInterfaceMethodList().size() + "/" + inspectedClassList.get(i).getNumberOfMethods() + ")";
                    data[i][3] = inspectedClassList.get(i).getNumberOfChildren();
                    data[i][4] = inspectedClassList.get(i).getDit();
                    data[i][5] = inspectedClassList.get(i).getLcom4();
                    data[i][6] = inspectedClassList.get(i).getYalcom();
                    data[i][7] = inspectedClassList.get(i).getUnsupportedExceptions();
                    data[i][8] = inspectedClassList.get(i).getReturnsNull();
                    //data[i][9] = inspectedClassList.get(i).getBodyLengthLessThanOneHundred();
                }
            }
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 3) {
                    Object value = getValueAt(row, column);
                    if(value == null) {
                        c.setBackground(Color.WHITE);
                    } else {
                        if (value instanceof Number) {
                            double v = ((Number) value).doubleValue();
                            System.out.println("value: " + value);
                            InspectedClass ic = (InspectedClass) inspectedClassList.get(row);
                            // InspectedClass ic = inspectedClasses.get(row);
                            c.setBackground(heatmapColor2(ic));
                        }
                    }
                } else if (column >= 1 && column <= 10) {
                    Object value = getValueAt(row, column);
                    System.out.println("value: " + value);
                    if(value == null || value == "Main Class") {
                        c.setBackground(Color.WHITE);
                    } else if (value instanceof Number) {
                        double v = ((Number) value).doubleValue();
                        // System.out.println(1 + " : " + v + " : " + inspectedClassList.get(row).getName() + " : " + row + " : " + column);
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

    private Color heatmapColor2(InspectedClass inspectedClass) {
        // inspectedClass.getWmc() > (inspectedClass.getNumberOfConstructors() + inspectedClass.getNumberOfMethods()) * 2
        double wmc = inspectedClass.getWmc();
        int noc = inspectedClass.getNumberOfConstructors();
        int nom = inspectedClass.getNumberOfMethods();

        // System.out.println(1 + " wmc = " + wmc + ", noc = " + noc + ", nom = " + nom);

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
            default:
                return srpMax;
        }
    }

    double normalize(String s, double value) {
        //System.out.print("s: " + s + ", value: " + value + ", ");
        switch (s) {
            case "NoM":     return clamp(value / nomMax);      // max 10
            case "isInterface": if(value <= 1.0) {
                                    return 1.0;
                                } else {
                                    return 2.0;
                                }
            case "NC":
//                System.out.println("NC: " + value + " - " + ncMax);
//                System.out.println("NC: " + (value / ncMax));
//                System.out.println("NC: " + clamp3(value));

                // return clamp3(value);      // max 10

                return normalizeNc(value);
                //return clamp(value / ncMax);      // max 10
            case "DIT":
//                System.out.println("DIT: " + value + " - " + ditMax);
//                System.out.println("DIT: " + clamp3(value));
//                System.out.println("DIT: " + clamp3(value/ ditMax));

                return normalizeDit(value);
                //return clamp3(value);      // max 10

                // return clamp(value / ditMax);       // max 5
            case "LCOM":    return clamp(value / lcomMax);      // max 10
            case "unsupportedOperationException":   if(value <= 1.0) {
                                                        return 1.0;
                                                    } else {
                                                        return 2.0;
                                                    }
            case "returnsNull": if(value <= 1.0) {
                                    return 1.0;
                                } else {
                                    return 2.0;
                                }
            case "Yalcom":
//                System.out.println("clamp(): " + value + " " + (value * 10) + " " + (yalcomMax - 15.0));
//                System.out.println("clamp(): " + clamp2((value * 10) / (yalcomMax - 15.0)));
                //return clamp((value * 10) / (yalcomMax - 15.0)); // -1.0 → 0.0, 0.9 → 1.0 // TODO für die Darstellung - 15.0 irgendwo wird falsch skaliert

                System.out.println("\nYalcom: " + value  + ", " + (value * 10) + ", " + ((value * 10) / (yalcomMax)));
                return normalizeYalcom((value * 10) / (yalcomMax - 15.0) );
            case "bodyLengthLessThanOneHundred":    if(value <= 1.0) {
                                                        //System.out.println(clamp(0 / methodBodyLengthMax));
                                                        return 1.0;
                                                    } else {
                                                        //System.out.println(clamp(1 / methodBodyLengthMax));
                                                        return 2.0;
                                                    }
            default: return 0.0;
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


    // TODO skalierung von Yalcom stimmt nicht
    // TODO Number of children stimmt auch nicht
    double normalizeYalcom(double v) {
        //System.out.println("v: " + v);
        if(v < 0.0) {
            return 0.4;
        }
        if(v == 0.0) {
            return 0.2;
        }
        // System.out.println("clamp2(): " + v);
        // System.out.println("clamp2(): " + (v + 1.0));
        return (v + 1.0);
        //return Math.max(-5.0, Math.min(1.0, v));
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

            boolean hasMainMethod = containsMainMethod(inspectedClassList.get(i));

            if(!inspectedClassList.get(i).getIsInterface() && !hasMainMethod) {
                //System.out.println("initDataset(): " + inspectedClassList.get(i).getName());
                //dataset.addValue(normalize("NoIM", noim, nom), inspectedClassList.get(i).getName(), "NoIM");
                dataset.addValue(numberOfMethodsEqualsNumberOfInterfaceMethodes(inspectedClassList.get(i)), inspectedClassList.get(i).getName(), "Methods");
                dataset.addValue(normalize("returnsNull", returnsNull), inspectedClassList.get(i).getName(), "returnsNull");
                dataset.addValue(normalize("NC", nc), inspectedClassList.get(i).getName(), "NC");
                dataset.addValue(normalize("DIT", dit), inspectedClassList.get(i).getName(), "DIT");
                dataset.addValue(normalize("LCOM", inspectedClassList.get(i).getLcom4()), inspectedClassList.get(i).getName(), "LCOM");
                dataset.addValue(normalize("Yalcom", inspectedClassList.get(i).getYalcom() * 10), inspectedClassList.get(i).getName(), "Yalcom");
                dataset.addValue(normalize("unsupportedOperationException", unsupportedOperationException), inspectedClassList.get(i).getName(), "unsupportedOperationException");
                dataset.addValue(normalize("bodyLengthLessThanOneHundred", bodyLengthLessThanOneHundred), inspectedClassList.get(i).getName(), "bodyLengthLessThanOneHundred");
            }
        }
    }

    double[] srpMax = {
            0.0,    // Dummy Value
            0.0,   // Interface
            0.0,   // MEthods
            5.0,    // NC
            5.0,    // DIT
            20.0,    // LCOM
            0.5,    // YALCOM
            0.0,    // Unsupported
            0.0,   // Return null

    };


    // dataset.addValue(normalize("Methods", 1.0), "Max", "Methods");
//        dataset.addValue(normalize("returnsNull", 1.0), "Max", "returnsNull");
//        dataset.addValue(normalize("NC", 3.0), "Max", "NC");
//        dataset.addValue(normalize("DIT", 4.0), "Max", "DIT");
//        dataset.addValue(normalize("LCOM", 2.0), "Max", "LCOM");
//        dataset.addValue(normalize("Yalcom", 0.75), "Max", "Yalcom");
//        dataset.addValue(normalize("unsupportedOperationException", 1.0), "Max", "unsupportedOperationException");
//        dataset.addValue(normalize("bodyLengthLessThanOneHundred", 1.0), "Max", "bodyLengthLessThanOneHundred");

    double numberOfMethodsEqualsNumberOfInterfaceMethodes(InspectedClass inspectedClass) {
        if(inspectedClass.getNumberOfMethods() > 0 &&  inspectedClass.getInterfaceMethodList().size() > 0) {
            if (inspectedClass.getNumberOfMethods() == inspectedClass.getInterfaceMethodList().size()) {
                return 1.0;
            }
        }
        return 2.0;
    }

    boolean containsMainMethod(InspectedClass inspectedClass) {

        for(int i = 0; i < inspectedClass.getMethodList().size(); i++) {
            //System.out.println("containsMainMethod(): " + inspectedClass.getMethodList().get(i).getName());
            if(inspectedClass.getMethodList().get(i).getName().toString().equals("main")) {
                // System.out.println("containsMainMethod(): " + inspectedClass.getName() + " - " + inspectedClass.isMainClass());
                inspectedClass.setIsMainClass(true);
                // System.out.println("containsMainMethod(): " + inspectedClass.getName() + " - " + inspectedClass.isMainClass());

                return true;
            }
        }
        return false;
    }

    double normalizeNC(double v) {
        return 1.0;
    }

    double normalizeDit(double v) {
        double[] values = new double[] { 0.0, 0.117, 0.234, 0.351, 0.468, 0.6, 1.2, 1.4, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0, 3.3, 3.6, 3.9, 4.2 };
        int index = (int) v;
        //System.out.println("index: " + index);
        return values[index];
    }

    double normalizeNc(double v) {
        double[] values = new double[] { 0.0, 0.117, 0.234, 0.351, 0.468, 0.6, 1.8, 2.1, 2.4, 2.7, 3.0, 3.3, 3.6, 3.9, 4.2 };
        int index = (int) v;
        //System.out.println("index: " + index);
        return values[index];
    }
}

