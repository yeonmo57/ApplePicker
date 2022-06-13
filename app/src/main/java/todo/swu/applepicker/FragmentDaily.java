package todo.swu.applepicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;

public class FragmentDaily extends Fragment {
    ImageButton iButton_calendar;
    TextView tv_date;
    EditText edit_dDay;
    EditText edit_comment;
    EditText edit_total_time;

    ImageButton iButton_task_add;
    ImageButton iButton_memo_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_daily, container, false);

        //이 버튼을 클릭하면 달력이 호출됨.
        iButton_calendar = (ImageButton) myView.findViewById(R.id.iButton_calendar);
        //선택한 날짜가 이텍스트 뷰에 나타난다.
        tv_date = (TextView) myView.findViewById(R.id.tv_date);
        edit_dDay = (EditText) myView.findViewById(R.id.edit_dDay);
        edit_comment = (EditText) myView.findViewById(R.id.edit_comment);
        edit_total_time = (EditText) myView.findViewById(R.id.edit_total_time);

        iButton_task_add = (ImageButton) myView.findViewById(R.id.iButton_task_add);
        iButton_memo_add = (ImageButton) myView.findViewById(R.id.iButton_memo_add);


        iButton_calendar.setOnClickListener(v -> {
            callDateDialog(myView);
        });

        edit_dDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit_dDay.setTextColor(getResources().getColor(R.color.green_text));
                edit_dDay.setBackground(null);
            }
        });

        edit_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit_comment.setTextColor(getResources().getColor(R.color.green_text));
                edit_comment.setBackground(null);
            }
        });

        edit_total_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit_total_time.setTextColor(getResources().getColor(R.color.green_text));
                edit_total_time.setBackground(null);
            }
        });

        //Task RecyclerView 시작
        //리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<TaskItem> mTaskItems = new ArrayList<>();
        //리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView taskRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_task);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Memo RecyclerView 시작
        //리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<MemoItem> mMemoItems = new ArrayList<>();
        mMemoItems.add(new MemoItem("첫번째 메모"));
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView memoRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_memo);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        

        iButton_task_add.setOnClickListener(v -> {
            mTaskItems.add(new TaskItem("과목 ",
                    "과제", R.drawable.ic_green_apple));
            taskRecyclerView.setAdapter(new TaskAdapter(mTaskItems));
        });

        iButton_memo_add.setOnClickListener(v -> {
            int i = 1;
            mMemoItems.add(new MemoItem("메모 " + i));
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            memoRecyclerView.setAdapter(new MemoAdapter(mMemoItems));
        });

        // Inflate the layout for this fragment
        return myView;
    }

    public void callDateDialog(View view) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getActivity().getSupportFragmentManager(), "dateFragment");
    }

    public void processDatePickerResult(String year, String month, String day, String day_of_week) {
        tv_date.setText(month + "/" + day + "(" + day_of_week + ")");
    }


}