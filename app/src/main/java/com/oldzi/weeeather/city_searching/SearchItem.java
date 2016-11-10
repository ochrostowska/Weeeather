package com.oldzi.weeeather.city_searching;

public class SearchItem {

    private String cityName;
    private String countryCode;
    private String country;
    private String latitude;
    private String longitude;

    public SearchItem(String cityName, String countryCode, String latitude, String longitude) {
        this.cityName = StringResponseModifications.cutCityName(cityName);
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
        try {
        country = StringResponseModifications.getNamefromCode(countryCode);
        } catch (Exception e) {
            country = "";
        }
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}

