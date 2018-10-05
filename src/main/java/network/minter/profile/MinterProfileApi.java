/*
 * Copyright (C) by MinterTeam. 2018
 * @link <a href="https://github.com/MinterTeam">Org Github</a>
 * @link <a href="https://github.com/edwardstock">Maintainer Github</a>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package network.minter.profile;

import com.google.gson.GsonBuilder;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import network.minter.core.MinterSDK;
import network.minter.core.crypto.BytesData;
import network.minter.core.crypto.EncryptedString;
import network.minter.core.crypto.MinterAddress;
import network.minter.core.internal.api.ApiService;
import network.minter.core.internal.api.converters.BigIntegerDeserializer;
import network.minter.core.internal.api.converters.BytesDataDeserializer;
import network.minter.core.internal.api.converters.EncryptedStringDeserializer;
import network.minter.core.internal.api.converters.EncryptedStringSerializer;
import network.minter.core.internal.api.converters.MinterAddressDeserializer;
import network.minter.core.internal.api.converters.MinterAddressSerializer;
import network.minter.core.internal.common.CallbackProvider;
import network.minter.profile.repo.ProfileAddressRepository;
import network.minter.profile.repo.ProfileAuthRepository;
import network.minter.profile.repo.ProfileInfoRepository;
import network.minter.profile.repo.ProfileRepository;
import okhttp3.logging.HttpLoggingInterceptor;

import static network.minter.core.internal.common.Preconditions.checkArgument;
import static network.minter.core.internal.common.Preconditions.checkNotNull;

/**
 * minter-android-profile. 2018
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class MinterProfileApi {
    private static final String BASE_API_URL = BuildConfig.BASE_API_URL;
    private static MinterProfileApi INSTANCE;
    private ApiService.Builder mApiService;
    private ProfileAuthRepository mAuthRepository;
    private ProfileInfoRepository mInfoRepository;
    private ProfileAddressRepository mAddressRepository;
    private ProfileRepository mProfileRepository;

    private MinterProfileApi(final CallbackProvider<String> token) {
        mApiService = new ApiService.Builder(BASE_API_URL, getGsonBuilder());
        mApiService.addHeader("Content-Type", "application/json");
        mApiService.addHeader("X-Minter-Client-Name", "MinterAndroid (profile)");
        mApiService.addHeader("X-Minter-Client-Version", BuildConfig.VERSION_NAME);
        mApiService.setDateFormat("yyyy-MM-dd HH:mm:ssX");
        mApiService.setAuthHeaderName("Authorization");
        mApiService.setTokenGetter(() -> {
            if (token == null) {
                return null;
            }

            return "Bearer " + token.get();
        });
    }

    /**
     * Init sdk with no debug log
     * @param token callback for getting token from your storage
     */
    public static void initialize(CallbackProvider<String> token) {
        initialize(token, false);
    }

    /**
     * Init sdk with debug log
     * @param token callback for getting token from your storage
     * @param debug enable debug logs
     */
    public static void initialize(CallbackProvider<String> token, boolean debug) {
        if (INSTANCE != null) {
            return;
        }

        INSTANCE = new MinterProfileApi(token);
        INSTANCE.mApiService.setDebug(debug);
        INSTANCE.mApiService.setDebugRequestLevel(debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    /**
     * Return constant coin avatar url using coin name
     * @param coinName string coin name (min len: 3, max: 10)
     * @return coin avatar url (if coin does not exists, returns some default avatar)
     */
    public static String getCoinAvatarUrl(final @Nonnull String coinName) {
        checkNotNull(coinName, "Coin name can't be null");
        checkArgument(coinName.length() >= 3 && coinName.length() <= 10, "Coin length must be from 3 to 10 chars");
        return BASE_API_URL + "/api/v1/avatar/by/coin/" + coinName.toUpperCase();
    }

    /**
     * Return constant user avatar url using user id
     * @param id user id
     * @return user avatar url (if user does not exists, returns some default avatar)
     */
    public static String getUserAvatarUrl(long id) {
        return getUserAvatarUrl(String.valueOf(id));
    }

    /**
     * Return constant user avatar url using user id
     * @param id string representation of numeric id
     * @return user avatar url (if user does not exists, returns some default avatar)
     * @see #getUserAvatarUrl(long)
     */
    public static String getUserAvatarUrl(String id) {
        checkNotNull(id, "Id required");
        return BASE_API_URL + "/api/v1/avatar/by/user/" + id;
    }

    /**
     * Return constant user avatar url using minter address Mx...
     * @param address minter address object
     * @return user avatar url (if user does not exists, returns some default avatar)
     */
    public static String getUserAvatarUrlByAddress(MinterAddress address) {
        return getUserAvatarUrlByAddress(address.toString());
    }

    /**
     * Return constant user avatar url using minter address Mx...
     * @param address minter address with prefix Mx
     * @return user avatar url (if user does not exists, returns some default avatar)
     */
    public static String getUserAvatarUrlByAddress(String address) {
        if (!address.startsWith(MinterSDK.PREFIX_ADDRESS)) {
            return getUserAvatarUrl(1);
        }

        checkNotNull(address, "address required");
        return BASE_API_URL + "/api/v1/avatar/by/address/" + address;
    }

    /**
     * Singleton sdk instance
     * @return
     */
    public static MinterProfileApi getInstance() {
        return INSTANCE;
    }

    /**
     * Api service builder instance
     * @return
     */
    public ApiService.Builder getApiService() {
        return mApiService;
    }

    /**
     * Creates new gson builder with predefined type adapters used in sdk
     * @return GsonBuilder
     */
    public GsonBuilder getGsonBuilder() {
        GsonBuilder out = new GsonBuilder();
        out.registerTypeAdapter(MinterAddress.class, new MinterAddressDeserializer());
        out.registerTypeAdapter(MinterAddress.class, new MinterAddressSerializer());
        out.registerTypeAdapter(BigInteger.class, new BigIntegerDeserializer());
        out.registerTypeAdapter(BytesData.class, new BytesDataDeserializer());
        out.registerTypeAdapter(EncryptedString.class, new EncryptedStringDeserializer());
        out.registerTypeAdapter(EncryptedString.class, new EncryptedStringSerializer());

        return out;
    }

    /**
     * Auth api repository
     * @return
     * @see ProfileAuthRepository
     * @see network.minter.profile.api.ProfileAuthEndpoint
     */
    public ProfileAuthRepository auth() {
        if (mAuthRepository == null) {
            mAuthRepository = new ProfileAuthRepository(mApiService);
        }

        return mAuthRepository;
    }

    /**
     * Info api repository
     * @return
     * @see ProfileInfoRepository
     * @see network.minter.profile.api.ProfileInfoEndpoint
     */
    public ProfileInfoRepository info() {
        if (mInfoRepository == null) {
            mInfoRepository = new ProfileInfoRepository(mApiService);
        }

        return mInfoRepository;
    }

    /**
     * Address api repository
     * @return
     * @see ProfileAddressRepository
     * @see network.minter.profile.api.ProfileAddressEndpoint
     */
    public ProfileAddressRepository address() {
        if (mAddressRepository == null) {
            mAddressRepository = new ProfileAddressRepository(mApiService);
        }

        return mAddressRepository;
    }

    /**
     * User profile api repository
     * @return
     * @see ProfileRepository
     * @see network.minter.profile.api.ProfileEndpoint
     */
    public ProfileRepository profile() {
        if (mProfileRepository == null) {
            mProfileRepository = new ProfileRepository(mApiService);
        }

        return mProfileRepository;
    }
}
