package com.bo.app2_matrialdesignui.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bo.app2_matrialdesignui.databinding.ActivityLoginBinding;
import com.bo.app2_matrialdesignui.logic.basic.BaseActivity;
import com.bo.app2_matrialdesignui.logic.utils.Constant;
import com.bo.app2_matrialdesignui.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding activityLoginBinding ;

    private Button btLogin;
    private TextInputEditText tietPassword;
    private TextInputEditText tietUsername;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;

    private boolean isPassword=false;
    private boolean isUsername=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding =ActivityLoginBinding .inflate(getLayoutInflater());
        setContentView(activityLoginBinding .getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btLogin = activityLoginBinding.btLogin;
        tilUsername = activityLoginBinding.tilUsername;
        tilPassword = activityLoginBinding.tilPassword;
        tietPassword = activityLoginBinding.tietPassword;
        tietUsername = activityLoginBinding.tietUsername;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btLogin.setOnClickListener(view->{
            String username = Objects.requireNonNull(tietUsername.getText()).toString();

            if(!isUsername(username)){
                tilUsername.setErrorEnabled(true);
                tilUsername.setError("用户名错误");
                isUsername =false;
            }else{
                tilUsername.setErrorEnabled(false);
                isUsername=true;
            }

            String password = Objects.requireNonNull(tietPassword.getText()).toString();

            if(!isPassword(password)){
                tilPassword.setErrorEnabled(true);
                tilPassword.setError("密码错误");
                isPassword =false;
            }else{
                tilPassword.setErrorEnabled(false);
                isPassword=true;
            }

            if(isUsername&&isPassword){
                onLogin();
            }
        });

        tietUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String username = Objects.requireNonNull(tietUsername.getText()).toString();

                    if(!isUsername(username)){
                        tilUsername.setErrorEnabled(true);
                        tilUsername.setError("用户名错误");
                        isUsername =false;
                    }else{
                        tilUsername.setErrorEnabled(false);
                        isUsername=true;
                    }
                }
            }
        });

        tietPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String password = Objects.requireNonNull(tietPassword.getText()).toString();

                    if(!isPassword(password)){
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("密码错误");
                        isPassword =false;
                    }else{
                        tilPassword.setErrorEnabled(false);
                        isPassword=true;
                    }
                }
            }
        });
    }

    private boolean isPassword(String password) {
        if(!TextUtils.isEmpty(password)){
            return password.length()>=6;
        }
        return false;
    }

    private boolean isUsername(String username) {
        if(TextUtils.isEmpty(username)){
            return false;
        }

        Pattern pattern = Pattern.compile(Constant.REGEX_EMAIL);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private void onLogin() {
        //Toast.makeText(getThisActivity(),"登录成功",Toast.LENGTH_SHORT).show();
        MainActivity.startMainActivity(getThisActivity());
    }
}