package main.java;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LotSeller {
    private Lot lot;
    private final BargainingTimer timerForEndSale;
    private final List<Participant> allParticipants;

    private Participant customer = null;
    private int presentPrice;

    private final Lock locker;

    public LotSeller(Lot lot, BargainingTimer timerForEndSale, List<Participant> participants) {
        this.lot = lot;
        this.timerForEndSale = timerForEndSale;
        this.allParticipants = participants;
        presentPrice = lot.startingPrice;
        locker = new ReentrantLock();
    }

    public Participant sellLot(){
        startSale();
        bargaining();
        return customer;
    }

    private void startSale(){
        startBargainingForEveryParticipant();
        for(int i=0;i<160;i++)System.out.print("*"); System.out.println();
        System.out.println("Start bargaining for lot:");
        System.out.println(getInformationAboutLot());
        notifyParticipantsAboutSettingNewPrice();
    }

    private void bargaining(){
        timerForEndSale.startCountdown();
        closeBargainingForEveryParticipant();
        System.out.println("Bargaining is end");
        if(customer != null){
            System.out.println("Participant " + customer.getName() + " buy this lot by price " + presentPrice);
        }
    }

    public void increasePrice(int newPrice, Participant customer){
        try{
            locker.lock();
            if(newPrice > presentPrice){
                presentPrice = newPrice;
                lot.setPresentPrice(presentPrice);
                this.customer = customer;

                timerForEndSale.notificationSettingNewPrice();
                notifyParticipantsAboutSettingNewPrice();
            }
        } finally {
            locker.unlock();
        }
    }
    private void startBargainingForEveryParticipant(){
        for(Participant p: allParticipants){
            p.startBargaining(this);
        }
    }

    private void notifyParticipantsAboutSettingNewPrice(){
        for(Participant participant: allParticipants){
            participant.notificationSettingNewPrice(presentPrice);
        }
        if(customer!=null) System.out.println("change price by " + customer.getName() + ", price " + presentPrice );
    }

    private void closeBargainingForEveryParticipant(){
        for(Participant p: allParticipants){
            p.endBargaining();
        }
    }

    public String getInformationAboutLot(){
        return lot.toString();
    }

}
