package com.acn.jive.mastadonweatherbot.mastodon;

import com.acn.jive.mastadonweatherbot.http.ImageService;
import com.acn.jive.mastadonweatherbot.http.ImageServiceException;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.Status;
import social.bigbone.api.entity.data.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PostStatus {

    private static final String INSTANCE = "mastodon.social";
    private static final String ACCESS_TOKEN = System.getenv("Mastodon_Access_Token");

    public MastodonPost execute(Weather weather) throws MastodonException {
        ImageService imageService = new ImageService();
        UploadImage uploadImage = new UploadImage();
        MastodonPost mastodonPost = new MastodonPost();

        final MastodonClient client = new MastodonClient.Builder(INSTANCE)
                .accessToken(ACCESS_TOKEN)
                .build();

        try {
            File tempFile = imageService.createFile();
            imageService.download(weather, tempFile);
            String mediaId = uploadImage.execute(client, tempFile);

            String statusText = weather.toString();
            List<String> mediaIds = Collections.singletonList(mediaId);
            Visibility visibility = Visibility.PUBLIC;

            Status status = client.statuses().postStatus(statusText, mediaIds, visibility).execute();
            mastodonPost.setPostTimestamp(LocalDateTime.now());
            mastodonPost.setPostLink(status.getUrl());
            mastodonPost.setIconUrl(weather.getIconUrl());
            mastodonPost.setDescription(statusText);
            return mastodonPost;

        } catch (BigBoneRequestException | ImageServiceException ex) {
            throw new MastodonException("An exception occurred while trying to post to Mastodon", ex);
        }
    }

}
