package com.jue.getdimen;

import android.animation.TypeEvaluator;

/**
 * 可以看到，PointEvaluator同样实现了TypeEvaluator接口并重写了evaluate()方法。
 * 其实evaluate()方法中的逻辑还是非常简单的，先是将startValue和endValue强转成Point对象，
 * 然后同样根据fraction来计算当前动画的x和y的值，最后组装到一个新的Point对象当中并返回。
 */
public class PointEvaluator implements TypeEvaluator {
  
    @Override  
    public Object evaluate(float fraction, Object startValue, Object endValue) {  
        Point startPoint = (Point) startValue;  
        Point endPoint = (Point) endValue;  
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());  
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());  
        Point point = new Point(x, y);  
        return point;  
    }  
  
}  