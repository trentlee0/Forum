package com.example.forum.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;

public class FooterHolder extends RecyclerView.ViewHolder {
    public LinearLayout loading_layout;
    public LinearLayout end_layout;
    public LinearLayout error_layout;

    public FooterHolder(View itemView) {
        super(itemView);
        loading_layout = itemView.findViewById(R.id.loading_layout);
        end_layout = itemView.findViewById(R.id.end_layout);
        error_layout = itemView.findViewById(R.id.error_layout);
        changeStatus(FooterStatus.NORMAL);
    }

    public void changeStatus(FooterStatus status) {
        switch (status) {
            case NORMAL:
                setAllGone();
                break;
            case LOADING:
                setAllGone();
                loading_layout.setVisibility(View.VISIBLE);
                break;
            case END:
                setAllGone();
                end_layout.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                setAllGone();
                error_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setAllGone() {
        if (loading_layout != null) loading_layout.setVisibility(View.GONE);
        if (end_layout != null) end_layout.setVisibility(View.GONE);
        if (error_layout != null) error_layout.setVisibility(View.GONE);
    }

    public enum FooterStatus {
        NORMAL,
        LOADING,
        END,
        ERROR
    }
}
