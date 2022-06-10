package com.example.meong_gae.ui.home;

import com.example.meong_gae.Global;

public class Hisotry {
    private String dateTime;
    private String classN;
    private String teacher;
    private String status;
    private int ticketId;
    private int nTicketId;

    public Hisotry(int ticketId, int nTicketId, String dateTime, String classN, String teacher, String status) {
        this.ticketId = ticketId;
        this.nTicketId = nTicketId;
        this.dateTime = dateTime;
        this.classN = classN;
        this.teacher = teacher;
        this.status = status;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getnTicketId() {
        return nTicketId;
    }

    public String getStatus() {
        return status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getClassN() {
        return classN;
    }


    public String getTeacher() {
        return teacher;
    }

}
