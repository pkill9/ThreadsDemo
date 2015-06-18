package com.ucf;

import java.util.Random;

/*
Name: Bruno Calabria
Course: CNT 4714 Summer 2015
Assignment title: Project One – Multi-threaded programming in Java
Date: May 30, 2015
Class: Enterprise
*/

public class Station implements Runnable {
    private static Random generator = new Random();
    private int workload;
    private int id;

    private Pipe left;
    private Pipe right;

    //Constructs a Station with given workload, id, and attached pipes.
    public Station(int workL, int stationNum, Pipe l, Pipe r) {
        workload = workL;
        id = stationNum;
        left = l;
        right = r;

        System.out.println("Station " + id + ": In-Connection set to pipe " + left.getId());
        System.out.println("Station " + id + ": Out-Connection set to pipe " + right.getId());
        System.out.println("Station " + id + ": workload set to " + workL);
    }
    //With both locks claimed does work for a random amount of time.
    private void doWork() throws InterruptedException {
        workload--;
        System.out.println("Station " + id + ": successfully flows on pipe " + left.getId());
        System.out.println("Station " + id + ": successfully flows on pipe " + right.getId());
        Thread.sleep( generator.nextInt(300) );
    }

    @Override
    // Does work until workload 0
    public void run() {
        try {
            while (workload > 0) {
                // Attempts to get lock for left pipe
                if (left.use(id)) {
                    //Attempts to get lock for right pipe
                    if (right.use(id)) {
                        doWork();
                        // once work is complete release right than left pipe.
                        right.release(id);
                        left.release(id);
                    }
                    else {
                        // If right pipe cant be acquired release left pipe.
                        left.release(id);
                    }
                }
                // Wait for a short amount of time.
                Thread.sleep(generator.nextInt(300));
            }
        }
        catch (InterruptedException exception) { exception.printStackTrace(); }
        System.out.println("* * Station " + id + ": Workload successfully completed. * *");
    }
}



