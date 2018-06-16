/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;

/**
 *
 * @author martasantos
 */
public class robot extends AdvancedRobot{
 
    private BufferedWriter out;

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        try {
            out.write(getX()+","+ getY()+","+ event.getName() + "," + event.getVelocity()); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException ex) {
            Logger.getLogger(robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        try {
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void onDeath(DeathEvent event) {
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            if (new File("DataSet").isFile()) {
                out = new BufferedWriter(new BufferedWriter(new FileWriter("DataSet", true)));
            } else {
                out = new BufferedWriter(new BufferedWriter(new FileWriter("DataSet")));
               // out.write("Xinimigo,Yinimigo,VelocidadeInimigo,HeadingInimigo, name\n");
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        Utils.robotGoTo(this, getBattleFieldWidth() / 2, getBattleFieldHeight() / 2);
        while (true) {
            turnRadarLeft(180);
        }}
    
    
    
}
