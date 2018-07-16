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

package network.minter.profile.repo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import network.minter.mintercore.MinterSDK;
import network.minter.mintercore.crypto.MinterAddress;
import network.minter.mintercore.internal.api.ApiService;
import network.minter.mintercore.internal.data.DataRepository;
import network.minter.profile.api.ProfileInfoEndpoint;
import network.minter.profile.models.AddressInfoResult;
import network.minter.profile.models.ProfileResult;
import network.minter.profile.models.User;
import retrofit2.Call;

import static network.minter.mintercore.internal.common.Preconditions.checkArgument;
import static network.minter.mintercore.internal.common.Preconditions.checkNotNull;

/**
 * minter-android-profile. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class ProfileInfoRepository extends DataRepository<ProfileInfoEndpoint> {
	public ProfileInfoRepository(@NonNull ApiService.Builder apiBuilder) {
        super(apiBuilder);
    }

	public Call<ProfileResult<AddressInfoResult>> getAddressWithUserInfo(String address) {
        return getInstantService().getAddressWithUserInfo(address);
    }

	public Call<ProfileResult<AddressInfoResult>> getAddressWithUserInfo(MinterAddress address) {
        return getAddressWithUserInfo(address.toString());
    }

	public Call<ProfileResult<List<AddressInfoResult>>> getAddressesWithUserInfoByStrings(List<String> addresses) {
        return getInstantService().getAddressesWithUserInfo(addresses);
    }

	public Call<ProfileResult<List<AddressInfoResult>>> getAddressesWithUserInfo(List<MinterAddress> addresses) {
        final List<String> out = new ArrayList<>(addresses.size());
        for (MinterAddress address : addresses) {
            out.add(address.toString());
        }

        return getAddressesWithUserInfoByStrings(out);
    }

	public Call<ProfileResult<User.Data>> getUserInfoByUsername(String username) {
        return getInstantService().getUserInfoByUsername(username);
    }

	public Call<ProfileResult<User.Data>> getUserInfoByUser(User user) {
        return getUserInfoByUser(user.data);
    }

	public Call<ProfileResult<User.Data>> getUserInfoByUser(User.Data userData) {
        return getUserInfoByUsername(userData.username);
    }

    /**
     * Find address by indistinct recipient value: username or email address
     *
     * @param input Can be address with prefix 'Mx', username with prefix '@' or email address
     * @return
     */
    public Call<ProfileResult<AddressInfoResult>> findAddressInfoByInput(@NonNull String input) {
        checkNotNull(input, "Input can't be null");
        checkArgument(!input.isEmpty(), "Input can't be empty string");
        checkArgument(input.length() >= 2, "Input length must have length more than 2 characters");

        if (input.substring(0, 2).equals(MinterSDK.PREFIX_ADDRESS) && input.length() == 42) {
            // searching data by address
            return getAddressWithUserInfo(input);
        } else if (input.substring(0, 1).equals("@")) {
            // searching data by username
            return getInstantService().findAddressByUsername(input.substring(1));
        } else {
            // searching by email
            return getInstantService().findAddressByEmail(input);
        }
    }

    @NonNull
    @Override
    protected Class<ProfileInfoEndpoint> getServiceClass() {
	    return ProfileInfoEndpoint.class;
    }

}
