package com.gitHub.past.calculation;

import com.gitHub.past.common.SysFun;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PointUtil {

    /**
     * 实现点到原点的距离非静态方法
     */
    public static Double pointToOriPoint(Point p) {
        // 得到x的平方
        double quadraticX = Math.pow(p.getX(), 2);
        // 得到y的平方
        double quadraticY = Math.pow(p.getY(), 2);
        // 平方和
        double quadraticSum = quadraticX + quadraticY;
        // 对平方和进行求平方根
        return Math.sqrt(quadraticSum);
    }

    /**
     * 计算两点间距离公式
     */
    public static Double distance(Point p1,Point p2){
        return Math.sqrt(
                Math.abs(
                        (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) +
                        (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
                )
        );
    }

    public static void main(String[] args) {
        List<Point> points = Arrays.asList(new Point(2, 2), new Point(1, 1), new Point(3, 3), new Point(4, 4));
        points = points.stream().sorted(Comparator.comparing(PointUtil::pointToOriPoint)).collect(Collectors.toList());
        SysFun.sysPrintln.accept(points);
    }
}
