import java.math.BigInteger;
import java.util.*;


public class Konto {
    public int kontonummer;
    public String inhaber;
    double kontostand;
    String IBAN;
    int gpt;

    Scanner tastatur = new Scanner(System.in);

    public void Konto () {
    }

    public void konto_erstellen(String cname, int cid){ //Erstellt ein neues Konto
//        /*
//        char kontoerstellen;
//        kontoerstellen = 'y';*/
//
//        //if(kontoerstellen == 'y') {
//            inhaber = name; //Angegebener Name des Inhabers wird festgelegt
//            kontonummer = kontonummer(); //Kontonummer wird generiert
//            kontostand = 0;
//            System.out.print ("\n Hallo "+ inhaber + "\n Vielen Dank, dass Sie sich für uns entschieden haben. :) \n Kontonummer: "+ get_kontonummer()+ "\n Kontostand: "+ kontostand + " Euro" + "\n IBAN: "+ gen_iban());
//
//        /*} else {
//            System.out.println("Sie wollen also kein Konto erstellen? Sind Sie sich sicher?");
//
//            if(kontoerstellen == 'y') {
//                System.out.println("Okay, dann bekommen Sie kein Konto");
//            } else {
//                konto_erstellen(name);
//            }
//        }*/

        inhaber = cname;
        kontonummer = cid;

        char option = '\0';


        System.out.println("Willkommen " + inhaber);
        System.out.println("Ihre Kontonummer ist " + kontonummer());
        System.out.println();
        System.out.println("A. Sehe deinen Kontostand");
        System.out.println("B. Taetige eine Ueberweisung");
        System.out.println("C. Hebe Geld ab");
        System.out.println("D. Transaktionen");
        System.out.println("E. Exit");


        do{
            System.out.println("-------------------------------");
            System.out.println("Wähle eine Option");
            System.out.println("-------------------------------");
            option = tastatur.next().charAt(0);

            Character.toUpperCase(option);

            switch (option) {
                case 'A':
                    System.out.println("Kontostand: " + get_kontostand());
                    break;
                case 'B':
                    transaction(2);
                    break;
                case 'C':
                    System.out.println("-------------------------------");
                    System.out.println("Gebe eine wieviel du abheben möchtest");
                    System.out.println("-------------------------------");
                    int amount2 = tastatur.nextInt();
                    abheben(amount2);
                    System.out.println("Du hast " + amount2 + " Euro abgehoben");
                    break;
                case 'D':
                    System.out.println("-------------------------------");
                    getPreviousTransaction();
                    System.out.println("-------------------------------");
                    System.out.println();
                    break;
                case 'E':
                    System.out.println("-------------------------------");
                    break;
                default:
                    System.out.println("Dies ist keine Option");
            }

        } while (option!= 'E');


    }

    public int kontonummer() {
        for (int i = 0; i < 10; i++) {
            int random = new Random().nextInt(9);
            kontonummer = kontonummer + random;
        }
        return kontonummer;
    }
    public String get_iban(){
        return IBAN;
    }

    public String gen_iban (){
        String countrycode = "DE";
        String recNumber = "131400";
        String blz = "85203647";
        String bban;

        bban = blz + kontonummer;
        String checkSumString = bban + recNumber;

        BigInteger checkSum = new BigInteger(checkSumString).mod(new BigInteger("97"));

        if (checkSum.intValue() < 10) {
            checkSumString = "0" + checkSum.toString();
        } else {
            checkSumString = checkSum.toString();
        }

        return countrycode + checkSumString + blz + kontonummer;


    }
    public String get_inhaber (){
        return inhaber;
    }
    public double get_kontostand(){
        return kontostand;
    }

    public ArrayList transaction (int amount) {

        ArrayList<String> ueberweisungsInformationen = new ArrayList<>();
        char ueberweisen = '0';
        String empfaengerName;
        String empfaengerIBAN;
        int betrag;
        String verwendungszweck;
 

        System.out.println("Wollen Sie eine Überweisung tätigen? y/n: ");
        ueberweisen = tastatur.next().charAt(0);

        if (ueberweisen == 'y') {

            System.out.println("Empfänger(Name oder Firma): ");
            empfaengerName = tastatur.nextLine();
            tastatur.nextLine();

            System.out.println("IBAN: ");
            empfaengerIBAN = tastatur.nextLine();

            System.out.println("Betrag: ");
            betrag = tastatur.nextInt();
            tastatur.nextLine();

            System.out.println("Verwendungszweck: ");
            verwendungszweck = tastatur.nextLine();
            tastatur.nextLine();


            ueberweisungsInformationen.add(IBAN);
            ueberweisungsInformationen.add(empfaengerName);
            ueberweisungsInformationen.add(empfaengerIBAN);
            ueberweisungsInformationen.add(String.valueOf(betrag));
            ueberweisungsInformationen.add(verwendungszweck);

            System.out.printf("Dies sind Ihre Überweisungsinformationen: %s\n", ueberweisungsInformationen);

            kontostand -= betrag;

            System.out.printf("Ihr neuer Kontostand lautet: %.2f Euro\n", kontostand);
            kontostand -= amount;
            gpt = -amount;

            return ueberweisungsInformationen;

        } else if (ueberweisen == 'n') {
            System.out.println("Sie wollen keine Überweisung tätigen!");
            return null;
        } else {
            System.out.println("Sie haben weder y noch n eingeben!");
            return null;
        }

    }

    public int get_kontonummer (){
        return kontonummer;
    }
    public void einzahlen (int amount){
        kontostand += amount;
        gpt = amount;
    }
    public void abheben (int amount) {
        if(amount != 0) {
            kontostand -= amount;
            gpt = -amount;
        }
    }

    public void getPreviousTransaction(){
        if (gpt > 0) {
            System.out.println("Einzahlen: " + gpt + " Euro");
        } else if (gpt < 0) {
            System.out.println("Abgehoben " + gpt + " Euro");
        } else {
            System.out.println("Es wurde keine Transaktion getätigt");
        }
    }

}
