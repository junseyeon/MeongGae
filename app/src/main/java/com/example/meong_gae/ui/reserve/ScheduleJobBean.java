package com.example.meong_gae.ui.reserve;

public class ScheduleJobBean {

    private int classId;
    private String dateTime;
    private String classN;
    private String teacher;
    private int maxNum;
    private int nowNum;

    public ScheduleJobBean(int classId, String dateTime, String classN, String teacher, int nowNum, int maxNum) {
        this.classId = classId;
        this.dateTime = dateTime;
        this.classN = classN;
        this.teacher = teacher;
        this.nowNum = nowNum;
        this.maxNum = maxNum;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDate(){
        //2022-05-29 형식만 남겨두기 위해서 시간부분 자름
        return dateTime.split("\\s")[0];
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getClassN() {
        return classN;
    }

    public void setClassN(String classN) {
        this.classN = classN;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getMaxNum() {
        return String.valueOf(maxNum);
    }

    public int getMaxNumInt() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getNowNum() {
        return String.valueOf(nowNum);
    }

    public int getNowNumInt() {
        return nowNum;
    }

    public void setNowNum(int nowNum) {
        this.nowNum = nowNum;
    }
}
