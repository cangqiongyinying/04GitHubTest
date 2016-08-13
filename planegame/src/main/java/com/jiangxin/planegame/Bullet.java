package com.jiangxin.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by my on 2016/8/11.
 */
public class Bullet {
    public float x,y;
    private Context context;
    private Bitmap bulletBitmap;
    public Bullet(Context context,float x,float y){
        this.context=context;
        this.x=x;
        this.y=y;
        this.bulletBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet1);
    }

    public void move(Canvas canvas, Paint paint){
        this.y -=15;
        canvas.drawBitmap(bulletBitmap,x,y,paint);
    }
}
