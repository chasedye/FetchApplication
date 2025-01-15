package com.fetchApplication;

import org.json.JSONArray;
import org.json.JSONObject;

public interface DataCallback {

    void Success(JSONArray obj);
    void EncounteredError(String errorMsg);
}
