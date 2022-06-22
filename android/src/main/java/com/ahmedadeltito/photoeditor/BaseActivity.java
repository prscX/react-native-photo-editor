package com.ahmedadeltito.photoeditor;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 09/06/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected static final int CAMERA_CODE = 0x0;
    protected static final int GALLERY_INTENT_CALLED = 0x1;
    protected static final int GALLERY_KITKAT_INTENT_CALLED = 0x2;
    protected static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY = 0x3;
    protected static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA = 0x4;
    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    protected Bitmap bitmap;
    protected boolean _taken;
    protected String selectedImagePath;
    protected String selectedOutputPath;

    private static final String PHOTO_PATH = "PhotoEditor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void openMedia() {

        final CharSequence[] items = {getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.upload_dialog_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    startCameraActivity();
                    dialog.dismiss();
                } else if (item == 1) {
                    openGallery();
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Method to show user msg material menu
    public void showMenu(final int caller) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.access_media_permissions_msg));
        builder.setPositiveButton(getString(R.string.continue_txt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (caller == 1) {
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(BaseActivity.this, getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

    // startCameraActivity() method fires once user shows camera option and CAMERA_CODE is an requestCode that will be used
    protected void startCameraActivity() {
        int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    getOutputMediaFile());
            photoPickerIntent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(
                    Intent.createChooser(photoPickerIntent, getString(R.string.upload_picker_title)),
                    CAMERA_CODE);
        } else {
            showMenu(1);
        }
    }

    /**
     * Create a file for saving an image
     */
    protected Uri getOutputMediaFile() {

        if (isSDCARDMounted()) {
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PHOTO_PATH);
            // Create a storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MediaAbstractActivity", getString(R.string.directory_create_fail));
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            selectedOutputPath = mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg";
            Log.d("MediaAbstractActivity", "selected camera path "
                    + selectedOutputPath);
            mediaFile = new File(selectedOutputPath);
            return Uri.fromFile(mediaFile);
        } else {
            return null;
        }
    }

    protected boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);

    }

    // openGallery() method fires once user shows gallery option and GALLERY_INTENT_CALLED and GALLERY_KITKAT_INTENT_CALLED
    // are requestCode(s) that will be used in ProfileMediaSherlockFragmetActivity
    protected void openGallery() {
        int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (!isKitKat) {
                Intent intent = new Intent();
                intent.setType("image/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.upload_picker_title)),
                        GALLERY_INTENT_CALLED);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
            }
        } else {
            showMenu(2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY: {
                // If request is cancelled, the result arrays are empty.
                int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    if (!isKitKat) {
                        Intent intent = new Intent();
                        intent.setType("image/jpeg");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(
                                Intent.createChooser(intent, getString(R.string.upload_picker_title)),
                                GALLERY_INTENT_CALLED);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/jpeg");
                        startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            getOutputMediaFile());
                    photoPickerIntent.putExtra("outputFormat",
                            Bitmap.CompressFormat.JPEG.toString());
                    // photoPickerIntent.putExtra("return-data", true);
                    startActivityForResult(
                            Intent.createChooser(photoPickerIntent, getString(R.string.upload_picker_title)),
                            CAMERA_CODE);
                } else {
                    Toast.makeText(this, getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected String getPath(final Uri uri) {
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (GalleryUtils.isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (GalleryUtils.isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return GalleryUtils.getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if (GalleryUtils.isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return GalleryUtils.getDataColumn(this, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return GalleryUtils.getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @Override
    protected void onDestroy() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        super.onDestroy();
    }
}
