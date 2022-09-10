package com.bo.designpatternsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.designpatternsdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding activityMainBinding;
    private Button btSimpleFactory;
    private Button btFactoryMethod;
    private Button btBuilder;
    private Button btProxy;
    private Button btDecorator;
    private Button btFacade;
    private Button btFlyweight;
    private Button btStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btSimpleFactory = activityMainBinding.btSimpleFactory;
        btFactoryMethod = activityMainBinding.btFactoryMethod;
        btBuilder = activityMainBinding.btBuilder;
        btProxy = activityMainBinding.btProxy;
        btDecorator = activityMainBinding.btDecorator;
        btFacade = activityMainBinding.btFacade;
        btFlyweight = activityMainBinding.btFlyweight;
        btStrategy = activityMainBinding.btStrategy;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btSimpleFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleFactory().createComputer(SimpleFactory.LENOVO).start();
            }
        });

        btFactoryMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FactoryMethod().createComputer();
            }
        });

        btBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuilderTest.start();
            }
        });

        btProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ProxyTest().buy();
                new ProxyTest().buyDynamic();
            }
        });

        btDecorator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DecoratorTest().use();
            }
        });

        btFacade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FacadeTest().test();
            }
        });

        btFlyweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FlyweightTest().test();
            }
        });

        btStrategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StrategyTest().test();
            }
        });
    }
}