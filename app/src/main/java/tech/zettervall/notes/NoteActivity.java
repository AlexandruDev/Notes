package tech.zettervall.notes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import tech.zettervall.mNotes.R;
import tech.zettervall.notes.models.Note;

public class NoteActivity extends BaseActivity {

    private static final String TAG = NoteActivity.class.getSimpleName();
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Find Views
        mToolbar = findViewById(R.id.toolbar);

        // Set ToolBar
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        // Set Fragments
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) { // Clicked Note
                Note note = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.NOTE));
                setNoteFragment(getNoteFragmentWithBundledNote(note));
            } else { // New Note
                setNoteFragment(new NoteFragment());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_favoritize:
                // Implemented in Fragment
                break;
            case R.id.action_delete:
                // Implemented in Fragment
                break;
        }
        return false;
    }
}