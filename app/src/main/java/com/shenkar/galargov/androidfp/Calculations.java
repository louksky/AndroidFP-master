package com.shenkar.galargov.androidfp;

class Calculations {
    public static long calculateCarLocation(long deltaT, float x0, int velocity) {
        return ((long) (x0 + velocity * deltaT / Constants.SPEED_FACTOR));
    }

    public static boolean calculateTangentLineAndCar(int aLine, int bLine, int aCar, int bCar, long radius) {
        long a = aCar * aCar + bCar * bCar * aLine * aLine;
        long b = 2 * bCar * bCar * aLine * bLine;
        long c = bCar * bCar * bLine * bLine - (radius * radius);
        long x1;
        long x2;
        long sq = b * b - 4 * a * c;
        if (sq <= 0) {
            return false;
        }
        else {
            x1 = (long)((-b + Math.sqrt(sq)) / (2 * a));
            x2 = (long)((-b - Math.sqrt(sq)) / (2 * a));
            return true;
        }
    }

    public static boolean calculateTouchCarAndLine(long xCar, long yCar, long aLine, long bLine, long radius) {
        long left = (yCar - aLine * xCar - bLine) * (yCar - aLine * xCar - bLine);
        long right = radius * radius * (1 + aLine * aLine);
        if (left == right) {
            return true;
        }
        else {
            return false;
        }
    }
}


//
//    int aLine = (y2-y1)/(x2-x1);
//    int bLine = (y2 - y1) - aLine(x2 -x1);
//
//
//
//    if (Calculations.calculateTouchCarAndLine(int myx, int myy, int aLine, int bLine, int radius) == true) {
//        cou++;
//        canvas.drawText("Ca "+cou,this.getWidth() - 400,70,textpaint);
//    }