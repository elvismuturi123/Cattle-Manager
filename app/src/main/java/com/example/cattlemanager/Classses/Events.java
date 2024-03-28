package com.example.cattlemanager;

public class Events {
    String event_id;
    String event_Date;
    String event_Type;
    String symptoms;
    String diagnosis;
    String technician;
    String event_Notes;
    public Events() {
    }
    public Events(String eventId, String evDate, String evType) {
    }
    public Events(String event_id, String event_Date, String event_Type, String symptoms, String diagnosis, String technician, String event_Notes) {
        this.event_id = event_id;
        this.event_Date = event_Date;
        this.event_Type = event_Type;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.technician = technician;
        this.event_Notes = event_Notes;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_Date() {
        return event_Date;
    }

    public String getEvent_Type() {
        return event_Type;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTechnician() {
        return technician;
    }

    public String getEvent_Notes() {
        return event_Notes;
    }
}
