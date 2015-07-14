package com.easylink.nj.httptask;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.LogMgr;

import org.json.JSONObject;

/**
 * Created by yihaibin on 15/7/14.
 */
public abstract class NjJsonListener<T> implements HttpTaskStringListener<NjJsonResponse<T>> {

    protected Class<?> mClazz;

    public NjJsonListener(Class<?> clazz) {

        mClazz = clazz;
    }

    @Override
    public void onTaskPre(){

    }

    @SuppressWarnings("unchecked")
    @Override
    public NjJsonResponse<T> onTaskResponse(String jsonText) {

        NjJsonResponse<T> resp = new NjJsonResponse<T>();
        if (TextUtils.isEmpty(jsonText)) {

            resp.setParseBrokenStatus();
            return resp;
        }

        try {

            JSONObject jsonObj = new JSONObject(jsonText);
            if (LogMgr.isDebug())
                LogMgr.d(jsonObj.toString());

            resp.setResult(jsonObj.getString("result"));

            if (resp.isSuccess()) {

                jsonText = jsonObj.getString("data").toString();
                if (TextUtils.isEmpty(jsonText)) {

                    resp.setData((T) mClazz.newInstance());
                } else {

                    if (jsonText.startsWith("[")) {

                        // JsonArray
                        resp.setData(((T) JSON.parseArray(jsonText, mClazz)));
                    } else {

                        // JsonObj
                        resp.setData((T) JSON.parseObject(jsonText, mClazz));
                    }
                }
            }

        } catch (Exception e) {

            resp.setParseBrokenStatus();
            if (LogMgr.isDebug()) {

                LogMgr.d(e.getMessage());
                e.printStackTrace();
            }
        }

        return resp;
    }

    @Override
    public boolean onTaskSaveCache(NjJsonResponse<T> resp) {

        return false;
    }

    @Override
    public void onTaskSuccess(NjJsonResponse<T> resp) {

        if (resp.isSuccess() && resp.getData() != null) {

            try {

                onTaskResult(resp.getData());

            } catch (ClassCastException e) {

                if (LogMgr.isDebug())
                    LogMgr.d("~~onTaskResult ClassCastException: " + e.getMessage());

                onTaskFailed(TASK_FAILED_RESPONSE_PARSE_ERROR, "");
                return;
            }

        } else {

            if (resp.isParseBroken()) {

                onTaskFailed(TASK_FAILED_RESPONSE_PARSE_ERROR, "");
            } else {

                onTaskFailed(TASK_FAILED_SERVER_ERROR, resp.getResult());
            }
        }
    }

    public abstract void onTaskResult(T result);

    @Override
    public void onTaskFailed(int failedCode) {

        onTaskFailed(failedCode, "");
    }

    public abstract void onTaskFailed(int failedCode, String msg);

    @Override
    public void onTaskAbort() {

    }
}
