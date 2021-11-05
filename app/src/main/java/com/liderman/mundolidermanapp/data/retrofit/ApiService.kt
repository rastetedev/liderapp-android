package com.liderman.mundolidermanapp.data.retrofit

import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.data.request.*
import com.liderman.mundolidermanapp.data.responses.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @POST(BuildConfig.URL_BASE_TRACE + "login")
    fun signIn(
        @Body request: LoginRequest
    ): Observable<LoginResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "announcements")
    fun getAnnouncements(): Observable<AnnouncementResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "contact-areas")
    fun contactAreas(): Observable<ContactResponse>

    @POST(BuildConfig.URL_BASE_TRACE + "contact-areas")
    fun qualityRequest(
        @Body request: QualityRequest
    ): Observable<Any>

    @GET(BuildConfig.URL_BASE_TRACE + "sgi-docs")
    fun getSgiDocs(): Observable<SgiDocResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "liderman-plays")
    fun getLidermanPlay(): Observable<LidermanPlayResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "lights/{user_id}/{token}")
    fun getTraffic(
        @Path("user_id") userId: String,
        @Path("token") token: String
    ): Observable<TrafficResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "ama-trato")
    fun getAmaTrata(): Observable<AmaTrataResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "ama-pago")
    fun getAmaPay(): Observable<AmaPayResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "ama-vida")
    fun getAmaLife(): Observable<AmaLifeResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "lidernet/{user_id}")
    fun getLiderNet(
        @Path("user_id") userId: String
    ): Observable<LidernetResponse>

    @POST(BuildConfig.URL_BASE_TRACE + "lidernet")
    fun responseLiderNet(
        @Body request: LiderNetRequest
    ): Observable<Any>

    @GET(BuildConfig.URL_BASE_TRACE + "tareo/{user_id}/11/{token}")
    fun getTasks(
        @Path("user_id") userId: String,
        @Path("token") token: String
    ): Observable<TaskResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "profile/{user_id}")
    fun getProfile(
        @Path("user_id") userId: String
    ): Observable<LoginResponse>

    @POST(BuildConfig.URL_BASE_TRACE + "profile")
    fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Observable<LoginResponse>

    @Multipart
    @POST(BuildConfig.URL_BASE_TRACE + "upload-image")
    fun uploadImage(
        @HeaderMap headers: Map<String, String>,
        @Part image: MultipartBody.Part,
        @Part("user_id") userId: String
    ): Observable<LoginResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "borrows/{user_id}/{token}")
    fun getBorrows(
        @Path("user_id") userId: String,
        @Path("token") token: String
    ): Observable<BorrowResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "payments/{user_id}/{period}/{token}")
    fun getPayments(
        @Path("user_id") userId: String,
        @Path("period") period: String,
        @Path("token") token: String
    ): Observable<PaymentResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "utilidades/{user_id}/{period}/{token}")
    fun getUtilidades(
        @Path("user_id") userId: String,
        @Path("period") period: String,
        @Path("token") token: String
    ): Observable<UtilidadesResponse>

    @GET(BuildConfig.URL_BASE_TRACE + "contrato/{user_id}/{token}")
    fun getContract(
        @Path("user_id") userId: String,
        @Path("token") token: String
    ): Observable<ContractResponse>

    @POST(BuildConfig.URL_BASE_TRACE + "contrato")
    fun updateStateContract(
        @Body request: UpdateContractRequest
    ): Observable<ContractResponse>

    @POST(BuildConfig.URL_BASE_TRACE + "logout")
    fun signOut(
        @Body request: LogoutRequest
    ): Observable<Any>

    @POST(BuildConfig.URL_BASE_TRACE + "analytics")
    fun sendEvent(
        @Body request: EventRequest
    ): Observable<Any>
}
