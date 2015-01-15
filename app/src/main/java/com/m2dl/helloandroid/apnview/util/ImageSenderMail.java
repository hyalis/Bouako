package com.m2dl.helloandroid.apnview.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;
import com.kristijandraca.backgroundmaillibrary.Utils;

public class ImageSenderMail extends ImageSender {


    public final String EMAIL = "biodiversity.android.m2dl@gmail.com";
    public final String PASS  = "ffRKyfA2KckXWS+xXuRs6w=="; //encrypted pass (real is O.F.Mm2dl)

    public ImageSenderMail(Context c, Bitmap i, String type)
    {
        super(c, i, type);
    }

    @Override
    public void sendImage() {
        String imagePath = this.saveBitmapToFile(this.image).getPath();

        BackgroundMail bm = new BackgroundMail(context);
        bm.setGmailUserName(EMAIL);
        bm.setGmailPassword(Utils.decryptIt(PASS));
        bm.setMailTo(EMAIL);
        bm.setFormSubject("New image uploaded to biodiversity android : "+ this.imageType);
        bm.setFormBody("You will find the new image in attachments.");
        bm.setSendingMessage("Sending image to android biodiversity mail database...");
        bm.setSendingMessageSuccess("Your message was sent successfully.");
        bm.setProcessVisibility(true);
        bm.setAttachment(imagePath);

        bm.send();
    }
}
