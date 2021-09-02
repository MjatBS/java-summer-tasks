package main.java;

public interface Participant {
    String getName();

    public boolean isParticipatedInPresentBargaining(String lotTitle);

    public int payForLot(int price);

    public void setLotTitle(String lotTitle);

    public void setSeller(LotSeller seller);

    public void startBargaining(LotSeller seller);

    public void endBargaining();

    public void notificationSettingNewPrice(int newPrice);
}