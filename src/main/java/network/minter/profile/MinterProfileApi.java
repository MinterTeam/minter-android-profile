/*
 * Copyright (C) by MinterTeam. 2018
 * @link https://github.com/MinterTeam
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

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.math.BigInteger;

import network.minter.mintercore.crypto.BytesData;
import network.minter.mintercore.crypto.EncryptedString;
import network.minter.mintercore.crypto.MinterAddress;
import network.minter.mintercore.internal.api.ApiService;
import network.minter.mintercore.internal.api.converters.BigIntegerDeserializer;
import network.minter.mintercore.internal.api.converters.BytesDataDeserializer;
import network.minter.mintercore.internal.api.converters.EncryptedStringDeserializer;
import network.minter.mintercore.internal.api.converters.EncryptedStringSerializer;
import network.minter.mintercore.internal.api.converters.MinterAddressDeserializer;
import network.minter.mintercore.internal.api.converters.MinterAddressSerializer;
import network.minter.mintercore.internal.api.converters.UriDeserializer;
import network.minter.profile.repo.ProfileAddressRepository;
import network.minter.profile.repo.ProfileAuthRepository;
import network.minter.profile.repo.ProfileInfoRepository;
import network.minter.profile.repo.ProfileRepository;
import okhttp3.logging.HttpLoggingInterceptor;

import static network.minter.mintercore.internal.common.Preconditions.checkArgument;
import static network.minter.mintercore.internal.common.Preconditions.checkNotNull;

/**
 * minter-android-profile. 2018
 *
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

	private MinterProfileApi() {
        mApiService = new ApiService.Builder(BASE_API_URL, getGsonBuilder());
        mApiService.addHeader("Content-Type", "application/json");
		mApiService.addHeader("X-Minter-Client-Name", "MinterAndroid (profile-minter)");
        mApiService.addHeader("X-Minter-Client-Version", BuildConfig.VERSION_NAME);
        mApiService.setDateFormat("yyyy-MM-dd HH:mm:ssX");
    }

    public static void initialize(boolean debug) {
        if (INSTANCE != null) {
            return;
        }

	    INSTANCE = new MinterProfileApi();
        INSTANCE.mApiService.setDebug(debug);
        INSTANCE.mApiService.setDebugRequestLevel(debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    public static String getCoinAvatarUrl(final @NonNull String coinName) {
        checkNotNull(coinName, "Coin name can't be null");
        checkArgument(coinName.length() >= 3 && coinName.length() <= 10, "Coin length must be from 3 to 10 chars");
        return BASE_API_URL + "/api/v1/avatar/by/coin/" + coinName.toUpperCase();
    }

    public static String getUserAvatarUrl(long id) {
        return getUserAvatarUrl(String.valueOf(id));
    }

    public static String getUserAvatarUrl(String id) {
        checkNotNull(id, "Id required");
        return BASE_API_URL + "/api/v1/avatar/by/user/" + id;
    }

	public static MinterProfileApi getInstance() {
        return INSTANCE;
    }

    public ApiService.Builder getApiService() {
        return mApiService;
    }

    public GsonBuilder getGsonBuilder() {
        GsonBuilder out = new GsonBuilder();
        out.registerTypeAdapter(MinterAddress.class, new MinterAddressDeserializer());
        out.registerTypeAdapter(MinterAddress.class, new MinterAddressSerializer());
        out.registerTypeAdapter(BigInteger.class, new BigIntegerDeserializer());
        out.registerTypeAdapter(BytesData.class, new BytesDataDeserializer());
        out.registerTypeAdapter(EncryptedString.class, new EncryptedStringDeserializer());
        out.registerTypeAdapter(EncryptedString.class, new EncryptedStringSerializer());
        out.registerTypeAdapter(Uri.class, new UriDeserializer());


        return out;
    }

	public ProfileAuthRepository auth() {
        if (mAuthRepository == null) {
	        mAuthRepository = new ProfileAuthRepository(mApiService);
        }

        return mAuthRepository;
    }

	public ProfileInfoRepository info() {
        if (mInfoRepository == null) {
	        mInfoRepository = new ProfileInfoRepository(mApiService);
        }

        return mInfoRepository;
    }

	public ProfileAddressRepository address() {
        if (mAddressRepository == null) {
	        mAddressRepository = new ProfileAddressRepository(mApiService);
        }

        return mAddressRepository;
    }

	public ProfileRepository profile() {
        if (mProfileRepository == null) {
	        mProfileRepository = new ProfileRepository(mApiService);
        }

        return mProfileRepository;
    }
}
