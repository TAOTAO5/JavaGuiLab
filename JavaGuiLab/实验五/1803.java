import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new Main();
    }

    JTextArea jta = null;
    JMenuBar jmb = null;
    JMenu jm = null;
    JMenuItem jmi1 = null;
    JMenuItem jmi2 = null;

    public Main() {
        jta = new JTextArea();
        this.add(jta);

        jmb = new JMenuBar();
        jm = new JMenu("File");

        this.setJMenuBar(jmb);
        jmb.add(jm);

        jmi1 = new JMenuItem("Open");
        jmi1.addActionListener(this);
        jmi1.setActionCommand("open");

        jmi2 = new JMenuItem("Save");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("save");

        jm.add(jmi1);
        jm.add(jmi2);

        this.setTitle("EditPlus");
        this.setSize(600, 400);
        this.setLocation(400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open")) {

            JFileChooser chooser1 = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt File", "txt");
            chooser1.setFileFilter(filter);
            chooser1.setDialogTitle("Select a file");
            int returnVal = chooser1.showOpenDialog(null);
            chooser1.setVisible(true);
            if (returnVal == JFileChooser.CANCEL_OPTION) {
                return;
            }

            String filepath = "";
            filepath = chooser1.getSelectedFile().getAbsolutePath();
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(filepath);
                br = new BufferedReader(fr);

                String s = "";
                String allCon = "";
                while ((s = br.readLine()) != null) {
                    allCon += s + "\r\n";
                }
                jta.setText(allCon);
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                try {
                    fr.close();
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } else if (e.getActionCommand().equals("save")) {
            JFileChooser chooser2 = new JFileChooser();
            chooser2.setDialogTitle("Save");
            int returnVal = chooser2.showSaveDialog(null);
            chooser2.setVisible(true);
            if (returnVal == JFileChooser.CANCEL_OPTION) {
                return;
            }
            String pathname = chooser2.getSelectedFile().getAbsolutePath();
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(pathname);
                bw = new BufferedWriter(fw);
                String str = this.jta.getText();
                System.out.println(str);
                bw.write(this.jta.getText());

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                try {
                    bw.close();
                    fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}