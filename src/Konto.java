import java.math.BigInteger;
import java.util.*;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Konto {
    public int kontonummer;
    public String inhaber;
    double kontostand;
    String IBAN;
    int amount3;
    int gpt;

    Scanner tastatur = new Scanner(System.in);

    public void Konto () {
    }

    public void konto_erstellen() {

        System.out.println("Willkommen! Wir freuen uns das Sie sich für \nunsere Bank entschieden haben!");
        System.out.println("Wie heißen Sie?");
        inhaber = tastatur.nextLine();
        System.out.println("Geben Sie ein Passwort ein!");
        String password = tastatur.nextLine();
        while(password.length() == 0 || password.length() <= 8 || password.length() > 16) {
            System.out.println("Ihr Passwort darf nicht leer sein!\n" +
                    "muss mehr als 8 Zeichen enthalten\n" +
                    "darf nicht mehr als 16 Zeichen enthalten");
            password = tastatur.nextLine();
        }

        JSONObject account = new JSONObject();
        account.put("name", inhaber);
        account.put("password", password);

        JSONObject accounts;
        try {
            String content = new String(Files.readAllBytes(Paths.get("Users/accounts.json")));
            accounts = new JSONObject(content);
        } catch (Exception e) {
            accounts = new JSONObject();
        }

        // Überprüfen, ob der Benutzername bereits existiert
        int count = 1;
        String originalUsername = inhaber;
        while (accounts.has(inhaber)) {
            inhaber = originalUsername + count;
            count++;
        }


        accounts.put(inhaber, account);

        try {
            Files.write(Paths.get("Users/accounts.json"), accounts.toString().getBytes());
            System.out.println("Konto erfolgreich erstellt!");
        } catch (Exception e) {
            System.out.println("Beim Erstellen des Kontos ist ein Fehler aufgetreten.");
            e.printStackTrace();
        }
    }

    public void konto_information(String cname, int cid){ //Erstellt ein neues Konto
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
                    transaction();
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

    public ArrayList transaction () {

        ArrayList<String> ueberweisungsInformationen = new ArrayList<>();
        char ueberweisen = '0';
        String empfaengerName;
        String empfaengerIBAN;
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
            amount3 = tastatur.nextInt();
            tastatur.nextLine();

            System.out.println("Verwendungszweck: ");
            verwendungszweck = tastatur.nextLine();
            tastatur.nextLine();


            ueberweisungsInformationen.add(IBAN);
            ueberweisungsInformationen.add(empfaengerName);
            ueberweisungsInformationen.add(empfaengerIBAN);
            ueberweisungsInformationen.add(String.valueOf(amount3));
            ueberweisungsInformationen.add(verwendungszweck);

            kontostand -= amount3;
            gpt = -amount3;

            System.out.printf("Dies sind Ihre Überweisungsinformationen: %s\n", ueberweisungsInformationen);

            System.out.printf("Ihr neuer Kontostand lautet: %.2f Euro\n", kontostand);


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
