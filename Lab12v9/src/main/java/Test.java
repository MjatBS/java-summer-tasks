package main.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    Lock locker;
    Thread[] agents;

    private int price=0;
    public Test(){
        locker = new ReentrantLock();
        agents = new Thread[5];
        for (int i=0; i < agents.length; i++){
            agents[i] = new A(this);
            agents[i].setName("Jojo part "+(i+1));
        }
    }
    public void startRunningAgents(){
        for (Thread a: agents){
            a.start();
        }
    }

    public void addPrice(int addedPrice, Thread agent){

        try{
            locker.lock();
            System.out.println("start" + agent.getName());
            if (addedPrice >= 0) {
                price += addedPrice;
            }
            Thread.sleep(2000); // As doing some actions
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("End" + agent.getName());
            locker.unlock();
        }

    }

    public static void main(String[] args){
        Test a = new Test();
        a.startRunningAgents();
    }
}

class A extends Thread{
    Test mainClass;
    public A(Test mainClass){
        this.mainClass = mainClass;
    }

    @Override
    public void run(){
        mainClass.addPrice(5, this);
    }
}
