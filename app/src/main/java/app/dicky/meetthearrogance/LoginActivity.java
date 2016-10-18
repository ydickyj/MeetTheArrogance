package app.dicky.meetthearrogance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 * Created by dicky on 2016/6/28.
 */
public class LoginActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        Button btnLoginWithoutregister = (Button)findViewById(R.id.btn_direct_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,LoginPasswordActivity.class));
            }
        });
        btnLoginWithoutregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,TestListActivity.class));
            }
        });
    }
}
