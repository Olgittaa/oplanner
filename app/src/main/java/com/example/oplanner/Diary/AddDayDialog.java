package com.example.oplanner.Diary;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.oplanner.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AddDayDialog extends Dialog {

    Context context;
    @BindView(R.id.toggleGroup)
    public MaterialButtonToggleGroup toggleGroup;

    @BindView(R.id.description)
    public TextInputEditText description;

    @BindView(R.id.positivebtn)
    public Button positivebtn;

    @BindView(R.id.negativebtn)
    public Button negativebtn;

    public AddDayDialog(@NonNull Context context) {
        super(context);

        this.context = context;
        setTitle("Add day");
        setContentView(R.layout.add_day);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);

        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);

        setData();

        this.show();
        negativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeButtonClick();
            }
        });

        positivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveButtonClick();
            }
        });
    }

    public int getImageByButton(int checkedId) {
        if (checkedId == R.id.perfectbtn) {
            return R.drawable.ic_perfect;
        }
        if (checkedId == R.id.goodbtn) {
            return R.drawable.ic_good;
        }
        if (checkedId == R.id.okbtn) {
            return R.drawable.ic_ok;
        }
        if (checkedId == R.id.sosobtn) {
            return R.drawable.ic_soso;
        }
        if (checkedId == R.id.badbtn) {
            return R.drawable.ic_bad;
        }
        return 0;
    }

    public void checkButtonByImage(int image) {
        if (image == R.drawable.ic_perfect) {
            toggleGroup.check(R.id.perfectbtn);
        }
        if (image == R.drawable.ic_good) {
            toggleGroup.check(R.id.goodbtn);
        }
        if (image == R.drawable.ic_ok) {
            toggleGroup.check(R.id.okbtn);
        }
        if (image == R.drawable.ic_soso) {
            toggleGroup.check(R.id.sosobtn);
        }
        if (image ==R.drawable.ic_bad) {
            toggleGroup.check(R.id.badbtn);
        }
    }


    protected abstract void setData();

    protected abstract void onPositiveButtonClick();

    protected abstract void onNegativeButtonClick();
}
