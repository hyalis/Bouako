package com.m2dl.helloandroid.apnview.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

public class ImageSenderMail extends ImageSender {


    public final String EMAIL = "biodiversity.android.m2dl@gmail.com";
    public final String PASS  = "onlyformem2dl";

    public ImageSenderMail(Context c, Bitmap i, String type)
    {
        super(c, i, type);
    }

    @Override
    public void sendImage() {

        String imagePath = this.savebitmap(this.image).getPath();

        BackgroundMail bm = new BackgroundMail(context);
        bm.setGmailUserName(EMAIL);
        //"DoE/GTiYpX5sz5zmTFuoHg==" is crypted "password"
        bm.setGmailPassword(PASS);
        bm.setMailTo(EMAIL);
        bm.setFormSubject("New image uploaded to biodiversity android"+ this.imageType);
        bm.setFormBody("You will find the new image in attachments.");


        bm.setSendingMessage("Loading...");
        bm.setSendingMessageSuccess("Your message was sent successfully.");
        bm.setProcessVisibility(false);
        bm.setAttachment(imagePath);
        bm.send();
    }
}
