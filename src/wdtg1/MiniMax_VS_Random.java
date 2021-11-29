package wdtg1;

import java.util.Random;

public class MiniMax_VS_Random {

    static int wersjaGry;
    static double ileRuchowMiniMax = 0;
    static double ileRuchowAlphaBeta = 0;
    static double CzasWykonaniaRuchowMiniMax = 0;
    static double CzasWykonaniaRuchowAlphaBeta = 0;

    static void graj(double ilosc, int wersjaGry, boolean czyPokazac, boolean czyAlphaBeta) {
        MiniMax_VS_Random.wersjaGry = wersjaGry;
        //StringBuilder nrPrzegranejGry = new StringBuilder("");
        Funkcje.czyPokazac = czyPokazac;
        double ileRazyWygralMiniMax = 0;
        int[][] tab = new int[3][3];
        for (int gra = 1; gra <= ilosc; gra++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    tab[x][y] = 0;
                }
            }
            if (Funkcje.czyPokazac) {
                System.out.println();
                System.out.println("Gra 'MiniMax VS Random' nr " + gra + ":");
                System.out.println("random - 1, minimax - 2");
                System.out.println();
            }
            int ileRuchow = 0;
            do {
                ruchRandom(tab, 1);
                ileRuchow++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if (Funkcje.czyWygrywa(tab, 1)) {
                    //nrPrzegranejGry.append(" " + gra + " ");
                    break;
                }
                ruchMiniMax(tab, 1, czyAlphaBeta);
                ileRuchow++;
                Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                if (Funkcje.czyWygrywa(tab, 2)) {
                    ileRazyWygralMiniMax++;
                    break;
                }
            } while (ileRuchow < MiniMax_VS_Random.wersjaGry * 2);
            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false) {
                MiniMax.glebia = 1000;
                do {
                    ruchRandom(tab, 2);
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    if (Funkcje.czyWygrywa(tab, 1)) {
                        //nrPrzegranejGry.append(" " + gra + " ");
                        break;
                    }
                    ruchMiniMax(tab, 2, czyAlphaBeta);
                    Funkcje.pokazPlansze(tab, Funkcje.czyPokazac);
                    if (Funkcje.czyWygrywa(tab, 2)) {
                        ileRazyWygralMiniMax++;
                        break;
                    }
                } while (Funkcje.brakRuchow == false);
            }
        }
        double procentWygranychMiniMaxa = ileRazyWygralMiniMax / ilosc;
        if (!czyAlphaBeta) {
            System.out.println("gra Random vs MiniMax:");
            System.out.println("MiniMax wygrywa z Random w: " + (procentWygranychMiniMaxa * 100) + "%");
            //if (Funkcje.czyPokazac) {
            //    System.out.println("gry przegrane przez MiniMaxa: " + nrPrzegranejGry);
            //}
            double sredniCzasWykonaniaRuchuMiniMax = (double) CzasWykonaniaRuchowMiniMax / (double) ileRuchowMiniMax;
            sredniCzasWykonaniaRuchuMiniMax = Funkcje.zaokraglenie(sredniCzasWykonaniaRuchuMiniMax);
            System.out.println("sredni czas wykonania ruchu przez MiniMax: " + sredniCzasWykonaniaRuchuMiniMax + " ms");
        } else {
            double sredniCzasWykonaniaRuchuAlphaBeta = (double) CzasWykonaniaRuchowAlphaBeta / (double) ileRuchowAlphaBeta;
            sredniCzasWykonaniaRuchuAlphaBeta = Funkcje.zaokraglenie(sredniCzasWykonaniaRuchuAlphaBeta);
            System.out.println("sredni czas wykonania ruchu przez MiniMax+AlphaBeta: " + sredniCzasWykonaniaRuchuAlphaBeta + " ms");
        }
        if (czyAlphaBeta) {
            System.out.println("========================================================");
        }
    }

    static void ruchRandom(int tab[][], int faza) {
        if (faza == 1) {
            Random r = new Random();
            int n = 0;
            int i = 0;
            do {
                n = r.nextInt(3);
                i = r.nextInt(3);
            } while (tab[n][i] != 0);
            tab[n][i] = 1;
        } else {
            Zlozonosc.wykonajPrzesuniecie(tab, 1);
        }
    }

    static void ruchMiniMax(int tab[][], int faza, boolean czyAlphaBeta) {
        int minimax = 2;
        if (!czyAlphaBeta) {
            double startMiniMax = System.currentTimeMillis();
            MiniMax.wykonajNajlepszyRuch(tab, faza, minimax);
            double stopMiniMax = System.currentTimeMillis();
            ileRuchowMiniMax++;
            CzasWykonaniaRuchowMiniMax += (double) (stopMiniMax - startMiniMax);
        } else {
            double startAlphaBeta = System.currentTimeMillis();
            AlphaBeta.wykonajNajlepszyRuch(tab, faza, minimax);
            double stopAlphaBeta = System.currentTimeMillis();
            ileRuchowAlphaBeta++;
            CzasWykonaniaRuchowAlphaBeta += (double) (stopAlphaBeta - startAlphaBeta);
        }
    }
}
