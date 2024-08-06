package com.acn.jive.mastadonweatherbot.persistence;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;

public class PostHistory {

    private String guid;
    private LocalDateTime timestampWeatherRequest;
    private JSONObject weatherApiResponse;
    private LocalDateTime timestampMastodonPosted;
    private String errorLogging;
    private String postLink;
    private Location location;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime getTimestampWeatherRequest() {
        return timestampWeatherRequest;
    }

    public void setTimestampWeatherRequest(LocalDateTime timestampWeatherRequest) {
        this.timestampWeatherRequest = timestampWeatherRequest;
    }

    public JSONObject getWeatherApiResponse() {
        return weatherApiResponse;
    }

    public void setWeatherApiResponse(JSONObject weatherApiResponse) {
        this.weatherApiResponse = weatherApiResponse;
    }

    public LocalDateTime getTimestampMastodonPosted() {
        return timestampMastodonPosted;
    }

    public void setTimestampMastodonPosted(LocalDateTime timestampMastodonPosted) {
        this.timestampMastodonPosted = timestampMastodonPosted;
    }

    public String getErrorLogging() {
        return errorLogging;
    }

    public void setErrorLogging(String errorLogging) {
        this.errorLogging = errorLogging;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
