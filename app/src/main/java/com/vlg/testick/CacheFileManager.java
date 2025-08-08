package com.vlg.testick;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheFileManager {

    public static boolean saveTextToCache(Context context, String fileName, String content) {
        try {
            File cacheFile = new File(context.getCacheDir(), fileName);

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
