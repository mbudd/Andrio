package dwoodru3.blackjack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by david on 11/30/2016.
 */

public class StartActivity extends AppCompatActivity {

    private EditText buyinEditText;
    public static int buyInTotal = 0; // tracks bbank

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        setUpActivity();
    } // ends onCreate

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpActivity();
    }

    private void setUpActivity()
    {
        buyinEditText = (EditText) findViewById(R.id.buyin_editText);

        if (buyInTotal != 0)
            buyinEditText.setText("" + buyInTotal);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    } // end setUpActivity

    public void clickBuy(View v)
    {

        buyInTotal = Integer.parseInt(buyinEditText.getText().toString());

        if (buyInTotal >= 20 && buyInTotal <= 200)
        {
            startActivity(new Intent(StartActivity.this, MainActivity.class));

        }

        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Minimum buy-in $20 maximum buy-in $200";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    } // ends clickBuy


}// end Class StartActivity
