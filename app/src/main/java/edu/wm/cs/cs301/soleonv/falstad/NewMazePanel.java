package edu.wm.cs.cs301.soleonv.falstad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by solivialeonvitervo on 11/26/17.
 */

public class NewMazePanel extends View {

    public Canvas canvas;
    public Paint paint;
    public Bitmap bitmap;
    private static final int BITMAP_W =  400;
    private static final int BITMAP_H = 400;


    public NewMazePanel(Context context,AttributeSet app ){
        super(context, app);
        init();

    }

    public NewMazePanel(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(BITMAP_W, BITMAP_H, Bitmap.Config.ARGB_8888);
        //similar to the gc
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, null , new Rect(0,0, canvas.getWidth(), canvas.getHeight()), paint);


    }

    @Override
    public void onMeasure( int width, int height){
        super.onMeasure(width, height);
        setMeasuredDimension(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
    }


    public void update(){
        invalidate();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Bitmap.createBitmap(BITMAP_W, BITMAP_H, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setGraphicsWhite(){

        paint.setColor(Color.WHITE);
    }

    public void setGraphicsGray(){

        paint.setColor(Color.GRAY);
    }

    public void setGraphicsYellow(){

        paint.setColor(Color.YELLOW);
    }

    public void setGraphicsRed(){

        paint.setColor(Color.RED);
    }

    public void setGraphicsBlack(){

        paint.setColor(Color.BLACK);
    }
    public void setGraphicsDarkGray(){

        paint.setColor(Color.DKGRAY);
    }
    public void setGraphicsBlue(){

        paint.setColor(Color.BLUE);
    }

    public void setColor(int[] colorArray){
        paint.setColor(Color.rgb(colorArray[0],colorArray[1], colorArray[2]));
    }

    public void fillRect(int x, int y, int w, int h){

        canvas.drawRect(x,y,x+w, y +h, paint);
    }

    public void setFont(Typeface f){
        paint.setTypeface(f);
    }

    public Paint.FontMetrics getFontMetrics(){

        return paint.getFontMetrics();
    }

    public void drawString(String str, int x, int y){

        canvas.drawText(str, x, y, paint);
    }

    public void fillPolygon(int[] x, int[] y, int nPoints){
        Paint wallpaint = new Paint();
        wallpaint.setColor(Color.RED);
        wallpaint.setStyle(Paint.Style.FILL);

        Path wallpath = new Path();

        wallpath.moveTo(x[0], y[0]);
        for(int i = 1; i < nPoints; i++ ){
            wallpath.lineTo(x[i], y[i]);
        }

        wallpath.lineTo(x[0], y[0]);
        canvas.drawPath(wallpath, paint);


    }

    public void drawLine(int x, int y, int x2, int y1){

        canvas.drawLine(x, y, x2, y1, paint);
    }

    public void fillOval(int x, int y, int width, int height){
        RectF oval = new RectF(x,y,x+width, y+ height);
        canvas.drawOval(oval, paint);
    }


    /**
     * converts the individual elements in array to one int
     * @param colorArray
     * @return
     */
    public static int getRGB(int[] colorArray){
        return Color.rgb(colorArray[0],colorArray[1], colorArray[2] );
    }

    /**
     * converts the color int into an rbg array
     * @param colorInt
     * @return
     */
    public static int[] ColorArray( int colorInt){
        int[] color = new int[3];
        color[0] = Color.red(colorInt);
        color[1] = Color.green(colorInt);
        color[2] = Color.blue(colorInt);
        return color;
    }

}

