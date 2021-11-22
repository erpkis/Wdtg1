package wdtg1;                                                                  //link do opisu gry: https://www.boardgamegeek.com/boardgame/28931/achi

public class Wdtg1 {

    static int opcja;

    public static void main(String[] args) {
        System.out.println("ACHI");
        boolean zakoncz = false;
        do {
            System.out.println();
            System.out.println("Ktora wersja gry Achi?:");
            System.out.println("3 - wersja na 3 pionki ('Tapatan')");
            System.out.println("4 - wersja na 4 pionki ('Klasyczne Achi')");
            System.out.println("(0 - zakoncz program)");
            Funkcje.wybierzOpcje();
            switch (opcja) {
                case 3:
                    Funkcje.ilePartii();
                    Funkcje.czyPokazacPrzebiegPartii();
                    System.out.println();
                    System.out.println(Zlozonosc.ZlozonoscMetoda1(3, Funkcje.czyPokazac));
                    System.out.println(Zlozonosc.ZlozonoscMetoda2(3));
                    System.out.println();
                    MiniMax_VS_Random.graj(Funkcje.ilosc, 3, Funkcje.czyPokazac);
                    System.out.println();
                    break;
                case 4:
                    Funkcje.ilePartii();
                    Funkcje.czyPokazacPrzebiegPartii();
                    System.out.println();
                    System.out.println(Zlozonosc.ZlozonoscMetoda1(4, Funkcje.czyPokazac));
                    System.out.println(Zlozonosc.ZlozonoscMetoda2(4));
                    System.out.println();
                    MiniMax_VS_Random.graj(Funkcje.ilosc, 4, Funkcje.czyPokazac);
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    System.out.println("Do widzenia");
                    zakoncz = true;
                    break;
                default:
                    System.out.println("Wprowadzono bledna dana, sprobuj ponownie");
                    break;
            }
        } while (!zakoncz);
        System.out.println();
    }
}