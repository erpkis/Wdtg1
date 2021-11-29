package wdtg1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;

public class AlphaBeta {

    static int glebia = 1000;

    static int minimax(int tab[][], int glebokosc, boolean czyMax, int faza, int gracz, int alpha, int beta) {
        if (faza == 1) {
            int przeciwnik = gracz == 1 ? 2 : 1;
            int wartosc = MiniMax.funkcjaKosztu(tab, gracz);
            if (wartosc == 10) {
                return wartosc;
            }
            if (wartosc == -10) {
                return wartosc;
            }
            if (MiniMax.czyKoniecPierwszejFazy(tab) == true) {
                return 0;
            }
            if (czyMax) {
                int best = -1000;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (tab[i][j] == 0) {
                            tab[i][j] = gracz;
                            int score = AlphaBeta.minimax(tab, glebokosc + 1, !czyMax, 1, gracz, alpha, beta);
                            best = Math.max(best, score);
                            tab[i][j] = 0;
                            alpha = Math.max(alpha, best);
                            if (beta <= alpha) {
                                break;
                            }
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
                            int score = AlphaBeta.minimax(tab, glebokosc + 1, !czyMax, 1, gracz, alpha, beta);
                            best = Math.min(best, score);
                            tab[i][j] = 0;
                            beta = Math.min(beta, best);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
                return best;
            }
        } else {
            glebia--;
            int przeciwnik = gracz == 1 ? 2 : 1;
            int wartosc = MiniMax.funkcjaKosztu(tab, gracz);
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
                        int score = AlphaBeta.minimax(tab, glebokosc + 1, !czyMax, 2, gracz, alpha, beta);
                        bestGracz = Math.max(bestGracz, score);
                        Funkcje.cofnijPrzesuniecie(tab, entry.getKey());
                        alpha = Math.max(alpha, bestGracz);
                        if (beta <= alpha) {
                            break;
                        }
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
                        int score = AlphaBeta.minimax(tab, glebokosc + 1, !czyMax, 2, gracz, alpha, beta);
                        bestPrzeciwnik = Math.min(bestPrzeciwnik, score);
                        Funkcje.cofnijPrzesuniecie(tab, entry.getKey());
                        beta = Math.min(beta, bestPrzeciwnik);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                return bestPrzeciwnik;
            }
        }
    }

    static void wykonajNajlepszyRuch(int[][] tab, int faza, int gracz) {
        int najWartosc;
        if (faza == 1) {
            najWartosc = -1000;
            int tabN = -1, tabI = -1;
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    if (tab[n][i] == 0) {
                        tab[n][i] = gracz;
                        int ruchWartosc = AlphaBeta.minimax(tab, 0, false, 1, gracz, -1000, 1000);
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
        } else if (faza == 2) {
            najWartosc = -1000;
            int najlepszy = -1;
            Boolean ruchy[] = new Boolean[32];
            Funkcje.znajdzDostepneRuchy(tab, ruchy, gracz);
            Funkcje.brakRuchow = true;
            for (int ruch = 0; ruch < 32; ruch++) {
                if (ruchy[ruch] == true) {
                    Funkcje.brakRuchow = false;
                    Funkcje.przesun(tab, ruch);
                    int ruchWartosc = AlphaBeta.minimax(tab, 0, false, 2, gracz, -1000, 1000);
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
}
