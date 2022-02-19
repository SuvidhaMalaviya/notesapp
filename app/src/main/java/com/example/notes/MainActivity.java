package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Suvidha Malaviya - 01-02-2022
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_add_note;
    Button btn_add_note;
    ListView lv_notes;

    static List<String> lst_notes;
    ArrayAdapter adapter;

    ToggleButton tb_mode_change;

    Toolbar toolbar;
    LinearLayout ll_add_section;

    Animation top_animation,middle_animation,bottom_animation;

    static boolean isNightModeOn = false;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        setAnimation();
    }

    /***
     * Initialise all the component,animators and set listeners
     */
    private void initComponents() {
        edt_add_note = findViewById(R.id.edt_add_note);
        btn_add_note = findViewById(R.id.btn_add_note);
        lv_notes = findViewById(R.id.lv_notes);
        tb_mode_change = findViewById(R.id.tb_mode_change);
        toolbar = findViewById(R.id.toolbar);
        ll_add_section = findViewById(R.id.ll_add_section);

        lst_notes = new ArrayList<>();

        adapter = new ArrayAdapter(this,R.layout.list_tile,lst_notes);

        top_animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middle_animation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        btn_add_note.setOnClickListener(this);
        tb_mode_change.setOnClickListener(this);

        lv_notes.setAdapter(adapter);
    }

    /***
     * Set animation for the component
     */
    private void setAnimation() {
        toolbar.setAnimation(top_animation);
        ll_add_section.setAnimation(middle_animation);
        lv_notes.setAnimation(bottom_animation);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_add_note)
        {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);

            String note = edt_add_note.getText().toString();
            if(note.isEmpty())
            {
                Drawable error_icon = getResources().getDrawable(R.drawable.ic_error);
                error_icon.setBounds(0, 0, error_icon.getIntrinsicWidth(), error_icon.getIntrinsicHeight());

                edt_add_note.setError("Required",error_icon);
                return;
            }

            lst_notes.add(note);
            edt_add_note.setText("");
            adapter.notifyDataSetChanged();

            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
            }
        }
        else if(view == tb_mode_change)
        {
            Log.e("Theme Mode : ", String.valueOf(isNightModeOn));

            if(isNightModeOn){
                isNightModeOn = false;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                isNightModeOn = true;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
    }

    /***
     * Check that the permission to access storage is given or not
     * @param permission
     * @param request_code
     */
    private void checkPermission(String permission, int request_code) {
        if(ContextCompat.checkSelfPermission(MainActivity.this,permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {permission},request_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Storage Permission Granted",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Storage Permission Decline",Toast.LENGTH_LONG).show();
            }
        }
    }
}