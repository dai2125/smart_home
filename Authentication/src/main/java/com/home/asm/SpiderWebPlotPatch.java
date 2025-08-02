package com.home.asm;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;

public class SpiderWebPlotPatch {

    private Map<String, Double[]> metricRanges = new HashMap<>();

    public SpiderWebPlot getPlot(CategoryDataset data) {


        final SpiderWebPlot plot = new SpiderWebPlot(data) {



            // put this many labels on each axis.
            private int ticks = DEFAULT_TICKS;
            private static final int DEFAULT_TICKS = 5;
            private NumberFormat format = NumberFormat.getInstance();
            // constant for creating perpendicular tick marks.
            private static final double PERPENDICULAR = 90;
            // the size of a tick mark, as a percentage of the entire line length.
            private static final double TICK_SCALE = 0.015;
            // the gap between the axis line and the numeric label itself.
            private int valueLabelGap = DEFAULT_GAP;
            private static final int DEFAULT_GAP = 10;
            // the threshold used for determining if something is "on" the axis
            private static final double THRESHOLD = 15;

            /**
             * {@inheritDoc}
             */
            @Override
            protected void drawLabel(final Graphics2D g2, final Rectangle2D plotArea, final double value, final int cat,
                                     final double startAngle, final double extent) {
                super.drawLabel(g2, plotArea, value, cat, startAngle, extent);

                final FontRenderContext frc = g2.getFontRenderContext();
                final String categoryName = getDataset().getColumnKey(cat).toString();
                final Double[] range = metricRanges.get(categoryName);
                //final int axisTicks = metricTicks.getOrDefault(categoryName, 5); // Default fallback
                int axisTicks = 5;

                switch(categoryName) {
                    case "NoM":
                        axisTicks = 10;
                        break;
                    case "NoF":
                        axisTicks = 10;
                        break;
                    case "WMC":
                        axisTicks = 5;
                        break;
                    case "NC":
                        axisTicks = 5;
                        break;
                    case "DIT":
                        axisTicks = 5;
                        break;
                    case "LCOM":
                        axisTicks = 5;
                        break;
                    case "Yalcom":
                        axisTicks = 10;
                        break;
                    case "FanIn":
                        axisTicks = 5;
                        break;
                    case "FanOut":
                        axisTicks = 5;
                        break;
                    case "InterfaceMethods":
                        axisTicks = 3;
                        break;

                }


                if (range == null) return;

                final double min = range[0];
                final double max = range[1];

                final double deltaX = plotArea.getCenterX();
                final double deltaY = plotArea.getCenterY();
                final Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0, Arc2D.OPEN);
                final Point2D point1 = arc1.getEndPoint();

                for (int i = 1; i <= axisTicks; i++) {
                    double scale = (double) i / axisTicks;

                    double dx = point1.getX() - deltaX;
                    double dy = point1.getY() - deltaY;

                    AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
                    double[] pt = new double[]{dx, dy};
                    double[] result = new double[2];
                    tx.transform(pt, 0, result, 0, 1);
                    double labelX = result[0] + deltaX;
                    double labelY = result[1] + deltaY;

                    // Tick marker (line)
                    AffineTransform tickTrans = AffineTransform.getScaleInstance(scale + TICK_SCALE, scale + TICK_SCALE);
                    tickTrans.transform(pt, 0, result, 0, 1);
                    double tickX = result[0] + deltaX;
                    double tickY = result[1] + deltaY;

                    double rotated = PERPENDICULAR;
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(rotated), labelX, labelY);
                    double[] tickPt = new double[]{tickX, tickY};
                    rotate.transform(tickPt, 0, result, 0, 1);
                    double x1 = result[0], y1 = result[1];

                    rotated = -PERPENDICULAR;
                    rotate = AffineTransform.getRotateInstance(Math.toRadians(rotated), labelX, labelY);
                    rotate.transform(tickPt, 0, result, 0, 1);
                    double x2 = result[0], y2 = result[1];

                    Composite oldComposite = g2.getComposite();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));

                    // Wert berechnen
                    double realValue = min + scale * (max - min);

                    //System.out.println("categoryName: "+ categoryName);
                    if ("Yalcom".equals(categoryName)) {
                        double[] yalcomLabels = new double[]{ 99.0, 75.0, 50.0, 25.0, 0.0, -10.0 };

                        for (double yVal : yalcomLabels) {
                            // Skaliere auf 0–1 Bereich
                            scale = (yVal - min) / (max - min);
                            //System.out.println("scale: " + scale + ", yVal: " + yVal + ", min: "+ min + ", max: "+ max);

                            dx = point1.getX() - deltaX;
                             dy = point1.getY() - deltaY;

                            tx = AffineTransform.getScaleInstance(scale, scale);
                            pt = new double[]{dx, dy};
                            result = new double[2];
                            tx.transform(pt, 0, result, 0, 1);
                            labelX = result[0] + deltaX;
                            labelY = result[1] + deltaY;

                            // Optional: Tickmark zeichnen wie bisher
                            // Tick marker (line)
                            tickTrans = AffineTransform.getScaleInstance(scale + TICK_SCALE, scale + TICK_SCALE);
                            tickTrans.transform(pt, 0, result, 0, 1);
                            tickX = result[0] + deltaX;
                            tickY = result[1] + deltaY;
                            rotated = PERPENDICULAR;
                            rotate = AffineTransform.getRotateInstance(Math.toRadians(rotated), labelX, labelY);
                            tickPt = new double[]{tickX, tickY};
                            rotate.transform(tickPt, 0, result, 0, 1);
                            x1 = result[0];
                            y1 = result[1];

                            rotated = -PERPENDICULAR;
                            rotate = AffineTransform.getRotateInstance(Math.toRadians(rotated), labelX, labelY);
                            rotate.transform(tickPt, 0, result, 0, 1);
                            x2 = result[0];
                            y2 = result[1];

                            oldComposite = g2.getComposite();
                            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                            g2.draw(new Line2D.Double(x1, y1, x2, y2));

                            // Label zeichnen
                            final String label = format.format(yVal);
                            final Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
                            final LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
                            final double ascent = lm.getAscent();

                            // Positionierungslogik wie bisher
                            labelX += valueLabelGap;
                            labelY += ascent / 2f;

                            g2.setPaint(getLabelPaint());
                            g2.setFont(getLabelFont());
                            g2.drawString(label, (float) labelX, (float) labelY);
                        }

                        return;
                    }


                    final String label = format.format(realValue);
                    final Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
                    final LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
                    final double ascent = lm.getAscent();

                    // Label positionieren
                    if (Math.abs(labelX - deltaX) < THRESHOLD) {
                        labelX += valueLabelGap;
                        labelY += ascent / 2f;
                    } else if (Math.abs(labelY - deltaY) < THRESHOLD) {
                        labelY += valueLabelGap;
                    } else if (labelX >= deltaX) {
                        if (labelY < deltaY) {
                            labelX += valueLabelGap;
                            labelY += valueLabelGap;
                        } else {
                            labelX -= valueLabelGap;
                            labelY += valueLabelGap;
                        }
                    } else {
                        if (labelY > deltaY) {
                            labelX -= valueLabelGap;
                            labelY -= valueLabelGap;
                        } else {
                            labelX += valueLabelGap;
                            labelY -= valueLabelGap;
                        }
                    }

                    g2.setPaint(getLabelPaint());
                    g2.setFont(getLabelFont());
                    g2.drawString(label, (float) labelX, (float) labelY);

                    g2.setComposite(oldComposite);
                }

                // Gitternetz (nur einmal pro Kategorie zeichnen, um Überlappung zu vermeiden)
                if (startAngle == this.getStartAngle()) {
                    for (int level = 1; level <= axisTicks; level++) {
                        double scale = (double) level / axisTicks;
                        Polygon web = new Polygon();

                        for (int c = 0; c < getDataset().getColumnCount(); c++) {
                            String colName = getDataset().getColumnKey(c).toString();
                            int ticksForAxis = metricTicks.getOrDefault(colName, 5);
                            double angle = getStartAngle() + c * extent;
                            double radians = Math.toRadians(angle);
                            double r = scale * (Math.min(plotArea.getWidth(), plotArea.getHeight()) / 2.0);

                            double x = plotArea.getCenterX() + Math.cos(radians) * r;
                            double y = plotArea.getCenterY() - Math.sin(radians) * r;

                            web.addPoint((int) x, (int) y);
                        }

                        Stroke oldStroke = g2.getStroke();
                        Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f,
                                new float[]{2.0f, 2.0f}, 0.0f);
                        g2.setStroke(dashed);
                        g2.setPaint(Color.LIGHT_GRAY);
                        g2.drawPolygon(web);
                        g2.setStroke(oldStroke);
                    }
                }
            }


        };


        return plot;
    }

    public void setMetricRanges(Map<String, Double[]> metricRanges) {
        this.metricRanges = metricRanges;
    }

    private Map<String, Integer> metricTicks = new HashMap<>();

    public void setMetricTicks(Map<String, Integer> metricTicks) {
        this.metricTicks = metricTicks;
    }



}