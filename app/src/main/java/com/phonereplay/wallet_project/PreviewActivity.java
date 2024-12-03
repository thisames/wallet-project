package com.phonereplay.wallet_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AigeStudio 2015-12-06
 * @author AigeStudio 2016-07-08
 */
public class PreviewActivity extends Activity implements WheelPicker.OnItemSelectedListener, View.OnClickListener {

    private WheelPicker wheelCenter;
    private Button gotoBtn;
    private Integer gotoBtnItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_preview);

        wheelCenter = findViewById(R.id.main_wheel_center);
        wheelCenter.setOnItemSelectedListener(this);
        List<String> bloodTypes = generateNumbersData();
        wheelCenter.setData(bloodTypes);
        gotoBtn = findViewById(R.id.goto_btn);
        randomlySetGotoBtnIndex();
        gotoBtn.setOnClickListener(this);
    }

    private void randomlySetGotoBtnIndex() {
        gotoBtnItemIndex = (int) (Math.random() * wheelCenter.getData().size());
        gotoBtn.setText("1");
    }

    private List<String> generateNumbersData() {
        List<String> bloodTypes = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            Integer inte = Integer.valueOf(i);
            bloodTypes.add(inte.toString());
        }
        return bloodTypes;
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {

        WheelPicker center = findViewById(R.id.main_wheel_center);

        String text = "";
        switch (picker.getId()) {
            case 1:
                text = "Left:";
                break;
            case 2:
                text = "Center:";
                break;
            case 3:
                text = "Right:";
                break;
        }
        Toast.makeText(this, text + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        wheelCenter.setSelectedItemPosition(gotoBtnItemIndex);
        randomlySetGotoBtnIndex();
    }

}