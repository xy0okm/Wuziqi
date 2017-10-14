package com.fhlj.manmachine;

import android.graphics.Point;
import java.util.ArrayList;
import java.util.List;

class AI{
    
    private static AI ai;
    private List<Point> ownList;
    private List<Point> rivalList;
    
    static AI getInstance(){
        if(ai == null){
            ai = new AI();
        }
        return ai;
    }
    
    Point getAIStep(List<Point> ownList, List<Point> rivalList){
        this.ownList = ownList;
        this.rivalList = rivalList;
        return getMaxLevelEmptyPoint(getEmptyPointList());
    }
    
    private Point getMaxLevelEmptyPoint(List<Point> emptyPointList){
        Point result = new Point();
        int maxLevel = 0;
        int maxCount = 0;
        for (Point p : emptyPointList){
            int levelHorizontal = checkHorizontal(p.x, p.y, rivalList);
            int levelRightDiagonal = checkRightDiagonal(p.x, p.y, rivalList);
            int levelLeftDiagonal = checkLeftDiagonal(p.x, p.y, rivalList);
            int levelVertical = checkVertIcal(p.x, p.y, rivalList);
            int level = getMaxLevel(levelHorizontal, levelRightDiagonal, levelLeftDiagonal, levelVertical);
            int count = getMaxLevelAmount(maxLevel, levelHorizontal, levelRightDiagonal, levelLeftDiagonal, levelVertical);
            if(level > maxLevel){
                maxLevel = level;
                maxCount = count;
                result = p;
            }else if(level == maxLevel){
                if(count > maxCount){
                    maxCount = count;
                    result = p;
                }
            }
        }
        return result;
    }
    
    private int getMaxLevelAmount(int max, int levelHorizontal, int levelRightDiagonal, int levelLeftDiagonal, int levelVertical){
        int count = 0;
        if(max == levelHorizontal){
            count++;
        }
        if(max == levelRightDiagonal){
            count++;
        }
        if(max == levelLeftDiagonal){
            count++;
        }
        if(max == levelVertical){
            count++;
        }
        return count;
    }
    
    private int getMaxLevel(int levelHorizontal, int levelRightDiagonal, int levelLeftDiagonal, int levelVertical){
        int result = levelHorizontal;
        if(result < levelRightDiagonal){
            result = levelRightDiagonal;
        }
        if(result < levelLeftDiagonal){
            result = levelLeftDiagonal;
        }
        if(result < levelVertical){
            result = levelVertical;
        }
        return result;
    }
    
    private List<Point> getEmptyPointList(){
        List<Point> EmptyPointList = new ArrayList<>();
        int i,j;
        for(i = 1; i <= ConstantUtil.MAX_LINE; i++){
            for(j = 1; j <= ConstantUtil.MAX_LINE; j++){
                if(checkIsEmptyPoint(i, j)){
                    EmptyPointList.add(new Point(i, j));
                }
            }
        }
        return EmptyPointList;
    }
    
    private boolean checkIsEmptyPoint(int x, int y){
        for (Point p : rivalList) {
            if(p.x == x && p.y == y){
                return false;
            }
        }
        for (Point p : ownList) {
            if(p.x == x && p.y == y){
                return false;
            }
        }
        return true;
    }
    
    private int checkHorizontal(int x, int y, List<Point> mWitharry2) {
		int count = 1;
		for (int i = 1; i < 5; i++) {
			if (mWitharry2.contains(new Point(x-i,y))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return count;
		for (int i = 1; i < 5; i++) {
			if (mWitharry2.contains(new Point(x+i,y))) {
				count++;
			}else {
				break;
			}
			if (count==5) return count;
		}
		return count;
	}

    private int checkRightDiagonal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x-i,y-i))) {
                count++;
            }else {
                break;
            }
        }
        if (count==5) return count;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x+i,y+i))) {
                count++;
            }else {
                break;
            }
            if (count==5) return count;
        }
        return count;
    }
    private int checkLeftDiagonal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x-i,y+i))) {
                count++;
            }else {
                break;
            }
        }
        if (count==5) return count;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x+i,y-i))) {
                count++;
            }else {
                break;
            }
            if (count==5) return count;
        }
        return count;
    }
    private int checkVertIcal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x,y-i))) {
                count++;
            }else {
                break;
            }
        }
        if (count==5) return count;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x,y+i))) {
                count++;
            }else {
                break;
            }
            if (count==5) return count;
        }
        return count;
    }
}