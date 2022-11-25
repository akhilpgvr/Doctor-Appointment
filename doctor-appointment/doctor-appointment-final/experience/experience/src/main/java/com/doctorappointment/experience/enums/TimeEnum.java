package com.doctorappointment.experience.enums;

public enum TimeEnum {
    TIME9AM_10AM("9AM-10AM"),
    TIME10AM_11AM("10AM-11AM"),
    TIME11AM_12PM("11AM-12PM"),
    TIME12PM_1PM("12PM-1PM"),
    TIME1PM_2PM("1PM-2PM"),
    TIME2PM_3PM("2PM-3PM"),
    TIME3PM_4PM("3PM-4PM"),
    TIME4PM_5PM("4PM-5PM"),
    TIME5PM_6PM("5PM-6PM"),
    TIME6PM_7PM("6PM-7PM"),
    TIME7PM_8PM("7PM-8PM"),
    TIME8PM_9PM("8PM-9PM");

    private String userTime;
    TimeEnum(final String time){
        userTime=time;
    }

    public String getPatientAppointmentTime() {
        return userTime;
    }
}
