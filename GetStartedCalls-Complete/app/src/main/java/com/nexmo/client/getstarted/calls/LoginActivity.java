package com.nexmo.client.getstarted.calls;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoUser;
import com.nexmo.client.request_listener.NexmoApiError;
import com.nexmo.client.request_listener.NexmoConnectionListener;
import com.nexmo.client.request_listener.NexmoRequestListener;
import com.nexmo.minirtcsdk.devicelayer.OnNetworkConnectionStateChangedListener;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUiAccordingToEnabledFeatures();

        NexmoHelper.init(getApplicationContext());

        NexmoClient.get().setConnectionListener(new NexmoConnectionListener() {
            @Override
            public void onConnectionStatusChange(@NonNull ConnectionStatus connectionStatus, @NonNull ConnectionStatusReason connectionStatusReason) {
                Log.v(NexmoHelper.TAG, "NexmoConnectionListener.Login onConnectionStatusChange "+ connectionStatus +" " + connectionStatusReason);
            }
        });
    }

    public void onLoginJaneClick(View view) {
        if (GetJWTForUser("JWT_JANE")) return;

        loginToSdk(NexmoHelper.JWT_JANE);

    }

    private boolean GetJWTForUser(String username) {
        //Some url endpoint that you may have
        String myUrl = "http://aurorascienceexploration.com:8000/generateJwt?user=" + username;
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();

        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();

            Log.d(NexmoHelper.TAG, "NexmoLoginListener.Login remote web request "+ result);

            loginToSdk(result);

            return true;
        }catch (Exception eex)
        {
            Log.e(NexmoHelper.TAG, "NexmoLoginListener.Login remote web request "+ eex.toString());
        }
        return false;
    }

    public void onLoginJoeClick(View view) {
        if (GetJWTForUser("JWT_JOE")) return;
        loginToSdk(NexmoHelper.JWT_JOE);
    }

    private void loginToSdk(String token) {
        NexmoClient.get().login(token);

        NexmoClient.get().getUser("CL_USER1", new NexmoRequestListener<NexmoUser>() {
            @Override
            public void onError(@NonNull NexmoApiError nexmoApiError) {
                Log.e(NexmoHelper.TAG, "NexmoLoginListener.Login getUser error "+ nexmoApiError.toString());
            }

            @Override
            public void onSuccess(@Nullable NexmoUser nexmoUser) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if(NexmoClient.get().getUser() != null) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    private void setUiAccordingToEnabledFeatures() {
        Button btnLoginJoe = findViewById(R.id.btnLoginJoe);
        List<NexmoHelper.Features> featuresList = Arrays.asList(NexmoHelper.enabledFeatures);
        btnLoginJoe.setVisibility(featuresList.contains(NexmoHelper.Features.IN_APP_to_IN_APP)? View.VISIBLE:View.GONE);
    }

}
