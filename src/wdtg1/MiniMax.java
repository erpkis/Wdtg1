package wdtg1;

import java.util.Random;
import static java.lang.Math.pow;

public class MiniMax {

    static double sumujWszystkieMozliwosci = 0;
    static double sumujWszystkieRuchy = 0;

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
            do {
                ////////////////////////////////////////////////////////////////
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                MiniMax.player = 2;
                MiniMax.opponent = 1;
                Move bestMove = findBestMove(tab, 0);
                tab[bestMove.row][bestMove.col] = 2;
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if (ileRuchow == wersjaGry * 2) {
                    break;
                }
                MiniMax.player = 1;
                MiniMax.opponent = 2;
                bestMove = findBestMove(tab, 0);
                tab[bestMove.row][bestMove.col] = 1;
                ileRuchow++;
                MiniMax.sumujWszystkieRuchy++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                ////////////////////////////////////////////////////////////////
                ileMozliwosci--;
                MiniMax.sumujWszystkieMozliwosci += ileMozliwosci;
                //System.out.println(MiniMax.sumujWszystkieRuchy);
                //System.out.println(MiniMax.sumujWszystkieMozliwosci);
                /*if ((Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false) && Funkcje.brakRuchow == false) {
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                } else {
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    break;
                }*/
            } while (true);
            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false) {
                do {
                    MiniMax.player = 1;
                    MiniMax.opponent = 2;
                    findBestMove(tab, 1);
                    ileRuchow++;
                    MiniMax.sumujWszystkieRuchy++;
                    //Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    //System.out.println(bestMove.row);//test
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
                    //Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    //System.out.println(MiniMax.sumujWszystkieRuchy);
                    //System.out.println(MiniMax.sumujWszystkieMozliwosci);
                } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false);
            }
            double srednia = MiniMax.sumujWszystkieMozliwosci / ileRuchow;
            MiniMax.sumujWszystkieMozliwosci = 0;
            parB += srednia;
        }
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
    static class Move {

        int row, col;
    };

    static int player = 1, opponent = 2;

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

        // If this maximizer's move
        if (isMax) {
            int best = -1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == 0) {
                        // Make the move
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board, depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } // If this minimizer's move
        else {
            int best = 1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == 0) {
                        // Make the move
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board, depth + 1, !isMax));

                        // Undo the move
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

        // If this maximizer's move
        if (isMax) {
            int best = -1000;
            MiniMax.player = 1;
            MiniMax.opponent = 2;
            Funkcje.wykonajPrzesuniecie(tab, MiniMax.player, 4);                //A TUTAJ SIE NIE SYPIE FAJNIE

            best = Math.max(best, minimax2(tab, depth + 1, !isMax));
            // Undo the move
            for (int x = 0; x < 3; x++) {                                       //kopiowanie tablicy
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = tab2[x][y];
                }
            }
            return best;
        } // If this minimizer's move
        else {
            int best = 1000;
            MiniMax.player = 2;
            MiniMax.opponent = 1;
            Funkcje.wykonajPrzesuniecie(tab, MiniMax.player, 4);                //TUTAJ SIE SYPIE

            best = Math.min(best, minimax2(tab, depth + 1, !isMax));
            // Undo the move
            for (int x = 0; x < 3; x++) {                                       //kopiowanie tablicy
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
                if (b[row][0] == player) {
                    return +10;
                } else if (b[row][0] == opponent) {
                    return -10;
                }
            }
        }
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == player) {
                    return +10;
                } else if (b[0][col] == opponent) {
                    return -10;
                }
            }
        }
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player) {
                return +10;
            } else if (b[0][0] == opponent) {
                return -10;
            }
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player) {
                return +10;
            } else if (b[0][2] == opponent) {
                return -10;
            }
        }
        return 0;
    }

    static Move findBestMove(int board[][], int faza) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        if (faza == 0) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == 0) {
                        // Make the move
                        board[i][j] = player;

                        // compute evaluation function for this
                        // move.
                        int moveVal = minimax(board, 0, false);

                        // Undo the move
                        board[i][j] = 0;

                        // If the value of the current move is
                        // more than the best value, then update
                        // best/
                        if (moveVal > bestVal) {
                            bestMove.row = i;
                            bestMove.col = j;
                            bestVal = moveVal;
                        }
                    }
                }
            }
        } else if (faza == 1) {
            Funkcje.wykonajPrzesuniecie(board, MiniMax.player, 4);              //WERSJA GRY = 4 !!!
            Funkcje.pokazPlansze(board, Funkcje.czyPokazac);                          //test
        }
        return bestMove;
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
