package br.com.helpers.rocketChatRestClientAndroid;

import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.*;


public class RocketChatClientAndroid {

	private final String serverUrl;
	private final String user;
	private final String password;
	private String xAuthToken;
	private String xUserId;
    private OkHttpClient client = new OkHttpClient();
    private Request.Builder builder = new Request.Builder();


	public RocketChatClientAndroid(String serverUrl, String User, String Password){
		this.serverUrl = serverUrl;
		this.user = User;
		this.password = Password;
	}

    public void authenticate(){
        try {

            RequestBody formBody = new FormBody.Builder()
                    .add("user", this.user)
                    .add("password", this.password)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Content-type", "application/x-www-form-urlencoded")
                    .url(this.serverUrl + "/api/login")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONObject res = new JSONObject(responseString);
            String status = res.getString("status");

            if(status.equals("success") == true){
                this.xAuthToken = res.getJSONObject("data").getString("authToken");
                this.xUserId = res.getJSONObject("data").getString("userId");
            }

            response.body().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        try {
            Request request = new Request.Builder()
                    .addHeader("Content-type", "application/x-www-form-urlencoded")
                    .url(this.serverUrl + "/api/logout")
                    .header("X-Auth-Token", this.xAuthToken)
                    .header("X-User-Id", this.xUserId)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONArray getPublicRooms(){
        try {
            Request request = new Request.Builder()
                    .addHeader("Content-type", "application/x-www-form-urlencoded")
                    .url(this.serverUrl + "/api/publicRooms")
                    .header("X-Auth-Token", this.xAuthToken)
                    .header("X-User-Id", this.xUserId)
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONArray rooms = new JSONObject(responseString).getJSONArray("rooms");

            return rooms;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getMessagesByRoom(String room){
        try {
            Request request = new Request.Builder()
                    .addHeader("Content-type", "application/x-www-form-urlencoded")
                    .url(this.serverUrl + "/api/rooms/" + room + "/messages")
                    .header("X-Auth-Token", this.xAuthToken)
                    .header("X-User-Id", this.xUserId)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONArray messages = new JSONObject(responseString).getJSONArray("messages");
            return messages;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	public String getServerUrl() {
		return serverUrl;
	}
}
