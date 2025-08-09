package com.vlg.testick;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheFileManager {

    public static boolean saveTextToCache(Context context, String fileName, String content) {
        try {
            File cacheFile = new File(context.getCacheDir(), fileName);

            if (cacheFile.exists()) {
                if (!cacheFile.delete()) {
                    Log.d("!!", "file delete");
                }
            }

            cacheFile = new File(context.getCacheDir(), fileName);

            try (FileOutputStream outputStream = new FileOutputStream(cacheFile)) {
                outputStream.write(content.getBytes());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
