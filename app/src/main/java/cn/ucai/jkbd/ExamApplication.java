package cn.ucai.jkbd;

import android.app.Application;

import java.util.List;

import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.ExamInfo;
import cn.ucai.jkbd.biz.ExamBiz;
import cn.ucai.jkbd.biz.IExamBiz;

/**
 * Created by clawpo on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication instance;
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        biz = new ExamBiz();
        initData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
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
