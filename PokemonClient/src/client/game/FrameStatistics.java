/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.game;

import java.util.Calendar;

/**
 * Mantêm informações relativos aos frames por segundo.
 * 
 * Calcula tempo médio dos frames a cada segundo.
 * 
 * @author bruno.weig
 */
public class FrameStatistics {
    // Tempo em milissegundos do frame anterior
    long beforeFrameTime = 0;
    
    // Tempo consumido em cada frame
    long currentFrameTime = 0;
    
    // Utilizado como timer
    long timerOneSecond;
    
    // Quantidade de frames por segundo
    int framesCounter = 0;
    
    int framesTimeCounter = 0;
    int framesTimeMean;
    
    // Valor esperado de FPS
    int fpsExpected;
    int timePerFrameExpected;
    
    // Quantidade real de frames a cada segundo
    int fpsActual;
    
    public FrameStatistics(int fps) {
        fpsExpected = fps;
        
        timePerFrameExpected = 1000 / fpsExpected;
    }
    
    /**
     * Deve ser chamado antes do loop principal
     */
    public void before() {
        if ( beforeFrameTime == 0 ) {
            timerOneSecond = Calendar.getInstance().getTimeInMillis();
        }

        beforeFrameTime = Calendar.getInstance().getTimeInMillis();
    }
    
    /**
     * Deve ser chamado após o loop principal
     */
    public void after() {
        framesCounter++;

        currentFrameTime = Calendar.getInstance().getTimeInMillis() - beforeFrameTime;

        // Somatório do tempo de cada frame
        framesTimeCounter += currentFrameTime;

        // Bloco executado a cada segundo
        if ( (beforeFrameTime - timerOneSecond) > 1000 ) {
            fpsActual = framesCounter;

            timerOneSecond = beforeFrameTime;

            // Média de tempo dos frames
            framesTimeMean = framesTimeCounter / framesCounter;

            framesTimeCounter = 0;
            framesCounter = 0;
        }
    }

    /**
     * Média do tempo de cada frame
     * 
     * @return 
     */
    public int getFramesTimeMean() {
        return framesTimeMean;
    }

    /**
     * Quantidade de vezes por segundo que os frames são processados no loop principal
     * 
     * @return 
     */
    public int getFpsExpected() {
        return fpsExpected;
    }

    /**
     * Tempo para o processamento de cada frame
     * 
     * @return 
     */
    public int getTimePerFrameExpected() {
        return timePerFrameExpected;
    }

    /**
     * Frames processados no último segundo
     * 
     * @return 
     */
    public int getFpsActual() {
        return fpsActual;
    }
}
