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

    public void download(Weather weather, File tempFile) throws ImageServiceException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            URL url = new URL("https:" + weather.getIconUrl());
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException ex) {
            throw new ImageServiceException("An exception occurred while downloading the image file", ex);
        }
    }

    public File createFile() throws ImageServiceException {
        try {
            File tempFile = File.createTempFile(
                    "icon", ".tmp",
                    new File(System.getProperty("java.io.tmpdir"))); // System property hat gespeichert wo mein temp-Ordner ist
            System.out.println(
                    "Temporary file is located on Specified location: "
                            + tempFile.getAbsolutePath());
            return tempFile;
        } catch (IOException ex) {
            throw new ImageServiceException("An exception occurred while creating the temporary file", ex);
        }
    }
}
