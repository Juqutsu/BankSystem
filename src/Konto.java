import java.math.BigInteger;
import java.util.*;
import java.io.*;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Konto extends Bank {
    public int kontonummer;
    public String inhaber;
    double kontostand;
    String IBAN;
    double betrag;
    double gpt;

    Scanner tastatur = new Scanner(System.in);
    Bank bank;

    public void Konto () {

        System.out.println("Konto erstellen (e) oder Anmelden? (a)");
        String input = tastatur.nextLine();

        switch (input) {
            case "e":
                konto_erstellen();
                break;
            case "a":
                login();
                break;
            default:
                System.out.println("Keine gültige Eingabe!");
                break;
        }

    }


    public void konto_erstellen() {

        System.out.println("Willkommen! Wir freuen uns, dass Sie sich für unsere Bank entschieden haben!");
        gen_iban();
        System.out.println("Wie heißen Sie?");
        inhaber = tastatur.nextLine();
        System.out.println("Geben Sie ein Passwort ein!");
        String password = tastatur.nextLine();
        while(password.length() == 0 || password.length() < 8 || password.length() > 16) {
            System.out.println("Ihr Passwort darf nicht leer sein!\n" +
                    "muss mehr als 8 Zeichen enthalten\n" +
                    "darf nicht mehr als 16 Zeichen enthalten");
            password = tastatur.nextLine();
        }


        KontoInfo kontoInfo = new KontoInfo(inhaber, password, get_iban(), kontonummer(), kontostand);

        kontoInfo.setIban(get_iban());

        JSONObject accounts;
        try {
            String content = new String(Files.readAllBytes(Paths.get("Users/accounts.json")));
            accounts = new JSONObject(content);
        } catch (Exception e) {
            accounts = new JSONObject();
        }

        // Überprüfen, ob der Benutzername bereits existiert
        int count = 1;
        String originalUsername = kontoInfo.getInhaber();
        while (accounts.has(kontoInfo.getInhaber())) {
            kontoInfo.setInhaber(originalUsername + count);
            count++;
        }

        accounts.put(kontoInfo.getInhaber(), kontoInfo.toJSON());

        try {
            Files.write(Paths.get("Users/accounts.json"), accounts.toString().getBytes());
            System.out.println("Konto erfolgreich erstellt!");
        } catch (Exception e) {
            System.out.println("Beim Erstellen des Kontos ist ein Fehler aufgetreten.");
            e.printStackTrace();
        }

        System.out.println("Willkommen " + kontoInfo.getInhaber());
        System.out.println("Ihr Passwort ist " + password);
    }

    public void login() {
        File file = new File("Users/accounts.json");
        JSONObject accounts;

        try {
            if (file.exists()) {
                String content = new String(new FileInputStream(file).readAllBytes());
                accounts = new JSONObject(content);
            } else {
                accounts = new JSONObject();
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
            String name = scanner.nextLine();
            System.out.println("Bitte geben Sie Ihr Passwort ein:");
            String password = scanner.nextLine();

            if (accounts.has(name)) {
                JSONObject user = accounts.getJSONObject(name);
                if (user.getString("password").equals(password)) {
                    inhaber = name;
                    kontonummer = user.getInt("kontonummer");
                    kontostand = user.getDouble("kontostand");

                    // Retrieve the IBAN using the correct key
                    IBAN = user.getString("iban");

                    System.out.println("Anmeldung erfolgreich!");
                    Bank bank = new Bank();
                    super.konto_information(this);
                } else {
                    System.out.println("Falsches Passwort!");
                    login();
                }
            } else {
                System.out.println("Benutzername existiert nicht!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gen_iban (){
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

        IBAN = countrycode + checkSumString + blz + kontonummer;

    }
    public String get_iban(){
        return IBAN;
    }


    public int kontonummer() {
        for (int i = 0; i < 10; i++) {
            int random = new Random().nextInt(9);
            kontonummer = kontonummer + random;
        }
        return kontonummer;
    }



    public String get_inhaber (){
        return inhaber;
    }
    public double get_kontostand(){
        return kontostand;
    }

    public ArrayList<String> transaction () {

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
            betrag = tastatur.nextDouble();
            tastatur.nextLine();

            if (betrag > kontostand) {
                System.out.println("Nicht ausreichender Kontostand für die Überweisung!");
                return null;
            }


            System.out.println("Verwendungszweck: ");
            verwendungszweck = tastatur.nextLine();
            tastatur.nextLine();


            ueberweisungsInformationen.add(get_iban());
            ueberweisungsInformationen.add(empfaengerName);
            ueberweisungsInformationen.add(empfaengerIBAN);
            ueberweisungsInformationen.add(String.valueOf(betrag));
            ueberweisungsInformationen.add(verwendungszweck);

            kontostand -= betrag;
            gpt = -betrag;


            System.out.printf("Dies sind Ihre Überweisungsinformationen: %s\n", ueberweisungsInformationen);

            System.out.printf("Ihr neuer Kontostand lautet: %.2f Euro\n", kontostand);


            return ueberweisungsInformationen;

        } else if (ueberweisen == 'n') {
            System.out.println("Sie wollen keine Überweisung tätigen!");
            return null;

        } else {
            System.out.println("Sie haben weder y noch n eingeben!");
            return  null;
        }
    }

    public int get_kontonummer (){
        return kontonummer;
    }
    public void einzahlen (double amount){
        kontostand += amount;
        gpt = amount;
    }
    public void abheben (Double amount) {
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

    public void updateKontostandInJSON(Konto konto) {
        File file = new File("Users/accounts.json");
        JSONObject accounts;

        try {
            if (file.exists()) {
                String content = new String(new FileInputStream(file).readAllBytes());
                accounts = new JSONObject(content);

                if (accounts.has(konto.inhaber)) {
                    JSONObject user = accounts.getJSONObject(konto.inhaber);
                    user.put("kontostand", konto.kontostand);

                    Files.write(Paths.get("Users/accounts.json"), accounts.toString().getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
