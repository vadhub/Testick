package com.vlg.testick.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class FileSaveFragment extends BaseFragment {
    private static final int STORAGE_PERMISSION_CODE = 100;
    protected String fileName = "";
    protected String content = "";
    protected String cacheFileName = "";
    protected boolean isChach;

    protected void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (isChach) {
                saveCacheToDocuments();
            } else {
                saveFileToDocuments();
            }
            return;
        }

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (isChach) {
                saveCacheToDocuments();
            } else {
                saveFileToDocuments();
            }
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isChach) {
                    saveCacheToDocuments();
                } else {
                    saveFileToDocuments();
                }
            } else {
                Toast.makeText(requireContext(),
                        "Permission denied! Cannot save file",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveFileToDocuments() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveViaMediaStore();
            } else {
                saveViaFileApi();
            }
            Toast.makeText(requireContext(),
                    "File saved: " + fileName,
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("!!", "File save error: " + e.getMessage());
            Toast.makeText(requireContext(),
                    "Save failed: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void saveViaMediaStore() throws IOException {
        ContentResolver resolver = requireContext().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri fileUri = resolver.insert(collection, values);

        if (fileUri == null) {
            throw new IOException("Failed to create file in MediaStore");
        }

        try (OutputStream os = resolver.openOutputStream(fileUri)) {
            if (os != null) {
                os.write(content.getBytes(StandardCharsets.UTF_8));
            } else {
                throw new IOException("Output stream is null");
            }
        }
    }

    private void saveViaFileApi() throws IOException {
        File docsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
        );

        if (!docsDir.exists() && !docsDir.mkdirs()) {
            throw new IOException("Failed to create Documents directory");
        }

        File file = new File(docsDir, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void saveCacheToDocuments() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveFromCacheViaMediaStore();
            } else {
                saveFromCacheViaFileApi();
            }
            Toast.makeText(requireContext(),
                    "File saved: " + fileName,
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("!!!", "File save error: " + e.getMessage());
            Toast.makeText(requireContext(),
                    "Save failed: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void saveFromCacheViaMediaStore() throws IOException {
        ContentResolver resolver = requireContext().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri fileUri = resolver.insert(collection, values);

        if (fileUri == null) {
            throw new IOException("Failed to create file in MediaStore");
        }

        File cacheFile = new File(requireContext().getCacheDir(), cacheFileName);
        try (InputStream is = new FileInputStream(cacheFile);
             OutputStream os = resolver.openOutputStream(fileUri)) {

            if (os == null) {
                throw new IOException("Output stream is null");
            }
            copyStream(is, os);
        }
    }

    private void saveFromCacheViaFileApi() throws IOException {
        File docsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
        );

        if (!docsDir.exists() && !docsDir.mkdirs()) {
            throw new IOException("Failed to create Documents directory");
        }

        File targetFile = new File(docsDir, fileName);

        File cacheFile = new File(requireContext().getCacheDir(), cacheFileName);

        try (InputStream is = new FileInputStream(cacheFile);
             OutputStream os = new java.io.FileOutputStream(targetFile)) {
            copyStream(is, os);
        }
    }

    private void copyStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[8192];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    }
}
