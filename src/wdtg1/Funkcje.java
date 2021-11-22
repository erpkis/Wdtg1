package wdtg1;

import java.util.Scanner;
import java.util.Random;

public class Funkcje {

    static boolean czyPokazac = false;
    static boolean brakRuchow = false;
    static int ilosc = 0;

    static void wybierzOpcje() {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            Wdtg1.opcja = sc.nextInt();
        } else {
            System.out.println("Wprowadzono bledna dana, sprobuj ponownie");
            wybierzOpcje();
        }
    }

    static int ilePartii() {
        System.out.println("Ile partii rozegrac?");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            Funkcje.ilosc = sc.nextInt();
            return Funkcje.ilosc;
        } else {
            return ilePartii();
        }
    }

    static boolean czyPokazacPrzebiegPartii() {
        System.out.println("Czy chcesz zobaczyc przebieg partii?");
        System.out.println("Wpisz 'true' jesli tak");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextBoolean()) {
            Funkcje.czyPokazac = sc.nextBoolean();
            return Funkcje.czyPokazac;
        } else {
            Funkcje.czyPokazac = false;
            return false;
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
        }
        if (tab[1][1] == gracz && tab[2][0] == gracz && tab[0][2] == gracz) {
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

    static double SPU(double ilosc, int wersjaGry) {
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

    static void znajdzDostepneRuchy(int[][] tab, Boolean ruchy[], int gracz) {
        for (int x = 0; x < 32; x++) {
            ruchy[x] = false;
        }
        Funkcje.brakRuchow = true;
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                if (tab[n][i] == 0) {
                    String przypadek = "" + n + i;
                    switch (przypadek) {
                        case "00":
                            if (tab[0][1] == gracz) { // [0][1] -> [0][0]
                                ruchy[0] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][0]
                                ruchy[1] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][0] == gracz) { // [1][0] -> [0][0]
                                ruchy[2] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "01":
                            if (tab[0][0] == gracz) { // [0][0] -> [0][1]
                                ruchy[3] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][1]
                                ruchy[4] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[0][2] == gracz) { // [0][2] -> [0][1]
                                ruchy[5] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "02":
                            if (tab[0][1] == gracz) { // [0][1] -> [0][2]
                                ruchy[6] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [0][2]
                                ruchy[7] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [0][2]
                                ruchy[8] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "10":
                            if (tab[0][0] == gracz) { // [0][0] -> [1][0]
                                ruchy[9] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [1][0]
                                ruchy[10] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][0] == gracz) { // [2][0] -> [1][0]
                                ruchy[11] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "11":
                            if (tab[0][0] == gracz) { // [0][0] -> [1][1]
                                ruchy[12] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[0][1] == gracz) { // [0][1] -> [1][1]
                                ruchy[13] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[0][2] == gracz) { // [0][2] -> [1][1]
                                ruchy[14] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][0] == gracz) { // [1][0] -> [1][1]
                                ruchy[15] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [1][1]
                                ruchy[16] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][0] == gracz) { // [2][0] -> [1][1]
                                ruchy[17] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][1] == gracz) { // [2][1] -> [1][1]
                                ruchy[18] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [1][1]
                                ruchy[19] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "12":
                            if (tab[0][2] == gracz) { // [0][2] -> [1][2]
                                ruchy[20] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [1][2]
                                ruchy[21] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [1][2]
                                ruchy[22] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "20":
                            if (tab[1][0] == gracz) { // [1][0] -> [2][0]
                                ruchy[23] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][0]
                                ruchy[24] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][1] == gracz) { // [2][1] -> [2][0]
                                ruchy[25] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "21":
                            if (tab[2][0] == gracz) { // [2][0] -> [2][1]
                                ruchy[26] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][1]
                                ruchy[27] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[2][2] == gracz) { // [2][2] -> [2][1]
                                ruchy[28] = true;
                                Funkcje.brakRuchow = false;
                            }
                            break;
                        case "22":
                            if (tab[2][1] == gracz) { // [2][1] -> [2][2]
                                ruchy[29] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][1] == gracz) { // [1][1] -> [2][2]
                                ruchy[30] = true;
                                Funkcje.brakRuchow = false;
                            }
                            if (tab[1][2] == gracz) { // [1][2] -> [2][2]
                                ruchy[31] = true;
                                Funkcje.brakRuchow = false;
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

    static void cofnijPrzesuniecie(int tab[][], int ruch) {
        switch (ruch) {
            //////////////////////////
            case 0:
                tab[0][1] = tab[0][0];
                tab[0][0] = 0;
                break;
            case 1:
                tab[1][1] = tab[0][0];
                tab[0][0] = 0;
                break;
            case 2:
                tab[1][0] = tab[0][0];
                tab[0][0] = 0;
                break;
            //////////////////////////
            case 3:
                tab[0][0] = tab[0][1];
                tab[0][1] = 0;
                break;
            case 4:
                tab[1][1] = tab[0][1];
                tab[0][1] = 0;
                break;
            case 5:
                tab[0][2] = tab[0][1];
                tab[0][1] = 0;
                break;
            //////////////////////////
            case 6:
                tab[0][1] = tab[0][2];
                tab[0][2] = 0;
                break;
            case 7:
                tab[1][1] = tab[0][2];
                tab[0][2] = 0;
                break;
            case 8:
                tab[1][2] = tab[0][2];
                tab[0][2] = 0;
                break;
            //////////////////////////
            case 9:
                tab[0][0] = tab[1][0];
                tab[1][0] = 0;
                break;
            case 10:
                tab[1][1] = tab[1][0];
                tab[1][0] = 0;
                break;
            case 11:
                tab[2][0] = tab[1][0];
                tab[1][0] = 0;
                break;
            //////////////////////////
            case 12:
                tab[0][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 13:
                tab[0][1] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 14:
                tab[0][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 15:
                tab[1][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 16:
                tab[1][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 17:
                tab[2][0] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 18:
                tab[2][1] = tab[1][1];
                tab[1][1] = 0;
                break;
            case 19:
                tab[2][2] = tab[1][1];
                tab[1][1] = 0;
                break;
            //////////////////////////
            case 20:
                tab[0][2] = tab[1][2];
                tab[1][2] = 0;
                break;
            case 21:
                tab[1][1] = tab[1][2];
                tab[1][2] = 0;
                break;
            case 22:
                tab[2][2] = tab[1][2];
                tab[1][2] = 0;
                break;
            //////////////////////////
            case 23:
                tab[1][0] = tab[2][0];
                tab[2][0] = 0;
                break;
            case 24:
                tab[1][1] = tab[2][0];
                tab[2][0] = 0;
                break;
            case 25:
                tab[2][1] = tab[2][0];
                tab[2][0] = 0;
                break;
            //////////////////////////
            case 26:
                tab[2][0] = tab[2][1];
                tab[2][1] = 0;
                break;
            case 27:
                tab[1][1] = tab[2][1];
                tab[2][1] = 0;
                break;
            case 28:
                tab[2][2] = tab[2][1];
                tab[2][1] = 0;
                break;
            //////////////////////////
            case 29:
                tab[2][1] = tab[2][2];
                tab[2][2] = 0;
                break;
            case 30:
                tab[1][1] = tab[2][2];
                tab[2][2] = 0;
                break;
            case 31:
                tab[1][2] = tab[2][2];
                tab[2][2] = 0;
                break;
            //////////////////////////
        }
    }
}
