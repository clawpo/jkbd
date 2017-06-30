package cn.ucai.jkbd.biz;

/**
 * Created by clawpo on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    void nextQuestion();
    void preQuestion();
    void commitExam();
}
