package com.ahmedadeltito.photoeditor;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 5/5/17.
 */

public class EmojiFragment extends Fragment implements EmojiAdapter.OnEmojiClickListener {

    private ArrayList<String> emojiIds;
    private PhotoEditorActivity photoEditorActivity;
    RecyclerView emojiRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoEditorActivity = (PhotoEditorActivity) getActivity();

        String[] emojis = photoEditorActivity.getResources().getStringArray(R.array.photo_editor_emoji);

        emojiIds = new ArrayList<>();
        Collections.addAll(emojiIds, emojis);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_photo_edit_emoji, container, false);

        emojiRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_photo_edit_emoji_rv);
        emojiRecyclerView.setHasFixedSize(true);
        emojiRecyclerView.setLayoutManager(new GridLayoutManager(photoEditorActivity, 4));
        EmojiAdapter adapter = new EmojiAdapter(photoEditorActivity, emojiIds);
        adapter.setOnEmojiClickListener(this);
        emojiRecyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onEmojiClickListener(String emojiName) {
        photoEditorActivity.addEmoji(emojiName);
    }
}
