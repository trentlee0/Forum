package com.example.forum.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.forum.R;

@SuppressLint("AppCompatCustomView")
public class MyImageButton extends ImageButton {
    private boolean checked;
    private Context context;

    public MyImageButton(Context context) {
        super(context);
        this.context = context;
    }

    public MyImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        notCheckedChange();
    }

    public MyImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        notCheckedChange();
    }

    public MyImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        notCheckedChange();
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) checkedChange();
        else notCheckedChange();
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setChecked(!checked);
        }
    }

    private void notCheckedChange() {
        setBackgroundColor(context.getColor(R.color.black));
    }

    private void checkedChange() {
        setBackgroundColor(context.getColor(R.color.grey));
    }
}
