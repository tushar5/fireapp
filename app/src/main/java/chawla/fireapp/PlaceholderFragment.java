package chawla.fireapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Tushar on 9/12/16.
 */
public class PlaceholderFragment extends Fragment {

    Vector<Job_class> temp;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "The Fragments";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            final View rootView = inflater.inflate(R.layout.my_chores, container, false);
            Log.d(TAG, "The temp");
            return rootView;
        }
        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
            Firebase mPref = new Firebase("https://fireapp-1914a.firebaseio.com/Jobs");
            final View rootView = inflater.inflate(R.layout.open_chores_data, container, false);


            mPref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d(TAG, "The Getting data counts:" + dataSnapshot.getChildrenCount());
                    temp = new Vector<Job_class>();
                    for (DataSnapshot postsnapsht : dataSnapshot.getChildren()){

                        Log.d(TAG, "The Getting formed data" + postsnapsht.getValue());
                        Map<String, String> map= postsnapsht.getValue(Map.class);
                        Job_class temp_object = new Job_class(map.get("Job Title"), map.get("Job Description"), map.get("Location"), map.get("Cost"), map.get("Time"), map.get("Job Status"), map.get("Uploader name"), map.get("Assignee"), map.get("Category"));
                        Log.d(TAG, "JOB TITLEH" + temp_object.getJob_title_());
                        temp.add(temp_object);
                    }
                    ListView lvProduct =(ListView) rootView.findViewById(R.id.listview_product);
                    List<Product> mProductList = new ArrayList<>();
                    for (int n=0;n<temp.size(); n++){
                        mProductList.add(new Product(temp.get(n).getCost(), "Bla", temp.get(n).getJob_title_(), temp.get(n).getJob_description_()));
                    }
                    ProductListAdapter adapter=new ProductListAdapter(getActivity().getApplicationContext(), mProductList);
                    lvProduct.setAdapter(adapter);
                    lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //  Log.e("H", "THe var is :"+ (((TextView)view).getText().toString()))
                            startActivity(new Intent(getActivity(), chore_info.class));
                        }
                    });
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.v("E-Value", "Error from Import" + firebaseError);
                }
            });
            return rootView;
        }
        else{
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;

        }

    }
}