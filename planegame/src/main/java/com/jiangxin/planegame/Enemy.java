package com.jiangxin.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by my on 2016/8/11.
 */
public class Enemy {
    public float x,y;
    private Context context;
    private Bitmap bitmap;

    public Enemy(Context context,float x, float y){
        this.context=context;
        this.x=x;
        this.y=y;
        this.bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy1_fly_1);
    }

    public void move(Canvas canvas, Paint paint,float speed){
        y+=speed;
        canvas.drawBitmap(bitmap,x,y,paint);
    }
}
