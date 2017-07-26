package com_ucast_manager.entity;

/**
 * Created by pj on 2017/7/19.
 */

public class ExtraWorkMSg {
    private  String ID;
    private  String login_id;
    private  String extra_work_reason;
    private  String extra_work_start_time;
    private  String extra_work_end_time;
    private  String extra_work_start_time_image;
    private  String extra_work_end_time_image;
    private  String extra_work_end_time_gps;
    private  String extra_work_start_gps;


    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getExtra_work_reason() {
        return extra_work_reason;
    }

    public void setExtra_work_reason(String extra_work_reason) {
        this.extra_work_reason = extra_work_reason;
    }

    public String getExtra_work_start_time() {
        return extra_work_start_time;
    }

    public void setExtra_work_start_time(String extra_work_start_time) {
        this.extra_work_start_time = extra_work_start_time;
    }

    public String getExtra_work_end_time() {
        return extra_work_end_time;
    }

    public void setExtra_work_end_time(String extra_work_end_time) {
        this.extra_work_end_time = extra_work_end_time;
    }

    public String getExtra_work_start_time_image() {
        return extra_work_start_time_image;
    }

    public void setExtra_work_start_time_image(String extra_work_start_time_image) {
        this.extra_work_start_time_image = extra_work_start_time_image;
    }

    public String getExtra_work_end_time_image() {
        return extra_work_end_time_image;
    }

    public void setExtra_work_end_time_image(String extra_work_end_time_image) {
        this.extra_work_end_time_image = extra_work_end_time_image;
    }

    public String getExtra_work_end_time_gps() {
        return extra_work_end_time_gps;
    }

    public void setExtra_work_end_time_gps(String extra_work_end_time_gps) {
        this.extra_work_end_time_gps = extra_work_end_time_gps;
    }

    public String getExtra_work_start_gps() {
        return extra_work_start_gps;
    }

    public void setExtra_work_start_gps(String extra_work_start_gps) {
        this.extra_work_start_gps = extra_work_start_gps;
    }
}
