package tech.zettervall.notes.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

import tech.zettervall.notes.utils.DateTimeHelper;

@Parcel
@Entity(tableName = "note")
public class Note implements Comparable<Note> {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String type, headline, text, date;

    // Empty Constructor for Parcel
    @Ignore
    public Note() {
    }

    @Ignore
    public Note(String type, String headline, String text) {
        this.type = type;
        this.headline = headline;
        this.text = text;
        this.date = DateTimeHelper.getCurrentDateTime();
    }

    public Note(int _id, String type, String headline, String text) {
        this._id = _id;
        this.type = type;
        this.headline = headline;
        this.text = text;
        this.date = DateTimeHelper.getCurrentDateTime();
    }

    public int get_id() {
        return _id;
    }

    public String getType() {
        return type;
    }

    public String getHeadline() {
        return headline;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Compare contents for diff check in Adapter.
     *
     * @param note Note to compare to this
     * @return 0 when match and otherwise -1
     */
    @Override
    public int compareTo(@NonNull Note note) {
        boolean matchHeadline = this.headline.equals(note.getHeadline()),
                matchText = this.text.equals(note.getText());
        if (matchHeadline && matchText) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", type='" + type + '\'' +
                ", headline='" + headline + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
