package ru.terrakok.cicerone.sample.ui.animations.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.mvp.animation.profile.ProfilePresenter;
import ru.terrakok.cicerone.sample.mvp.animation.profile.ProfileView;
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView, BackButtonListener {
    private ImageView avatar;

    @Inject
    Router router;

    @InjectPresenter
    ProfilePresenter presenter;

    @ProvidePresenter
    ProfilePresenter providePresenter() {
        return new ProfilePresenter(R.drawable.ava_1, router);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatar = (ImageView) view.findViewById(R.id.avatar_imageView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatar.setTransitionName(ProfileActivity.PHOTO_TRANSITION);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPhotoClicked();
            }
        });
    }

    @Override
    public void showPhoto(int resId) {
        avatar.setImageResource(resId);

        //for shared element animation
        avatar.setTag(resId);
    }

    public View getAvatarViewForAnimation() {
        return avatar;
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
