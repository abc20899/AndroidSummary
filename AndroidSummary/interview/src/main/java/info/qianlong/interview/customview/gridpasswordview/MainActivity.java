
package info.qianlong.interview.customview.gridpasswordview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import info.qianlong.interview.R;

public class MainActivity extends Activity {

    private GridPasswordView gpvNormal;

    private GridPasswordView gpvLength;

    private GridPasswordView gpvTransformation;

    private GridPasswordView gpvPasswordType;

    private GridPasswordView gpvCustomUi;

    private GridPasswordView gpvNormalTwice;

    private Spinner pswtypeSp;

    boolean isFirst = true;

    String firstPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passw);
        initViews();
        onPwdChangedTest();
    }

    private void initViews() {
        gpvNormal = (GridPasswordView)findViewById(R.id.gpv_normal);
        gpvLength = (GridPasswordView)findViewById(R.id.gpv_length);
        gpvTransformation = (GridPasswordView)findViewById(R.id.gpv_transformation);
        gpvPasswordType = (GridPasswordView)findViewById(R.id.gpv_passwordType);
        gpvCustomUi = (GridPasswordView)findViewById(R.id.gpv_customUi);
        gpvNormalTwice = (GridPasswordView)findViewById(R.id.gpv_normail_twice);

        pswtypeSp = (Spinner)findViewById(R.id.pswtype_sp);
        pswtypeSp.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onTypeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void onTypeSelected(int position) {
        switch (position) {
            case 0:
                gpvPasswordType.setPasswordType(PasswordType.NUMBER);
                break;

            case 1:
                gpvPasswordType.setPasswordType(PasswordType.TEXT);
                break;

            case 2:
                gpvPasswordType.setPasswordType(PasswordType.TEXTVISIBLE);
                break;

            case 3:
                gpvPasswordType.setPasswordType(PasswordType.TEXTWEB);
                break;
        }

    }

    // Test GridPasswordView.clearPassword() in OnPasswordChangedListener.
    // Need enter the password twice and then check the password , like Alipay
    void onPwdChangedTest() {
        gpvNormalTwice
                .setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
                    @Override
                    public void onTextChanged(String psw) {
                        if (psw.length() == 6 && isFirst) {
                            gpvNormalTwice.clearPassword();
                            isFirst = false;
                            firstPwd = psw;
                        } else if (psw.length() == 6 && !isFirst) {
                            if (psw.equals(firstPwd)) {
                                Log.d("MainActivity", "The password is: " + psw);
                            } else {
                                Log.d("MainActivity",
                                        "password doesn't match the previous one, try again!");
                                gpvNormalTwice.clearPassword();
                                isFirst = true;
                            }
                        }
                    }

                    @Override
                    public void onInputFinish(String psw) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
