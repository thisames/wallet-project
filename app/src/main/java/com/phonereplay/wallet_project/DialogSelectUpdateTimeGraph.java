package com.phonereplay.wallet_project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.aigestudio.wheelpicker.WheelPicker;
import java.util.ArrayList;
import java.util.List;

public class DialogSelectUpdateTimeGraph extends DialogFragment
    implements WheelPicker.OnItemSelectedListener {

  GraphBitcoinConfig config = GraphBitcoinConfig.getInstance();

  DialogSelectUpdateTimeGraph() {}

  @Override
  public void onResume() {
    super.onResume();

    int width = getResources().getDisplayMetrics().widthPixels;
    int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    if (getDialog() != null && getDialog().getWindow() != null) {
      getDialog().getWindow().setLayout(width, height);
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    WheelPicker wheelCenter;
    Dialog dialog = super.onCreateDialog(savedInstanceState);

    dialog.setContentView(R.layout.ac_preview);

    wheelCenter = dialog.findViewById(R.id.main_wheel_center);

    if (wheelCenter == null) {
      throw new IllegalStateException("WheelPicker with ID main_wheel_center not found in layout.");
    }

    wheelCenter.setOnItemSelectedListener(this);
    List<String> numbersData = generateNumbersData();
    int selectedItemPosition = getSelectedItemPosition(numbersData);
    wheelCenter.setData(numbersData);
    wheelCenter.setSelectedItemPosition(selectedItemPosition);

    return dialog;
  }

  private int getSelectedItemPosition(List<String> numbersData) {
    for (int i = 0; i < numbersData.size(); i++) {
      if (numbersData.get(i).equals(String.valueOf(config.getTimeUpdateGraph()))) {
        return i;
      }
    }
    return 0;
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
  public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
    int timeUpdateGraph = Integer.parseInt((String) o);
    config.setTimeUpdateGraph(timeUpdateGraph);

    if (getActivity() instanceof GraphConfig) {
      GraphConfig activity = (GraphConfig) getActivity();
      activity.updateButtonText();
      activity.paintTimeUpdateGraphTextButton();
    }

    super.dismiss();
  }
}
