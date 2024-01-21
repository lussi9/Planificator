package es.upm.etsiinf.planificator.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Entity
public class Event {

    @SerializedName("name")
    @PrimaryKey
    @NonNull
    @Expose
    private String name;


    @NonNull
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("hour")
    @Expose
    private String hour;

    @SerializedName("description")
    @Expose
    private String description;

    public Event(){

    }

    public Event( @NonNull String name, String date, String hour, String description) {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
