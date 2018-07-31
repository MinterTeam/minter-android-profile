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

package network.minter.profile.repo;

import android.support.annotation.NonNull;

import java.util.List;

import network.minter.core.internal.api.ApiService;
import network.minter.core.internal.data.DataRepository;
import network.minter.profile.api.ProfileAddressEndpoint;
import network.minter.profile.models.ProfileAddressData;
import network.minter.profile.models.ProfileResult;
import retrofit2.Call;

/**
 * minter-android-profile. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class ProfileAddressRepository extends DataRepository<ProfileAddressEndpoint> implements DataRepository.Configurator {
	public ProfileAddressRepository(@NonNull ApiService.Builder apiBuilder) {
        super(apiBuilder);
    }

	public Call<ProfileResult<List<ProfileAddressData>>> getAddresses() {
        return getInstantService(this).getAddresses();
    }

	public Call<ProfileResult<List<ProfileAddressData>>> getAddresses(int page) {
        return getInstantService(this).getAddresses(page);
    }

	public Call<ProfileResult<List<ProfileAddressData>>> getAddressesWithEncrypted() {
        return getInstantService(this).getAddressesWithEncrypted();
    }

	public Call<ProfileResult<Object>> delete(String addressId) {
        return getInstantService(this).deleteAddress(addressId);
    }

	public Call<ProfileResult<Object>> delete(ProfileAddressData address) {
        return delete(address.id);
    }

	public Call<ProfileResult<Object>> addAddress(ProfileAddressData data) {
        return getInstantService(this).addAddress(data);
    }

	public Call<ProfileResult<Object>> updateAddress(ProfileAddressData addressData) {
        return getInstantService(this).updateAddress(addressData.id, addressData);
    }

	public Call<ProfileResult<Object>> setAddressMain(boolean isMain, ProfileAddressData data) {
        data.isMain = isMain;
        return getInstantService(this).updateAddress(data.id, data);
    }

    @Override
    public void configure(ApiService.Builder api) {
        api.authRequired(true);
    }

    @NonNull
    @Override
    protected Class<ProfileAddressEndpoint> getServiceClass() {
	    return ProfileAddressEndpoint.class;
    }

}
