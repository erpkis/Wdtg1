package wdtg1;

import java.util.Random;
import static java.lang.Math.pow;

public class Zlozonosc {

    static double sumujWszystkieMozliwosci = 0;
    static double sumujWszystkieRuchy = 0;

    static double ZlozonoscMetoda1(int wersjaGry, boolean czyPokazac) {
        //Funkcje.czyPokazac = czyPokazac;
        double wynik = Zlozonosc.graj(Funkcje.ilosc, wersjaGry);
        System.out.print("Zlozonosc gry (metoda1): ");
        return wynik;
    }

    static double ZlozonoscMetoda2(int wersjaGry) {
        double wszystkieMozliwosci = pow(3, 9);
        double StosunekPoprawnychUstawien = Funkcje.SPU(Funkcje.ilosc, wersjaGry);
        double wynik = wszystkieMozliwosci * StosunekPoprawnychUstawien;
        System.out.print("Zlozonosc gry (metoda2): ");
        return wynik;
    }

    static double graj(double ilosc, int wersjaGry) {
        double parB = 0, parD = 0;
        int[][] tab = new int[3][3];
        Random r = new Random();
        int n, i;
        int ileRuchow, ileMozliwosci;
        for (int gra = 0; gra < ilosc; gra++) {
            for (int x = 0; x < 3; x++) {                                       //resetowanie planszy
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
                if ((Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false) && Funkcje.brakRuchow == false) {
                    ileRuchow++;
                    Zlozonosc.sumujWszystkieRuchy++;
                } else {
                    ileRuchow++;
                    Zlozonosc.sumujWszystkieRuchy++;
                    break;
                }
            } while (ileRuchow < wersjaGry * 2);
            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false) {
                do {
                    Funkcje.wykonajPrzesuniecie(tab, 1, wersjaGry);
                    Zlozonosc.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    if (Funkcje.czyWygrywa(tab, 1)) {
                        break;
                    }
                    Funkcje.wykonajPrzesuniecie(tab, 2, wersjaGry);
                    Zlozonosc.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false);
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
        System.out.println("parametr b: " + parB);
        System.out.println("parametr d: " + parD);
        double wynik = pow(parB, parD);
        return wynik;
    }
}
