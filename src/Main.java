import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Bank bank = new Bank();
        SQL sql = new SQL();
        Konto konto = new Konto();

//        Scanner tastatur = new Scanner(System.in);

//        System.out.println("Wie heißen Sie?");
//        String A = tastatur.nextLine();
//        bank.konto_anlegen(A);

        //konto.konto_information("Michael", 01);
        konto.konto_erstellen();

        System.out.println("Programm ist zu Ende");
    }

}
