package com.m2dl.helloandroid.apnview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;
import com.kristijandraca.backgroundmaillibrary.Utils;

import java.util.ArrayList;

public class ImageSenderMail extends ImageSender {


    public final String EMAIL = "biodiversity.android.m2dl@gmail.com";
    public final String PASS  = "ffRKyfA2KckXWS+xXuRs6w=="; //encrypted pass (real is O.F.Mm2dl)

    public ImageSenderMail(Context c, Bitmap i, String type)
    {
        super(c, i, type);
    }

    /**
     *
     */
    @Override
    public void sendImage(String login, String date, String position, String commentaire) {
        String imagePath = this.saveBitmapToFile(this.image).getPath();

        try {
            BackgroundMail bm = new BackgroundMail(context);
            bm.setGmailUserName(EMAIL);
            bm.setGmailPassword(Utils.decryptIt(PASS));
            bm.setMailTo(EMAIL);
            //bm.setFormSubject("New image uploaded to biodiversity android : "+ this.imageType);

            bm.setFormSubject("\""+ login + "\" uploaded a \"" + this.imageType + "\" the " + date + " at \"" + position + "\" with commentary  : " + commentaire);

            bm.setFormBody("Commentaire :" + commentaire);
            bm.setSendingMessage("Sending image to android biodiversity mail database...");
            bm.setSendingMessageSuccess("Your message was sent successfully.");
            bm.setProcessVisibility(true);
            bm.setAttachment(imagePath);

            bm.send();
        } catch (Exception e)
        {
            Log.e("BACKGROUNDMAIL" , "Exception catchée :"+ e.toString());
        }
    }
}
