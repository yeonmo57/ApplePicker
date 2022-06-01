package todo.swu.applepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentDaily;
    Fragment fragmentSNS;
    Fragment fragmentOCR;
    TextView tv_title;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        Log.e(this.getClass().getName(), "mainActivity 실행");

        tv_title = (TextView)findViewById(R.id.tv_title);
        fragmentDaily = new FragmentDaily();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragmentDaily, "fragment_daily").commit();
        // 초기화면 설정

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_daily:
                            onMoveDaily();
                            tv_title.setText("Daily");
                            return true;

                        case R.id.tab_sns:
                            onMoveSNS();
                            tv_title.setText("SNS");
                            return true;

                        case R.id.tab_ocr:
                            onMoveOCR();
                            tv_title.setText("OCR");
                            return true;
                    }
                    return false;
                });
    }

    private void onMoveDaily(){
        if(fragmentDaily == null){
            fragmentDaily = new FragmentDaily();
            fragmentManager.beginTransaction().add(R.id.container, fragmentDaily, "fragment_daily").commit();
        }
        if(fragmentDaily != null)fragmentManager.beginTransaction().show(fragmentDaily).commit();
        if(fragmentSNS != null)fragmentManager.beginTransaction().hide(fragmentSNS).commit();
        if(fragmentOCR != null)fragmentManager.beginTransaction().hide(fragmentOCR).commit();
    }

    private void onMoveSNS(){
        if(fragmentSNS == null){
            fragmentSNS = new FragmentSNS();
            fragmentManager.beginTransaction().add(R.id.container, fragmentSNS).commit();
        }
        if(fragmentDaily != null)fragmentManager.beginTransaction().hide(fragmentDaily).commit();
        if(fragmentSNS != null)fragmentManager.beginTransaction().show(fragmentSNS).commit();
        if(fragmentOCR != null)fragmentManager.beginTransaction().hide(fragmentOCR).commit();
    }

    private void onMoveOCR(){
        if(fragmentOCR == null){
            fragmentOCR = new FragmentOCR();
            fragmentManager.beginTransaction().add(R.id.container, fragmentOCR).commit();
        }
        if(fragmentDaily != null)fragmentManager.beginTransaction().hide(fragmentDaily).commit();
        if(fragmentSNS != null)fragmentManager.beginTransaction().hide(fragmentSNS).commit();
        if(fragmentOCR != null)fragmentManager.beginTransaction().show(fragmentOCR).commit();
    }
}
