package wdtg1;

import java.util.Random;
import static java.lang.Math.pow;

public class MiniMax {

    static double sumujWszystkieMozliwosci = 0;
    static double sumujWszystkieRuchy = 0;
    static int player = 1, opponent = 2;

    static double graj(double ilosc, int wersjaGry, boolean czyPokazac) {
        Funkcje.algorytm = "minimax";
        Funkcje.czyPokazac = czyPokazac;
        double parB = 0, parD;
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
                System.out.println("Gra nr " + (gra + 1) + ":");
                System.out.println();
            }
            int x = r.nextInt(3);
            int y = r.nextInt(3);
            tab[x][y] = 1;
            ileRuchow++;
            MiniMax.sumujWszystkieRuchy++;
            Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
            do {
                ////////////////////////////////////////////////////////////////
                MiniMax.player = 2;
                MiniMax.opponent = 1;
                findBestMove(tab, 0);
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if (ileRuchow == wersjaGry * 2) {
                    break;
                }
                MiniMax.player = 1;
                MiniMax.opponent = 2;
                findBestMove(tab, 0);
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                ////////////////////////////////////////////////////////////////
                ileMozliwosci--;
                MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
                //System.out.println(MiniMax.sumujWszystkieRuchy);
                //System.out.println(MiniMax.sumujWszystkieMozliwosci);
                if ((Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false) && Funkcje.brakRuchow == false) {
                    //ileRuchow++;
                    //MiniMax.sumujWszystkieRuchy++;
                } else {
                    //ileRuchow++;
                    //MiniMax.sumujWszystkieRuchy++;
                    break;
                }
            } while (true);
            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false) {
                do {
                    MiniMax.player = 1;
                    MiniMax.opponent = 2;
                    findBestMove(tab, 1);
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    //System.out.println(MiniMax.sumujWszystkieRuchy);
                    //System.out.println(MiniMax.sumujWszystkieMozliwosci);
                    if (Funkcje.czyWygrywa(tab, 1)) {
                        break;
                    }
                    MiniMax.player = 2;
                    MiniMax.opponent = 1;
                    findBestMove(tab, 1);
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    //System.out.println(MiniMax.sumujWszystkieRuchy);
                    //System.out.println(MiniMax.sumujWszystkieMozliwosci);
                } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false);
            }
            double srednia = MiniMax.sumujWszystkieMozliwosci / ileRuchow;
            MiniMax.sumujWszystkieMozliwosci = 0;
            parB += srednia;
        }
        MiniMax.player = 1;
        MiniMax.opponent = 2;
        parB /= Funkcje.ilosc;
        parD = MiniMax.sumujWszystkieRuchy / Funkcje.ilosc;
        //System.out.println(parB);
        //System.out.println(parD);
        double wynik = pow(parB, parD);
        return wynik;
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    static int minimax(int board[][], int depth, boolean isMax) {
        int score = funkcjaKosztu(board);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (isMovesLeft(board) == false) {
            return 0;
        }
        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = MiniMax.player;
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = MiniMax.opponent;
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    static int minimax2(int[][] tab, int depth, boolean isMax) {
        int[][] tab2 = new int[3][3];
        for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
            for (int y = 0; y < 3; y++) {
                tab2[x][y] = tab[x][y];
            }
        }
        int score = funkcjaKosztu(tab);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (isMovesLeft(tab) == false) {
            return 0;
        }
        if (isMax) {
            int best = -1000;
            Funkcje.wykonajPrzesuniecie(tab, MiniMax.player, 4);
            findBestMove(tab, 1);
            best = Math.max(best, minimax2(tab, depth + 1, !isMax));
            for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = tab2[x][y];
                }
            }
            return best;
        } else {
            int best = 1000;
            Funkcje.wykonajPrzesuniecie(tab, MiniMax.opponent, 4);
            findBestMove(tab, 1);
            best = Math.min(best, minimax2(tab, depth + 1, !isMax));
            for (int x = 0; x < 3; x++) {                                           //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = tab2[x][y];
                }
            }
            return best;
        }

    }

    static int funkcjaKosztu(int b[][]) {
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == MiniMax.player) {
                    return +10;
                } else if (b[row][0] == MiniMax.opponent) {
                    return -10;
                }
            }
        }
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == MiniMax.player) {
                    return +10;
                } else if (b[0][col] == MiniMax.opponent) {
                    return -10;
                }
            }
        }
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == MiniMax.player) {
                return +10;
            } else if (b[0][0] == MiniMax.opponent) {
                return -10;
            }
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == MiniMax.player) {
                return +10;
            } else if (b[0][2] == MiniMax.opponent) {
                return -10;
            }
        }
        return 0;
    }

    static void findBestMove(int[][] tab, int faza) {
        int bestVal = -1000;
        int tabN = -1, tabI = -1;
        if (faza == 0) {
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    if (tab[n][i] == 0) {
                        tab[n][i] = MiniMax.player;
                        int moveVal = minimax(tab, 0, false);
                        tab[n][i] = 0;
                        if (moveVal > bestVal) {
                            tabN = n;
                            tabI = i;
                            bestVal = moveVal;
                        }
                    }
                }
            }
            tab[tabN][tabI] = MiniMax.player;
        } else if (faza == 1) {
            int najlepszy = -1;
            int najWartosc = -1000;
            int[][] tab2 = new int[3][3];
            for (int x = 0; x < 3; x++) {                                       //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab2[x][y] = tab[x][y];
                }
            }
            for (int ruch = 0; ruch < 32; ruch++) {
                if (Funkcje.ruchy[ruch] == 1) {
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
            Funkcje.przesun(tab, najlepszy);
                for (int x = 0; x < 32; x++) {
                    Funkcje.ruchy[x] = 0;
                }
                Funkcje.brakRuchow = false;
        }
    }

    static boolean isMovesLeft(int board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
