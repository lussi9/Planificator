package es.upm.etsiinf.planificator.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import es.upm.etsiinf.planificator.model.Event;

@Dao
public interface EventDao {

    @Insert(onConflict = REPLACE)
    void save(List<Event> eventdetails);

    @Insert(onConflict = REPLACE)
    void save(Event event);

    @Query("DELETE FROM Event")
    void deleteAll();

    @Query("SELECT * FROM Event WHERE date = :date ORDER BY hour ASC")
    LiveData<List<Event>> getEventsByDate(String date);

    @Query("SELECT * FROM Event WHERE name = :name")
    LiveData<Event> getEvent(String name);
}
