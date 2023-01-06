package liverary.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NlRetrofitClient {
    private static NlRetrofitClient instance = null;
    private static NlService retrofitInterface;
    private static String baseUrl = "https://www.nl.go.kr/NL/search/openApi/";

    private NlRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(NlService.class);
    }

    public static NlRetrofitClient getInstance() {
        if (instance == null) {
            instance = new NlRetrofitClient();
        }
        return instance;
    }

    public NlService getRetrofitInterface() {
        return retrofitInterface;
    }
}
