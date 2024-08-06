package com.acn.jive.mastadonweatherbot.mastodon;

import java.time.LocalDateTime;

public class MastodonPost {

    private LocalDateTime postTimestamp;
    private String postLink;

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
}
