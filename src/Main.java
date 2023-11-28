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

        konto.konto_erstellen("Michael", 01);

        System.out.println("Programm ist zu Ende");
    }

}
