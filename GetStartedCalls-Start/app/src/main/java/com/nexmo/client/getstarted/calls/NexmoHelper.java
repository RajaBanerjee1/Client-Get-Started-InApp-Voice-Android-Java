package com.nexmo.client.getstarted.calls;

import android.content.Context;
import android.util.Log;

import com.nexmo.client.NexmoCall;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoConnectionState;
import com.nexmo.client.request_listener.NexmoConnectionListener;

import java.lang.ref.WeakReference;

class NexmoHelper {

    private static final String TAG = "Nexmo-get-started";

    public static Features[] enabledFeatures = {Features.IN_APP_to_IN_APP, Features.PHONE_to_IN_APP, Features.IN_APP_to_PHONE};

    enum Features {IN_APP_to_IN_APP, PHONE_to_IN_APP, IN_APP_to_PHONE}

    static final String USER_NAME_JANE = "Jane";
    static final String USER_NAME_JOE = "Joe";
    static final String USER_ID_JANE = "USR-XXX"; //TODO: swap with the UserId you generated for Jane
    static final String USER_ID_JOE = "USR-XXX"; //TODO: swap with the UserId you generated for Joe
    static final String JWT_JANE = "PLACEHOLDER";//TODO: swap with the JWT you generated for Jane
    static final String JWT_JOE = "PLACEHOLDER"; //TODO: swap with the JWT you generated for Joe

    public static NexmoCall currentCall;
    private static boolean didInit;

    public static void init(Context appContext) {
        if (didInit) {
            return;
        }
        didInit = true;
        new NexmoClient.Builder()
                .build(appContext)
                .setConnectionListener(new NexmoConnectionListener() {
                    @Override
                    public void onConnectionStatusChange(ConnectionStatus status, ConnectionStatusReason reason) {
                        Log.d(TAG, "NexmoConnectionListener.onConnectionStatusChange : $status : $reason");
                    }
                });
    }

    public static String getUserName() {
        return NexmoClient.get().getUser().getName();
    }

    public static String getOtherUserName() {
        return NexmoClient.get().getUser().getName().equals(USER_NAME_JANE) ? USER_NAME_JOE : USER_NAME_JANE;
    }

    public static String getOtherUserId() {
        return NexmoClient.get().getUser().getName().equals(USER_NAME_JANE) ? USER_ID_JOE : USER_ID_JANE;
    }
}
