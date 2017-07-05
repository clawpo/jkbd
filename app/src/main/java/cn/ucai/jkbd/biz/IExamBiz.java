package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.bean.Exam;

/**
 * Created by clawpo on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Exam getExam();
    Exam getExam(int index);
    Exam nextQuestion();
    Exam preQuestion();
    int commitExam();
    String getExamIndex();
}
