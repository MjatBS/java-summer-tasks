package main.java;


import java.time.LocalTime;

public class BargainingTimer{
    private LocalTime lastSettingPrice;
    private final int timeForSetNewPrice;

    public BargainingTimer(int timeForSetNewPrice){
        this.timeForSetNewPrice = timeForSetNewPrice;
    }


    public void startCountdown(){
        lastSettingPrice = LocalTime.now();
        while((LocalTime.now().getSecond() - lastSettingPrice.getSecond()) < timeForSetNewPrice){
        }
    }

    public void notificationSettingNewPrice(){
        lastSettingPrice = LocalTime.now();
    }

}
