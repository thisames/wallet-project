package com.phonereplay.wallet_project;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DialogQRCodeBTCAddress extends DialogFragment {

  private ImageView qrCodeIV;

  private final String bitcoinAddress;

  DialogQRCodeBTCAddress(String bitcoinAddress) {
    this.bitcoinAddress = bitcoinAddress;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.setContentView(R.layout.dialog_qrcode_btc_address);
    qrCodeIV = dialog.findViewById(R.id.idIVQrcode);

    generateQRCode(bitcoinAddress);

    return dialog;
  }

  private void generateQRCode(String text) {
    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    try {
      Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
      qrCodeIV.setImageBitmap(bitmap);
    } catch (WriterException e) {
      e.printStackTrace();
    }
  }
}
