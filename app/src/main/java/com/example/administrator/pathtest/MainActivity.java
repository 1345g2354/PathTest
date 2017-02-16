package com.example.administrator.pathtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.administrator.pathtest.view.metrix.SetPolyToPoly;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);


        setContentView(R.layout.metrix_layout);

        final SetPolyToPoly poly = (SetPolyToPoly) findViewById(R.id.poly);

        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        assert group != null;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.point0: poly.setTestPoint(0); break;
                    case R.id.point1: poly.setTestPoint(1); break;
                    case R.id.point2: poly.setTestPoint(2); break;
                    case R.id.point3: poly.setTestPoint(3); break;
                    case R.id.point4: poly.setTestPoint(4); break;
                }
            }
        });
    }
}
