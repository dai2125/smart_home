package com.home.asm;

import org.jfree.chart.block.BlockResult;
import org.jfree.chart.block.EntityBlockParams;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.entity.TitleEntity;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.text.G2TextMeasurer;
import org.jfree.chart.text.TextBlock;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.ui.*;
import org.jfree.chart.util.*;
import org.jfree.data.Range;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RotateTextTitle extends TextTitle implements Serializable, Cloneable, PublicCloneable {

    private static final long serialVersionUID = 8372008692127477443L;
    public static final Font DEFAULT_FONT = new Font("SansSerif", 1, 12);
    public static final Paint DEFAULT_TEXT_PAINT;
    private String text;
    private Font font;
    private HorizontalAlignment textAlignment;
    private transient Paint paint;
    private transient Paint backgroundPaint;
    private String toolTipText;
    private String urlText;
    private TextBlock content;
    private boolean expandToFitSpace;
    private int maximumLinesToDisplay;

    public RotateTextTitle() {
        this("");
    }

    public RotateTextTitle(String text) {
        this(text, DEFAULT_FONT, DEFAULT_TEXT_PAINT, Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT, Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
    }

    public RotateTextTitle(String text, Font font) {
        this(text, font, DEFAULT_TEXT_PAINT, Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT, Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
    }

    public RotateTextTitle(String text, Font font, Paint paint, RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, RectangleInsets padding) {
        super();
        this.expandToFitSpace = false;
        this.maximumLinesToDisplay = Integer.MAX_VALUE;
        if (text == null) {
            throw new NullPointerException("Null 'text' argument.");
        } else if (font == null) {
            throw new NullPointerException("Null 'font' argument.");
        } else if (paint == null) {
            throw new NullPointerException("Null 'paint' argument.");
        } else {
            this.text = text;
            this.font = font;
            this.paint = paint;
            this.textAlignment = horizontalAlignment;
            this.backgroundPaint = null;
            this.content = null;
            this.toolTipText = null;
            this.urlText = null;
        }
    }

    private final double angle = Math.PI / 2;


//    @Override
//    public void draw(Graphics2D g2, Rectangle2D area) {
//        g2 = (Graphics2D) g2.create();
//
//        g2.setFont(font);
//        FontMetrics fm = g2.getFontMetrics();
//        int textWidth = fm.stringWidth(text);
//        int textHeight = fm.getHeight();
//
//        float x = (float) area.getCenterX();
//        float y = (float) area.getCenterY();
//
//        g2.rotate(angle, x, y);
//        g2.drawString(text, x - textWidth / 2f, y + textHeight / 4f);
//        g2.dispose();
//        //return null;
//
////        g2.rotate(angle, area.getCenterX(), area.getCenterY());
////        g2.drawString(text, (float) area.getCenterX(), (float) area.getCenterY());
////        g2.rotate(-angle, area.getCenterX(), area.getCenterY());
//    }

//    @Override
//    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
//        g2.setFont(font);
//        FontMetrics fm = g2.getFontMetrics();
//        int height = fm.stringWidth(text);
//        int width = fm.stringWidth(text);
//
//        return new Size2D(width + 10, height + 10);
//    }
//
//    @Override
//    public Object draw(Graphics2D graphics2D, Rectangle2D rectangle2D, Object o) {
//        return null;
//    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        Args.nullNotPermitted(text, "text");
        if (!this.text.equals(text)) {
            this.text = text;
            this.notifyListeners(new TitleChangeEvent(this));
        }

    }

    public HorizontalAlignment getTextAlignment() {
        return this.textAlignment;
    }

    public void setTextAlignment(HorizontalAlignment alignment) {
        Args.nullNotPermitted(alignment, "alignment");
        this.textAlignment = alignment;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        Args.nullNotPermitted(font, "font");
        if (!this.font.equals(font)) {
            this.font = font;
            this.notifyListeners(new TitleChangeEvent(this));
        }

    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        if (!this.paint.equals(paint)) {
            this.paint = paint;
            this.notifyListeners(new TitleChangeEvent(this));
        }

    }

    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    public void setBackgroundPaint(Paint paint) {
        this.backgroundPaint = paint;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public String getToolTipText() {
        return this.toolTipText;
    }

    public void setToolTipText(String text) {
        this.toolTipText = text;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public String getURLText() {
        return this.urlText;
    }

    public void setURLText(String text) {
        this.urlText = text;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public boolean getExpandToFitSpace() {
        return this.expandToFitSpace;
    }

    public void setExpandToFitSpace(boolean expand) {
        this.expandToFitSpace = expand;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public int getMaximumLinesToDisplay() {
        return this.maximumLinesToDisplay;
    }

    public void setMaximumLinesToDisplay(int max) {
        this.maximumLinesToDisplay = max;
        this.notifyListeners(new TitleChangeEvent(this));
    }

    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        double width = fm.getHeight(); // weil rotiert
        double height = fm.stringWidth(text);
        return new Size2D(width + 10, height + 10);
    }

    protected Size2D arrangeNN(Graphics2D g2) {
        Range max = new Range((double)0.0F, (double)Float.MAX_VALUE);
        return this.arrangeRR(g2, max, max);
    }

    protected Size2D arrangeFN(Graphics2D g2, double w) {
        RectangleEdge position = this.getPosition();
        if (position != RectangleEdge.TOP && position != RectangleEdge.BOTTOM) {
            if (position != RectangleEdge.LEFT && position != RectangleEdge.RIGHT) {
                throw new RuntimeException("Unrecognised exception.");
            } else {
                float maxWidth = Float.MAX_VALUE;
                g2.setFont(this.font);
                this.content = TextUtils.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, new G2TextMeasurer(g2));
                this.content.setLineAlignment(this.textAlignment);
                Size2D contentSize = this.content.calculateDimensions(g2);
                return this.expandToFitSpace ? new Size2D(contentSize.getHeight(), (double)maxWidth) : new Size2D(contentSize.height, contentSize.width);
            }
        } else {
            float maxWidth = (float)w;
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);
            return this.expandToFitSpace ? new Size2D((double)maxWidth, contentSize.getHeight()) : contentSize;
        }
    }

    protected Size2D arrangeRN(Graphics2D g2, Range widthRange) {
        Size2D s = this.arrangeNN(g2);
        if (widthRange.contains(s.getWidth())) {
            return s;
        } else {
            double ww = widthRange.constrain(s.getWidth());
            return this.arrangeFN(g2, ww);
        }
    }

    protected Size2D arrangeRR(Graphics2D g2, Range widthRange, Range heightRange) {
        RectangleEdge position = this.getPosition();
        if (position != RectangleEdge.TOP && position != RectangleEdge.BOTTOM) {
            if (position != RectangleEdge.LEFT && position != RectangleEdge.RIGHT) {
                throw new RuntimeException("Unrecognised exception.");
            } else {
                float maxWidth = (float)heightRange.getUpperBound();
                g2.setFont(this.font);
                this.content = TextUtils.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, new G2TextMeasurer(g2));
                this.content.setLineAlignment(this.textAlignment);
                Size2D contentSize = this.content.calculateDimensions(g2);
                return this.expandToFitSpace ? new Size2D(contentSize.getHeight(), (double)maxWidth) : new Size2D(contentSize.height, contentSize.width);
            }
        } else {
            float maxWidth = (float)widthRange.getUpperBound();
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);
            return this.expandToFitSpace ? new Size2D((double)maxWidth, contentSize.getHeight()) : contentSize;
        }
    }

    public void draw(Graphics2D g2, Rectangle2D area) {
        this.draw(g2, area, (Object)null);
    }

    @Override
    public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
        System.out.println("RotateTextTitle draw() called");

        if (text == null || text.isEmpty()) {
            return null;
        }

        ChartEntity entity = null;
        if (params instanceof EntityBlockParams p && p.getGenerateEntities()) {
            entity = new TitleEntity(area, this, this.toolTipText, this.urlText);
        }

        // Optional: Hintergrund
        if (backgroundPaint != null) {
            g2.setPaint(backgroundPaint);
            g2.fill(area);
        }

        // Zeichnung vorbereiten
        Font originalFont = g2.getFont();
        Paint originalPaint = g2.getPaint();
        g2.setFont(font);
        g2.setPaint(paint != null ? paint : Color.BLACK);

        // Zentrum der Zeichenfläche
        float centerX = (float) area.getCenterX();
        float centerY = (float) area.getCenterY();

        // Rotation: 90° im Uhrzeigersinn
        g2 = (Graphics2D) g2.create();
        g2.rotate(Math.PI / 2, centerX, centerY);

        // Text zentriert auf rotierter Fläche zeichnen
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        // Zeichne Text mittig bezogen auf das Zentrum
        g2.drawString(text, centerX - textWidth / 2f, centerY + textHeight / 4f);

        g2.dispose();
        g2.setFont(originalFont);
        g2.setPaint(originalPaint);

        // Entity zurückgeben
        if (entity != null) {
            StandardEntityCollection sec = new StandardEntityCollection();
            sec.add(entity);
            BlockResult result = new BlockResult();
            result.setEntityCollection(sec);
            return result;
        }

        return null;
    }

    protected void drawHorizontal(Graphics2D g2, Rectangle2D area) {
        Rectangle2D titleArea = (Rectangle2D)area.clone();
        g2.setFont(this.font);
        g2.setPaint(this.paint);
        TextBlockAnchor anchor = null;
        float x = 0.0F;
        HorizontalAlignment horizontalAlignment = this.getHorizontalAlignment();
        if (horizontalAlignment == HorizontalAlignment.LEFT) {
            x = (float)titleArea.getX();
            anchor = TextBlockAnchor.TOP_LEFT;
        } else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
            x = (float)titleArea.getMaxX();
            anchor = TextBlockAnchor.TOP_RIGHT;
        } else if (horizontalAlignment == HorizontalAlignment.CENTER) {
            x = (float)titleArea.getCenterX();
            anchor = TextBlockAnchor.TOP_CENTER;
        }

        float y = 0.0F;
        RectangleEdge position = this.getPosition();
        if (position == RectangleEdge.TOP) {
            y = (float)titleArea.getY();
        } else if (position == RectangleEdge.BOTTOM) {
            y = (float)titleArea.getMaxY();
            if (horizontalAlignment == HorizontalAlignment.LEFT) {
                anchor = TextBlockAnchor.BOTTOM_LEFT;
            } else if (horizontalAlignment == HorizontalAlignment.CENTER) {
                anchor = TextBlockAnchor.BOTTOM_CENTER;
            } else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
                anchor = TextBlockAnchor.BOTTOM_RIGHT;
            }
        }

        this.content.draw(g2, x, y, anchor);
    }

    protected void drawVertical(Graphics2D g2, Rectangle2D area) {
        Rectangle2D titleArea = (Rectangle2D)area.clone();
        g2.setFont(this.font);
        g2.setPaint(this.paint);
        TextBlockAnchor anchor = null;
        float y = 0.0F;
        VerticalAlignment verticalAlignment = this.getVerticalAlignment();
        if (verticalAlignment == VerticalAlignment.TOP) {
            y = (float)titleArea.getY();
            anchor = TextBlockAnchor.TOP_RIGHT;
        } else if (verticalAlignment == VerticalAlignment.BOTTOM) {
            y = (float)titleArea.getMaxY();
            anchor = TextBlockAnchor.TOP_LEFT;
        } else if (verticalAlignment == VerticalAlignment.CENTER) {
            y = (float)titleArea.getCenterY();
            anchor = TextBlockAnchor.TOP_CENTER;
        }

        float x = 0.0F;
        RectangleEdge position = this.getPosition();
        if (position == RectangleEdge.LEFT) {
            x = (float)titleArea.getX();
        } else if (position == RectangleEdge.RIGHT) {
            x = (float)titleArea.getMaxX();
            if (verticalAlignment == VerticalAlignment.TOP) {
                anchor = TextBlockAnchor.BOTTOM_RIGHT;
            } else if (verticalAlignment == VerticalAlignment.CENTER) {
                anchor = TextBlockAnchor.BOTTOM_CENTER;
            } else if (verticalAlignment == VerticalAlignment.BOTTOM) {
                anchor = TextBlockAnchor.BOTTOM_LEFT;
            }
        }

        this.content.draw(g2, x, y, anchor, x, y, (-Math.PI / 2D));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof TextTitle)) {
            return false;
        } else {
            TextTitle that = (TextTitle)obj;
            if (!ObjectUtils.equal(this.text, that.getText())) {
                return false;
            } else if (!ObjectUtils.equal(this.font, that.getFont())) {
                return false;
            } else if (!PaintUtils.equal(this.paint, that.getPaint())) {
                return false;
            } else if (this.textAlignment != that.getTextAlignment()) {
                return false;
            } else if (!PaintUtils.equal(this.backgroundPaint, that.getBackgroundPaint())) {
                return false;
            } else if (this.maximumLinesToDisplay != that.getMaximumLinesToDisplay()) {
                return false;
            } else if (this.expandToFitSpace != that.getExpandToFitSpace()) {
                return false;
            } else if (!ObjectUtils.equal(this.toolTipText, that.getToolTipText())) {
                return false;
            } else {
                return !ObjectUtils.equal(this.urlText, that.getURLText()) ? false : super.equals(obj);
            }
        }
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (this.text != null ? this.text.hashCode() : 0);
        result = 29 * result + (this.font != null ? this.font.hashCode() : 0);
        result = 29 * result + (this.paint != null ? this.paint.hashCode() : 0);
        result = 29 * result + (this.backgroundPaint != null ? this.backgroundPaint.hashCode() : 0);
        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.paint, stream);
        SerialUtils.writePaint(this.backgroundPaint, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.paint = SerialUtils.readPaint(stream);
        this.backgroundPaint = SerialUtils.readPaint(stream);
    }

    static {
        DEFAULT_TEXT_PAINT = Color.BLACK;
    }
}
