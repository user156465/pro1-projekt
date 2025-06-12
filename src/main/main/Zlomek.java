package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Zlomek implements Comparable{
    private int citatel;
    private int jmenovatel;

    public Zlomek(int citatel, int jmenovatel) throws ArithmeticException {
        this(citatel,jmenovatel,true);
    }

    public Zlomek(int citatel, int jmenovatel, boolean upraveny) throws ArithmeticException
    {
        if (jmenovatel == 0)
            throw new ArithmeticException("Nulovy jmenovatel");
        this.jmenovatel = jmenovatel;
        this.citatel = citatel;
        if (upraveny)
        {
            vyresZnamenko();
            zkrat();
        }
    }
    public Zlomek(String s)
    {
        s= s.replaceAll("[()]","");
        String [] r = s.split("/");
        if (r.length == 1)
            this.jmenovatel = 1;
        else
            this.jmenovatel = Integer.parseInt(r[1]);

        this.citatel = Integer.parseInt(r[0]);

        vyresZnamenko();
        zkrat();
    }

    public Zlomek(int citatel){
        this(citatel,1);
    }

    public void zkrat() {
        int nsd = main.Util.nsd(Math.abs(this.citatel), Math.abs(this.jmenovatel));
        this.citatel /= nsd;
        this.jmenovatel /= nsd;
    }

    private void vyresZnamenko() {
        if (this.jmenovatel < 0)
        {
            this.jmenovatel *= -1;
            this.citatel *= -1;
        }
    }

    public int getCitatel() {
        return citatel;
    }

    public int getJmenovatel() {
        return jmenovatel;
    }


    public Zlomek secti(Zlomek other) {
        int jmenovatel = this.jmenovatel * other.jmenovatel;
        int citatel = this.citatel * other.jmenovatel + this.jmenovatel*other.citatel;

        return new Zlomek(citatel, jmenovatel);
    }

    public Zlomek odecti(Zlomek other) {
        int jmenovatel = this.jmenovatel * other.jmenovatel;
        int citatel = this.citatel * other.jmenovatel - this.jmenovatel*other.citatel;

        return new Zlomek(citatel, jmenovatel);
    }

    public Zlomek nasob(Zlomek other) {
        int jmenovatel = this.jmenovatel * other.jmenovatel;
        int citatel = this.citatel * other.citatel;

        return new Zlomek(citatel, jmenovatel);
    }

    public Zlomek vydel(Zlomek other) throws ArithmeticException {
        int jmenovatel = this.jmenovatel * other.citatel;
        int citatel = this.citatel * other.jmenovatel;

        if (jmenovatel == 0)
            throw new ArithmeticException("Deleni nulou!");
        else
           return new Zlomek(citatel, jmenovatel);
    }

    public static void saveToFile(String filename, Collection<Zlomek> zlomky)
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            for (Zlomek z : zlomky)
                bufferedWriter.write(z.toString() + "\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<Zlomek> readFromFile(String filename) {
        ArrayList<Zlomek> zlomky = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                zlomky.add(new Zlomek(line.trim()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return zlomky;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Zlomek z = (Zlomek) o;

        return this.citatel * z.jmenovatel == z.citatel * this.jmenovatel;
    }

    @Override
    public int compareTo(Object other) {
        Zlomek z = (Zlomek) other;
        if (this.equals(other))
            return 0;
        else if (this.citatel*z.jmenovatel < z.citatel*this.jmenovatel)
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return jmenovatel != 1 ? "(" + citatel + ")/(" + jmenovatel + ")" : "(" + citatel + ")";
    }
}

