package nguyen.anh.littleboss_xuong.network;

import java.util.List;
import java.util.Map;

import nguyen.anh.littleboss_xuong.model.Category;
import nguyen.anh.littleboss_xuong.model.Product;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitService {
    @GET("api/get-all-user")
    Call<List<Object>> getAllUser();

    @FormUrlEncoded
    @POST("api/login/username")
    Call<Map<String, Object>> loginUsername(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("api/login/username")
    Call<Map<String, Object>> loginEmail(@Field("email") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("api/register")
    Call<Map<String,Object>> Register(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );
    @GET("api/get-all-category")
    Call<List<Category>> getAllCategory();
//    http://localhost:3000/api/product/category/:category_id
    @GET("api/product/category/{category_id}")
    Call<Map<String,List<Product>>> getAllProductbyId(@Path("category_id") String category_id);
    // http://localhost:3000/api/product/is-pet
    @GET("api/product/is-pet")
    Call<Map<String,List<Product>>> getAllProductIsPet();
}

