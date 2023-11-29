import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    Scanner tastatur = new Scanner(System.in);


    public Bank () {
    }

    public String get_iban(Konto a){
        return a.get_iban();
    }

    public void ueberweisen(Konto sender, Konto empfaenger) {
        ArrayList<String> infos = sender.transaction();

        String betrag = infos.get(3);
        System.out.println("Zu überweisender Betrag: " + betrag);

        // Überweisungsbetrag dem Empfängerkonto hinzufügen
        empfaenger.einzahlen(Double.valueOf(betrag));

        System.out.println("Überweisung abgeschlossen!");

        // Aktualisiere Kontostände in der JSON-Datei
        updateKontostandInJSON(sender, empfaenger);
    }

    public void konto_anlegen (){
        Konto konto = new Konto();

        konto.konto_erstellen();
    }

    public void konto_information(Konto konto){


        char option = '\0';

        System.out.println("Willkommen " + konto.get_inhaber());
        System.out.println("Ihre Kontonummer ist " + konto.get_kontonummer());
        System.out.println("Ihre IBAN ist " + konto.get_iban());
        System.out.println("Ihr Kontostand ist " + konto.get_kontostand());
        System.out.println();
        System.out.println("A. Sehe deinen Kontostand");
        System.out.println("B. Taetige eine Ueberweisung");
        System.out.println("C. Einzahlen");
        System.out.println("D. Abheben");
        System.out.println("E. Transaktionen");
        System.out.println("F. Abmelden");
        System.out.println("G. Exit");

        do{
            System.out.println("-------------------------------");
            System.out.println("Wähle eine Option");
            System.out.println("-------------------------------");
            option = tastatur.next().charAt(0);

            Character.toUpperCase(option);

            switch (option) {
                case 'A':
                    System.out.println("Kontostand: " + konto.get_kontostand());
                    break;
                case 'B':
                    System.out.println("-------------------------------");
                    System.out.println("Geben Sie den Empfängernamen ein:");
                    String empfaengerName = tastatur.next();

                    JSONObject accounts = readAccountJSON();
                    Konto empfaengerKonto = getKontoFromJSON(accounts, empfaengerName);

                    if (empfaengerKonto != null) {
                        ueberweisen(konto, empfaengerKonto);
                        updateKontostandInJSON(konto, empfaengerKonto);
                    } else {
                        System.out.println("Empfängerkonto nicht gefunden.");
                    }
                    break;
                case 'C':
                    System.out.println("-------------------------------");
                    System.out.println("Gebe eine wieviel du einzahlen möchtest");
                    System.out.println("-------------------------------");
                    int amount = tastatur.nextInt();
                    konto.einzahlen(amount);
                    System.out.println("Du hast " + amount + " Euro eingezahlt");
                    break;
                case 'D':
                    System.out.println("-------------------------------");
                    System.out.println("Gebe eine wieviel du abheben möchtest");
                    System.out.println("-------------------------------");
                    double amount2 = tastatur.nextInt();
                    konto.abheben(amount2);
                    System.out.println("Du hast " + amount2 + " Euro abgehoben");
                    break;
                case 'E':
                    System.out.println("-------------------------------");
                    //ueberweisen(konto, this);
                    System.out.println("-------------------------------");
                    System.out.println();
                    break;
                case 'F':
                    konto.Konto();
                    break;
                case 'G':
                    System.out.println("-------------------------------");
                    break;
                default:
                    System.out.println("Dies ist keine Option");
            }
            konto.updateKontostandInJSON(konto);
        } while (option!= 'G');



    }
    private void updateKontostandInJSON(Konto sender, Konto empfaenger) {
        File file = new File("Users/accounts.json");
        JSONObject accounts;

        try {
            if (file.exists()) {
                String content = new String(new FileInputStream(file).readAllBytes());
                accounts = new JSONObject(content);

                // Update des Kontostands des Senders
                if (accounts.has(sender.inhaber)) {
                    JSONObject senderUser = accounts.getJSONObject(sender.inhaber);
                    senderUser.put("kontostand", sender.kontostand);
                }

                // Update des Kontostands des Empfängers
                if (accounts.has(empfaenger.inhaber)) {
                    JSONObject empfaengerUser = accounts.getJSONObject(empfaenger.inhaber);
                    empfaengerUser.put("kontostand", empfaenger.kontostand);
                }

                Files.write(Paths.get("Users/accounts.json"), accounts.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Konto getKontoFromJSON(JSONObject accounts, String username) {
        if (accounts != null && accounts.has(username)) {
            JSONObject user = accounts.getJSONObject(username);
            Konto konto = new Konto();

            konto.inhaber = username;
            konto.kontonummer = user.getInt("kontonummer");
            konto.kontostand = user.getDouble("kontostand");
            konto.IBAN = user.getString("iban");

            return konto;
        }

        return null;
    }

    public JSONObject readAccountJSON() {
        File file = new File("Users/accounts.json");

        try {
            if (file.exists()) {
                String content = new String(new FileInputStream(file).readAllBytes());
                return new JSONObject(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
