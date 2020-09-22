package com.example.laundryapp.fragments.pojo;

public class Orders {
    public String oreder_id;
    public String recipient_name;
    public String phone;

    public Orders(String oreder_id, String recipient_name, String phone, String status) {
        this.oreder_id = oreder_id;
        this.recipient_name = recipient_name;
        this.phone = phone;
        this.status = status;
    }

    public String status;

    public String getOreder_id() {
        return oreder_id;
    }

    public void setOreder_id(String oreder_id) {
        this.oreder_id = oreder_id;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
