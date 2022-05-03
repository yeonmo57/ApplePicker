package todo.swu.applepicker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView readData = (TextView) findViewById(R.id.readData);
        Button saveBtn = (Button) findViewById(R.id.saveBtn) ;
        Button readBtn = (Button) findViewById(R.id.readBtn) ;


                readBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        readData.setText(document.getId()+"=>"+document.getData());
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                    }});

                saveBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textView.setText("Red");

                        // Create a new user with a first and last name
                        Map<String, Object> user = new HashMap<>();
                        user.put("first", "kim");
                        user.put("last", "hyerin");
                        user.put("born", 1999);

                        // Add a new document with a generated ID
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }

                });
            }
        });
        Thread thread = new Thread() {
            public void run() {
                OcrExam api = new OcrExam();
            }
        };
        thread.start();

    }

}