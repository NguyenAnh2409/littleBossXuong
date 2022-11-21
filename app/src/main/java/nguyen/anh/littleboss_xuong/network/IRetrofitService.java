package nguyen.anh.littleboss_xuong.network;

import java.util.List;
import java.util.Map;

import nguyen.anh.littleboss_xuong.model.Cart;
import nguyen.anh.littleboss_xuong.model.Category;
import nguyen.anh.littleboss_xuong.model.Product;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
    // http://localhost:3000/api/cart/add/:id
    @FormUrlEncoded
    @POST("api/cart/add/{id}")
    Call<Map<String,Object>> addCart(
            @Path("id") String id,
            @Field("ReceiptId") String ReceiptId,
            @Field("ProductId") String ProductId,
            @Field("Quantity") int Quantity,
            @Field("Price") double Price
    );
//    http://localhost:3000/api/cart/:id
    @GET("api/cart/{id}")
    Call<Map<String,List<Object>>> getAllCart(@Path("id") String id);

//    http://localhost:3000/api/products/636cef6afc13ae3841000001/detail
    @GET("api/products/{id}/detail")
    Call<Product> getProductDetail(@Path("id") String id);
//    http://localhost:3000/api/cart/buy
    @FormUrlEncoded
    @POST("api/cart/buy")
    Call<Map<String,Object>> buyCart(
            @Field("id") String id,
            @Field("isBill") boolean isBill,
            @Field("sumMoney") double sumMoney
    );
//    http://localhost:3000/api/cart/delete/:id
    @DELETE("api/cart/delete/{id}")
    Call<Map<String,Object>> deleteCart(
            @Path("id") String id
    );

//    http://localhost:3000/api/cart/update
    @FormUrlEncoded
    @POST("api/cart/update")
    Call<Map<String,Object>> updateCart(
            @Field("id") String id,
            @Field("ReceiptId") String ReceiptId,
            @Field("ProductId") String ProductId,
            @Field("Quantity") int Quantity,
            @Field("Price") double Price
    );
}

