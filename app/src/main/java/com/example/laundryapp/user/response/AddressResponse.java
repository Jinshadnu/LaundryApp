package com.example.laundryapp.user.response;

public class AddressResponse {
    private Addres address;
    private String status;

    public Addres getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }



    private class Addres {
        private String user_id;

        private String street_address;

        private String zone_no;

        private String building_number;

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
