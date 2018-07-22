package com.example.android.health_in_time;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.health_in_time.database_sql.DatabaseHandler;
import com.example.android.health_in_time.helpers.InputValidation;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.example.android.health_in_time.MainActivity.EXTRA_MESSAGE;


public class LoginActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {

    Button b1;
    int counter = 3;
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    public static String username;

    private TextInputLayout textInputLayoutNickName;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextNickName;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHandler databaseHelper;
    private Contacts user;

    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // getSupportActionBar().hide();


        //Log.d(TAG, "This entered");
        initViews();
        initListeners();
        initObjects();

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNickName = (TextInputLayout) findViewById(R.id.textInputLayoutNickName);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextNickName = (TextInputEditText) findViewById(R.id.textInputEditTextNickName);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHandler(activity);
        inputValidation = new InputValidation(activity);

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    static final int REGISTER_CONTACT_REQUEST = 0;

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                //System.out.println("user = " + databaseHelper.getAllUser());
                //System.out.println();
                //Log.d(TAG, "This entered");
                verifyFromSQLite();

                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                //System.out.println("register enterd");
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivityForResult(intentRegister, REGISTER_CONTACT_REQUEST);
                break;
        }
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == REGISTER_CONTACT_REQUEST) {
             if (resultCode == RESULT_OK) {
                 // A contact was picked.  Here we will just display it
                 // to the user.
                 Context context = getApplicationContext();
                 CharSequence text = "User registered succesfully :)";
                 int duration = Toast.LENGTH_SHORT;

                 Toast toast = Toast.makeText(context, text, duration);
                 toast.show();
             }
         }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        Log.d(TAG, textInputEditTextNickName.toString());
        if (!inputValidation.isInputEditTextFilled(textInputEditTextNickName, textInputLayoutNickName, getString(R.string.error_message_nickname))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        System.out.println("FIELds are corrrrrrrrrrrrrrrrrect");
        Authenication ath = new Authenication();
        String usernm = textInputEditTextNickName.getText().toString().trim();
        String pass = textInputEditTextPassword.getText().toString().trim();

        try {
            if(ath.authenticate(getApplicationContext(), usernm, pass)){

                System.out.println("Input is valid");
                username = textInputEditTextNickName.getText().toString().trim();

                System.out.println("Username is savedddddddddddddddddd = " + username);
                //user = databaseHelper.getUser(username);

                //Intent accountsIntent = new Intent(activity, UsersListActivity.class);
                //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                //Intent accountsIntent = new Intent(this, HeartRateMonitor.class);

                emptyInputEditText();
                //startActivity(accountsIntent);


            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(nestedScrollView, getString(R.string.error_valid_nick_password), Snackbar.LENGTH_LONG).show();
            }
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            //e.printStackTrace();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextNickName.setText(null);
        textInputEditTextPassword.setText(null);
        launchHearRateMonitor(nestedScrollView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        //System.out.println("crashes_came_here");
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                //Snackbar.make(mLayout, "Camera permission was granted. Starting preview.",
                //        Snackbar.LENGTH_SHORT)
                //        .show();
                gotAccess();
            } else {
                System.out.println("PERMISSOON DENIED");
                // Permission request was denied.
                //Snackbar.make(mLayout, "Camera permission request was denied.",
                 //       Snackbar.LENGTH_SHORT)
                 //       .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            System.out.println("more info");
            //Snackbar.make(mLayout, "Camera access is required to display the camera preview.",
            //       Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
            // Request the permission
            // System.out.println("crashes1.5");
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
            //}
            //}).show();

        } else {
            System.out.println("crashes2");
            //Snackbar.make(mLayout,
            //        "Permission is not available. Requesting camera permission.",
            //        Snackbar.LENGTH_SHORT).show();
            //System.out.println("crashes3");
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    private void showCameraPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            //Snackbar.make(mLayout,
            //       "Camera permission is available. Starting preview.",
            //        Snackbar.LENGTH_SHORT).show();
            System.out.println("Permission is available");
            gotAccess();

        } else {
            // Permission is missing and must be requested.
            System.out.println("Requesting..");
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
    }



    public void launchHearRateMonitor(View view){
       showCameraPreview();
    }

    public void gotAccess(){
        System.out.println("Access granted");

        Intent intent = new Intent(this, HeartRateMonitor.class);
        String message = username;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        //finish();
    }

    public String getTextInputLayoutNickName() {
        return textInputLayoutNickName.toString();
    }
//b1.setOnClickListener(new View.OnClickListener() {
    //@Override
    /*
    public void login(View view) {
        b1 = (Button) findViewById(R.id.);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        TextView typerror = (TextView) findViewById(R.id.typerror);
        Log.d("auth", "username = " + username.getText().toString() + " pass = " + password.getText().toString());

        if (username.getText().toString().equals("Admin") &&
                password.getText().toString().equals("admin")) {

            Toast.makeText(LoginActivity.this,
                    "Redirecting...", Toast.LENGTH_SHORT).show();
            launchHearRateMonitor(view);
        } else {
            Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();

            typerror.setVisibility(View.VISIBLE);
            typerror.setBackgroundColor(Color.BLUE);
            counter--;
            typerror.setText(Integer.toString(counter));

            if (counter == 0) {
                Toast.makeText(LoginActivity.this, "You entered wrong password or username 3 times",
                        Toast.LENGTH_SHORT).show();
                b1.setEnabled(false);
            }
        }
    }
    //});

    */

}
