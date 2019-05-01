import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class myDraw extends JFrame{


    Graphics g;
    Ue[] ueTable;
    Image ue,antenna;

    public myDraw(Ue[] ueTable, int range){

        setSize(1000,1000);
        setResizable(false);

        setTitle("Network");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);


        g = getGraphics();
        this.ueTable = ueTable;

        ImageIcon ueIcon = new ImageIcon("src/ue.png");
        ImageIcon antennaIcon = new ImageIcon("src/antenna.png");

        ue = ueIcon.getImage().getScaledInstance(15,15,Image.SCALE_FAST);
        antenna = antennaIcon.getImage().getScaledInstance(30,30,Image.SCALE_FAST);
        ueIcon = new ImageIcon(ue);
        antennaIcon = new ImageIcon(antenna);


        g.drawImage(antenna, range/2, range/2, null);
        for(int i=0;i<ueTable.length;i++)
            g.drawImage(ue, ueTable[i].x, ueTable[i].y, null);
    }
    public  void draw(){
        super.paintComponents(g);
        super.setBackground(Color.white);
        g.drawImage(antenna, 500, 500, null);
        for(int i=0;i<ueTable.length;i++)
            g.drawImage(ue, ueTable[i].x, ueTable[i].y, null);
    }
    public void close(){
        setVisible(false);
        g.dispose();
    }
}