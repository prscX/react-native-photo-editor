package com.ahmedadeltito.photoeditor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 5/5/17.
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {

//    private Typeface emojiFont;
    private Context context;
    private List<String> emojiIds;
    private LayoutInflater inflater;
    private OnEmojiClickListener onEmojiClickListener;

    public EmojiAdapter(@NonNull Context context, @NonNull List<String> emojiIds) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.emojiIds = emojiIds;
//        emojiFont = Typeface.createFromAsset(context.getAssets(), "emojione-android.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_photo_edit_emoji_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String emojiId = emojiIds.get(position);
        holder.emojiTextView.setText(convertEmoji(emojiId));
    }

    @Override
    public int getItemCount() {
        return emojiIds.size();
    }

    public void setOnEmojiClickListener(OnEmojiClickListener onEmojiClickListener) {
        this.onEmojiClickListener = onEmojiClickListener;
    }

    private String convertEmoji(String emoji) {
        String returnedEmoji = "";
        try {
            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
            returnedEmoji = getEmojiByUnicode(convertEmojiToInt);
        } catch (NumberFormatException e) {
            returnedEmoji = "";
        }
        return returnedEmoji;
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            emojiTextView = (TextView) itemView.findViewById(R.id.fragment_photo_edit_emoji_tv);
            emojiTextView.setTypeface(PhotoEditorActivity.emojiFont);
            emojiTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEmojiClickListener != null)
                        onEmojiClickListener.onEmojiClickListener(emojiIds.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnEmojiClickListener {
        void onEmojiClickListener(String emojiName);
    }
}
