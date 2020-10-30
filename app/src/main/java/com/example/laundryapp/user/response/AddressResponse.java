package com.example.laundryapp.user.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressResponse {

    @SerializedName("address")
    public ArrayList<Addres> address;
    @SerializedName("status")
    public String status;

    public ArrayList<Addres> getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }



    public class Addres {
        @SerializedName("user_id")
        public String user_id;

        @SerializedName("street_address")
        public String street_address;

        @SerializedName("zone_number")
        public String zone_no;

        @SerializedName("building_number")
        public String building_number;

        public String getUser_id() {
            return user_id;
        }

        public String getStreet_address() {
            return street_address;
        }

        public String getZone_no() {
            return zone_no;
        }

        public String getBuilding_number() {
            return building_number;
        }




    }
}
