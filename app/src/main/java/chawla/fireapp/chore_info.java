package chawla.fireapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class chore_info extends AppCompatActivity {

    private static final String TAG = "Info for the Chore";

    public String bla="";
    public String f_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_info);

        final TextView jobtitle = (TextView) findViewById(R.id.title);
        final TextView jobdescription = (TextView) findViewById(R.id.description);
        final ListView listview = (ListView) findViewById(R.id.loc_time);
        Button btn = (Button) findViewById(R.id.take_job);



        try {

             bla = getIntent().getExtras().getString("my_object");
             f_name = getIntent().getExtras().getString("first_name");
        }
        catch(NullPointerException e){
            f_name="nothing";
        }
        //Log.e("Reason", bla);
        final Firebase main = new Firebase("https://fireapp-1914a.firebaseio.com/Jobs");

        main.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapsht : dataSnapshot.getChildren()) {
                    String the_KEY = postsnapsht.getKey().toString();
                    if (the_KEY.equalsIgnoreCase(bla.toString())) {
                        Map<String, String> map= postsnapsht.getValue(Map.class);
                        Log.e(TAG, "JOB TITLE"+ map.get("Job Title"));
                        Log.e(TAG, "Job Description"+ map.get("Job Description"));
                        Log.e(TAG, "Location"+ map.get("Location"));
                        Log.e(TAG, "Cost"+ map.get("Cost"));

                        Log.e(TAG, "Time"+ map.get("Time"));
                        Log.e(TAG, "JOB status"+ map.get("Job Status"));
                        Log.e(TAG, "JOB uploader"+ map.get("Uploader name"));
                        Log.e(TAG, "JOB Assignee"+ map.get("Assignee"));
                        Log.e(TAG, "JOB Catagory"+ map.get("Category"));

                        jobtitle.setText(map.get("Job Title"));
                        jobdescription.setText(map.get("Job Description"));
                        String[] ITname= {
                                "Time",
                                "Location",
                        };
                        Integer[] imgid={
                               R.mipmap.ic_launcher,
                                R.mipmap.fab_tab,
                        };
                        Integer[] logid ={
                          R.mipmap.plus_sign,
                                R.drawable.common_full_open_on_phone,
                        };

                        ChoreList_apdapter adapter = new ChoreList_apdapter(getApplicationContext(), ITname, imgid, logid);
                        listview.setAdapter(adapter);







                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, "The error is: " + firebaseError);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = f_name;
                Log.e(TAG, "The first name is : "+ f_name);

                if ( !("nothing").equalsIgnoreCase(temp) ) {
                    Firebase obj_ref = main.child(bla);
                    Firebase assignee = obj_ref.child("Assignee");
                    assignee.setValue(f_name);
                }
                else {
                    Toast.makeText(getApplicationContext(), "This is your own chore. You can't take it", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    public Context getcontext(){
        return chore_info.this;

    }
}
