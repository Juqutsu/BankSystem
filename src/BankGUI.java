import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.BorderLayout;

public class BankGUI extends JFrame {

    JFrame frame = new JFrame("Bank System");
    JPanel main = new JPanel();
    JLabel KontoInhaber = new JLabel();
    JLabel Kontonummer = new JLabel();
    JLabel Kontostand = new JLabel();
    JLabel IBAN = new JLabel();
    JButton exit = new JButton();

    public void AccountGUI(Konto a) {
        Font mainFont = new Font("Serif", Font.PLAIN, 12);
        frame.setSize(900, 1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setFont(Font.getFont("Poppins"));

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS + SwingConstants.CENTER));
        main.setBackground(Color.decode("#f7e1d7"));
        frame.add(main);

        KontoInhaber.setText("Name: "+a.get_inhaber());
        KontoInhaber.setAlignmentX(Component.CENTER_ALIGNMENT);
        KontoInhaber.setAlignmentY(Component.CENTER_ALIGNMENT);
        main.add(KontoInhaber);

        Kontonummer.setText("Kontonummer: "+ String.valueOf(a.get_kontonummer()));
        Kontonummer.setAlignmentX(Component.CENTER_ALIGNMENT);
        Kontonummer.setAlignmentY(Component.CENTER_ALIGNMENT);
        main.add(Kontonummer);

        Kontostand.setText("Kontostand: " + String.valueOf(a.get_kontostand()));
        Kontostand.setAlignmentX(Component.CENTER_ALIGNMENT);
        Kontostand.setAlignmentY(Component.CENTER_ALIGNMENT);
        main.add(Kontostand);

        IBAN.setText("IBAN: " + a.get_iban());
        Kontostand.setAlignmentX(Component.CENTER_ALIGNMENT);
        Kontostand.setAlignmentY(Component.CENTER_ALIGNMENT);
        main.add(IBAN,Component.CENTER_ALIGNMENT);

        exit.setText("Exit");
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        exit.setBackground(Color.RED);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        main.add(exit);

    }


}
