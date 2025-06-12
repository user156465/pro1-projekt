package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    private static Zlomek propagace(Zlomek z1, Zlomek z2) throws ArithmeticException
    {
        Zlomek vysl;
        try
        {
            vysl = z1.vydel(z2);
        }
        catch (ArithmeticException e)
        {
            System.out.println("Deleni nulou");
            throw e;
        }
        finally {
            vysl = new Zlomek(1);
        }
        return vysl;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Zlomek z1;
        try {
            z1 = new Zlomek(9,12);
        }
        catch (ArithmeticException e) {
            z1 = new Zlomek(1);
            System.out.println(e.getMessage());
            System.out.println("Nahrazujeme neplatny zlomek hodnotou 1");
        }

        Zlomek z2;
        try {
            z2 = new Zlomek(0,-6);
        }
        catch (ArithmeticException e) {
            z2 = new Zlomek(1);
            System.out.println(e.getMessage());
            System.out.println("Nahrazujeme neplatny zlomek hodnotou 1");
        }

        Zlomek soucet = main.OperaceSeZlomky.secti(z1,z2);
        Zlomek rozdil = main.OperaceSeZlomky.odecti(z1,z2);
        Zlomek soucin = z1.nasob(z2);
        Zlomek podil;

        try {
            podil = z1.vydel(z2);
        }
        catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            podil = new Zlomek(1);
        }

        System.out.println(z1);
        System.out.println(z2);

        int n = 10;
        ArrayList<Zlomek> zlomky = new ArrayList<>(n);

        for (int i = n-1; i >=0; i--) {
            zlomky.add(new Zlomek(i,i+1));
        }
        System.out.println(zlomky);
        zlomky.add(z1);
        System.out.println(zlomky);
        Zlomek z3 = new Zlomek(3,4);
        int index = zlomky.lastIndexOf(z3);
        boolean pritomen = zlomky.contains(z3);
        System.out.println(pritomen);
        System.out.println("index = " + index);
        Zlomek.saveToFile("zlomky.txt", zlomky);
        Collections.sort(zlomky);
        System.out.println(zlomky);
        zlomky.clear();
        zlomky = Zlomek.readFromFile("zlomky.txt");
        System.out.println(zlomky);
    }
}
