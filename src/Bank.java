import java.util.Scanner;

public class Bank {

    Scanner tastatur = new Scanner(System.in);

    public void Bank () {

    }

    public String get_iban(Konto a){
        return a.get_iban();
    }

    public void ueberweisen (Konto sender, Konto empfaenger){
        //sender.transaction();
        //empfaenger.einzahlen();
    }

//    public void konto_anlegen (String name){
//        Konto konto = new Konto();
//        konto.konto_erstellen("name", 01);
//    }
}
