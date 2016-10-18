package com.jue.getdimen;

import android.animation.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 1.基本上还是很简单的，总共也没几行代码。
 * 首先在自定义View的构造方法当中初始化了一个Paint对象作为画笔，并将画笔颜色设置为蓝色，接着在onDraw()方法当中进行绘制。
 * 这里我们绘制的逻辑是由currentPoint这个对象控制的，如果currentPoint对象不等于空，那么就调用drawCircle()方法在currentPoint的坐标位置画出一个半径为50的圆，
 * 如果currentPoint对象是空，那么就调用startAnimation()方法来启动动画。
 那么我们来观察一下startAnimation()方法中的代码，其实大家应该很熟悉了，就是对Point对象进行了一个动画操作而已。
 这里我们定义了一个startPoint和一个endPoint，坐标分别是View的左上角和右下角，并将动画的时长设为5秒。
 然后有一点需要大家注意的，就是我们通过监听器对动画的过程进行了监听，每当Point值有改变的时候都会回调onAnimationUpdate()方法。
 在这个方法当中，我们对currentPoint对象进行了重新赋值，并调用了invalidate()方法，这样的话onDraw()方法就会重新调用，并且由于currentPoint对象的坐标已经改变了，那么绘制的位置也会改变，于是一个平移的动画效果也就实现了。
 */

/**
 * 2.大家应该都还记得，ObjectAnimator内部的工作机制是通过寻找特定属性的get和set方法，然后通过方法不断地对值进行改变，从而实现动画效果的。
 * 因此我们就需要在MyAnimView中定义一个color属性，并提供它的get和set方法。这里我们可以将color属性设置为字符串类型，使用#RRGGBB这种格式来表示颜色值，
 * *注意在setColor()方法当中，我们编写了一个非常简单的逻辑，就是将画笔的颜色设置成方法参数传入的颜色，然后调用了invalidate()方法。
 * 这段代码虽然只有三行，但是却执行了一个非常核心的功能，就是在改变了画笔颜色之后立即刷新视图，然后onDraw()方法就会调用。
 * 在onDraw()方法当中会根据当前画笔的颜色来进行绘制，这样颜色也就会动态进行改变了。
 * 代码如下所示：
 */


public class MyAnimView extends View {
  
    public static final float RADIUS = 50f;  
  
    private Point currentPoint;  
  
    private Paint mPaint;

    private Paint mNewPaint;

    private String color;
    ValueAnimator anim2;
    ValueAnimator anim;

    private boolean isTrue =false;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mPaint.setColor(Color.BLUE);
        mNewPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mNewPaint.setColor(Color.BLACK);
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {  
            currentPoint = new Point(RADIUS, RADIUS);  
            drawCircle(canvas);  
            startAnimation();  
        }else if(isTrue) {
            isTrue =false;
            anim = new ValueAnimator();
            anim2 = new ValueAnimator();
            startAnimation();
            drawCircle(canvas);
        }else {
            drawCircle(canvas);
        }  
    }  
  
    private void drawCircle(Canvas canvas) {  
        float x = currentPoint.getX();  
        float y = currentPoint.getY();  
        canvas.drawCircle(100, 100, RADIUS, mNewPaint);
        canvas.drawLine(0,0,x,y,mNewPaint);
    }  
  
    private void startAnimation() {


        ValueAnimator mAnimatorAlpha = ValueAnimator.ofInt(255,0);
        mAnimatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (int)animation.getAnimatedValue();
                Log.e("123132:",""+a);
                mNewPaint.setAlpha((int)animation.getAnimatedValue());
                invalidate();
            }
        });
        mAnimatorAlpha.setDuration(2000);
        mAnimatorAlpha.start();

//        Point startPoint = new Point(RADIUS, RADIUS);
//        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
//        ValueAnimator anim1 = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//
//        anim = ValueAnimator.ofObject(new ColorEvaluator(),
//                "#FF0000", "#0000FF" );
//        anim2 = ValueAnimator.ofObject( new ColorEvaluator(),
//                "#0000FF", "#FF0000");
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(anim1).with(anim2);
//        animSet.setDuration(5000);
//        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                currentPoint = (Point) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//
//        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mNewPaint.setColor(Color.parseColor((String) animation.getAnimatedValue()));
//                invalidate();
//            }
//        });
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mNewPaint.setColor(Color.parseColor((String) animation.getAnimatedValue()));
//                invalidate();
//            }
//        });
//
//        anim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                    isTrue = true;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        anim2.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                anim.setDuration(5000);
//                    anim.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        anim2.start();
    }  
  
} 