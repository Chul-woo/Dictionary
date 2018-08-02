package com.example.owen_kim.dictionary.APIS;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.owen_kim.dictionary.R;

public class CustomDialog extends Dialog {

    private TextView titleView;
    private TextView contentView;
    private Button leftButton;
    private Button rightButton;
    private String mTitle;
    private String mContent;
    public EditText addedName;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams ipWindow = new WindowManager.LayoutParams();
        ipWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(ipWindow);
        setContentView(R.layout.custom_dialog);

        titleView = (TextView) findViewById(R.id.titleView);
        contentView = (TextView) findViewById(R.id.contentView);
        addedName = (EditText) findViewById(R.id.addName);
        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);

        titleView.setText(mTitle);
        contentView.setText(mContent);


        if(mLeftClickListener != null && mRightClickListener != null){
            leftButton.setOnClickListener(mLeftClickListener);
            rightButton.setOnClickListener(mRightClickListener);
        }

    }

    public CustomDialog(@NonNull Context context, String mTitle, String mContent,
                        View.OnClickListener leftListener,
                        View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}
