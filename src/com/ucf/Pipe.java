package com.ucf;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/*
Name: Bruno Calabria
Course: CNT 4714 Summer 2015
Assignment title: Project One – Multi-threaded programming in Java
Date: May 30, 2015
Class: Enterprise
*/

public class Pipe {

    private Lock used;
    boolean inUse;
    private int id;

    public Pipe(int id){
        this.id = id;
        used = new ReentrantLock();
    }

    public int getId(){
        return id;
    }

    // Tries to claim lock and returns if it was successful or not.
    public boolean use(int stationId) throws InterruptedException {
        if(used.tryLock(0,TimeUnit.MILLISECONDS)){
            inUse = true;
            System.out.println("Station " + stationId + ": granted access to pipe " + id);
            return true;
        }
        else{
            return false;
        }
    }
    //Releases lock.
    public void release(int stationId){

        inUse = false;
        used.unlock();
        System.out.println("Station " + stationId + ": released access to pipe " + id);
    }
}
