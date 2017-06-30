package cn.ucai.jkbd;

import android.app.Application;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.ExamInfo;
import cn.ucai.jkbd.utils.OkHttpUtils;

/**
 * Created by clawpo on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(instance);
        String uri = "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        mExamInfo = result;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
    }

    public ExamInfo getExamInfo() {
        return mExamInfo;
    }

    public void setExamInfo(ExamInfo examInfo) {
        mExamInfo = examInfo;
    }

    public List<Exam> getExamList() {
        return mExamList;
    }

    public void setExamList(List<Exam> examList) {
        mExamList = examList;
    }
}
