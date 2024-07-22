package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.weather.Weather;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.MediaAttachment;
import social.bigbone.api.entity.data.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;
import social.bigbone.api.method.FileAsMediaAttachment;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class PostStatus {

    private static final String INSTANCE = "mastodon.social";
    private static final String ACCESS_TOKEN = "j1teFkzqidPCXSsl9iBLfjy3csKpLKtnNnPwkfqJOIg";

    public void postStatus(Weather weather) throws BigBoneRequestException {

        // Instantiate client
        final MastodonClient client = new MastodonClient.Builder(INSTANCE)
                .accessToken(ACCESS_TOKEN)
                .build();

        // Upload image (später ggf. mit classLoader?)
        /*
        final File uploadFile = new File("./src/main/resources/cake.jpg");
        final MediaAttachment uploadedFile = client.media().uploadMediaAsync(
                new FileAsMediaAttachment(uploadFile, "image/jpg")
        ).execute();
        final String mediaId = uploadedFile.getId();
        */


        // Post status
        String statusText = "The weather in " + weather.getCity() + " is " + weather.getDescription().toLowerCase() + " and the temperature is " + weather.getTemperature() + "°C";

        //final List<String> mediaIds = Collections.singletonList(mediaId);
        final Visibility visibility = Visibility.PRIVATE;

        client.statuses().postStatus(statusText, null, visibility).execute();

    }
}