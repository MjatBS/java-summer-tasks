package main.java;


public class ParticipantImpl implements Participant, Runnable {
    /**
     * In this project Participant is a bot, which make not rational actions
     * In developing
     */
    private String name;
    private int money;

    private String lotTitle;
    private int lotPrice;
    private LotSeller seller = null;

    public ParticipantImpl(String name, int money){
        this.name = name;
        this.money = money;
    }

    @Override
    public void run() {
        while(true){
            try {

                if(seller != null){
                    participateInBargaining();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void participateInBargaining() throws InterruptedException {

        double involvement = Math.random()+0.5; // May be more than 1
        int maxPrice = (int)(involvement * money);
        int myPrice = lotPrice+1;
        while(seller != null){
            if (lotPrice < maxPrice && lotPrice != myPrice){
                myPrice = lotPrice + (int)(100 * Math.random());
                seller.increasePrice(myPrice, this);
            }
            Thread.sleep((long) (2000 * Math.random()));
        }
    }

    public String getName() {
        return name;
    }

    public boolean isParticipatedInPresentBargaining(String lotTitle){
        if (Math.random() > 0){
            setLotTitle(lotTitle);
            return true;
        } else {
            return false;
        }
    }

    // need other logic
    public int payForLot(int price){
        if (price > (int)0.5*money){
            return 0;
        } else {
            money -= price;
            return price;
        }
    }

    public void setLotTitle(String lotTitle) {
        this.lotTitle = lotTitle;
    }

    public void setSeller(LotSeller seller) {
        this.seller = seller;
    }

    public void startBargaining(LotSeller seller){
        setSeller(seller);
    }

    public void endBargaining(){
        setSeller(null);
        setLotTitle("");
    }

    public void notificationSettingNewPrice(int newPrice){
        lotPrice = newPrice;
    }

    @Override
    public String toString(){
        return getName();
    }

}
