package gui;

import main.Zlomek;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class ApplicationWindow extends JFrame {
    private Zlomek z1,z2;
    private ArrayList<Zlomek> zlomky;

    private JPanel pVstup, pVypocet, pVystup, pInputOutput;
    private JScrollPane scText;

    private JLabel labCit1, labCit2, labJmen1, labJmen2;
    private JTextArea vystup;
    private JTextField tfCit1, tfCit2, tfJmen1, tfJmen2;
    private JButton btVymaz, btNacist, btUlozit, btPlus, btMinus, btMultiply, btDivide;
    private JButton btPorovnej, btZkrat, btDesetinne;

    public ApplicationWindow() {
        super("Hlavní okno aplikace");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initModel();
        initGui();
    }

    private void initModel()
    {
        zlomky = new ArrayList<>();
    }

    private void initGui() {
        setLayout(new BorderLayout(10, 10));

        pVstup = new JPanel();
        pVstup.setLayout(new BoxLayout(pVstup, BoxLayout.Y_AXIS));

        JPanel pZlomek1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labCit1 = new JLabel("Čitatel 1:");
        tfCit1 = new JTextField("", 5);
        labJmen1 = new JLabel("Jmenovatel 1:");
        tfJmen1 = new JTextField("", 5);
        pZlomek1.add(labCit1);
        pZlomek1.add(tfCit1);
        pZlomek1.add(labJmen1);
        pZlomek1.add(tfJmen1);

        JPanel pZlomek2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labCit2 = new JLabel("Čitatel 2:");
        tfCit2 = new JTextField("", 5);
        labJmen2 = new JLabel("Jmenovatel 2:");
        tfJmen2 = new JTextField("", 5);
        pZlomek2.add(labCit2);
        pZlomek2.add(tfCit2);
        pZlomek2.add(labJmen2);
        pZlomek2.add(tfJmen2);

        pVstup.add(pZlomek1);
        pVstup.add(pZlomek2);

        add(pVstup, BorderLayout.WEST);

        pVypocet = new JPanel();
        pVypocet.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btPlus = new JButton("+");
        pVypocet.add(btPlus);
        btMinus = new JButton("-");
        pVypocet.add(btMinus);
        btMultiply = new JButton("*");
        pVypocet.add(btMultiply);
        btDivide = new JButton("/");
        pVypocet.add(btDivide);

        btPorovnej = new JButton("Porovnej");
        pVypocet.add(btPorovnej);
        btZkrat = new JButton("Zkrať");
        pVypocet.add(btZkrat);
        btDesetinne = new JButton("Na desetinné číslo");
        pVypocet.add(btDesetinne);

        add(pVypocet, BorderLayout.NORTH);

        pVystup = new JPanel();
        pVystup.setLayout(new BorderLayout());
        vystup = new JTextArea(15, 40);
        vystup.setLineWrap(true);
        vystup.setWrapStyleWord(true);
        vystup.setEditable(false);
        scText = new JScrollPane(vystup);
        pVystup.add(scText, BorderLayout.CENTER);

        btNacist = new JButton("Načíst");
        btUlozit = new JButton("Uložit");
        btVymaz = new JButton("Vymaž");
        pInputOutput = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pInputOutput.add(btUlozit);
        pInputOutput.add(btNacist);
        pInputOutput.add(btVymaz);
        pVystup.add(pInputOutput, BorderLayout.SOUTH);

        add(pVystup, BorderLayout.CENTER);

        setSize(800, 500);

        btNacist.addActionListener(this::btNacistNabidkuActionPerformed);
        btUlozit.addActionListener(this::btUlozitNabidkuActionPerformed);
        btVymaz.addActionListener(this::btVymazActionPerformed);

        btPlus.addActionListener(this::btPlusActionPerformed);
        btMinus.addActionListener(this::btMinusActionPerformed);
        btMultiply.addActionListener(this::btMultiplyActionPerformed);
        btDivide.addActionListener(this::btDivideActionPerformed);

        btPorovnej.addActionListener(this::btPorovnejActionPerformed);
        btZkrat.addActionListener(this::btZkratActionPerformed);
        btDesetinne.addActionListener(this::btDesetinneActionPerformed);
    }

    private Zlomek vytvorZlomek(JTextField tfCit1, JTextField tfJmen1) {
        int c = Integer.parseInt(tfCit1.getText());
        int j = Integer.parseInt(tfJmen1.getText());
        return new Zlomek(c, j);
    }

    private String vypisZlomky(ArrayList<Zlomek> zlomky) {
        StringBuilder sb = new StringBuilder();
        for (Zlomek z : zlomky) {
            sb.append(z.toString()).append("\n");
        }
        return sb.toString();
    }

    private void btVymazActionPerformed(ActionEvent e) {
        zlomky.clear();
        vystup.setText("");
    }

    private void btUlozitNabidkuActionPerformed(ActionEvent evt) {
        JFileChooser fch = new JFileChooser();
        fch.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fch.setFileFilter(new FileNameExtensionFilter("Textové soubory (*.txt)", "txt"));
        if (fch.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fch.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".txt")) {
                f = new File(f.getParentFile(), f.getName() + ".txt");
            }
            if (f.exists()) {
                int response = JOptionPane.showConfirmDialog(this, "Zadaný soubor již existuje, přepsat?", "Uložit výstup", JOptionPane.YES_NO_CANCEL_OPTION);
                if (response != JOptionPane.YES_OPTION)
                    return;
            }
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(f))) {
                writer.write(vystup.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Chyba při ukládání!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btNacistNabidkuActionPerformed(ActionEvent evt) {
        JFileChooser fch = new JFileChooser();
        fch.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fch.setFileFilter(new FileNameExtensionFilter("Textové soubory (*.txt)", "txt"));
        if (fch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fch.getSelectedFile();
            String[] buffer = f.getName().trim().split("\\.");
            String extension = buffer.length > 1 ? buffer[buffer.length - 1] : "";
            if (!"txt".equals(extension.toLowerCase())) {
                JOptionPane.showMessageDialog(this, "Načítejte pouze .txt soubory!", "Chyba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(f))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                vystup.setText(sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Chyba při načítání!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void pridejUnikatniZlomek(Zlomek z) {
        for (Zlomek existujici : zlomky) {
            if (existujici.equals(z)) return;
        }
        zlomky.add(z);
    }

    private void btPlusActionPerformed(ActionEvent evt) {
        Zlomek z1 = vytvorZlomek(tfCit1, tfJmen1);
        Zlomek z2 = vytvorZlomek(tfCit2, tfJmen2);
        Zlomek vysledek = z1.secti(z2);
        vystup.append(z1 + " + " + z2 + " = " + vysledek.toString() + "\n");
        pridejUnikatniZlomek(z1);
        pridejUnikatniZlomek(z2);
        pridejUnikatniZlomek(vysledek);
    }

    private void btMinusActionPerformed(ActionEvent evt) {
        Zlomek z1 = vytvorZlomek(tfCit1, tfJmen1);
        Zlomek z2 = vytvorZlomek(tfCit2, tfJmen2);
        Zlomek vysledek = z1.odecti(z2);
        vystup.append(z1 + " - " + z2 + " = " + vysledek.toString() + "\n");
        pridejUnikatniZlomek(z1);
        pridejUnikatniZlomek(z2);
        pridejUnikatniZlomek(vysledek);
    }

    private void btMultiplyActionPerformed(ActionEvent evt) {
        Zlomek z1 = vytvorZlomek(tfCit1, tfJmen1);
        Zlomek z2 = vytvorZlomek(tfCit2, tfJmen2);
        Zlomek vysledek = z1.nasob(z2);
        vystup.append(z1 + " * " + z2 + " = " + vysledek.toString() + "\n");
        pridejUnikatniZlomek(z1);
        pridejUnikatniZlomek(z2);
        pridejUnikatniZlomek(vysledek);
    }

    private void btDivideActionPerformed(ActionEvent evt) {
        Zlomek z1 = vytvorZlomek(tfCit1, tfJmen1);
        Zlomek z2 = vytvorZlomek(tfCit2, tfJmen2);
        Zlomek vysledek = z1.vydel(z2);
        vystup.append(z1 + " / " + z2 + " = " + vysledek.toString() + "\n");
        pridejUnikatniZlomek(z1);
        pridejUnikatniZlomek(z2);
        pridejUnikatniZlomek(vysledek);
    }

    private void btPorovnejActionPerformed(ActionEvent evt) {
        Zlomek z1 = vytvorZlomek(tfCit1, tfJmen1);
        Zlomek z2 = vytvorZlomek(tfCit2, tfJmen2);
        int cmp = z1.compareTo(z2);
        String vysledek;
        if (cmp == 0) vysledek = z1 + " = " + z2;
        else if (cmp < 0) vysledek = z1 + " < " + z2;
        else vysledek = z1 + " > " + z2;
        vystup.append("Porovnání: " + vysledek + "\n");
        pridejUnikatniZlomek(z1);
        pridejUnikatniZlomek(z2);
    }

    private void btZkratActionPerformed(ActionEvent evt) {
        Zlomek z = vytvorZlomek(tfCit1, tfJmen1);
        z.zkrat();
        vystup.append("Zkrácený zlomek: " + z + "\n");
        pridejUnikatniZlomek(z);
    }

    private void btDesetinneActionPerformed(ActionEvent evt) {
        Zlomek z = vytvorZlomek(tfCit1, tfJmen1);
        double desetinne = (double) z.getCitatel() / z.getJmenovatel();
        vystup.append(z + " = " + desetinne + "\n");
        pridejUnikatniZlomek(z);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApplicationWindow().setVisible(true));
    }
}
