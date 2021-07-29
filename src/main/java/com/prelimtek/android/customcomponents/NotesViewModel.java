package com.prelimtek.android.customcomponents;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prelimtek.android.basecomponents.dao.BaseDAOFactory;

import java.util.Date;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private final static String TAG = NotesViewModel.class.getName();
    private Context context;
    private BaseDAOFactory daoFactory;
    private TextDAOInterface combinedDao;
    private TextDAOInterface localDao;
    private TextDAOInterface remoteDao;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        try {
            daoFactory = BaseDAOFactory.instance(context);
            combinedDao = (TextDAOInterface) daoFactory.open(BaseDAOFactory.TYPE.BOTH);
            localDao = (TextDAOInterface) daoFactory.open(BaseDAOFactory.TYPE.LOCAL);
            remoteDao = (TextDAOInterface) daoFactory.open(BaseDAOFactory.TYPE.REMOTE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    NotesModel curNotes;

    String modelId;

    public void initializeModel(String id){
        modelId = id;
    }

    public void setCurNotes(NotesModel curNotes) {
        this.curNotes = curNotes;
    }

    public LiveData<List<NotesModel>> loadAllNotes(){
        return null;
    }

    public List<NotesModel> getAllLocalNotes(Long beforeDate, Long afterDate, int pageBufferSize, int offset) {
        return localDao.getNotes(modelId, beforeDate,afterDate, pageBufferSize, offset);
    }
}
