package tech.zettervall.notes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import tech.zettervall.mNotes.R;
import tech.zettervall.notes.models.Tag;

public class TagAdapter extends PagedListAdapter<Tag, TagAdapter.TagViewHolder> {

    private static final String TAG = TagAdapter.class.getSimpleName();
    private OnTagClickListener mOnTagClickListener;

    public TagAdapter(OnTagClickListener onTagClickListener) {
        super(DIFF_CALLBACK);
        mOnTagClickListener = onTagClickListener;
    }

    /**
     * Callback to check for difference and decide whether to update the list.
     */
    private static DiffUtil.ItemCallback<Tag> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Tag>() {
                @Override
                public boolean areItemsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
                    return oldItem.equals(newItem);
                }
            };

    /**
     * ViewHolder.
     */
    class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTagTextView;
        private ImageView mClearImageView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find Views
            mTagTextView = itemView.findViewById(R.id.list_tag_textview);
            mClearImageView = itemView.findViewById(R.id.list_tag_clear_imageview);

            // Set OnClickListener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnTagClickListener.onTagClick(getAdapterPosition());
        }
    }

    /**
     * Callback interface.
     */
    public interface OnTagClickListener {
        void onTagClick(int index);
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_tag, viewGroup, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = getItem(position);
        if (tag != null) {
            String tagString = "#" + tag.getTag();
            holder.mTagTextView.setText(tagString);
        }
    }
}
