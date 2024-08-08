package com.acn.jive.mastadonweatherbot.mastodon;

import social.bigbone.MastodonClient;
import social.bigbone.api.entity.MediaAttachment;
import social.bigbone.api.exception.BigBoneRequestException;
import social.bigbone.api.method.FileAsMediaAttachment;
import java.io.File;

public class UploadImage {

    public String execute(MastodonClient client, File tempFile) throws MastodonException {
        try {
            MediaAttachment uploadedFile = client.media().uploadMediaAsync(
                    new FileAsMediaAttachment(tempFile, "image/png")
            ).execute();
            return uploadedFile.getId();
        } catch(BigBoneRequestException ex) {
            throw new MastodonException("An exception occurred while uploading the image to mastodon", ex);
        }
    }

}
