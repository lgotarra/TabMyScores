package laura.gotarra.tabmyscores;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabView extends View {


    private ArrayList<Integer> chords_frets;
    private Integer min;

    public TabView(Context context) {
        super(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setChords_frets(ArrayList<Integer> chords_frets) {
        this.chords_frets = chords_frets;
        init();
        invalidate();
    }

    void init() {
        min = chords_frets.get(0);
        calculMin();
    }

    public void calculMin(){
        if( !chords_frets.isEmpty())
        {
            for (Integer element : chords_frets){
                if (element < min ) {
                    min = element;
                }
            }
            if ( min == 0 ){
                min = 1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint lines = new Paint();
        lines.setStyle(Paint.Style.STROKE);
        lines.setStrokeWidth(3);
        lines.setARGB(255,128,128,128);

        Paint text = new Paint();
        text.setStrokeWidth(0);
        text.setStyle(Paint.Style.FILL);
        text.setColor(Color.BLACK);
        text.setTextSize(35);

        Paint circle = new Paint();
        circle.setStyle(Paint.Style.FILL);
        circle.setColor(Color.RED);

        final int width = canvas.getWidth() - ((int)Math.floor(lines.getStrokeWidth())) / 2;
        final int height = canvas.getHeight();
        final int vertsep = (height/10);

        float y = vertsep;
        float x = 1;
        float x_dist = (width - lines.getStrokeWidth()*10)/4;
        float r = width/40;

        for (int i = 0; i < 6; i++) {
            canvas.drawLine(0, y, x_dist*4, y, lines);
            // canvas.drawRect(0, y, canvas.getWidth(), 10+y, lines);
            y += vertsep;
        }
        for (int i = 0; i<5; i++) {
            canvas.drawLine(x,vertsep,x,y-vertsep,lines);
            //canvas.drawRect(x,30,x+10,y-40,lines);
            x += x_dist;
        }


        Rect bounds = new Rect();
        text.getTextBounds(Integer.toString(min), 0, 1, bounds);
        x = width/8 - bounds.width()/2 - r/4;
        for (int i = min; i < min + 5; i++) {
            text.getTextBounds(Integer.toString(i), 0, 1, bounds);
            canvas.drawText(Integer.toString(i), x, y+vertsep/4, text);
            x += width/4 - bounds.width()/2;
        }

        y = vertsep;
        for (int i = 0; i < 6; i++){
            if (chords_frets.get(i) > 0) {
                canvas.drawCircle((chords_frets.get(i)-min)*x_dist +x_dist/2,y,r, circle);
            }
            y+=vertsep;
        }

    }

}


