package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auction {
    private List<Lot> lots;
    private List<Participant> allParticipants;
    private Map<Participant, Integer> excludedFromParticipation;
    private int timeSuspensionFromBargaining = 1;

    private static Auction instance;
    public static Auction getInstance(){
        if (instance == null){
            instance = new Auction();
        }
        return instance;
    }
    private Auction(){
        initLots();
        initAllParticipants();
        runAllParticipants();
        excludedFromParticipation = new HashMap<>();
    }
    private void initLots(){
        // init lots for example
        lots = new ArrayList<>();
        lots.add(new Lot("Figures", 2500));
        lots.add(new Lot("Coffee", 680));
        lots.add(new Lot("Pictures", 3200));
    }
    private void initAllParticipants(){
        // data for example
        allParticipants = new ArrayList<>();
        allParticipants.add(new ParticipantImpl("Astolfo", 7854));
        allParticipants.add(new ParticipantImpl("Ivan", 10000));
        allParticipants.add(new ParticipantImpl("Joseph", 3000));
        allParticipants.add(new ParticipantImpl("Ignat", 12000));
    }
    List<Thread> participationThreads;
    private void runAllParticipants(){
        // for example
        participationThreads = new ArrayList<>();
        for(Participant p: allParticipants){
            Thread pt = new Thread((Runnable) p);
            pt.start();
            participationThreads.add(pt);
        }
    }
    private void stopAllParticipants(){
        for(Thread pt: participationThreads){
            pt.stop();
        }
    }

    public void startAuction(){
        BargainingTimer mainTimer = new BargainingTimer(5);
        for (Lot lot: lots){
            List<Participant> willingToParticipate = getWillingToParticipate(lot);
            LotSeller seller = new LotSeller(lot, mainTimer, willingToParticipate);

            Participant customer = seller.sellLot();
            if (customer != null){
                int payment = customer.payForLot(lot.getPresentPrice());
                if(payment == lot.getPresentPrice()){
                    transferLot(lot, customer);
                } else {
                    System.out.println(customer + "not payed");
                    excludedFromParticipation.put(customer, timeSuspensionFromBargaining);
                }
            }

            decreaseSuspensionFromBidding();
        }
        stopAllParticipants();
    }

    private void transferLot(Lot lot, Participant customer){
        // doing some business logic
    }

    private List<Participant> getWillingToParticipate(Lot lot){
        List<Participant> result = new ArrayList<>();
        for(Participant p: allParticipants){
            if(!excludedFromParticipation.containsKey(p) &&
                    p.isParticipatedInPresentBargaining(lot.title)){
                result.add(p);
            }
        }
        return result;
    }

    private void decreaseSuspensionFromBidding(){
        List<Participant> returnToParticipation = new ArrayList<>();
        excludedFromParticipation.forEach((participant, integer) -> {
            integer -= 1;
            if (integer < 0) returnToParticipation.add(participant);
        });
        for (Participant p: returnToParticipation){
            excludedFromParticipation.remove(p);
        }
    }

    public static void main(String[] args) {
        Auction auction = Auction.getInstance();
        auction.startAuction();
    }
}
