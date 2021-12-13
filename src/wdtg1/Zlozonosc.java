package wdtg1;

import java.util.Random;
import static java.lang.Math.pow;

public class Zlozonosc {

    static double sumujWszystkieMozliwosci = 0;
    static double sumujWszystkieRuchy = 0;

    static double ZlozonoscMetoda1(int wersjaGry, boolean czyPokazac) {
        double wynik = Zlozonosc.graj(Funkcje.ilosc, wersjaGry);
        System.out.print("Zlozonosc gry (metoda1): ");
        wynik = Funkcje.zaokraglenie(wynik);
        return wynik;
    }

    static double ZlozonoscMetoda2(int wersjaGry) {
        double wszystkieMozliwosci = pow(3, 9);
        double StosunekPoprawnychUstawien = Funkcje.SPU(Funkcje.ilosc, wersjaGry);
        double wynik = wszystkieMozliwosci * StosunekPoprawnychUstawien;
        System.out.print("Zlozonosc gry (metoda2): ");
        wynik = Funkcje.zaokraglenie(wynik);
        return wynik;
    }

    static double graj(double ilosc, int wersjaGry) {
        double parB = 0, parD = 0;
        int[][] tab = new int[3][3];
        Random r = new Random();
        int n, i;
        int ileRuchow, ileMozliwosci;
        System.out.println("========================================================");
        for (int gra = 0; gra < ilosc; gra++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = 0;
                }
            }
            ileMozliwosci = 9;
            ileRuchow = 0;
            Zlozonosc.sumujWszystkieMozliwosci = 0;
            Zlozonosc.sumujWszystkieMozliwosci += ileMozliwosci;
            if (Funkcje.czyPokazac) {
                System.out.println("Gra 'Random' nr " + (gra + 1) + ":");
                System.out.println();
            }
            do {
                n = 0;
                i = 0;
                do {
                    n = r.nextInt(3);
                    i = r.nextInt(3);
                } while (tab[n][i] != 0);
                tab[n][i] = (ileRuchow % 2) + 1;
                ileMozliwosci--;
                Zlozonosc.sumujWszystkieMozliwosci += ileMozliwosci;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if ((Funkcje.czyWygrywa(tab, 1) == false
                        && Funkcje.czyWygrywa(tab, 2) == false)
                        && Funkcje.brakRuchow == false) {
                    ileRuchow++;
                    Zlozonosc.sumujWszystkieRuchy++;
                } else {
                    ileRuchow++;
                    Zlozonosc.sumujWszystkieRuchy++;
                    break;
                }
            } while (ileRuchow < wersjaGry * 2);
            if (Funkcje.czyWygrywa(tab, 1) == false
                    && Funkcje.czyWygrywa(tab, 2) == false
                    && Funkcje.brakRuchow == false) {
                do {
                    Zlozonosc.wykonajPrzesuniecie(tab, 1);
                    Zlozonosc.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    if (Funkcje.czyWygrywa(tab, 1)) {
                        break;
                    }
                    Zlozonosc.wykonajPrzesuniecie(tab, 2);
                    Zlozonosc.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                } while (Funkcje.czyWygrywa(tab, 1) == false
                        && Funkcje.czyWygrywa(tab, 2) == false
                        && Funkcje.brakRuchow == false);
            }
            double srednia = Zlozonosc.sumujWszystkieMozliwosci / ileRuchow;
            Zlozonosc.sumujWszystkieMozliwosci = 0;
            parB += srednia;
            double sredniaD = Zlozonosc.sumujWszystkieRuchy;
            Zlozonosc.sumujWszystkieRuchy = 0;
            parD += sredniaD;
        }
        parB /= Funkcje.ilosc;
        parD /= Funkcje.ilosc;
        parB = Funkcje.zaokraglenie(parB);
        parD = Funkcje.zaokraglenie(parD);
        System.out.println("gra Random:");
        System.out.println("parametr b: " + parB);
        System.out.println("parametr d: " + parD);
        double wynik = pow(parB, parD);
        return wynik;
    }

    static void wykonajPrzesuniecie(int tab[][], int gracz) {
        int ileMozliwosci = 0;
        Boolean ruchy[] = new Boolean[32];
        Funkcje.znajdzDostepneRuchy(tab, ruchy, gracz);
        Funkcje.brakRuchow = true;
        for (int i = 0; i < 32; i++) {
            if (ruchy[i] == true) {
                Funkcje.brakRuchow = false;
                ileMozliwosci++;
            }
        }
        int ruch = 0;
        do {
            Random losujRuch = new Random();
            ruch = losujRuch.nextInt(32);
        } while (ruchy[ruch] == false && Funkcje.brakRuchow == false);
        Zlozonosc.sumujWszystkieMozliwosci += ileMozliwosci;
        if (Funkcje.brakRuchow == false) {
            Funkcje.przesun(tab, ruch);
        }
        for (int x = 0; x < 32; x++) {
            ruchy[x] = false;
        }
        Funkcje.brakRuchow = false;
    }
}
