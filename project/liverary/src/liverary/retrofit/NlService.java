package liverary.retrofit;

import liverary.vo.NlBooksResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NlService {
	@GET("search.do?apiType=json&srchTarget=total&pageNum=1&systemType=%EC%98%A4%ED%94%84%EB%9D%BC%EC%9D%B8%EC%9E%90%EB%A3%8C")
	Call<NlBooksResult> getNlBooksResult(@Query("key") String secretKey, @Query("pageSize") int max, @Query("kwd") String keyword);
}
