package com.example.vijay.setwallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity implements View.OnClickListener {

    ImageView imageView1;
    Button button;
    int tophone;
   // WallpaperManager myWallpaperManager= WallpaperManager.getInstance(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tophone=R.drawable.img1;
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView)findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView)findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView)findViewById(R.id.imageView4);
        ImageView imageView5 = (ImageView)findViewById(R.id.imageView5);
        ImageView imageView6 = (ImageView)findViewById(R.id.imageView6);
        ImageView imageView7 = (ImageView)findViewById(R.id.imageView7);
        ImageView imageView8 = (ImageView)findViewById(R.id.imageView8);
        ImageView imageView9 = (ImageView)findViewById(R.id.imageView9);
        button=(Button)findViewById(R.id.button);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);

        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.imageView2:
                imageView1.setImageResource(R.drawable.img1);
                tophone=R.drawable.img1;
                break;

            case R.id.imageView3:
                imageView1.setImageResource(R.drawable.img2);
                tophone=R.drawable.img2;
                break;

            case R.id.imageView4:
                imageView1.setImageResource(R.drawable.img3);
                tophone=R.drawable.img3;
                break;

            case R.id.imageView5:
                imageView1.setImageResource(R.drawable.img4);
                tophone=R.drawable.img4;
                break;

            case R.id.imageView6:
                imageView1.setImageResource(R.drawable.img5);
                tophone=R.drawable.img5;
                break;

            case R.id.imageView7:
                imageView1.setImageResource(R.drawable.img6);
                tophone=R.drawable.img6;
                break;

            case R.id.imageView8:
                imageView1.setImageResource(R.drawable.img7);
                tophone=R.drawable.img7;
                break;

            case  R.id.imageView9:
                imageView1.setImageResource(R.drawable.img8);
                tophone=R.drawable.img8;
                break;

            case R.id.button:

                InputStream a = getResources().openRawResource(tophone);
                Bitmap image = BitmapFactory.decodeStream(a);
                try{
                    //myWallpaperManager.setResource();
                    getApplicationContext().setWallpaper(image);
                    Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }
}
