package com.ahmedadeltito.photoeditor;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import ui.photoeditor.R;
/**
 * Created by Ahmed Adel on 5/4/17.
 */

public class ImageFragment extends Fragment implements ImageAdapter.OnImageClickListener {

    private ArrayList<Bitmap> stickerBitmaps;
    private PhotoEditorActivity photoEditorActivity;
    RecyclerView imageRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoEditorActivity = (PhotoEditorActivity) getActivity();

        TypedArray images = getResources().obtainTypedArray(R.array.photo_editor_photos);

        ArrayList<Integer> stickers = (ArrayList<Integer>) getActivity().getIntent().getExtras().getSerializable("stickers");

        if (stickers != null && stickers.size() > 0) {
            stickerBitmaps = new ArrayList<>();

            for (int i = 0;i < stickers.size();i++) {
                stickerBitmaps.add(decodeSampledBitmapFromResource(getActivity().getResources(), stickers.get(i), 120, 120));
            }
        } else {
            stickerBitmaps = new ArrayList<>();
            for (int i = 0; i < images.length(); i++) {
                stickerBitmaps.add(decodeSampledBitmapFromResource(photoEditorActivity.getResources(), images.getResourceId(i, -1), 120, 120));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_photo_edit_image, container, false);

        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_photo_edit_image_rv);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(photoEditorActivity, 3));
        ImageAdapter adapter = new ImageAdapter(photoEditorActivity, stickerBitmaps);
        adapter.setOnImageClickListener(this);
        imageRecyclerView.setAdapter(adapter);

        return rootView;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public void onImageClickListener(Bitmap image) {
        photoEditorActivity.addImage(image);
    }
}
