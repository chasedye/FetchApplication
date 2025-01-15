package com.fetchApplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetchApplication.databinding.MainactivityBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // tag is for logging purposes
    private final String TAG = this.getClass().toString();

    // content view binding
    private MainactivityBinding viewBinding;
    // recycler objects
    private RecyclerView recyclerView;
    private MyAdapter viewAdapter;
    private List<DataObject> retrievedObjects = new ArrayList<>();
    // network objects
    private NetworkUtility networkUtility;
    private final String dataURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";


    /**
     * Callback variable for network
     */
    private DataCallback callback = new DataCallback() {
        @Override
        public void Success(JSONArray obj) {
            try{
                // go through array and check
                JSONArray dataArray = obj;
                for(int i = 0; i < dataArray.length(); i++)
                {
                    JSONObject item = dataArray.getJSONObject(i);
                    DataObject temp = new DataObject((String) item.getString("name"), (int) item.getInt("id"), (int) item.getInt("listId"));
                    if(!temp.isNameNull()) retrievedObjects.add(temp);
                }

                Collections.sort(retrievedObjects, new Comparator<DataObject>() {
                    @Override
                    public int compare(DataObject o1, DataObject o2) {
                        // this will compare list id's
                        int listIDCompare = Integer.compare(o1.getListID(), o2.getListID());
                        if(listIDCompare != 0)
                            return listIDCompare;

                        // this will compare the number in the name
                        int numberCompare = Integer.compare(o1.getNameInt(), o2.getNameInt());
                        if (numberCompare != 0) {
                            return numberCompare;
                        } else {
                            // If both the listID and the extracted number are the same, compare by name
                            // this honestly shouldnt happen but always good to have
                            return o1.getName().compareTo(o2.getName());
                        }
                    }
                });

                // since updating UI this needs to be run on the ui thread
                runOnUiThread(() -> {
                    viewAdapter.addItems(retrievedObjects);
                });



            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        @Override
        public void EncounteredError(String errorMsg) {
            Log.e(TAG, errorMsg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflate viewbinding and set view
        viewBinding = MainactivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        recyclerView = viewBinding.recyclerViewObj;
        // set network utility and callback
        networkUtility = new NetworkUtility(callback);
        networkUtility.fetchData(dataURL);
        // set adapter
        viewAdapter = new MyAdapter();
        recyclerView.setAdapter(viewAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

}
