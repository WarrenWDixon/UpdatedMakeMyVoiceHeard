package com.example.android.makemyvoiceheard;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private TextView  mOfficialLabelTV;
    private TextView  mOfficialNameTV;
    private TextView  mPartyLabelTV;
    private TextView  mAddressLine1TV;
    private TextView  mAddressLine2TV;
    private TextView  mOfficialURLTV;
    private ImageView mOfficialImageIV;
    private Button    mPhoneNumberBTN;

    private String mPhoneNumber;
    private String mOfficialLabel;
    private String mOfficialName;
    private String mPartyLabel;
    private String mAddressLine1;
    private String mAddressLine2;
    private String mOfficialURL;
    private String mOfficialImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Log.d("WWD", "in Detail Activity onCreate");
        setContentView(R.layout.activity_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.my_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent  = getIntent();
        mOfficialLabel = intent.getStringExtra(MainActivity.OFFICIAL_TYPE);
        mOfficialName  = intent.getStringExtra(MainActivity.OFFICIAL_NAME);
        mAddressLine1  = intent.getStringExtra(MainActivity.OFFICIAL_ADDRESS_LINE1);
        mAddressLine2  = intent.getStringExtra(MainActivity.OFFICIAL_ADDRESS_LINE2);
        mPartyLabel    = intent.getStringExtra(MainActivity.OFFICIAL_PARTY);
        mOfficialURL   = intent.getStringExtra(MainActivity.OFFICIAL_URL);
        mOfficialImage = intent.getStringExtra(MainActivity.OFFICIAL_PHOTO_URL);
        mPhoneNumber   = intent.getStringExtra(MainActivity.OFFICIAL_PHONE);

        Log.d("WWD", "mOfficialLabel is  " + mOfficialLabel);
        Log.d("WWD", "mOfficialName is " + mOfficialName);
        Log.d("WWD", "mAddressLine1 is " + mAddressLine1);
        Log.d("WWD", "mAddressLine2 is " + mAddressLine2);
        Log.d("WWD", "mWebsite is " + mOfficialURL);
        Log.d("WWD", "mOfficialImage is " + mOfficialImage);

        mOfficialLabelTV = (TextView) findViewById(R.id.official_label);
        mOfficialNameTV  = (TextView) findViewById(R.id.official_name);
        mPartyLabelTV    = (TextView) findViewById(R.id.party_label);
        mAddressLine1TV  = (TextView) findViewById(R.id.address_line_1);
        mAddressLine2TV  = (TextView) findViewById(R.id.address_line_2);
        mOfficialURLTV   = (TextView) findViewById(R.id.website);
        mOfficialImageIV = (ImageView) findViewById(R.id.official_image);
        mPhoneNumberBTN  = (Button) findViewById(R.id.phone);

        mOfficialLabelTV.setText(mOfficialLabel);
        mOfficialNameTV.setText(mOfficialName);
        mPartyLabelTV.setText(mPartyLabel);
        mAddressLine1TV.setText(mAddressLine1);
        mAddressLine2TV.setText(mAddressLine2);
        mOfficialURLTV.setText(mOfficialURL);
        mPhoneNumberBTN.setText(mPhoneNumber);

        if (mOfficialImage.length() == 0) {
            mOfficialImageIV.setImageResource(R.drawable.nophoto);
        } else {
            Picasso.get().load(mOfficialImage).into(mOfficialImageIV);
        }


    }

    public void placeCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Log.d("WWD", "phone number is " + mPhoneNumber);
        callIntent.setData(Uri.parse("tel:" + mPhoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("WWD", " *************** permission for call denied aborting call");
            return;
        }
        Log.d("WWD", " ---------------------- placing call");
        startActivity(callIntent);
    }
}