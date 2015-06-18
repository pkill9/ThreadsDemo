package com.ucf;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Name: Bruno Calabria
Course: CNT 4714 Summer 2015
Assignment title: Project One – Multi-threaded programming in Java
Date: May 30, 2015
Class: Enterprise
*/

public class Main {

    public static void main(String[] args) {
        try {
            // Open config.txt
            Scanner in = new Scanner(new File("config.txt"));
            int stations = in.nextInt();

            // Create the pipes to be used by the stations.
            Pipe [] pipes = new Pipe[stations];
            for (int i = 0; i < stations; i++){
                pipes[i] = new Pipe(i);
            }

            // Start executors with a thread per station
            ExecutorService app = Executors.newFixedThreadPool(stations);

            try{
                System.out.println("******************Starting Simulation********************");
                // Start each station thread.
                for (int i = 0; i<stations; i++){
                    if (i == 0){
                        app.execute( new Station(in.nextInt(),i,pipes[stations - 1],pipes[i]));
                    }else{
                        app.execute( new Station(in.nextInt(),i, pipes[i-1],pipes[i]));
                    }
                }
            }
            catch ( Exception exception ){ exception.printStackTrace();}
            finally {

                app.shutdown();
                while(!app.isTerminated()){
                    //wait until all threads finish and application terminates.
                }
                System.out.println("*************Ending Simulation******************");
            }
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }
}
