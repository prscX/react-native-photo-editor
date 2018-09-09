package com.ahmedadeltito.photoeditor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class UtilFunctions {

    // private static final String pattern =
    // "\\s*([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})\\s*";
    // private static final String pattern =
    // "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";

    private static final AlphaAnimation enableAnim = new AlphaAnimation(0.6f,
            1.0f);

    private static final AlphaAnimation disableAnim = new AlphaAnimation(1.0f,
            0.6f);

    public static boolean stringIsEmpty(String string) {

        if (string != null) {
            if (!string.trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean stringIsNotEmpty(String string) {
        if (string != null && !string.equals("null")) {
            if (!string.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static String[] stringTokenizer(String string) {

        StringTokenizer tokens = new StringTokenizer(string, ",");
        String[] result = new String[tokens.countTokens()];

        for (int i = 0; i < tokens.countTokens(); i++) {
            result[i] = tokens.nextToken();
        }

        return result;
    }

    public static String ArrayToString(ArrayList<String> string) {

        String result = "";
        StringBuilder builder = new StringBuilder();
        int len = string.size() - 1;
        if (string.size() > 0) {
            for (int i = 0; i < len; i++) {
                result += string.get(i) + ", ";
                builder.append(string.get(i));
                builder.append(", ");
            }
            result += string.get(len);
            builder.append(string.get(len));
        }

        return builder.toString();
    }

    public static boolean isValidEmail(String s) {

        // return Pattern.matches(pattern, s);
        try {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static void enableView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                v.setAlpha(1.0f);
                return;
            } catch (Exception e) {
            }
        } else {
            enableAnim.setDuration(100);
            enableAnim.setFillAfter(true);
            v.startAnimation(enableAnim);
        }
    }

    public static void disableView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                v.setAlpha(0.6f);
                return;
            } catch (Exception e) {
            }
        } else {
            disableAnim.setDuration(100);
            disableAnim.setFillAfter(true);
            v.startAnimation(disableAnim);
        }

    }

    public static String stripHtml(String html) {
        String s = Html.fromHtml(html).toString();
        s = s.replaceAll("[\n\r\t]", " ");
        return s;
    }

    public static boolean containSpace(String s) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0
                && youtubeUrl.startsWith("http")) {
            String expression = "^.*((youtu.be"
                    + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
            }
        }
        return video_id;
    }

    public static String encodeImage(Bitmap bitmap) {
        String encodedImage = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            baos.close();
            baos = null;
            encodedImage = Base64.encodeToString(b,
                    Base64.NO_WRAP);
        } catch (Exception e) {

        }
        return encodedImage;
    }

    public static void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showKeyboardAt(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
