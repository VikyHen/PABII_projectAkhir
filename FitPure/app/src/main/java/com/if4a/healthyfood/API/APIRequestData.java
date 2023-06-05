package com.if4a.healthyfood.API;

import com.if4a.healthyfood.Model.ModelKalori;
import com.if4a.healthyfood.Model.ModelResponse;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();

    @GET("retrieveKalori.php")
    Call<ModelKalori> ardRetrieveKalori();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("gambar") String gambar,
            @Field("deskripsi") String deskripsi,
            @Field("kalori") int kalori
            );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("gambar") String gambar,
            @Field("deskripsi") String deskripsi,
            @Field("kalori") int kalori
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("createManfaat.php")
    Call<ModelResponse> ardCreateManfaat(
            @Field("food_id") String food_id,
            @Field("kandungan") String kandungan,
            @Field("manfaatKandungan") String manfaatKandungan,
            @Field("jumlah") String jumlah
    );

    @FormUrlEncoded
    @POST("updateManfaat.php")
    Call<ModelResponse> ardUpdateManfaat(
            @Field("id_manfaat") String id_manfaat,
            @Field("kandungan") String kandungan,
            @Field("manfaatKandungan") String manfaatKandungan,
            @Field("jumlah") String jumlah
    );

    @FormUrlEncoded
    @POST("deleteManfaat.php")
    Call<ModelResponse> ardDeleteManfaat(
            @Field("id_manfaat") String id_manfaat
    );

    @FormUrlEncoded
    @POST("addKalori.php")
    Call<ModelKalori> ardAddKalori(
            @Field("nama") String nama,
            @Field("gambar") String gambar,
            @Field("kalori") int kalori
    );

    @FormUrlEncoded
    @POST("deleteKalori.php")
    Call<ModelKalori> ardDeleteKalori(
            @Field("id_kalori") String id_kalori
    );
}
