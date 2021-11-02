package wdtg1;

import java.util.Random;
import static java.lang.Math.pow;

public class MiniMax {

    static double sumujWszystkieMozliwosci = 0;
    static double sumujWszystkieRuchy = 0;
    static int gracz = 1, przeciwnik = 2;

    static double graj(double ilosc, int wersjaGry, boolean czyPokazac) {
        Funkcje.czyPokazac = czyPokazac;
        double parB = 0, parD = 0;
        int[][] tab = new int[3][3];
        Random r = new Random();
        int ileRuchow, ileMozliwosci;
        for (int gra = 0; gra < ilosc; gra++) {                                 //resetowanie planszy
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = 0;
                }
            }
            ileMozliwosci = 9;
            ileRuchow = 0;
            MiniMax.sumujWszystkieMozliwosci = 0;
            MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
            if (Funkcje.czyPokazac) {
                System.out.println("Gra 'MiniMax' nr " + (gra + 1) + ":");
                System.out.println();
            }
            int x = r.nextInt(3);
            int y = r.nextInt(3);
            tab[x][y] = 1;
            ileRuchow++;
            MiniMax.sumujWszystkieRuchy++;
            Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
            do {
                MiniMax.gracz = 2;
                MiniMax.przeciwnik = 1;
                wykonajNajlepszyRuch(tab, 0);
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                ileMozliwosci--;
                MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if (ileRuchow == wersjaGry * 2) {
                    break;
                }
                MiniMax.gracz = 1;
                MiniMax.przeciwnik = 2;
                wykonajNajlepszyRuch(tab, 0);
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                ileMozliwosci--;
                MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
            } while (true);
            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false) {
                do {
                    MiniMax.gracz = 1;
                    MiniMax.przeciwnik = 2;
                    wykonajNajlepszyRuch(tab, 1);
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    if (Funkcje.czyWygrywa(tab, 1) == true || Funkcje.brakRuchow == true) {
                        break;
                    }
                    MiniMax.gracz = 2;
                    MiniMax.przeciwnik = 1;
                    wykonajNajlepszyRuch(tab, 1);
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false);
            }
            double sredniaB = MiniMax.sumujWszystkieMozliwosci / ileRuchow;
            MiniMax.sumujWszystkieMozliwosci = 0;
            parB += sredniaB;
            double sredniaD = MiniMax.sumujWszystkieRuchy;
            MiniMax.sumujWszystkieRuchy = 0;
            parD += sredniaD;
        }
        MiniMax.gracz = 1;
        MiniMax.przeciwnik = 2;
        parB /= Funkcje.ilosc;
        parD /= Funkcje.ilosc;
        System.out.println("parametr b: " + parB);
        System.out.println("parametr d: " + parD);
        double wynik = pow(parB, parD);
        System.out.print("Zlozonosc gry dla algorytmu MiniMax: ");
        return wynik;
    }

    static int minimax(int tab[][], int glebokosc, boolean czyMax) {
        int wartosc = funkcjaKosztu(tab);
        if (wartosc == 10) {
            return wartosc;
        }
        if (wartosc == -10) {
            return wartosc;
        }
        if (czyMoznaWykonacRuch(tab) == false) {
            return 0;
        }
        if (czyMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tab[i][j] == 0) {
                        tab[i][j] = MiniMax.gracz;
                        best = Math.max(best, minimax(tab, glebokosc + 1, !czyMax));
                        tab[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tab[i][j] == 0) {
                        tab[i][j] = MiniMax.przeciwnik;
                        best = Math.min(best, minimax(tab, glebokosc + 1, !czyMax));
                        tab[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    static int minimax2(int[][] tab, int glebokosc, boolean czyMax) {
        int[][] tab2 = new int[3][3];
        for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
            for (int y = 0; y < 3; y++) {
                tab2[x][y] = tab[x][y];
            }
        }
        int wartosc = funkcjaKosztu(tab);
        if (wartosc == 10) {
            return wartosc;
        }
        if (wartosc == -10) {
            return wartosc;
        }
        if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false) {
            return 0;
        }
        if (czyMax) {
            int best = -1000;
            Funkcje.znajdzDostepneRuchy(tab, MiniMax.gracz);
            wykonajNajlepszyRuch(tab, 1);
            best = Math.max(best, minimax2(tab, glebokosc + 1, !czyMax));
            for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = tab2[x][y];
                }
            }
            return best;
        } else {
            int best = 1000;
            Funkcje.znajdzDostepneRuchy(tab, MiniMax.przeciwnik);
            wykonajNajlepszyRuch(tab, 1);
            best = Math.min(best, minimax2(tab, glebokosc + 1, !czyMax));
            for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = tab2[x][y];
                }
            }
            return best;
        }

    }

    static int funkcjaKosztu(int t[][]) {
        for (int wiersz = 0; wiersz < 3; wiersz++) {
            if (t[wiersz][0] == t[wiersz][1] && t[wiersz][1] == t[wiersz][2]) {
                if (t[wiersz][0] == MiniMax.gracz) {
                    return +10;
                } else if (t[wiersz][0] == MiniMax.przeciwnik) {
                    return -10;
                }
            }
        }
        for (int kolumna = 0; kolumna < 3; kolumna++) {
            if (t[0][kolumna] == t[1][kolumna] && t[1][kolumna] == t[2][kolumna]) {
                if (t[0][kolumna] == MiniMax.gracz) {
                    return +10;
                } else if (t[0][kolumna] == MiniMax.przeciwnik) {
                    return -10;
                }
            }
        }
        if (t[0][0] == t[1][1] && t[1][1] == t[2][2]) {
            if (t[0][0] == MiniMax.gracz) {
                return +10;
            } else if (t[0][0] == MiniMax.przeciwnik) {
                return -10;
            }
        }
        if (t[0][2] == t[1][1] && t[1][1] == t[2][0]) {
            if (t[0][2] == MiniMax.gracz) {
                return +10;
            } else if (t[0][2] == MiniMax.przeciwnik) {
                return -10;
            }
        }
        return 0;
    }

    static void wykonajNajlepszyRuch(int[][] tab, int faza) {
        int najWartosc = -1000;
        if (faza == 0) {
            najWartosc = -1000;
            int tabN = -1, tabI = -1;
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    if (tab[n][i] == 0) {
                        tab[n][i] = MiniMax.gracz;
                        int ruchWartosc = minimax(tab, 0, false);
                        tab[n][i] = 0;
                        if (ruchWartosc > najWartosc) {
                            tabN = n;
                            tabI = i;
                            najWartosc = ruchWartosc;
                        }
                    }
                }
            }
            tab[tabN][tabI] = MiniMax.gracz;
        } else if (faza == 1) {
            najWartosc = -1000;
            int najlepszy = -1;
            int ileMozliwosci = 0;
            int[][] tab2 = new int[3][3];
            for (int x = 0; x < 3; x++) {                                       //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab2[x][y] = tab[x][y];
                }
            }
            Funkcje.znajdzDostepneRuchy(tab, MiniMax.gracz);
            Funkcje.brakRuchow = true;
            for (int ruch = 0; ruch < 32; ruch++) {
                if (Funkcje.ruchy[ruch] == true) {
                    Funkcje.brakRuchow = false;
                    ileMozliwosci++;
                    Funkcje.przesun(tab, ruch);
                    int ruchWartosc = minimax2(tab, 0, false);
                    for (int x = 0; x < 3; x++) {                               //kopiowanie tablicy
                        for (int y = 0; y < 3; y++) {
                            tab[x][y] = tab2[x][y];
                        }
                    }
                    if (ruchWartosc > najWartosc) {
                        najlepszy = ruch;
                        najWartosc = ruchWartosc;
                    }
                }
            }
            MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
            Funkcje.przesun(tab, najlepszy);
            for (int x = 0; x < 32; x++) {
                Funkcje.ruchy[x] = false;
            }
        }
    }

    static boolean czyMoznaWykonacRuch(int tab[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
