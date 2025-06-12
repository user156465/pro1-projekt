package main;

public class Util {
    public static int nsd(int a, int b)
    {
        while (b != 0)
        {
            int c = a%b;
            a = b;
            b = c;
        }
        return a;
    }
}

