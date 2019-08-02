package tech.zettervall.notes.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import tech.zettervall.notes.models.Note;
import tech.zettervall.notes.repositories.NoteRepository;

/**
 * ViewModel for MainActivity.
 * Used for when user clicks a notification, this fetches the
 * clicked Note based on ID received from the notification.
 */
public class MainActivityViewModel extends AndroidViewModel {

    private LiveData<Note> mNotificationNote;
    private NoteRepository mNoteRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = NoteRepository.getInstance(application);
    }

    public LiveData<Note> getNotificationNote() {
        return mNotificationNote;
    }

    public void setNote(int noteID) {
        this.mNotificationNote = mNoteRepository.getNoteLiveData(noteID);
    }
}
