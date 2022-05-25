package todo.swu.applepicker;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class FragmentOCR extends Fragment {
    Button galleryBtn;
    Button cameraBtn;
    ImageView ocrImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_ocr, container, false);
        galleryBtn = (Button) myView.findViewById(R.id.galleryBtn);
        cameraBtn = (Button) myView.findViewById(R.id.cameraBtn);
        ocrImageView = (ImageView) myView.findViewById(R.id.ocrImageView);

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            public static final int sub = 1001; /*OcrExam 액티비티를 띄우기 위한 요청코드(상수)*/

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

                Log.e(this.getClass().getName(), "Fragment 실행 1");

                NetworkTask networkTask = new NetworkTask();
                networkTask.execute();

            }
        });

        cameraBtn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "카메라 버튼 누름", Toast.LENGTH_SHORT).show();
        });

        // Inflate the layout for this fragment
        return myView;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            requestHttpURLConnection.SendImage();
            return "null";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
        }
    }

}


