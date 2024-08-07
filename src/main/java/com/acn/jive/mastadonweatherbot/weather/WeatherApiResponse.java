package com.acn.jive.mastadonweatherbot.weather;

import org.json.simple.JSONObject;
import java.time.LocalDateTime;

public class WeatherApiResponse {

    private JSONObject jsonObject;
    private LocalDateTime requestTimestamp;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
}
