package todo.swu.applepicker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentDaily;
    Fragment fragmentSNS;
    Fragment fragmentOCR;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        Log.e(this.getClass().getName(), "mainActivity 실행");
        fragmentDaily = new FragmentDaily();
        fragmentSNS = new FragmentSNS();
        fragmentOCR = new FragmentOCR();
        tv_title = (TextView)findViewById(R.id.tv_title);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaily).commit();
        // 초기화면 설정

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_daily:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaily).commit();
                            tv_title.setText("Daily");
                            return true;

                        case R.id.tab_sns:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSNS).commit();
                            tv_title.setText("SNS");
                            return true;

                        case R.id.tab_ocr:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentOCR).commit();
                            tv_title.setText("OCR");
                            return true;
                    }
                    return false;
                });
    }
}
