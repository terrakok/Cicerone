package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.annotations.RelatedActivity;
import ru.terrakok.cicerone.annotations.RelatedFragment;

/**
 * Created by andriybiskup on 8/30/17.
 */

public class AnnotationNavigator {

    private Navigator navigator;
    private Class screensClass;

    public AnnotationNavigator(Activity activity, int containerViewId, Class screensClass) {
        this.screensClass = screensClass;
        this.navigator = initNavigator(activity, containerViewId);
    }

    private AppNavigator initNavigator(final Activity activity, int containerViewId) {
        return new AppNavigator(activity, containerViewId) {
            @Override
            protected Intent createActivityIntent(String screenKey, Object data) {
                Map<String, Class<? extends Activity>> activities = getActivitiesScreens(screensClass);
                if (!activities.containsKey(screenKey)) return null;
                Intent intent = new Intent(activity, activities.get(screenKey));
                if (data != null) intent.putExtras((Bundle) data);
                return intent;
            }

            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                Map<String, Class<? extends Fragment>> fragments = getFragmentScreens(screensClass);
                if (!fragments.containsKey(screenKey)) {
                    return null;
                }
                Fragment fragment = null;
                try {
                    fragment = fragments.get(screenKey).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                if (data != null) fragment.setArguments((Bundle) data);
                return fragment;

            }
        };
    }

    private Map<String, Class<? extends Activity>> getActivitiesScreens(Class screenClass) {
        Map<String, Class<? extends Activity>> activities = new HashMap<>();
        for (Field field : findFieldsWithAnnotation(screenClass, RelatedActivity.class)){
            try {
                activities.put((String) field.get(screenClass), field.getAnnotation(RelatedActivity.class).activity());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return activities;
    }

    private Map<String, Class<? extends Fragment>> getFragmentScreens(Class screenClass) {
        Map<String, Class<? extends Fragment>> fragments = new HashMap<>();
        for (Field field : findFieldsWithAnnotation(screenClass, RelatedFragment.class)){
            try {
                fragments.put((String) field.get(screenClass), field.getAnnotation(RelatedFragment.class).fragment());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return fragments;
    }

    private static Set<Field> findFieldsWithAnnotation(Class<?> classs, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
