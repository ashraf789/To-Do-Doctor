package net.a6te.lazycoder.tododoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.a6te.lazycoder.tododoctor.database.AppointmentDatabaseHelper;
/*
* app name : ToDoDoctor
* starting development date : 4.4.17
* dead line : 12.4.17
* web : lazycoder.6te.net
* */
public class MainActivity extends AppCompatActivity {

    //its all for login layout
    private EditText emailEt;
    private EditText passwordEt;
    public static final String TAG = "ToDoDoctor";


    //its all for sign up layout
    private EditText signUpEmailEt;
    private EditText signUpPasswordEt;
    private EditText signUpNameEt;

    private SavedData savedData;//share preference class
    private LinearLayout layoutLogin,layoutSignUp;
    private AppointmentDatabaseHelper appointmentDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAll();//initializing all variable
    }

    private void initializeAll() {
        // for log in layout
        emailEt = (EditText) findViewById(R.id.emailEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);

        // for sign up layout
        signUpEmailEt = (EditText) findViewById(R.id.signupEmailEt);
        signUpPasswordEt = (EditText) findViewById(R.id.signupPasswordEt);
        signUpNameEt = (EditText) findViewById(R.id.nameEt);


        savedData = new SavedData(this);
        layoutLogin = (LinearLayout) findViewById(R.id.layout_login);
        layoutSignUp = (LinearLayout) findViewById(R.id.layout_signup);
        appointmentDatabaseHelper = new AppointmentDatabaseHelper(this);//creating database if not already exist
    }


    public void buttonOnclick(View view) {
        if (view.getId() == R.id.loginBtn){
            loginPageVisible();//when user want to login then login page will be visible
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            //if password or email is empty then this method will be called

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    emailEt.setError("Email can,t be empty");
                }
                if (password.isEmpty()){
                    passwordEt.setError("Password can,t be empty");
                }

                return;
            }

            //when email is pass is not empty then this line will be execute
            if (email.equals(savedData.getEmail()) && password.equals(savedData.getPassword())){
                //if email and password is correct then it will be execute
                startActivity(new Intent(MainActivity.this,Doctors.class));
            }else {
                Toast.makeText(MainActivity.this,"Wrong user email or password",Toast.LENGTH_LONG).show();
            }

        }else if (view.getId() == R.id.signupBtn){
            loginPageUnVisible();//sign up page visible

            String email = signUpEmailEt.getText().toString();
            String password = signUpPasswordEt.getText().toString();

            if (email.isEmpty() || password.isEmpty() || signUpNameEt.getText().toString().isEmpty()){
                if (email.isEmpty()){
                    emailEt.setError("Email can,t be empty");
                }
                if (password.isEmpty()){
                    passwordEt.setError("Password can,t be empty");
                }
                if (signUpNameEt.getText().toString().isEmpty()){
                    signUpNameEt.setError("Name can,t be empty");
                }
                return;
            }

            savedData.saveData(email,password);
            signUpNameEt.setText("");
            signUpEmailEt.setText("");
            signUpPasswordEt.setText("");
            startActivity(new Intent(MainActivity.this,Doctors.class));
            loginPageVisible();//when user come back to main page

        }
    }


    public void loginPageVisible(){
        layoutLogin.setVisibility(View.VISIBLE);
        layoutSignUp.setVisibility(View.GONE);
    }
    public void loginPageUnVisible(){
        layoutLogin.setVisibility(View.GONE);
        layoutSignUp.setVisibility(View.VISIBLE);
    }

}
