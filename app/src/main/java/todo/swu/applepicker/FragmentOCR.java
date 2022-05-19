package todo.swu.applepicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class FragmentOCR extends Fragment {
    Button galleryBtn;
    Button cameraBtn;
    ImageView ocrImageView;

    //갤러리 호출

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_ocr, container, false);
        galleryBtn = (Button) myView.findViewById(R.id.galleryBtn);
        cameraBtn = (Button) myView.findViewById(R.id.cameraBtn);
        ocrImageView = (ImageView) myView.findViewById(R.id.ocrImageView);

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            //Toast.makeText(getActivity(), "갤러리 버튼 누름", Toast.LENGTH_SHORT).show();
            public static final int sub = 1001; /*OcrExam 액티비티를 띄우기 위한 요청코드(상수)*/

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

                Log.e(this.getClass().getName(), "Fragment 실행 1");

                SendImage();

            }
        });

        cameraBtn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "카메라 버튼 누름", Toast.LENGTH_SHORT).show();
        });

        // Inflate the layout for this fragment
        return myView;
    }

    public void SendImage() {
        String apiURL = "https://nxie8hhe8q.apigw.ntruss.com/custom/v1/15052/d9ca1275cb57d960372ef532fa2ff936a1f9e789c8af3e55617a0bd60c605d31/general";
        String secretKey = "R3NXT3dscldUa3FnYUxxUmFJZWR5R0FwRExIbFJ4b2M=";
        String imageFile = "storage/emulated/0/Pictures/ocr_test01.png";

        Log.e(this.getClass().getName(), "SendImage 실행 1");
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            Log.e(this.getClass().getName(), "SendImage 실행 2");
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "png");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            Log.e(this.getClass().getName(), "SendImage 실행 3");
            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            Log.e(this.getClass().getName(), "SendImage 실행 4");
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
            Log.e(this.getClass().getName(), "SendImage 실행 5");
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
        }
        //return 1;
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
    }

/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            // 사진업로드 이벤트
            case REQUEST_CODE:
            // 사진 선택
                if(resultCode == Activity.RESULT_OK)
                {
                    try{
                        // Image 상대경로를 가져온다
                        Uri uri = data.getData();
                        // Image의 절대경로를 가져온다
                        String imagePath = getRealPathFromURI(uri);
                        //File변수에 File을 집어넣는다
                        destFile = new File(imagePath);
                        // 이미지 전송
                        SendImage();
                    } catch(Exception e) {
                        // 대기메시지 종료
                        activity.progressDismiss();
                    }
                }
                // 사진 선택 취소
                else if(resultCode == activity.RESULT_CANCELED)
                {
                    Toast.makeText(activity, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
                break;
            }
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ocrImageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    */

}


