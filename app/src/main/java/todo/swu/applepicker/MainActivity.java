package todo.swu.applepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);

        Button DBBtn = (Button) findViewById(R.id.DBBtn) ;
        DBBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Red") ;
            }
        });
    }

}