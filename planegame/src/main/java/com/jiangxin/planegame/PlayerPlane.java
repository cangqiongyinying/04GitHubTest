package com.jiangxin.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by my on 2016/8/11.
 */
public class PlayerPlane extends View {

    private Paint mPaint;
    private Bitmap planeBitmap;
    private float planeX,planeY;
    private List<Bullet> bullets=new ArrayList<>();
    private List<Enemy> enemies=new ArrayList<>();
    private Context context;
    private int count;
    public PlayerPlane(Context context) {
        this(context,null);
    }

    public PlayerPlane(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PlayerPlane(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        mPaint=new Paint();
        planeBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.myplane);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        planeX=event.getX()-33;
        planeY=event.getY()-22;
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(planeBitmap,planeX,planeY,mPaint);
        if (count%10==0) {
            Bullet bullet = new Bullet(context, planeX + 33, planeY);
            bullets.add(bullet);
        }
        for (int i=0;i<bullets.size();i++){
            Bullet bullet1=bullets.get(i);
            bullet1.move(canvas,mPaint);
            if (bullet1.y<0){
                bullets.remove(bullet1);
            }
        }
        Random random=new Random();
        float ran=random.nextInt(canvas.getWidth());
        if (count%15==0) {
            Enemy enemy = new Enemy(context, ran, 0);
            enemies.add(enemy);
        }
        for (int i=0;i<enemies.size();i++){
            Enemy enemy1=enemies.get(i);
            enemy1.move(canvas,mPaint,10);
            if (enemy1.y>canvas.getHeight()){
                bullets.remove(enemy1);
            }
        }
        count++;
        invalidate();
    }

}
