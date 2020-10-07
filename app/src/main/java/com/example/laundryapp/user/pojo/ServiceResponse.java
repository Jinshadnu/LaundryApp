package com.example.laundryapp.user.pojo;

import com.example.laundryapp.fragments.pojo.Categories;
import com.example.laundryapp.fragments.pojo.Services;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("services")
    public ArrayList<ourServices> services;



    public ArrayList<ourServices> getServices() {
        return services;
    }

    public void setServices(ArrayList<ourServices> services) {
        this.services = services;
    }


    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }




    private class ourServices {
        @SerializedName("service_description")
        private String service_description;

        @SerializedName("service_name")
        private String service_name;

        @SerializedName("service_id")
        private String service_id;

        @SerializedName("service_image")
        private String service_image;

        @SerializedName("category")
        private ArrayList<Categorise> category;

        public ArrayList<Categorise> getCategory() {
            return category;
        }



        public String getService_description ()
        {
            return service_description;
        }

        public void setService_description (String service_description)
        {
            this.service_description = service_description;
        }

        public String getService_name ()
        {
            return service_name;
        }

        public void setService_name (String service_name)
        {
            this.service_name = service_name;
        }

        public String getService_id ()
        {
            return service_id;
        }

        public void setService_id (String service_id)
        {
            this.service_id = service_id;
        }

        public String getService_image ()
        {
            return service_image;
        }

        public void setService_image (String service_image)
        {
            this.service_image = service_image;
        }


        private class Categorise {
            @SerializedName("category_name")
            private String category_name;

            @SerializedName("category_id")
            private String category_id;

            @SerializedName("items")
            private Item[] items;

            private class Item {
                @SerializedName("quantity")
                private String quantity;

                @SerializedName("item_id")
                private String item_id;

                @SerializedName("price")
                private String price;

                @SerializedName("item_name")
                private String item_name;

                public String getQuantity ()
                {
                    return quantity;
                }

                public void setQuantity (String quantity)
                {
                    this.quantity = quantity;
                }

                public String getItem_id ()
                {
                    return item_id;
                }

                public void setItem_id (String item_id)
                {
                    this.item_id = item_id;
                }

                public String getPrice ()
                {
                    return price;
                }

                public void setPrice (String price)
                {
                    this.price = price;
                }

                public String getItem_name ()
                {
                    return item_name;
                }

                public void setItem_name (String item_name)
                {
                    this.item_name = item_name;
                }


            }
        }
    }
}
