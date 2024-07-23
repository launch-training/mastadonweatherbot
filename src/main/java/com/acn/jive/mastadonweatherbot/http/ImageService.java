package com.acn.jive.mastadonweatherbot.http;

import com.acn.jive.mastadonweatherbot.weather.Weather;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class ImageService {


    public void download(Weather weather, File tempFile) throws IOException {
        URL url = new URL("https:" + weather.getIconUrl());
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        FileChannel fileChannel = fileOutputStream.getChannel();

        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }

    public File createFile() throws IOException {
        File tempFile = File.createTempFile(
                "icon", ".tmp",
                new File(System.getProperty("java.io.tmpdir"))); // System property hat gespeichert wo mein temp-Ordner ist
        System.out.println(
                "Temporary file is located on Specified location: "
                        + tempFile.getAbsolutePath());
        return tempFile;
    }


}
