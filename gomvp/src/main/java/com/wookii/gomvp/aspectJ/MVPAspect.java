package com.wookii.gomvp.aspectJ;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.GoMVP;
import com.wookii.gomvp.annotation.DataSource;
import com.wookii.gomvp.annotation.GoActionBack;
import com.wookii.gomvp.annotation.GoBack;
import com.wookii.gomvp.annotation.GoError;
import com.wookii.gomvp.annotation.Presenter;
import com.wookii.gomvp.base.GoView;
import com.wookii.gomvp.base.LifecyclePresenter;
import com.wookii.gomvp.respository.DataSourceInjection;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.view.DefaultView;
import com.wookii.gomvp.view.IGoView;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author wuchen
 */
@Aspect
public class MVPAspect {

    private static final String TAG = "MVPAspect";
    public static String pin;

    @Pointcut("execution(* *..Activity+.onCreate(..))")
    public void activityCreate(){}

    @Pointcut("execution(* *..Fragment+.onCreateView(..))")
    public void fragmentCreate(){}

    @Pointcut("execution(@com.wookii.gomvp.annotation.DataBack * *(..))")
    public void dataBack(){}

    @Before("activityCreate() || fragmentCreate()")
    public void createPresenterForField(JoinPoint joinPoint) throws IllegalAccessException, InstantiationException {
        Object obj = joinPoint.getThis();
        String className = obj.getClass().getName();
        Context context = getContext(obj);
        Class<?> objClass = obj.getClass();
        Field[] declaredFields = objClass.getDeclaredFields();

        GoView view = getGoView(obj);

        for (Field f : declaredFields) {

            DataSource repositoryBind = f.getAnnotation(DataSource.class);
            if(repositoryBind != null) {
                GoDataSource repository = getRepository(context,repositoryBind);
                GoLog.I(className +":getRepository:" + repository);

                GoLog.I(className +":createPresenterForField：presenter annotation not null, createRetrofit presenter!");
                LifecyclePresenter lifecyclePresenter = getLifecyclePresenter(joinPoint, repository, view);
                f.setAccessible(true);
                f.set(obj,lifecyclePresenter);
                break;
            }

            Presenter presenterAnnotation = f.getAnnotation(Presenter.class);
            GoLog.D(className + ":inti declaredFields:" + f.getName() + ",type::" + f.getType().getName() + ",annotation:" + presenterAnnotation);
            //如果Presenter注解不为null，创建
            if(presenterAnnotation != null) {
                GoLog.I(className +":createPresenterForField：presenter annotation not null, createRetrofit presenter!");
                LifecyclePresenter lifecyclePresenter = getLifecyclePresenter(joinPoint, null, view);
                f.setAccessible(true);
                f.set(obj,lifecyclePresenter);
                break;
            }

        }
        if(view instanceof DefaultView) {
            handleMethods(objClass, (DefaultView)view);
        } else {
            //throw new RuntimeException("if use annotation, do not let Activity ro Fragment implements any View object!");
        }

    }

    private void handleMethods(Class<?> objClass, DefaultView view) {
        Method[] declaredMethods = objClass.getDeclaredMethods();
        //收集GoBack方法
        HashMap<String,Method> dataBack = new HashMap<>();
        //收集GoError方法
        HashMap<String,Method> onError = new HashMap<>();
        //收集GoActionBack
        HashMap<String,Method> actionBack = new HashMap<>();

        for (Method m:
                declaredMethods) {
            GoBack goBackAnnotation = m.getAnnotation(GoBack.class);
            if(goBackAnnotation != null) {
                String name = m.getParameterTypes()[0].getName();
                dataBack.put(name, m);
                GoLog.I("createPresenterForField: GoBack annotation: Method name:" + m.getName() + " type :" + name);

            }
            GoActionBack goActionBack = m.getAnnotation(GoActionBack.class);
            if(goActionBack != null) {
                String action = goActionBack.action();
                if(!TextUtils.isEmpty(action)){
                    String name = m.getParameterTypes()[0].getName();
                    actionBack.put(action, m);
                    GoLog.I("createPresenterForField: GoActionBack annotation: Method name:" + m.getName() + " action :" + action);

                }
            }

            GoError goErrorAnnotation = m.getAnnotation(GoError.class);
            if(goErrorAnnotation != null) {
                if(m.getParameterTypes().length >= 2) {
                    String name = m.getParameterTypes()[0].getName();
                    onError.put(name, m);
                } else {
                    onError.put("String_all", m);
                }

                GoLog.I("createPresenterForField: GoError annotation: Method name:" + m.getName() + " type :");

            }



        }
        if(dataBack.size() != 0) {
            try {
                view.addGoBackMethods(dataBack);
            } catch (Exception e) {

            }
        }

        if(onError.size() != 0) {
            try {
                view.addGoErrorMethods(onError);
            } catch (Exception e) {

            }
        }

        if(actionBack.size() != 0) {
            view.addGoActionBack(actionBack);
        }
    }

    public GoDataSource getRepository(Context context, DataSource repositoryBind) throws InstantiationException, IllegalAccessException {
        Class<? extends DataSourceInjection> value = repositoryBind.value();
        return value.newInstance().provideRepository(context);
    }

    @NonNull
    public GoView getGoView(Object obj) {
        GoView view;
        if(!(obj instanceof IGoView)) {
            view = new DefaultView<>(obj);
        } else {
            view = (GoView) obj;
        }
        return view;
    }


    public LifecyclePresenter getLifecyclePresenter(JoinPoint joinPoint, GoDataSource repository, GoView view) throws InstantiationException, IllegalAccessException {
        Object obj = joinPoint.getThis();
        //创建presenter
        LifecyclePresenter presenter;
        if(obj instanceof Activity) {
            presenter = new LifecyclePresenter((AppCompatActivity)obj);
        } else if(obj instanceof Fragment) {
            presenter = new LifecyclePresenter((Fragment) obj);
        } else {
            presenter = new LifecyclePresenter((Context)obj);
        }


        //初始化GoMVP
        GoMVP mvp = new GoMVP.Builder()
                .view(view)
                .presenter(presenter)
                .repository(repository)
                .build();
        presenter = mvp.getPresenter();
        return presenter;
    }


    public Context getContext(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object).getApplicationContext();
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getContext();
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getContext();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
