package com.example.vijay.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by vijay on 5/29/2015.
 */
public class Login_user extends ActionBarActivity {


    EditText user_name_edit_text;
    EditText password_edit_text;
    Button login_button;
   //final Context context = getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);


        login_button = (Button) (findViewById(R.id.login));

        user_name_edit_text = (EditText) findViewById(R.id.username);
        password_edit_text = (EditText) findViewById(R.id.password);




        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    login_verify();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        });

    }
        void login_verify() throws SQLException {

            DBUserAdapter dbUser = new DBUserAdapter(Login_user.this);
            dbUser.open();
            String password = password_edit_text.getText().toString();
            String username = user_name_edit_text.getText().toString();

            if(dbUser.Login(username, password))
            {
                Intent intent = new Intent(this, Login_Success.class);
                startActivity(intent);
            }else{
                Toast.makeText(Login_user.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
            }
            dbUser.close();
        }


 }



