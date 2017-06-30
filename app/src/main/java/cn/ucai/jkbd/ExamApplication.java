package cn.ucai.jkbd;

import android.app.Application;

import java.util.List;

import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.ExamInfo;

/**
 * Created by clawpo on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM_INFO = "load_exam_info";
    public static String LOAD_EXAM_QUESTION = "load_exam_question";
    public static String LOAD_DATA_SUCCESS = "load_data_success";
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ExamApplication getInstance(){
        return instance;
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
