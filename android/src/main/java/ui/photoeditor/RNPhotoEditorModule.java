
package ui.photoeditor;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
import com.ahmedadeltito.photoeditor.PhotoEditorActivity;
import android.content.Intent;

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

    Intent intent = new Intent(getCurrentActivity(), PhotoEditorActivity.class);
    intent.putExtra("selectedImagePath", path);

    getCurrentActivity().startActivity(intent);

//    if (mActivity != null) {
//      mActivity.startActivityForResult(intent, mRequestCode);
//    } else if (mFragment != null) {
//      mFragment.startActivityForResult(intent, mRequestCode);
//    } else {
//      mSupportFragment.startActivityForResult(intent, mRequestCode);
//    }

//      PhotoEditorSDK photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(PhotoEditorActivity.this)
//      .buildPhotoEditorSDK();
  }
}