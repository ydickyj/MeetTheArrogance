package com.jue.getdimen;

/**
 * 好的，那FloatEvaluator是系统内置好的功能，并不需要我们自己去编写，但介绍它的实现方法是要为我们后面的功能铺路的。
 * 前面我们使用过了ValueAnimator的ofFloat()和ofInt()方法，分别用于对浮点型和整型的数据进行动画操作的，
 * 但实际上ValueAnimator中还有一个ofObject()方法，是用于对任意对象进行动画操作的。
 * 但是相比于浮点型或整型数据，对象的动画操作明显要更复杂一些，因为系统将完全无法知道如何从初始对象过度到结束对象，
 * 因此这个时候我们就需要实现一个自己的TypeEvaluator来告知系统如何进行过度。
 下面来先定义一个Point类，如下所示：
 */


public class Point {
  
    private float x;  
  
    private float y;  
  
    public Point(float x, float y) {  
        this.x = x;  
        this.y = y;  
    }  
  
    public float getX() {  
        return x;  
    }  
  
    public float getY() {  
        return y;  
    }  
  
}

/**
 * Point类非常简单，只有x和y两个变量用于记录坐标的位置，并提供了构造方法来设置坐标，以及get方法来获取坐标。
 */
