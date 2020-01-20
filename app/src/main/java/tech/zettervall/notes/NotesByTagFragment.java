package tech.zettervall.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import tech.zettervall.mNotes.R;
import tech.zettervall.notes.adapters.NoteAdapter;
import tech.zettervall.notes.models.Note;
import tech.zettervall.notes.models.Tag;
import tech.zettervall.notes.utils.RecyclerViewUtil;
import tech.zettervall.notes.viewmodels.NotesByTagFragmentViewModel;

public class NotesByTagFragment extends BaseListFragment {

    private static final String TAG = AllNotesFragment.class.getSimpleName();
    private NotesByTagFragmentViewModel mNotesByTagFragmentViewModel;
    private Tag mTag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_notelist, container, false);

        // Get TagID
        if (getArguments() != null) {
            mTag = Parcels.unwrap(getArguments().getParcelable((Constants.TAG)));
        }

        // Initialize ViewModel
        mNotesByTagFragmentViewModel = ViewModelProviders.of(this).get(NotesByTagFragmentViewModel.class);
        mNotesByTagFragmentViewModel.setNotes(mTag.getId(), null);

        // Find Views
        mRecyclerView = rootView.findViewById(R.id.fragment_notelist_recyclerview);
        mFab = rootView.findViewById(R.id.fragment_notelist_fab);
        emptyTextView = rootView.findViewById(R.id.fragment_notelist_is_empty_textview);

        // Set Adapter / LayoutManager / Decoration
        mNoteAdapter = new NoteAdapter(this);
        mLayoutManager = RecyclerViewUtil.getDefaultLinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mNoteAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewUtil.setRecyclerViewDecoration(mLayoutManager, mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mItemToucherHelperCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        try {
                            Note note = mNoteAdapter.getCurrentList().get(viewHolder.getAdapterPosition());
                            note.setTrash(true);
                            mNotesByTagFragmentViewModel.updateNote(note);
                            String toastMessage = note.getTitle() != null && !note.getTitle().isEmpty() ?
                                    getString(R.string.note_trashed_detailed, note.getTitle()) :
                                    getString(R.string.note_trashed);
                            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                };
        new ItemTouchHelper(mItemToucherHelperCallback).attachToRecyclerView(mRecyclerView);

        // Set FAB OnClickListener
        mFab.setOnClickListener(this::fabClick);

        // Set title
        getActivity().setTitle(mTag.getTitle());

        // Subscribe Observers
        subscribeObservers();

        return rootView;
    }

    @Override
    protected void fabClick(View v) {
        callback.onFragmentFabClick(false, mTag);
    }

    @Override
    public void subscribeObservers() {
        mNotesByTagFragmentViewModel.getNotes().observe(this, super::updateAdapter);
    }

    @Override
    public void refreshObservers(@Nullable String query) {
        mNotesByTagFragmentViewModel.getNotes().removeObservers(getViewLifecycleOwner());
        mNotesByTagFragmentViewModel.setNotes(mTag.getId(), query);
        subscribeObservers();
    }

    @Override
    public void onNoteClick(int index) {
        try {
            // Get Note
            Note note = mNoteAdapter.getCurrentList().get(index);
            // Send Note to callback interface
            callback.onNoteClick(note);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
