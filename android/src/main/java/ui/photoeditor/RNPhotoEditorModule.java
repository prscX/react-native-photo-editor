
package ui.photoeditor;

import com.ahmedadeltito.photoeditor.PhotoEditorActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import java.util.ArrayList;

public class RNPhotoEditorModule extends ReactContextBaseJavaModule {

  private static final int PHOTO_EDITOR_REQUEST = 1;
  private static final String E_PHOTO_EDITOR_CANCELLED = "E_PHOTO_EDITOR_CANCELLED";


  private Callback mDoneCallback;
  private Callback mCancelCallback;

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      if (requestCode == PHOTO_EDITOR_REQUEST) {

        if (mDoneCallback != null) {

          if (resultCode == Activity.RESULT_CANCELED) {
            mCancelCallback.invoke(resultCode);
          } else {
            mDoneCallback.invoke(intent.getExtras().getString("imagePath"));
          }

        }

        mCancelCallback = null;
        mDoneCallback = null;
      }
    }
  };

  public RNPhotoEditorModule(ReactApplicationContext reactContext) {
    super(reactContext);

    reactContext.addActivityEventListener(mActivityEventListener);

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


    mCancelCallback = onCancel;
    mDoneCallback = onDone;

    getCurrentActivity().startActivityForResult(intent, PHOTO_EDITOR_REQUEST);
  }
}
