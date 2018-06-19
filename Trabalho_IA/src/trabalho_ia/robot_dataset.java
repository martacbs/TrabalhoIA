/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ia;


import FreeCBR.CBR;
import FreeCBR.Feature;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import robocode.AdvancedRobot;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;


/**
 *
 * @author martasantos
 */
public class robot_dataset extends AdvancedRobot{
 
    private CBR cbr;
    private String CBR_FILE = "/Users/martasantos/robocode/datasets/tp/iarobot.cbr"; //o caminho para o dataset
    private String LOG_FILE = "/Users/martasantos/robocode/datasets/tp/iarobot.log"; //o caminho para o ficheiro de log

 @Override
    public void run()
    {
        super.run();

        try
        {
            cbr = new CBR(CBR_FILE, LOG_FILE, true, true);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        
            //se não existe cria a estrutura do dataset
            try
            {
                new BufferedWriter(new FileWriter(new File(CBR_FILE))).close(); //cria o ficheiro
                new BufferedWriter(new FileWriter(new File(LOG_FILE))).close(); //cria o ficheiro

                cbr = new CBR(CBR_FILE, LOG_FILE, true, true);

                cbr.addFeature("distancia", Feature.FEATURE_TYPE_STRING);
                cbr.addFeature("x", Feature.FEATURE_TYPE_FLOAT);
                cbr.addFeature("y", Feature.FEATURE_TYPE_FLOAT);
                cbr.addFeature("data", Feature.FEATURE_TYPE_STRING);
            } catch (IOException e1) {e1.printStackTrace();}
        }
        catch (IOException e) {e.printStackTrace();}

        while(true)
        {
            setTurnRight(360);
            setAhead(100);
            setTurnRadarLeft(360);
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event)
    {
        super.onScannedRobot(event);

        //sempre que observa o robot adiciona uma nova linha ao dataset
        Feature[] caso = new Feature[4];
        caso[0] = event.getDistance() > 150? new Feature("longe") : new Feature("perto");
        Point2D ponto = Utils.getEnemyCoordinates(this, event.getBearing(), event.getDistance());
        caso[1] = new Feature((float)ponto.getX());
        caso[2] = new Feature((float)ponto.getY());
        caso[3] = new Feature(Utils.dataAtualFormatada());

        cbr.addCase(caso);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event)
    {
        super.onRoundEnded(event);

        //sempre que termina uma ronda guarda os dados em ficheiro
        try
        {
            cbr.saveSet(CBR_FILE, true);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
