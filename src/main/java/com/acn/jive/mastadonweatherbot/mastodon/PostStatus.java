package com.acn.jive.mastadonweatherbot.mastodon;

import com.acn.jive.mastadonweatherbot.http.ImageService;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.data.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PostStatus {

    private static final String INSTANCE = "mastodon.social";
    private static final String ACCESS_TOKEN = "j1teFkzqidPCXSsl9iBLfjy3csKpLKtnNnPwkfqJOIg";

    public void execute(Weather weather) throws BigBoneRequestException {
        ImageService imageService = new ImageService();
        UploadImage uploadImage = new UploadImage();

        // Instantiate client
        final MastodonClient client = new MastodonClient.Builder(INSTANCE)
                .accessToken(ACCESS_TOKEN)
                .build();

        // Download image from URL weather.getUrl()
        try {
            File tempFile = imageService.createFile();
            imageService.download(weather, tempFile);
            String mediaId = uploadImage.execute(client, tempFile);

            String statusText = weather.toString();
            List<String> mediaIds = Collections.singletonList(mediaId);
            Visibility visibility = Visibility.PUBLIC;

            client.statuses().postStatus(statusText, mediaIds, visibility).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
