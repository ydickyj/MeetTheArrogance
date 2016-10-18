package com.jue.getdimen;

import android.animation.TypeEvaluator;

/**
 * 在开始动手之前，我们还需要掌握另外一个知识点，就是TypeEvaluator的用法。
 * 可能在大多数情况下我们使用属性动画的时候都不会用到TypeEvaluator，
 * 但是大家还是应该了解一下它的用法，以防止当我们遇到一些解决不掉的问题时能够想起来还有这样的一种解决方案。
 那么TypeEvaluator的作用到底是什么呢？
 简单来说，就是告诉动画系统如何从初始值过度到结束值。
 我们在上一篇文章中学到的ValueAnimator.ofFloat()方法就是实现了初始值与结束值之间的平滑过度，那么这个平滑过度是怎么做到的呢？
 其实就是系统内置了一个FloatEvaluator，它通过计算告知动画系统如何从初始值过度到结束值，我们来看一下FloatEvaluator的代码实现：
 */
public class FloatEvaluator implements TypeEvaluator {
    public Object evaluate(float fraction, Object startValue, Object endValue) {  
        float startFloat = ((Number) startValue).floatValue();  
        return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);  
    }  
}

/**
 可以看到，FloatEvaluator实现了TypeEvaluator接口，然后重写evaluate()方法。
 evaluate()方法当中传入了三个参数，第一个参数fraction非常重要，这个参数用于表示动画的完成度的，我们应该根据它来计算当前动画的值应该是多少，
 第二第三个参数分别表示动画的初始值和结束值。
 那么上述代码的逻辑就比较清晰了，用结束值减去初始值，算出它们之间的差值，然后乘以fraction这个系数，再加上初始值，那么就得到当前动画的值了。
 */