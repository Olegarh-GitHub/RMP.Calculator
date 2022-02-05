package com.example.rmpcalculator;

import android.os.AsyncTask;

import org.json.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
public class HttpCalculator
{
    private static String _url = "http://api.mathjs.org/v4/";
    private static HttpClient _httpClient = new DefaultHttpClient();

    public Double calculate(String expression) throws IOException, HttpException, JSONException {
        HttpResponse response = createRequest(expression);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new HttpException("Пиздец");
        }

        HttpEntity entity = response.getEntity();
        String convertedResult = null;
        JSONObject jsonResult = null;

        if (entity != null) {
            InputStream resultStream = entity.getContent();

            byte[] result = IOUtils.toByteArray(resultStream);

            convertedResult = new String(result);
            jsonResult = new JSONObject(convertedResult);
        }

        if (jsonResult == null)
            throw new NullPointerException("Не пришел ответ");

        return jsonResult.getDouble("result");
    }

    private static HttpResponse createRequest(String expression) throws IOException {
        HttpPost httpPost = new HttpPost(_url);

        String params = "{ \"expr\" : " + "\"" + expression + "\"" + "}";

        httpPost.setEntity(new StringEntity(params, "UTF-8"));
        httpPost.setHeader("content-type", "application/json");

        HttpResponse response = _httpClient.execute(httpPost);
        return response;
    }
}
