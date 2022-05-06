package todo.swu.applepicker;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {
    int testNum = 10;
    StringBuffer response = new StringBuffer();

    public void setResponse(StringBuffer res){
        response = res;
    }

    public StringBuffer getResponse(){
        return response;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hi");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView readData = (TextView) findViewById(R.id.readData);
        final TextView ocrData = (TextView) findViewById(R.id.ocrView);

        Button saveBtn = (Button) findViewById(R.id.saveBtn) ;
        Button readBtn = (Button) findViewById(R.id.readBtn) ;
        Button ocrBtn = (Button) findViewById(R.id.ocrBtn) ;



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

        /*
        System.out.println("test");
        String apiURL = "https://nxie8hhe8q.apigw.ntruss.com/custom/v1/15052/d9ca1275cb57d960372ef532fa2ff936a1f9e789c8af3e55617a0bd60c605d31/general";
        String secretKey = "UUNjZ1p4ZXRMRk1VZlpramFkY1lwSFBrck5DS3hFdWw";
        String imageFile = "http://sc1.swu.ac.kr/~moo/ocr_test/ocr_test01.png";



        try {
            ocrData.setText("test1");
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);
            ocrData.setText("test2");

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            ocrData.setText("test3-1");
            JSONObject image = new JSONObject();
            ocrData.setText("test3-2");
            image.put("format", "png");
            image.put("name", "demo");
            ocrData.setText("test3-3");
            JSONArray images = new JSONArray();
            ocrData.setText("test3-4");
            images.put(image);
            ocrData.setText("test3-5");
            json.put("images", images);
            ocrData.setText("test3-6");
            String postParams = json.toString();
            ocrData.setText("test3-7");

            con.connect();
            ocrData.setText("test4");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            ocrData.setText("test5");
            long start = System.currentTimeMillis();
            ocrData.setText("test6");
            File file = new File(imageFile);
            ocrData.setText("test7");
            writeMultiPart(wr, postParams, file, boundary);
            ocrData.setText("test8");
            wr.close();


            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            //StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();
            System.out.println("test5");
            System.out.println(response);
            //setResponse(response);
            ocrData.setText(response);
            //testNum=200;
        } catch (Exception e) {
            System.out.println(e);
        }
        ocrBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ocrData.setText("test");
                //ocrData.setText(getResponse());
                //System.out.println(response);
            }});

    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();

    */

        String apiURL = "https://nxie8hhe8q.apigw.ntruss.com/custom/v1/15052/d9ca1275cb57d960372ef532fa2ff936a1f9e789c8af3e55617a0bd60c605d31/general";
        String secretKey = "UUNjZ1p4ZXRMRk1VZlpramFkY1lwSFBrck5DS3hFdWw";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "png");
            image.put("url", "http://sc1.swu.ac.kr/~moo/ocr_test/ocr_test01.png"); // image should be public, otherwise, should use data
            // FileInputStream inputStream = new FileInputStream("YOUR_IMAGE_FILE");
            // byte[] buffer = new byte[inputStream.available()];
            // inputStream.read(buffer);
            // inputStream.close();
            // image.put("data", buffer);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();
            ocrData.setText("test1");

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            ocrData.setText("test2");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            ocrData.setText("test3");
            System.out.println(response);
            ocrData.setText("4");
        } catch (Exception e) {
            System.out.println(e);
        }

    }


}