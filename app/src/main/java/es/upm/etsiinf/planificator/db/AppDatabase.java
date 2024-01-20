package es.upm.etsiinf.planificator.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.upm.etsiinf.planificator.model.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static String DBNAME;

    public abstract EventDao eventdetailsDao();


    public static AppDatabase getDatabase(final Context context) {
        DBNAME = "planificatorDB";
        if(INSTANCE==null){
            synchronized (AppDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DBNAME).
                            fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;

    }

}
