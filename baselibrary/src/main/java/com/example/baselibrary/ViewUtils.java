package com.example.baselibrary;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {


    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static void inject(Activity activity,Object object){
        inject(new ViewFinder(activity),object);
    }

    //View中
    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    //Fragment
    public static void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }

    /**
     * 兼容方法
     * @param viewFinder
     * @param object  反射执行的类
     */
    private static void inject(ViewFinder viewFinder,Object object){
        injectFiled(viewFinder,object);
        injectEvent(viewFinder,object);
    }

    /**
     * 事件注入
     * @param viewFinder
     * @param object
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //1.获取类中所有方法
        //1. 获取所有属性
        Class<?> cl = object.getClass();
        //获取所有方法包括私有
        Method[] methods = cl.getDeclaredMethods();
        //2.获取value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if(onClick != null){
                //获取注解里面的id值
                int[] viewIds =  onClick.value();
                for (int viewId : viewIds) {
                    //3. findViewById 找到View
                    View view = viewFinder.findViewById(viewId);
                    if(view != null){
                        //4 . 设置点击事件
                        view.setOnClickListener(new DeclaredOnClickListener(method,object));
                    }
                }

            }
        }


        //4. 设置点击事件
    }

    private static class DeclaredOnClickListener implements View.OnClickListener{

        private Object object;
        private Method method;

        public DeclaredOnClickListener(Method method,Object object){
            this.object = object;
            this.method = method;
        }

        @Override
        public void onClick(View v) {
            try {
                method.setAccessible(true);
                //5. 反射执行方法
                method.invoke(object,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    method.invoke(object,null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 属性注入
     * @param viewFinder
     * @param object
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {

        //1. 获取所有属性
        Class<?> cl = object.getClass();
        //获取所有属性包括私有
        Field[] fields = cl.getDeclaredFields();
        //2.  获取viewById的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if(viewById != null){
                //获取注解里面的id值
                int viewId =  viewById.value();
                //3. 找到View
                View view = viewFinder.findViewById(viewId);
                if(view == null){
                   continue;
                }
                //能够注入所有的修饰符，private project
                field.setAccessible(true);
                //4. 动态注入设置View
                try {
                    field.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
