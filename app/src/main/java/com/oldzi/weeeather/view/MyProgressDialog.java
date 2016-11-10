package com.oldzi.weeeather.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.oldzi.weeeather.R;

/**
 * Created by Oldzi on 07.09.2016.
 */
public class MyProgressDialog extends ProgressDialog {

    private Context context;

    public MyProgressDialog(Context context) {
        super(context);
        this.context = context;
        this.setProgressStyle(R.style.AppTheme_Dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.progress_dialog);
        ProgressBar v = (ProgressBar) findViewById(R.id.progress_bar);
        v.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.coffeeYellow2), android.graphics.PorterDuff.Mode.SRC_ATOP);
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
