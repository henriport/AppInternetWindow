import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Application extends JFrame {
    private JButton calculate;
    private JButton cancel;
    private JLabel textUSD;
    private JLabel textEUR;
    private JLabel textEURTOUSD;
    private JLabel valueUSD;
    private JLabel valueEUR;
    private JLabel valueEURTOUSD;
    private JPanel panel;

    public Application() {
        super("Currency rate");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));

        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parser = new Parser();
                try {
                    parser.workParser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(parser.getDay() + " " + parser.getMonth());

                String s = Double.toString(parser.getUsd());
                valueUSD.setText(s + " RUR");
                System.out.println("USD: " + parser.getUsd() + " RUR");

                s = Double.toString(parser.getEur());
                valueEUR.setText(s + " RUR");
                System.out.println("EUR: " + parser.getEur() + " RUR");

                s = Double.toString(parser.getRatio());

                valueEURTOUSD.setText(s.substring(0, s.indexOf(".") + 4));
                System.out.print("USD for 1 EUR:");
                //s = Double.toString(parser.getEur() / parser.getUsd());
                System.out.format("%.4f USD\n", parser.getEur() / parser.getUsd());
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add("USD", textUSD);
        panel.add("valUSD", valueUSD);
        panel.add("EUR", textEUR);
        panel.add("valEUR", valueEUR);
        panel.add("EUR/USD", textEURTOUSD);
        panel.add("valEUR/USD", valueEURTOUSD);
        panel.add("calculate", calculate);
        panel.add("cancel", cancel);
        setContentPane(panel);
        setSize(400, 400);
        pack();
    }
}