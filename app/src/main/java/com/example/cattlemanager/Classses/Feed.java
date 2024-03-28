package com.example.cattlemanager.Classses;

public class Feed {

    String feedDayId;
    String feedingDtae;
    String feedTpe;
    String components;

    String milkProduced;
    String cattleNumber;
    String programNotes;

    public Feed() {
    }

    public Feed(String feedDayId, String feedingDtae, String feedTpe, String components, String milkProduced, String cattleNumber, String programNotes) {
        this.feedDayId = feedDayId;
        this.feedingDtae = feedingDtae;
        this.feedTpe = feedTpe;
        this.components = components;
        this.milkProduced = milkProduced;
        this.cattleNumber = cattleNumber;
        this.programNotes = programNotes;
    }

    public String getFeedDayId() {
        return feedDayId;
    }

    public String getFeedingDtae() {
        return feedingDtae;
    }

    public String getFeedTpe() {
        return feedTpe;
    }

    public String getComponents() {
        return components;
    }

    public String getMilkProduced() {
        return milkProduced;
    }

    public String getCattleNumber() {
        return cattleNumber;
    }

    public String getProgramNotes() {
        return programNotes;
    }
}
