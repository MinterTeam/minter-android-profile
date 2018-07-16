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

package network.minter.profile.api;

import java.util.List;

import network.minter.profile.models.ProfileAddressData;
import network.minter.profile.models.ProfileResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * minter-android-profile. Май 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public interface ProfileAddressEndpoint {

    /**
     * Get current user address list
     * @return
     */
    @GET("/api/v1/addresses")
    Call<ProfileResult<List<ProfileAddressData>>> getAddresses();

    /**
     * Get current user address list (with paging)
     * Default limit: 20 by page
     *
     * @return
     */
    @GET("/api/v1/addresses")
    Call<ProfileResult<List<ProfileAddressData>>> getAddresses(@Query("page") int page);

    /**
     * Get current user address list with encrypted data
     * @return
     */
    @GET("/api/v1/addresses/encrypted")
    Call<ProfileResult<List<ProfileAddressData>>> getAddressesWithEncrypted();

    /**
     * Get single address information
     * @param addressId
     * @return
     */
    @GET("/api/v1/addresses/{id}")
    Call<ProfileResult<ProfileAddressData>> getAddress(@Path("id") int addressId);

    /**
     * Get single address information with encrypted data
     * @param addressId
     * @return
     */
    @GET("/api/v1/addresses/{id}/encrypted")
    Call<ProfileResult<ProfileAddressData>> getAddressWithEncrypted(@Path("id") int addressId);

    /**
     * Create new address
     * @see ProfileAddressData
     * @param data
     * @return
     */
    @POST("/api/v1/addresses")
    Call<ProfileResult<Object>> addAddress(@Body ProfileAddressData data);

    /**
     * Update existent address
     * @param addressId
     * @see ProfileAddressData#id
     * @param data
     * @see ProfileAddressData
     * @return
     */
    @PUT("/api/v1/addresses/{id}")
    Call<ProfileResult<Object>> updateAddress(@Path("id") String addressId, @Body ProfileAddressData data);

    /**
     * Delete existent address by it id
     * @param addressId
     * @return
     */
    @DELETE("/api/v1/addresses/{id}")
    Call<ProfileResult<Object>> deleteAddress(@Path("id") String addressId);
}
