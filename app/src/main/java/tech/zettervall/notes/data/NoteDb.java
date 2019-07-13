package tech.zettervall.notes.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tech.zettervall.notes.data.typeconverters.TagTypeConverter;
import tech.zettervall.notes.models.Note;
import tech.zettervall.notes.models.Tag;

@Database(entities = {Note.class, Tag.class}, version = 5, exportSchema = false)
@TypeConverters(TagTypeConverter.class)
public abstract class NoteDb extends RoomDatabase {

    private static final String TAG = NoteDb.class.getSimpleName();
    private static final String DB_NAME = "notes_db";
    private static NoteDb INSTANCE;

    public abstract NoteDao noteDao();

    public abstract TagDao tagDao();

    public static NoteDb getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDb.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "Creating new db instance..");
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDb.class,
                            DB_NAME
                    ).build();
                }
            }
        }
        Log.d(TAG, "Getting db instance..");
        return INSTANCE;
    }
}
