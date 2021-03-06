package com.left.im.util;

import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.left.im.Config;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class FaceppDetect {

    public static void detect(final Bitmap bm, final CallBack callBack) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    HttpRequests requests = new HttpRequests(Config.faceppkey, Config.faceppsecretkey, true, true);
                    Bitmap bmSmall = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] arrays = stream.toByteArray();
                    PostParameters params = new PostParameters();
                    params.setImg(arrays);
                    JSONObject jsonObject = requests.detectionDetect(params);
                    if (callBack != null) {
                        callBack.success(jsonObject);
                    }
                } catch (FaceppParseException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.error(e);
                    }
                }
            }
        }).start();
    }

    public interface CallBack {
        void success(JSONObject result);

        void error(FaceppParseException exception);
    }
}
