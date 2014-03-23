package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
    private static Logic LOGIC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LOGIC = new Logic(new Button[]{
                (Button)findViewById(R.id.btn1),
                (Button)findViewById(R.id.btn2),
                (Button)findViewById(R.id.btn3),
                (Button)findViewById(R.id.btn4),
                (Button)findViewById(R.id.btn5),
                (Button)findViewById(R.id.btn6),
                (Button)findViewById(R.id.btn7),
                (Button)findViewById(R.id.btn8),
                (Button)findViewById(R.id.btn9),
        },
                (TextView)findViewById(R.id.notify),
                (Button)findViewById(R.id.play));
    }
    public void OnHintButtonClick(View view){
        byte coordinates = 0;
        switch (view.getId()){
            case R.id.btn1: coordinates = 11; break;
            case R.id.btn2: coordinates = 12; break;
            case R.id.btn3: coordinates = 13; break;
            case R.id.btn4: coordinates = 21; break;
            case R.id.btn5: coordinates = 22; break;
            case R.id.btn6: coordinates = 23; break;
            case R.id.btn7: coordinates = 31; break;
            case R.id.btn8: coordinates = 32; break;
            case R.id.btn9: coordinates = 33; break;
        }
        LOGIC.Step((Button)view, coordinates);
    }

    public void OnPlayButtonClick(View view){
        try{
            LOGIC.Clear(); LOGIC.FirstStep();
            LOGIC.play.setEnabled(false);
            LOGIC.SetButtons(true);
        }
        catch (Exception e){
            Toast.makeText(this, "Error", 3000);
        }
    }

    public void OnExitButtonClick(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameActivity.this);
        alertDialog.setTitle("Выйти?");

        alertDialog.setMessage("Вы действительно хотите выйти?");

        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }
    }
}
