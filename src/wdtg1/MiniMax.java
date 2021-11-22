package wdtg1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;

public class MiniMax {

    static int glebia = 1000;

    static int minimax(int tab[][], int glebokosc, boolean czyMax, int gracz) {
        int przeciwnik = gracz == 1 ? 2 : 1;
        int wartosc = funkcjaKosztu(tab, gracz);
        if (wartosc == 10) {
            return wartosc;
        }
        if (wartosc == -10) {
            return wartosc;
        }
        if (czyKoniecPierwszejFazy(tab) == true) {
            return 0;
        }
        if (czyMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tab[i][j] == 0) {
                        tab[i][j] = gracz;
                        int score = minimax(tab, glebokosc + 1, !czyMax, gracz);
                        best = Math.max(best, score);
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
                        tab[i][j] = przeciwnik;
                        int score = minimax(tab, glebokosc + 1, !czyMax, gracz);
                        best = Math.min(best, score);
                        tab[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    static int minimax2(int[][] tab, int glebokosc, boolean czyMax, int gracz) {
        glebia--;
        int przeciwnik = gracz == 1 ? 2 : 1;
        int wartosc = funkcjaKosztu(tab, gracz);
        if (wartosc == 10) {
            return wartosc;
        }
        if (wartosc == -10) {
            return wartosc;
        }
        if (glebia <= 0) {
            return wartosc;
        }

        if (czyMax) {
            int bestGracz = -1000;
            Boolean ruchy[] = new Boolean[32];
            Funkcje.znajdzDostepneRuchy(tab, ruchy, gracz);

            List<Boolean> moves = Arrays.stream(ruchy)
                    .collect(toList());
            Map<Integer, Boolean> indexToMove = new LinkedHashMap<>();
            for (int move = 0; move < moves.size(); move++) {
                indexToMove.put(move, moves.get(move));
            }

            for (Map.Entry<Integer, Boolean> entry : indexToMove.entrySet()) {
                if (entry.getValue() == true) {
                    Funkcje.przesun(tab, entry.getKey());
                    int score = minimax2(tab, glebokosc + 1, !czyMax, gracz);
                    bestGracz = Math.max(bestGracz, score);
                    Funkcje.cofnijPrzesuniecie(tab, entry.getKey());
                }
            }
            return bestGracz;
        } else {
            int bestPrzeciwnik = 1000;
            Boolean ruchy[] = new Boolean[32];
            Funkcje.znajdzDostepneRuchy(tab, ruchy, przeciwnik);

            List<Boolean> moves = Arrays.stream(ruchy)
                    .collect(toList());
            Map<Integer, Boolean> indexToMove = new HashMap<>();
            for (int move = 0; move < moves.size(); move++) {
                indexToMove.put(move, moves.get(move));
            }

            for (Map.Entry<Integer, Boolean> entry : indexToMove.entrySet()) {
                if (entry.getValue() == true) {
                    Funkcje.przesun(tab, entry.getKey());
                    int score = minimax2(tab, glebokosc + 1, !czyMax, gracz);
                    bestPrzeciwnik = Math.min(bestPrzeciwnik, score);
                    Funkcje.cofnijPrzesuniecie(tab, entry.getKey());
                }
            }
            return bestPrzeciwnik;
        }
    }

    static int funkcjaKosztu(int t[][], int gracz) {
        int przeciwnik = gracz == 1 ? 2 : 1;
        for (int wiersz = 0; wiersz < 3; wiersz++) {
            if (t[wiersz][0] == t[wiersz][1] && t[wiersz][1] == t[wiersz][2]) {
                if (t[wiersz][0] == gracz) {
                    return +10;
                } else if (t[wiersz][0] == przeciwnik) {
                    return -10;
                }
            }
        }
        for (int kolumna = 0; kolumna < 3; kolumna++) {
            if (t[0][kolumna] == t[1][kolumna] && t[1][kolumna] == t[2][kolumna]) {
                if (t[0][kolumna] == gracz) {
                    return +10;
                } else if (t[0][kolumna] == przeciwnik) {
                    return -10;
                }
            }
        }
        if (t[0][0] == t[1][1] && t[1][1] == t[2][2]) {
            if (t[0][0] == gracz) {
                return +10;
            } else if (t[0][0] == przeciwnik) {
                return -10;
            }
        }
        if (t[0][2] == t[1][1] && t[1][1] == t[2][0]) {
            if (t[0][2] == gracz) {
                return +10;
            } else if (t[0][2] == przeciwnik) {
                return -10;
            }
        }
        return 0;
    }

    static void wykonajNajlepszyRuch(int[][] tab, int faza, int gracz) {
        int najWartosc;
        if (faza == 0) {
            najWartosc = -1000;
            int tabN = -1, tabI = -1;
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    if (tab[n][i] == 0) {
                        tab[n][i] = gracz;
                        int ruchWartosc = minimax(tab, 0, false, gracz);
                        tab[n][i] = 0;
                        if (ruchWartosc > najWartosc) {
                            tabN = n;
                            tabI = i;
                            najWartosc = ruchWartosc;
                        }
                    }
                }
            }
            tab[tabN][tabI] = gracz;
        } else if (faza == 1) {
            najWartosc = -1000;
            int najlepszy = -1;
            Boolean ruchy[] = new Boolean[32];
            Funkcje.znajdzDostepneRuchy(tab, ruchy, gracz);
            Funkcje.brakRuchow = true;
            for (int ruch = 0; ruch < 32; ruch++) {
                if (ruchy[ruch] == true) {
                    Funkcje.brakRuchow = false;
                    Funkcje.przesun(tab, ruch);
                    int ruchWartosc = minimax2(tab, 0, false, gracz);
                    Funkcje.cofnijPrzesuniecie(tab, ruch);
                    if (ruchWartosc > najWartosc) {
                        najlepszy = ruch;
                        najWartosc = ruchWartosc;
                    }
                }
            }
            Funkcje.przesun(tab, najlepszy);
            for (int x = 0; x < 32; x++) {
                ruchy[x] = false;
            }
        }
    }

    static boolean czyKoniecPierwszejFazy(int tab[][]) {
        int wersjaGry = Wdtg1.opcja;
        int ileWolnychPol = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    ileWolnychPol++;
                }
            }
        }
        if (ileWolnychPol == 8 - (wersjaGry * 2)) {
            return true;
        } else {
            return false;
        }
    }
}
