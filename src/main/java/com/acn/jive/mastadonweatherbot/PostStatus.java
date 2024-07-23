package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.weather.Weather;
import social.bigbone.MastodonClient;
import social.bigbone.api.entity.MediaAttachment;
import social.bigbone.api.entity.data.Visibility;
import social.bigbone.api.exception.BigBoneRequestException;
import social.bigbone.api.method.FileAsMediaAttachment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
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

        // Download image from URL weather.getUrl()
        try {
            File tempFile = createFile();
            downloadImg(weather, tempFile);
            String mediaId = uploadImg(client, tempFile);

            String statusText = "The weather in " + weather.getCity() + " is " + weather.getDescription().toLowerCase() + " and the temperature is " + weather.getTemperature() + "°C";
            List<String> mediaIds = Collections.singletonList(mediaId);
            Visibility visibility = Visibility.PUBLIC;

            client.statuses().postStatus(statusText, mediaIds, visibility).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String uploadImg(MastodonClient client, File tempFile) throws BigBoneRequestException {
        MediaAttachment uploadedFile = client.media().uploadMediaAsync(
                new FileAsMediaAttachment(tempFile, "image/png")
        ).execute();
        String mediaId = uploadedFile.getId();
        return mediaId;
    }

    private void downloadImg(Weather weather, File tempFile) throws IOException {
        URL url = new URL("https:" + weather.getIconUrl());
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        FileChannel fileChannel = fileOutputStream.getChannel();

        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }

    private File createFile() throws IOException {
        File tempFile = File.createTempFile(
                "icon", ".tmp",
                new File(System.getProperty("java.io.tmpdir"))); // System property hat gespeichert wo mein temp-Ordner ist
        System.out.println(
                "Temporary file is located on Specified location: "
                        + tempFile.getAbsolutePath());
        return tempFile;
    }
}
