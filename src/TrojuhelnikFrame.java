import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TrojuhelnikFrame extends JFrame {
    private JFileChooser chooser = new JFileChooser(".");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu;
    private JMenuItem menuItem;
    private JPanel mainPanel;
    private JTextField textFieldA;
    private JTextField textFieldB;
    private JTextField textFieldC;
    private JButton kopirujButton;
    private JButton lzeSestrojitButton;
    private File selectedFile;

    private ArrayList<String> list = new ArrayList<>();

    private TrojuhelnikFrame(){
        initComponents();
    }

    private void initComponents(){
        setContentPane(mainPanel);
        setJMenuBar(menuBar);

        menu = new JMenu("Soubor");
        menuBar.add(menu);

        menuItem = new JMenuItem("Načti");
        menu.add(menuItem);
        menuItem.addActionListener(e->nacti());

        menuItem = new JMenuItem("Ulož");
        menu.add(menuItem);
        menuItem.addActionListener(e->uloz());

        menu = new JMenu("Akce");
        menuBar.add(menu);

        menuItem = new JMenuItem("Kopíruj A do B i C");
        menu.add(menuItem);
        menuItem.addActionListener(e->kopiruj());
        kopirujButton.addActionListener(e->kopiruj());

        menuItem = new JMenuItem("Lze sestrojit trojúhelník?");
        menu.add(menuItem);
        menuItem.addActionListener(e->lzeSestrojit());
        lzeSestrojitButton.addActionListener(e->lzeSestrojit());
    }

    private void kopiruj(){
        String cislo = textFieldA.getText();
        textFieldB.setText(cislo);
        textFieldC.setText(cislo);
    }

    private void lzeSestrojit(){
        int a = Integer.parseInt(textFieldA.getText());
        int b = Integer.parseInt(textFieldB.getText());
        int c = Integer.parseInt(textFieldC.getText());

        if (a+b > c) {
            JOptionPane.showMessageDialog(null, "Ze stran délek A: " + a + ", B: " + b + " a C: " + c + " lze sestrojit trojúhelník");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Ze stran délek A: "+a+", B: "+b+" a C: "+c+" nelze sestrojit trojúhelník");
        }
    }


    private void nacti(){
        int result = chooser.showOpenDialog(mainPanel);

        if (result == JFileChooser.APPROVE_OPTION){
            selectedFile = chooser.getSelectedFile();
            nactiSoubor();
        }
    }

    private void nactiSoubor() {
        try{
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(selectedFile)));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] polozky = line.split(";");
                textFieldA.setText(polozky[0]);
                textFieldB.setText(polozky[1]);
                textFieldC.setText(polozky[2]);
                list.add(line);
            }

        }

        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void uloz(){
        int result = chooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            ulozSoubor(selectedFile);
        }
    }

    private void ulozSoubor(File soubor){
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(soubor)))
        ){
            writer.print(""+textFieldA.getText()+";"+textFieldB.getText()+";"+textFieldC.getText());
        }

        catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }


    public static void main(String[] args) {
        TrojuhelnikFrame frame = new TrojuhelnikFrame();

        frame.setVisible(true);
        frame.setBounds(700,400,300,175);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Trojúhelník");
    }
}
