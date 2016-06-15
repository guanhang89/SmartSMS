package example.guanhang.smartsms.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import example.guanhang.smartsms.R;

/**
 * Activity的基类
 * Created by guanhang on 2016/5/28.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();

    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processOnlick(View view);
    @Override
    public void onClick(View v) {
        processOnlick(v);
    }
}
