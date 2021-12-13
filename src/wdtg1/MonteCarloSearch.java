package wdtg1;

import static java.lang.System.exit;
import java.util.Random;

public class MonteCarloSearch {

    static int najlepszePrzesuniecie;

    static void MonteCarlo(int tab[][], int ileSymulacji, int czyjRuch, int wersjaGry) {
        int ruch = czyjRuch;
        int najlepszeN = -1, najlepszeI = -1;
        int najPrzesuniecie = -1;
        int najlepszyWynik = -1;
        int ilePustychPol = 0;
        for (int n = 0; n < 3; n++) {
            for (int i = 0; i < 3; i++) {
                if (tab[n][i] == 0) {
                    ilePustychPol++;
                }
            }
        }
        int r = 0;
        int temp[][] = new int[3][3];
        if (ilePustychPol > 9 - (wersjaGry * 2)) {    //faza1
            for (int n = 0; n < 3; n++) {
                for (int i = 0; i < 3; i++) {
                    if (tab[n][i] == 0) {
                        tab[n][i] = czyjRuch;
                        for (int a = 0; a < 3; a++) {
                            for (int b = 0; b < 3; b++) {
                                temp[a][b] = tab[a][b];
                            }
                        }
                        for (int x = 0; x < ileSymulacji; x++) {
                            do {
                                ruch = (ruch % 2) + 1;
                                MonteCarloSearch.wykonajRuch(tab, ruch, 1);
                                if (Funkcje.czyWygrywa(tab, czyjRuch)) {
                                    r++;
                                }
                            } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && ilePustychPol > 9 - (wersjaGry * 2));
                            if (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && ilePustychPol > 9 - (wersjaGry * 2)) {
                                do {
                                    ruch = (ruch % 2) + 1;
                                    MonteCarloSearch.wykonajRuch(tab, ruch, 2);
                                    if (Funkcje.czyWygrywa(tab, czyjRuch)) {
                                        r++;
                                    }
                                } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false && Funkcje.brakRuchow == false);
                            }
                            for (int a = 0; a < 3; a++) {
                                for (int b = 0; b < 3; b++) {
                                    tab[a][b] = temp[a][b];
                                }
                            }
                        }
                        if (r > najlepszyWynik) {
                            najlepszeN = n;
                            najlepszeI = i;
                            najlepszyWynik = r;
                        }
                        tab[n][i] = 0;
                    }
                    r = 0;
                }
            }
            tab[najlepszeN][najlepszeI] = czyjRuch;
        } else {                                //faza2
            int temp2[][] = new int[3][3];
            r = 0;
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    temp[a][b] = tab[a][b];
                }
            }
            for (int n = 0; n < 32; n++) {
                MonteCarloSearch.sprawdzPrzesuniecie(tab, czyjRuch, n);
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        temp2[a][b] = tab[a][b];
                    }
                }
                for (int x = 0; x < ileSymulacji; x++) {
                    do {
                        ruch = (ruch % 2) + 1;
                        MonteCarloSearch.wykonajRuch(tab, ruch, 2);
                        if (Funkcje.czyWygrywa(tab, czyjRuch)) {
                            r++;
                        }
                    } while (Funkcje.czyWygrywa(tab, 1) == false && Funkcje.czyWygrywa(tab, 2) == false);
                    for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                            tab[a][b] = temp2[a][b];
                        }
                    }
                }
                if (r > najlepszyWynik) {
                    najPrzesuniecie = MonteCarloSearch.najlepszePrzesuniecie;
                    najlepszyWynik = r;
                }
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        temp[a][b] = tab[a][b];
                    }
                }
            }
            Funkcje.przesun(tab, najPrzesuniecie);
        }
    }

    static void wykonajRuch(int tab[][], int czyjRuch, int faza) {
        if (faza == 1) {
            Random r = new Random();
            int n = 0;
            int i = 0;
            do {
                n = r.nextInt(3);
                i = r.nextInt(3);
            } while (tab[n][i] != 0);
            tab[n][i] = czyjRuch;
        } else {
            Zlozonosc.wykonajPrzesuniecie(tab, czyjRuch);
        }
    }

    static void sprawdzPrzesuniecie(int tab[][], int gracz, int n) {
        Boolean ruchy[] = new Boolean[32];
        Funkcje.znajdzDostepneRuchy(tab, ruchy, gracz);
        Funkcje.brakRuchow = true;
        for (int i = 0; i < 32; i++) {
            if (ruchy[i] == true) {
                Funkcje.brakRuchow = false;
            }
        }
        int ruch = 0;
        do {
            Random losujRuch = new Random();
            ruch = losujRuch.nextInt(32);
            MonteCarloSearch.najlepszePrzesuniecie = ruch;
        } while (ruchy[ruch] == false && Funkcje.brakRuchow == false);
        for (; n < 32; n++) {
            if (ruchy[n] == true && Funkcje.brakRuchow == false) {
                Funkcje.przesun(tab, n);
            }
        }
        for (int x = 0; x < 32; x++) {
            ruchy[x] = false;
        }
        Funkcje.brakRuchow = false;
    }

    static int[][] uzupelnianiePlanszy(String ustawienieNaPlanszy) {
        String temp[] = ustawienieNaPlanszy.split(" ");
        int tab[][] = new int[3][3];
        if (temp.length == 9) {
            for (int i = 0; i < 9; i++) {
                if (temp[i].length() != 1 || Integer.parseInt(temp[i]) < 0 || Integer.parseInt(temp[i]) > 2) {
                    System.out.println("Niestety cos poszlo nie tak, koncze program");
                    exit(0);
                }
            }
            for (int i = 0; i < 9; i++) {
                if (i <= 2) {
                    tab[0][i] = Integer.parseInt(temp[i]);
                }
                if (i > 2 && i <= 5) {
                    tab[1][i - 3] = Integer.parseInt(temp[i]);
                }
                if (i > 5) {
                    tab[2][i - 6] = Integer.parseInt(temp[i]);
                }
            }
        } else {
            System.out.println("Niestety cos poszlo nie tak, koncze program");
            exit(0);
        }
        return tab;
    }
}
