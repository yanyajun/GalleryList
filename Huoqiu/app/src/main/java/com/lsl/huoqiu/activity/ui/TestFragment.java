package com.lsl.huoqiu.activity.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.lsl.huoqiu.R;

public class TestFragment extends Fragment {
    private ImageView imageView;
    private PlayerView playerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.photo_ablum_item, container, false);

        playerView = mView.findViewById(R.id.playerView);

        imageView = mView.findViewById(R.id.coverView);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("===>", "fragment onViewCreated: ");

        playerView.setUseController(false);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
//        playerView.setPlayer(null);
        imageView.setBackgroundResource(R.mipmap.cover);


        if (mFragmentCreateListener != null) {
            mFragmentCreateListener.created();
        }

    }

    public PlayerView getPlayView() {
        return playerView;
    }

    public void showPlayView() {
        if (imageView != null) {
            if (imageView.getVisibility() == View.VISIBLE) {
                imageView.setVisibility(View.INVISIBLE);
            }
            Log.e("===>", "showPlayView: ");
        }
    }

    public void stopVideo(boolean stop) {
        if (playerView != null && stop) {
//            playerView.setPlayer(null);
        }
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private FragmentCreateListener mFragmentCreateListener;
    public void setFragmentCreateListener(FragmentCreateListener listener) {
        mFragmentCreateListener = listener;
    }
    public interface FragmentCreateListener {
        void created();
    }
}
