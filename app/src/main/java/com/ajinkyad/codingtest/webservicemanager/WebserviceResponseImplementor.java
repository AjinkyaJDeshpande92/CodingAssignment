package com.ajinkyad.codingtest.webservicemanager;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * This class is used to handle the response from the Webservice.This class implements the Volley Library onResponse and onErrorResponse Callbacks
 * <p>
 * Functions-
 * 1)Handle the response from the Webservice
 * 2)Distinguish between Success/Failure
 * 3)Send the Data to Calling Module
 *
 * @author AjinkyaD
 * @version 1.0
 */
class WebserviceResponseImplementor implements Response.ErrorListener, Response.Listener {
    private WebCall callType;
    private WebserviceResponseListener webserviceResponseListener;

    /**
     * The Listener Initialisation Class.The response will be redirected to the caller screen
     *
     * @param callType                   - The Web Method Call Type
     * @param webserviceResponseListener - The Caller Screen Listener
     */
    WebserviceResponseImplementor(WebCall callType, WebserviceResponseListener webserviceResponseListener) {
        this.callType = callType;
        this.webserviceResponseListener = webserviceResponseListener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (webserviceResponseListener != null) {
            webserviceResponseListener.onFailure(error.networkResponse.statusCode, callType, null);
        }
    }

    @Override
    public void onResponse(Object response) {
        if (webserviceResponseListener != null) {
            webserviceResponseListener.onSuccess(200, callType, response);
        }
    }

}
