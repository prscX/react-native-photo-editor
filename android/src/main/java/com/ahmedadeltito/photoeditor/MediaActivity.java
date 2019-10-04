package com.ahmedadeltito.photoeditor;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import android.util.Log;
import ui.photoeditor.R;
/**
 * Created by Ahmed Adel on 09/06/2017.
 */

public abstract class MediaActivity extends BaseActivity {

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_CANCELED:
                break;
            case RESULT_OK:
                if (requestCode == GALLERY_INTENT_CALLED || requestCode == CAMERA_CODE
                        || requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                    if (requestCode == GALLERY_INTENT_CALLED) {
                        selectedImageUri = data.getData();
                        selectedImagePath = getPath(selectedImageUri);
                    } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                        selectedImageUri = data.getData();
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        // Check for the freshest data.
                        if (selectedImageUri != null) {
                            getContentResolver().takePersistableUriPermission(
                                    selectedImageUri, takeFlags);
                            selectedImagePath = getPath(selectedImageUri);
                        }
                    } else {
                        selectedImagePath = selectedOutputPath;
                    }

                    if (UtilFunctions.stringIsNotEmpty(selectedImagePath)) {
                        // decode image size
                        BitmapFactory.Options o = new BitmapFactory.Options();
                        o.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(selectedImagePath, o);
                        // Find the correct scale value. It should be the power of
                        // 2.
                        int width_tmp = o.outWidth, height_tmp = o.outHeight;
                        Log.d("MediaActivity", "MediaActivity : image size : "
                                + width_tmp + " ; " + height_tmp);
                        final int MAX_SIZE = getResources().getDimensionPixelSize(
                                R.dimen.image_loader_post_width);
                        int scale = 1;
                        // while (true) {
                        // if (width_tmp / 2 < MAX_SIZE
                        // || height_tmp / 2 < MAX_SIZE)
                        // break;
                        // width_tmp /= 2;
                        // height_tmp /= 2;
                        // scale *= 2;
                        // }
                        if (height_tmp > MAX_SIZE || width_tmp > MAX_SIZE) {
                            if (width_tmp > height_tmp) {
                                scale = Math.round((float) height_tmp
                                        / (float) MAX_SIZE);
                            } else {
                                scale = Math.round((float) width_tmp
                                        / (float) MAX_SIZE);
                            }
                        }
                        Log.d("MediaActivity", "MediaActivity : scaling image by factor : " + scale);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
                        _taken = true;
                        onPhotoTaken();
                        System.gc();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void onPhotoTaken();
}
