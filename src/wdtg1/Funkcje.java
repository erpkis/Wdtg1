package wdtg1;

import java.util.Scanner;
import java.util.Random;

public class Funkcje {

    static boolean czyPokazac = false;
    static boolean brakRuchow = false;
    static int ilosc = 0;
    static String algorytm = "brak";
    static boolean[] ruchy = new boolean[32];

    static int ilePartii() {
        System.out.println("Ile partii rozegrac?");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            Funkcje.ilosc = sc.nextInt();
            return Funkcje.ilosc;
        } else {
            return 0;
        }
    }

    static void pokazPlansze(int[][] tab, boolean czyPokazac) {
        if (czyPokazac) {
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    System.out.print(tab[a][b]);
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    static boolean czyWygrywa(int[][] tab, int gracz) {
        boolean wygrana = false;
        for (int i = 0; i < 3; i++) {
            if (tab[0][i] == gracz && tab[1][i] == gracz && tab[2][i] == gracz) {
                wygrana = true;
                break;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tab[i][0] == gracz && tab[i][1] == gracz && tab[i][2] == gracz) {
                wygrana = true;
                break;
            }
        }
        if (tab[1][1] == gracz && tab[0][0] == gracz && tab[2][2] == gracz) {
            wygrana = true;
        } else if (tab[1][1] == gracz && tab[2][0] == gracz && tab[0][2] == gracz) {
            wygrana = true;
        }
        return wygrana;
    }

    static boolean czyPoprawne(int ileBialych, int ileCzarnych, int wersjaGry, int[][] tab) {
        boolean poprawne = true;
        if (ileCzarnych > wersjaGry || ileBialych > wersjaGry) {
            poprawne = false;
        }
        if (ileCzarnych - ileBialych > 1 || ileBialych - ileCzarnych > 1) {
            poprawne = false;
        }
        if (czyWygrywa(tab, 1) == true && czyWygrywa(tab, 2) == true) {
            poprawne = false;
        }
        if (czyWygrywa(tab, 1) == true && ileBialych < ileCzarnych) {
            poprawne = false;
        }
        if (czyWygrywa(tab, 2) == true && ileCzarnych < ileBialych) {
            poprawne = false;
        }
        return poprawne;
    }

    static double SPU(double ilosc, int wersjaGry) {                            //funkcja liczy Stosunek Poprawnych Ustawien pionkow na planszy do wszystkich mozliwych ustawien pionkow na planszy
        double ilePoprawnych = 0;
        for (int ile = 1; ile <= ilosc; ile++) {
            int ileBialych = 0, ileCzarnych = 0;
            int[][] tab = new int[3][3];
            Random r = new Random();
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    tab[n][i] = r.nextInt(3);
                    if (tab[n][i] == 1) {
                        ileBialych++;
                    } else if (tab[n][i] == 2) {
                        ileCzarnych++;
                    }

                }
            }
            if (Funkcje.czyPoprawne(ileBialych, ileCzarnych, wersjaGry, tab)) {
                ilePoprawnych++;
            }
        }
        double wynik = ilePoprawnych / ilosc;
        return wynik;
    }

    static void wykonajPrzesuniecie(int tab[][], int gracz, int wersjaGry) {
        int ileMozliwosci = 0;
        for (int x = 0; x < 9 - (wersjaGry * 2); x++) {
            znajdzDostepneRuchy(tab, gracz);
        }
        Funkcje.brakRuchow = true;
        for (int i = 0; i < 32; i++) {
            if (Funkcje.ruchy[i] == true) {
                Funkcje.brakRuchow = false;
                ileMozliwosci++;
            }
        }
        switch (algorytm) {
            case "brak":
                int ruch = 0;
                do {
                    Random losujRuch = new Random();
                    ruch = losujRuch.nextInt(32);
                } while (Funkcje.ruchy[ruch] == false && Funkcje.brakRuchow == false);
                Zlozonosc.sumujWszystkieMozliwosci += ileMozliwosci;
                if (Funkcje.brakRuchow == false) {
                    Funkcje.przesun(tab, ruch);
                }
                for (int x = 0; x < 32; x++) {                                  //reset
                    Funkcje.ruchy[x] = false;
                }
                Funkcje.brakRuchow = false;
                break;
            case "minimax":

                break;
            case "megamax":
                //TODO
                break;
        }
    }

    static void znajdzDostepneRuchy(int[][] tab, int gracz) {
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                if (tab[n][i] == 0) {
                    String przypadek = "" + n + i;
                    switch (przypadek) {
                        case "00":
                            if (tab[0][1] == gracz) { // [0][1] -> [0][0]
                                Funkcje.ruchy[0] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][0]
                                Funkcje.ruchy[1] = true;
                            }
                            if (tab[1][0] == gracz) { // [1][0] -> [0][0]
                                Funkcje.ruchy[2] = true;
                            }
                            break;
                        case "01":
                            if (tab[0][0] == gracz) { // [0][0] -> [0][1]
                                Funkcje.ruchy[3] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][1]
                                Funkcje.ruchy[4] = true;
                            }
                            if (tab[0][2] == gracz) { // [0][2] -> [0][1]
                                Funkcje.ruchy[5] = true;
                            }
                            break;
                        case "02":
                            if (tab[0][1] == gracz) { // [0][1] -> [0][2]
                                Funkcje.ruchy[6] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][2]
                                Funkcje.ruchy[7] = true;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [0][2]
                                Funkcje.ruchy[8] = true;
                            }
                            break;
                        case "10":
                            if (tab[0][0] == gracz) { // [0][0] -> [1][0]
                                Funkcje.ruchy[9] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [1][0]
                                Funkcje.ruchy[10] = true;
                            }
                            if (tab[2][0] == gracz) { // [2][0] -> [1][0]
                                Funkcje.ruchy[11] = true;
                            }
                            break;
                        case "11":
                            if (tab[0][0] == gracz) { // [0][0] -> [1][1]
                                Funkcje.ruchy[12] = true;
                            }
                            if (tab[0][1] == gracz) { // [0][1] -> [1][1]
                                Funkcje.ruchy[13] = true;
                            }
                            if (tab[0][2] == gracz) { // [0][2] -> [1][1]
                                Funkcje.ruchy[14] = true;
                            }
                            if (tab[1][0] == gracz) { // [1][0] -> [1][1]
                                Funkcje.ruchy[15] = true;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [1][1]
                                Funkcje.ruchy[16] = true;
                            }
                            if (tab[2][0] == gracz) { // [2][0] -> [1][1]
                                Funkcje.ruchy[17] = true;
                            }
                            if (tab[2][1] == gracz) { // [2][1] -> [1][1]
                                Funkcje.ruchy[18] = true;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [1][1]
                                Funkcje.ruchy[19] = true;
                            }
                            break;
                        case "12":
                            if (tab[0][2] == gracz) { // [0][2] -> [1][2]
                                Funkcje.ruchy[20] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [1][2]
                                Funkcje.ruchy[21] = true;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [1][2]
                                Funkcje.ruchy[22] = true;
                            }
                            break;
                        case "20":
                            if (tab[1][0] == gracz) { // [1][0] -> [2][0]
                                Funkcje.ruchy[23] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][0]
                                Funkcje.ruchy[24] = true;
                            }
                            if (tab[2][1] == gracz) { // [2][1] -> [2][0]
                                Funkcje.ruchy[25] = true;
                            }
                            break;
                        case "21":
                            if (tab[2][0] == gracz) { // [2][0] -> [2][1]
                                Funkcje.ruchy[26] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][1]
                                Funkcje.ruchy[27] = true;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [2][1]
                                Funkcje.ruchy[28] = true;
                            }
                            break;
                        case "22":
                            if (tab[2][1] == gracz) { // [2][1] -> [2][2]
                                Funkcje.ruchy[29] = true;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][2]
                                Funkcje.ruchy[30] = true;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [2][2]
                                Funkcje.ruchy[31] = true;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    static void przesun(int[][] tab, int ruch) {
        switch (ruch) {
            //////////////////////////
            case 0:
                tab[0][0] = tab[0][1];
                tab[0][1] = 0;
                break;
            case 1:
                tab[0][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 2:
                tab[0][0] = tab[1][0];
                tab[1][0] = 0;
                break;
            //////////////////////////
            case 3:
                tab[0][1] = tab[0][0];
                tab[0][0] = 0;
                break;
            case 4:
                tab[0][1] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 5:
                tab[0][1] = tab[0][2];
                tab[0][2] = 0;
                break;
            //////////////////////////
            case 6:
                tab[0][2] = tab[0][1];
                tab[0][1] = 0;
                break;
            case 7:
                tab[0][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 8:
                tab[0][2] = tab[1][2];
                tab[1][2] = 0;
                break;
            //////////////////////////
            case 9:
                tab[1][0] = tab[0][0];
                tab[0][0] = 0;
                break;
            case 10:
                tab[1][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 11:
                tab[1][0] = tab[2][0];
                tab[2][0] = 0;
                break;
            //////////////////////////
            case 12:
                tab[1][1] = tab[0][0];
                tab[0][0] = 0;
                break;
            case 13:
                tab[1][1] = tab[0][1];
                tab[0][1] = 0;
                break;
            case 14:
                tab[1][1] = tab[0][2];
                tab[0][2] = 0;
                break;
            case 15:
                tab[1][1] = tab[1][0];
                tab[1][0] = 0;
                break;
            case 16:
                tab[1][1] = tab[1][2];
                tab[1][2] = 0;
                break;
            case 17:
                tab[1][1] = tab[2][0];
                tab[2][0] = 0;
                break;
            case 18:
                tab[1][1] = tab[2][1];
                tab[2][1] = 0;
                break;
            case 19:
                tab[1][1] = tab[2][2];
                tab[2][2] = 0;
                break;
            //////////////////////////
            case 20:
                tab[1][2] = tab[0][2];
                tab[0][2] = 0;
                break;
            case 21:
                tab[1][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 22:
                tab[1][2] = tab[2][2];
                tab[2][2] = 0;
                break;
            //////////////////////////
            case 23:
                tab[2][0] = tab[1][0];
                tab[1][0] = 0;
                break;
            case 24:
                tab[2][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 25:
                tab[2][0] = tab[2][1];
                tab[2][1] = 0;
                break;
            //////////////////////////
            case 26:
                tab[2][1] = tab[2][0];
                tab[2][0] = 0;
                break;
            case 27:
                tab[2][1] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 28:
                tab[2][1] = tab[2][2];
                tab[2][2] = 0;
                break;
            //////////////////////////
            case 29:
                tab[2][2] = tab[2][1];
                tab[2][1] = 0;
                break;
            case 30:
                tab[2][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 31:
                tab[2][2] = tab[1][2];
                tab[1][2] = 0;
                break;
            //////////////////////////
        }
    }
}
