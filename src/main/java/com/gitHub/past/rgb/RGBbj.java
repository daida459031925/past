package com.gitHub.past.rgb;

/**
 * 两个rgb颜色比较  越大越不一样
 * 颜色相近算法
 */
public class RGBbj {
    public static void main(String[] args) {

        System.out.println(ColourDistance(new RGB(0,0,0),new RGB(0,0,0)));
    }

    static double ColourDistance(RGB e1, RGB e2)
    {
        long rmean = ( (long)e1.getR() + (long)e2.getR() ) / 2;
        long r = (long)e1.getR() - (long)e2.getR();
        long g = (long)e1.getG() - (long)e2.getG();
        long b = (long)e1.getB() - (long)e2.getB();
        return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
    }
}
