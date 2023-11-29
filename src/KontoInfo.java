import org.json.JSONObject;

public class KontoInfo {
    private String inhaber;
    private String password;
    private String iban;
    private int kontonummer;
    private double kontostand;

    public KontoInfo(String inhaber, String password, String iban, int kontonummer, double kontostand) {
        this.inhaber = inhaber;
        this.password = password;
        this.iban = iban;
        this.kontonummer = kontonummer;
        this.kontostand = kontostand;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("inhaber", inhaber);
        jsonObject.put("password", password);
        jsonObject.put("iban", iban);
        jsonObject.put("kontonummer", kontonummer);
        jsonObject.put("kontostand", kontostand);
        return jsonObject;
    }

    public static KontoInfo fromJSON(JSONObject jsonObject) {
        String inhaber = jsonObject.getString("inhaber");
        String password = jsonObject.getString("password");
        String iban = jsonObject.getString("iban");
        int kontonummer = jsonObject.getInt("kontonummer");
        double kontostand = jsonObject.getDouble("kontostand");

        return new KontoInfo(inhaber, password, iban, kontonummer, kontostand);
    }

    // Getter und Setter hier...

    public String getInhaber() {
        return inhaber;
    }

    public void setInhaber(String inhaber) {
        this.inhaber = inhaber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

}