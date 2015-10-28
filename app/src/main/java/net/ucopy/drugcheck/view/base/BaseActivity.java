package net.ucopy.drugcheck.view.base;/**
 * Created by yjx on 15/4/28.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * User: YJX
 * Date: 2015-04-28
 * Time: 23:01
 * 基础activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        ctx = this;
        ButterKnife.bind(this);

        initData();
        setListener();

    }

    /**
     * 得到布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 设置监听器
     */
    protected void setListener() {

    }

    /**
     * activity之间的跳转
     *
     * @param clazz    目标activity
     * @param isfinish 是否关闭
     */
    protected void jumpAct(Class clazz, boolean isfinish, int flags) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(flags);
        startActivity(intent);
        if (isfinish) {
            this.finish();
        }
    }

    /**
     * Fragment之间的切换
     *
     * @param from 当前
     * @param to   目标
     * @param id
     * @param tag
     */
    protected void jumpFrm(Fragment from, Fragment to, int id, String tag) {
        if (to == null) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (from == null) {
            transaction.add(id, to, tag);
        } else {
            transaction.hide(from);
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(id, to, tag);
            }
        }
        transaction.commitAllowingStateLoss();
    }


}
