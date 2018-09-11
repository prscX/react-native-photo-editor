
package ui.photoeditor;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
import com.ahmedadeltito.photoeditor.PhotoEditorActivity;
import android.content.Intent;

import java.util.ArrayList;

public class RNPhotoEditorModule extends ReactContextBaseJavaModule {

  public RNPhotoEditorModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNPhotoEditor";
  }

  @ReactMethod
  public void Edit(final ReadableMap props, final Callback onDone, final Callback onCancel) {
    String path = props.getString("path");

    //Process Stickers
    ReadableArray stickers = props.getArray("stickers");
    ArrayList<Integer> stickersIntent = new ArrayList<Integer>();

    for (int i = 0;i < stickers.size();i++) {
      int drawableId = getReactApplicationContext().getResources().getIdentifier(stickers.getString(i), "drawable", getReactApplicationContext().getPackageName());

      stickersIntent.add(drawableId);
    }

    //Process Hidden Controls
    ReadableArray hiddenControls = props.getArray("hiddenControls");
    ArrayList hiddenControlsIntent = new ArrayList<>();

    for (int i = 0;i < hiddenControls.size();i++) {
      hiddenControlsIntent.add(hiddenControls.getString(i));
    }

    //Process Colors
    ReadableArray colors = props.getArray("colors");
    ArrayList colorPickerColors = new ArrayList<>();

    for (int i = 0;i < colors.size();i++) {
      colorPickerColors.add(Color.parseColor(colors.getString(i)));
    }


    Intent intent = new Intent(getCurrentActivity(), PhotoEditorActivity.class);
    intent.putExtra("selectedImagePath", path);
    intent.putExtra("colorPickerColors", colorPickerColors);
    intent.putExtra("hiddenControls", hiddenControlsIntent);
    intent.putExtra("stickers", stickersIntent);

    getCurrentActivity().startActivityForResult(intent, 1);
  }
}