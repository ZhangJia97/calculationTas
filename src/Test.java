public class Test {
    private static int mySqrt(int x) {
        double t=(double)x;//(double)可以省
        double x0=x;
        x0=x0/2+t/(2*x0);
        while(Math.abs(x0*x0-t)>0.00001)
            x0=x0/2+t/(2*x0);
        System.out.println(x0);
        return (int)x0;//double 转int类型必须使用强制类型转化
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(2));
    }
}
