package cn.ucai.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.ExamInfo;
import cn.ucai.jkbd.biz.ExamBiz;
import cn.ucai.jkbd.biz.IExamBiz;

/**
 * Created by clawpo on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4,tvLoad;
    LinearLayout layoutLoading;
    ImageView mImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;


    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast = new LoadExamBroadcast();
        mLoadQuestionBroadcast = new LoadQuestionBroadcast();
        setListener();
        initView();
        biz = new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
        dialog = (ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo = (TextView) findViewById(R.id.tv_examinfo);
        tvExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        tvOp1 = (TextView) findViewById(R.id.tv_op1);
        tvOp2 = (TextView) findViewById(R.id.tv_op2);
        tvOp3 = (TextView) findViewById(R.id.tv_op3);
        tvOp4 = (TextView) findViewById(R.id.tv_op4);
        tvLoad = (TextView) findViewById(R.id.tv_load);
        mImageView = (ImageView) findViewById(R.id.im_exam_image);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void initData() {
        if (isLoadExamInfoReceiver && isLoadQuestionsReceiver){
            if (isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                }
                List<Exam> examList = ExamApplication.getInstance().getExamList();
                if (examList != null) {
                    showExam(examList);
                }
            }else{
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showExam(List<Exam> examList) {
        Exam exam = examList.get(0);
        if (exam!=null){
            tvExamTitle.setText(exam.getQuestion());
            tvOp1.setText(exam.getItem1());
            tvOp2.setText(exam.getItem2());
            tvOp3.setText(exam.getItem3());
            tvOp4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);
        }
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isLoadExamInfo = true;
            }
            isLoadExamInfoReceiver = true;
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isLoadQuestions = true;
            }
            isLoadQuestionsReceiver =true;
            initData();
        }
    }
}
