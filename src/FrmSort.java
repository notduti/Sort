import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FrmSort extends JFrame implements ActionListener {

    private ArrayList<Integer> relayList = null;
    private int max = 0;
    private int i = -1;
    private JMenuItem mniOpen = null;
    private JMenuItem mniClose = null;
    private JCartesian graph = null;
    private JButton btnStart = null;
    private JButton btnShuffle = null;
    private Timer timer = null;

    public FrmSort(ArrayList<Integer> list) {

        this.relayList = list;

        setSize(480, 480);
        setLocationRelativeTo(null);
        setTitle("Graphic sort");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        populate();

        setVisible(true);

        this.timer = new Timer(250 / this.relayList.size(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sort();
                populate();
            }
        });
    }

    private void initUI() {

        pnlNorth();
        pnlCenter();
        pnlSouth();
    }

    private void pnlNorth() {

        JMenuBar mnbFile = new JMenuBar();

        JMenu mnuFile = new JMenu("File");
        this.mniOpen = new JMenuItem("Open..");
        this.mniClose = new JMenuItem("Close");

        mnuFile.add(this.mniOpen);
        mnuFile.add(this.mniClose);
        mnbFile.add(mnuFile);

        this.add(mnbFile, BorderLayout.NORTH);

        this.mniOpen.addActionListener(this);
        this.mniClose.addActionListener(this);
    }

    private void pnlCenter() {

        findMax();
        this.graph = new JCartesian(370, 370,
                -(this.relayList.size() * 0.1), -(this.max * 0.1), this.relayList.size() * 1.1,
                this.max + (this.max * 0.1));

        this.add(this.graph, BorderLayout.CENTER);
    }

    private void pnlSouth() {

        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnStart = new JButton("Start");
        this.btnShuffle = new JButton("Shuffle");

        pnlSouth.add(this.btnStart);
        pnlSouth.add(this.btnShuffle);
        this.add(pnlSouth, BorderLayout.SOUTH);

        this.btnStart.addActionListener(this);
        this.btnShuffle.addActionListener(this);
    }

    private void populate() {
/*
        System.out.print("[i = " + this.i + "]");
        for(Integer i: this.relayList) System.out.print(i + " ");
        System.out.println();
*/
        this.graph.clean();
        double i = 0;
        for(int s: this.relayList) {

            if(i == this.i) this.graph.fillRect(i, 0, i + 1, s, Color.GREEN);
            //System.out.println(i + ";0;" + (i + 1) + ";" + s);
            else this.graph.fillRect(i, 0, i + 1, s, Color.BLACK);
            i++;
        }
    }

    private void sort() {

        if(isSorted()) {

            timer.stop();
            this.i = -1;
            populate();
            return;
        }

        int l = this.relayList.size();
        this.i = 0;
        while(this.i < (l - 1)) {
            int j = this.i + 1;
            for(; j < l; j++)
                if(relayList.get(i) > relayList.get(j)) {
                    Collections.swap(this.relayList, i, j);
                    return;
                }
            this.i++;
        }
    }

    public boolean isSorted() {

        boolean sorted = true;
        for (int i = 1; i < this.relayList.size(); i++)
            if (this.relayList.get(i - 1).compareTo(this.relayList.get(i)) > 0) sorted = false;

        return sorted;
    }

    private void findMax() {

        this.max = 0;
        for(int i: this.relayList)
            if(i > this.max)
                this.max = i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.mniOpen) {

            JFileChooser fc = new JFileChooser();
            int rc = fc.showOpenDialog(this);
            if(rc != JFileChooser.APPROVE_OPTION) return;
            String fileName = fc.getSelectedFile().getAbsolutePath();

            this.relayList = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String line;
                while((line = br.readLine()) != null) {

                    String[] list = line.split(";");
                    for(String s: list)
                        this.relayList.add(Integer.parseInt(s));
                }

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            findMax();
            this.graph.clean();
            SwingUtilities.invokeLater(() -> new FrmSort(this.relayList));
        }

        if(e.getSource() == this.mniClose) {

            System.exit(0);
        }

        if(e.getSource() == this.btnStart) {
            this.timer.start();
        }

        if(e.getSource() == this.btnShuffle) {
            this.i = -1;
            Collections.shuffle(this.relayList);
            populate();
        }
    }

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(7);
        list.add(2);
        list.add(3);
        list.add(5);
        SwingUtilities.invokeLater(() -> new FrmSort(list));
    }
}