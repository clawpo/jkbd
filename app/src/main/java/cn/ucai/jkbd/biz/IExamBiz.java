package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.bean.Exam;

/**
 * Created by clawpo on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Exam getExam();
    Exam nextQuestion();
    Exam preQuestion();
    void commitExam();
    String getExamIndex();
}
