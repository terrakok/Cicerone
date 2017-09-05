package ru.terrakok.cicerone.annotations;


import android.app.Fragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by andriybiskup on 8/30/17.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface RelatedFragment {

    Class<? extends Fragment> fragment();

}
