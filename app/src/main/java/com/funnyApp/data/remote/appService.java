package com.funnyApp.data.remote;


import com.funnyApp.data.model.Profile;
import com.funnyApp.data.model.Register;
import com.funnyApp.data.model.User;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by android on 4/25/17.
 */

public interface appService {

    @POST("api/users")
    Observable<Response<Register>> createUser(@Body Register user);

    @POST("oauth/token")
    Observable<Response<HashMap<String,String>>> login(@Body HashMap<String,String> userInfo);

    @POST("api/activate_account")
    Observable<Response<HashMap<String,String>>> activateAccount(@Body HashMap<String,String> user);

    @POST("api/send_a_new_password")
    Observable<Response<HashMap<String,String>>> sendNewPassword(@Body HashMap<String,String> user);

    @GET("api/my_profile")
    Observable<Response<Profile>> myProfile(@Query("access_token") String token);

    @PUT("api/edit_my_profile")
    Observable<Response<Profile>> editProfile(@Query("access_token") String token, @Body Profile profile);

    @PUT("api/change_password")
    Observable<Response<User>> sendNewPassword(@Query("access_token") String token, @Body User user);

    @PUT("/api/upload_avatar")
    Observable<Response<Profile>> editImage(@Query("access_token") String token,  @Body Profile profile);
}
