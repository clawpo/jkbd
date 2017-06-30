package cn.ucai.jkbd;

import android.app.Application;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.ExamInfo;
import cn.ucai.jkbd.bean.Result;
import cn.ucai.jkbd.utils.OkHttpUtils;
import cn.ucai.jkbd.utils.ResultUtils;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
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


                OkHttpUtils<String> utils1 = new OkHttpUtils<>(instance);
                String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String jsonStr) {
                                Result result = ResultUtils.getListResultFromJson(jsonStr);
                                if (result!=null && result.getError_code()==0){
                                    List<Exam> list = result.getResult();
                                    if (list!=null && list.size()>0){
                                        mExamList = list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
            }
        }).start();
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
