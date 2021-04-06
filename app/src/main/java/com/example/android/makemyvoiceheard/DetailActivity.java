package com.example.android.makemyvoiceheard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private TextView mOfficialLabel;
    private TextView mOfficialName;
    private TextView mPartyLabel;
    private TextView mAddressLine1;
    private TextView mAddressLine2;
    private TextView mWebsite;
    private ImageView mOfficialImage;
    private Button mButtonPhoneNumber;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Log.d("WWD", "in Detail Activity onCreate");
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Integer index = intent.getIntExtra(MainActivity.IMAGE_SELECTION, 0);
        //  Log.d("WWD", "the selection index was " + index);

        // get references to xml
        mOfficialLabel = (TextView) findViewById(R.id.official_label);
        mOfficialName = (TextView) findViewById(R.id.official_name);
        mPartyLabel = (TextView) findViewById(R.id.party_label);
        mAddressLine1 = (TextView) findViewById(R.id.address_line_1);
        mAddressLine2 = (TextView) findViewById(R.id.address_line_2);
        mWebsite = (TextView) findViewById(R.id.website);
        mOfficialImage = (ImageView) findViewById(R.id.official_image);
        mButtonPhoneNumber = (Button) findViewById(R.id.phone);

        if (index == MainActivity.SENATOR_ONE) {
            mOfficialLabel.setText("YOUR SENATOR");
            mOfficialName.setText(JsonUtil.getSenator1Name());
            mPartyLabel.setText(JsonUtil.getSenator1Party());
            mAddressLine1.setText(JsonUtil.getSenator1AddressLine1());
            mAddressLine2.setText(JsonUtil.getSenator1AddressLine2());
            mWebsite.setText(JsonUtil.getSenator1URL());
            mPhoneNumber = JsonUtil.getSenator1Phone();
            mButtonPhoneNumber.setText(mPhoneNumber);
            String officialUrl = JsonUtil.getSenator1PhotoURL();
            // Log.d("WWD", "officialUrl is " + officialUrl);
            if (officialUrl.length() == 0) {
                mOfficialImage.setImageResource(R.drawable.nophoto);
            } else {
                Picasso.get().load(officialUrl).into(mOfficialImage);
            }
        } else if (index == MainActivity.SENATOR_TWO) {
            mOfficialLabel.setText("YOUR SENATOR");
            mOfficialName.setText(JsonUtil.getSenator2Name());
            mPartyLabel.setText(JsonUtil.getSenator2Party());
            mAddressLine1.setText(JsonUtil.getSenator2AddressLine1());
            mAddressLine2.setText(JsonUtil.getSenator2AddressLine2());
            mWebsite.setText(JsonUtil.getSenator2URL());
            mPhoneNumber = JsonUtil.getSenator2Phone();
            mButtonPhoneNumber.setText(mPhoneNumber);
            String officialUrl = JsonUtil.getSenator2PhotoURL();
            // Log.d("WWD", "officialUrl is " + officialUrl);
            if (officialUrl.length() == 0) {
                mOfficialImage.setImageResource(R.drawable.nophoto);
            } else {
                Picasso.get().load(officialUrl).into(mOfficialImage);
            }
        } else {
            mOfficialLabel.setText("YOUR REPRESENTATIVE");
            mOfficialName.setText(JsonUtil.getRepresentativeName());
            mPartyLabel.setText(JsonUtil.getRepresentativeParty());
            mAddressLine1.setText(JsonUtil.getRepresentativeAddressLine1());
            mAddressLine2.setText(JsonUtil.getRepresentativeAddressLine2());
            mWebsite.setText(JsonUtil.getRepresentativeURL());
            String officialUrl = JsonUtil.getRepresentativePhotoURL();
            mPhoneNumber = JsonUtil.getRepresentativePhone();
            mButtonPhoneNumber.setText(mPhoneNumber);
            //  Log.d("WWD", "officialUrl is " + officialUrl);
            if (officialUrl.length() == 0) {
                mOfficialImage.setImageResource(R.drawable.nophoto);
            } else {
                Picasso.get().load(officialUrl).into(mOfficialImage);
            }
        }

    }

    public void placeCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: +" + mPhoneNumber));
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