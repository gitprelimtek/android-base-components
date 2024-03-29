package com.prelimtek.android.customcomponents;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.NonNull;


public class TextListProcUtils {

    @Deprecated
    public static void showOrRefreshNotesListFragment(@NonNull String modelId ,@NonNull int notes_list_framelayout, @NonNull FragmentManager childTransactionManager , @NonNull TextDAOInterface dbHelper, boolean editable) {
        showOrRefreshNotesListFragment(modelId,notes_list_framelayout,childTransactionManager,dbHelper,null,null);
    }
    @Deprecated
    public static void showOrRefreshNotesListFragment(@NonNull String modelId ,@NonNull int notes_list_framelayout, @NonNull FragmentManager childTransactionManager , @NonNull TextDAOInterface dbHelper, NotesListDisplayFragment.OnNoteSelectedListener noteSelectedListener) {
        showOrRefreshNotesListFragment(modelId,notes_list_framelayout,childTransactionManager,dbHelper,null,null);
    }
    @Deprecated
    public static void showOrRefreshNotesListFragment(@NonNull String modelId ,@NonNull int notes_list_framelayout, @NonNull FragmentManager childTransactionManager , @NonNull TextDAOInterface dbHelper, boolean editable, NotesListDisplayFragment.LayoutManagerDirection layoutManagerDirection) {
        showOrRefreshNotesListFragment(modelId,notes_list_framelayout,childTransactionManager,dbHelper,null,layoutManagerDirection);
    }

    @Deprecated
    public static void showOrRefreshNotesListFragment(@NonNull String modelId ,@NonNull int notes_list_framelayout, @NonNull FragmentManager childTransactionManager , @NonNull TextDAOInterface dbHelper, NotesListDisplayFragment.OnNoteSelectedListener noteSelectedListener,NotesListDisplayFragment.LayoutManagerDirection layoutManagerDirection){

        //System.out.println("!!!!!!!!  showOrRefreshImageListFragment called -> currentImagesModel size ="+currentImagesModel.getImageNames().size());

        //FragmentManager childTransactionManager = getChildFragmentManager();

        FragmentTransaction transaction = childTransactionManager.beginTransaction();

        Fragment oldFragment = childTransactionManager.findFragmentById(notes_list_framelayout);


        NotesListDisplayFragment notesListFragment = new NotesListDisplayFragment();
        notesListFragment.setDBHelper(dbHelper);
        notesListFragment.setNoteSelectedListener(noteSelectedListener);
        notesListFragment.setLayoutManagerDirection(layoutManagerDirection);

        Bundle imgBundle = new Bundle();
        imgBundle.putSerializable(NotesListDisplayFragment.MODEL_ID_KEY, modelId);
        notesListFragment.setArguments(imgBundle);


        if(oldFragment!=null){
            //transaction.detach(oldFragment).attach(newImgsListFragment).commit();
            transaction
                    .replace(notes_list_framelayout, notesListFragment).commit();
        }else {
            transaction
                    .add(notes_list_framelayout, notesListFragment).commit();
        }


    }

    /** Depends on NotesViewModel; NotesViewModel should be initialized by calling activity/fragment */
    public static void showOrRefreshNotesListFragment(@NonNull int notes_list_framelayout,
                                                      @NonNull FragmentManager childTransactionManager ,
                                                      NotesListDisplayFragmentV2.OnNoteSelectedListener noteSelectedListener,
                                                      NotesListDisplayFragmentV2.LayoutManagerDirection layoutManagerDirection){

        FragmentTransaction transaction = childTransactionManager.beginTransaction();

        Fragment oldFragment = childTransactionManager.findFragmentById(notes_list_framelayout);

        NotesListDisplayFragmentV2 notesListFragment = new NotesListDisplayFragmentV2();
        notesListFragment.setNoteSelectedListener(noteSelectedListener);
        notesListFragment.setLayoutManagerDirection(layoutManagerDirection);

        Bundle imgBundle = new Bundle();
        notesListFragment.setArguments(imgBundle);


        if(oldFragment!=null){
            //transaction.detach(oldFragment).attach(newImgsListFragment).commit();
            transaction
                    .replace(notes_list_framelayout, notesListFragment).commit();
        }else {
            transaction
                    .add(notes_list_framelayout, notesListFragment).commit();
        }


    }

}
