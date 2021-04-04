package com.example.android.makemyvoiceheard;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private TextView  mOfficialLabel;
    private TextView  mOfficialName;
    private TextView  mPartyLabel;
    private TextView  mAddressLine1;
    private TextView  mAddressLine2;
    private TextView  mWebsite;
    private ImageView mOfficialImage;
    private Button    mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WWD", "in Detail Activity onCreate");
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Integer index = intent.getIntExtra(MainActivity.IMAGE_SELECTION, 0);
        Log.d("WWD", "the selection index was " + index);

        // get references to xml
        mOfficialLabel = (TextView) findViewById(R.id.official_label);
        mOfficialName  = (TextView) findViewById(R.id.official_name);
        mPartyLabel    = (TextView) findViewById(R.id.party_label);
        mAddressLine1  = (TextView) findViewById(R.id.address_line_1);
        mAddressLine2  = (TextView) findViewById(R.id.address_line_2);
        mWebsite       = (TextView) findViewById(R.id.website);
        mOfficialImage = (ImageView) findViewById(R.id.official_image);
        mPhoneNumber   = (Button)   findViewById(R.id.phone);

        if (index == MainActivity.SENATOR_ONE) {
            mOfficialLabel.setText("YOUR SENATOR");
            mOfficialName.setText(JsonUtil.getSenator1Name());
            mPartyLabel.setText(JsonUtil.getSenator1Party());
            mAddressLine1.setText(JsonUtil.getSenator1AddressLine1());
            mAddressLine2.setText(JsonUtil.getSenator1AddressLine2());
            mWebsite.setText(JsonUtil.getSenator1URL());
            mPhoneNumber.setText(JsonUtil.getSenator1Phone());
            String officialUrl = JsonUtil.getSenator1PhotoURL();
            Log.d("WWD", "officialUrl is " + officialUrl);
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
            mPhoneNumber.setText(JsonUtil.getSenator2Phone());
            String officialUrl = JsonUtil.getSenator2PhotoURL();
            Log.d("WWD", "officialUrl is " + officialUrl);
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
            mPhoneNumber.setText(JsonUtil.getRepresentativePhone());
            Log.d("WWD", "officialUrl is " + officialUrl);
            if (officialUrl.length() == 0) {
                mOfficialImage.setImageResource(R.drawable.nophoto);
            } else {
                Picasso.get().load(officialUrl).into(mOfficialImage);
            }
        }

    }
}