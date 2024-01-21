package es.upm.etsiinf.planificator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.List;

import es.upm.etsiinf.planificator.db.AppDatabase;
import es.upm.etsiinf.planificator.model.Event;


public class EventViewModel extends AndroidViewModel {

    private AppDatabase db;
    private LiveData<List<Event>> eventDetailsLiveDataSortDay;

    private LiveData<List<Event>> eventDetailsfordate;

    public EventViewModel(@NonNull Application application){
        super(application);
        init();
    }

    private void init(){
        db = AppDatabase.getDatabase(getApplication().getApplicationContext());
    }

    private void subscribeToEventChanges(){
        eventDetailsfordate = db.eventdetailsDao().getAll();
        
    }

}
