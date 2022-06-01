package todo.swu.applepicker;

<<<<<<< HEAD
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
=======
import android.os.Bundle;
import android.view.View;
>>>>>>> c1151ae39cf678a7f7d0302e2d53244be0600340

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

<<<<<<< HEAD
import java.io.File;

=======
>>>>>>> c1151ae39cf678a7f7d0302e2d53244be0600340
public class OcrEditActivity extends AppCompatActivity {
    MaterialButtonToggleGroup toggle_btn_group;
    ConstraintLayout bottom_sheet;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_edit);

<<<<<<< HEAD
        Intent receive_intent = getIntent();

        String jsonResponse = receive_intent.getStringExtra("jsonResponse");
        String imagePath = receive_intent.getStringExtra("imagePath");

        Log.e(jsonResponse.toString(), "json response");
        Log.e(imagePath.toString(), "imagePath");


        // 이미지뷰에 이미지 띄우기
        File imgFile = new  File(imagePath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.iv_ocr_image);
            myImage.setImageBitmap(myBitmap);
        }

=======
>>>>>>> c1151ae39cf678a7f7d0302e2d53244be0600340
        toggle_btn_group = (MaterialButtonToggleGroup) findViewById(R.id.toggle_btn_group_ocr_select);
        toggle_btn_group.addOnButtonCheckedListener((toggle_btn_group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.btn_select_Task:
                        //BottomSheet에 Task 부분에 선택한 (바운딩 박스 안의)문장이 더해지게 함
                        break;

                    case R.id.btn_select_Memo:
                        //BottomSheet에 Memo 부분에 선택한 (바운딩 박스 안의)문장이 더해지게 함
                        break;
                }
            }
        });

        bottom_sheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //bottomSheet의 상태 변화에 따라 호출할 함수 지정
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //bottomSheet의 펼쳐진 정도에 따른 동작 지정
                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
            }
        });
    }
}
