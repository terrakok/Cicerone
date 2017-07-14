package ru.terrakok.cicerone.sample.ui.animations.photos;

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
import ru.terrakok.cicerone.sample.mvp.animation.photos.SelectPhotoPresenter;
import ru.terrakok.cicerone.sample.mvp.animation.photos.SelectPhotoView;
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

public class SelectPhotoFragment extends MvpAppCompatFragment implements SelectPhotoView, BackButtonListener {
    private static final String ARG_RESULT_CODE = "arg_result_code";
    private static final String ARG_ANIM_DESTINATION = "arg_anim_dest";

    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private ImageView photo4;

    @Inject
    Router router;

    @InjectPresenter
    SelectPhotoPresenter presenter;

    public static SelectPhotoFragment getNewInstance(int resultCode) {
        SelectPhotoFragment fragment = new SelectPhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESULT_CODE, resultCode);
        fragment.setArguments(args);
        return fragment;
    }

    public void setAnimationDestinationId(int resId) {
        Bundle arguments = getArguments();
        arguments.putInt(ARG_ANIM_DESTINATION, resId);
        setArguments(arguments);
    }

    private int getAnimationDestionationId() {
        return getArguments().getInt(ARG_ANIM_DESTINATION);
    }

    @ProvidePresenter
    SelectPhotoPresenter providePresenter() {
        return new SelectPhotoPresenter(router, getArguments().getInt(ARG_RESULT_CODE));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photo1 = (ImageView) view.findViewById(R.id.select_image_1);
        photo2 = (ImageView) view.findViewById(R.id.select_image_2);
        photo3 = (ImageView) view.findViewById(R.id.select_image_3);
        photo4 = (ImageView) view.findViewById(R.id.select_image_4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photo1.setOnClickListener(clickListener);
        photo2.setOnClickListener(clickListener);
        photo3.setOnClickListener(clickListener);
        photo4.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            photo1.setTransitionName(null);
            photo2.setTransitionName(null);
            photo3.setTransitionName(null);
            photo4.setTransitionName(null);
            v.setTransitionName(ProfileActivity.PHOTO_TRANSITION);
            presenter.onPhotoClick((Integer) v.getTag());
        }
    };

    @Override
    public void showPhotos(int[] resurceIds) {
        if (resurceIds.length >= 4) {
            photo1.setImageResource(resurceIds[0]);
            photo2.setImageResource(resurceIds[1]);
            photo3.setImageResource(resurceIds[2]);
            photo4.setImageResource(resurceIds[3]);

            photo1.setTag(resurceIds[0]);
            photo2.setTag(resurceIds[1]);
            photo3.setTag(resurceIds[2]);
            photo4.setTag(resurceIds[3]);

            //for shared element animation
            int animRes = getAnimationDestionationId();
            photo1.setTransitionName(animRes == resurceIds[0] ? ProfileActivity.PHOTO_TRANSITION : null);
            photo2.setTransitionName(animRes == resurceIds[1] ? ProfileActivity.PHOTO_TRANSITION : null);
            photo3.setTransitionName(animRes == resurceIds[2] ? ProfileActivity.PHOTO_TRANSITION : null);
            photo4.setTransitionName(animRes == resurceIds[3] ? ProfileActivity.PHOTO_TRANSITION : null);
        }
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
