package com.example.schoolapp.Model;

public class TimeTableModel {
    String id;
    String subject;
    String starttime;
    String endtime;
    String classid;
    String sectionid;
    String sessionid;
    String dayname;
    String teachername;
    String isActive;
    String isFalse;

    public String getPeriod() {
        return Period;
    }

    String Period;

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getClassid() {
        return classid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public String getDayname() {
        return dayname;
    }
//9058414497
    public String getTeachername() {
        return teachername;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getIsFalse() {
        return isFalse;
    }


    public TimeTableModel(String id, String subject, String starttime, String endtime, String classid, String sectionid,
                          String sessionid, String dayname, String teachername, String isActive, String isFalse,
                          String Period) {
        this.id = id;
        this.subject = subject;
        this.starttime = starttime;
        this.endtime = endtime;
        this.classid = classid;
        this.sectionid = sectionid;
        this.sessionid = sessionid;
        this.dayname = dayname;
        this.teachername = teachername;
        this.isActive = isActive;
        this.isFalse = isFalse;
        this.Period = Period;
    }
}
