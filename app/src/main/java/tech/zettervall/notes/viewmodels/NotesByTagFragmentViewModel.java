package tech.zettervall.notes.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import tech.zettervall.notes.Constants;
import tech.zettervall.notes.models.Note;
import tech.zettervall.notes.repositories.NoteRepository;

/**
 * ViewModel for NotesByTagFragment.
 */
public class NotesByTagFragmentViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;
    private LiveData<PagedList<Note>> mNotes;

    public NotesByTagFragmentViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = NoteRepository.getInstance(application);
    }

    public LiveData<PagedList<Note>> getNotes() {
        return mNotes;
    }

    public void setNotes(int tagID, @Nullable String query) {
        mNotes = new LivePagedListBuilder<>(mNoteRepository.getNotesPagedList(tagID, query),
                Constants.NOTE_LIST_PAGE_SIZE).build();
    }

    public void updateNote(Note note) {
        mNoteRepository.updateNote(note);
    }
}
