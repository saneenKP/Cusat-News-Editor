package com.example.cneditor;

import android.net.Uri;

import java.io.Serializable;


@SuppressWarnings("serial")
public class MessageDetails implements Serializable {

    private String title;
    private String description;
    private String time_from;
    private String time_till;
    private String college;
    private String department;
    private String venue;
    private String date;
    private long User_key;
    private String Date_published;
    private String Message_id;
    private String image_url;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public void setTime_till(String time_till) {
        this.time_till = time_till;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser_key(long user_key) {
        User_key = user_key;
    }

    public void setDate_published(String date_published) {
        Date_published = date_published;
    }

    public void setMessage_id(String message_id) {
        Message_id = message_id;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime_from() {
        return time_from;
    }

    public String getTime_till() {
        return time_till;
    }

    public String getCollege() {
        return college;
    }

    public String getDepartment() {
        return department;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public long getUser_key() {
        return User_key;
    }

    public String getDate_published() {
        return Date_published;
    }

    public String getMessage_id() {
        return Message_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public MessageDetails() {
    }

    public MessageDetails(String title, String description, String time_from, String time_till, String college, String department, String venue, String date, long user_key, String date_published, String message_id, String image_url) {
        this.title = title;
        this.description = description;
        this.time_from = time_from;
        this.time_till = time_till;
        this.college = college;
        this.department = department;
        this.venue = venue;
        this.date = date;
        User_key = user_key;
        Date_published = date_published;
        Message_id = message_id;
        this.image_url = image_url;
    }
}
