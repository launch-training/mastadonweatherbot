package com.acn.jive.mastadonweatherbot.mastodon;

import java.time.LocalDateTime;

public class MastodonPost {

    private LocalDateTime postTimestamp;
    private String postLink;
    private String iconUrl;
    private String description;

    public LocalDateTime getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(LocalDateTime postTimestamp) {
        this.postTimestamp = postTimestamp;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
