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

import java.util.Map;

import network.minter.core.internal.api.ApiService;
import network.minter.core.internal.data.DataRepository;
import network.minter.core.internal.helpers.CollectionsHelper;
import network.minter.profile.api.ProfileEndpoint;
import network.minter.profile.models.PasswordChangeRequest;
import network.minter.profile.models.ProfileRequestResult;
import network.minter.profile.models.ProfileResult;
import network.minter.profile.models.User;
import retrofit2.Call;

import static network.minter.core.internal.common.Preconditions.checkNotNull;

/**
 * minter-android-profile. 2018
 * Private user data api repository
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class ProfileRepository extends DataRepository<ProfileEndpoint> implements DataRepository.Configurator {
	public ProfileRepository(@NonNull ApiService.Builder apiBuilder) {
        super(apiBuilder);
    }

    /**
     * Getting user profile from web console
     * @return
     */
    public Call<ProfileResult<User.Data>> getProfile() {
        return getInstantService().getProfile();
    }

    /**
     * Updating user profile by profile data
     * @param data
     * @return
     */
    public Call<ProfileResult<ProfileRequestResult>> updateProfile(@NonNull User.Data data) {
        checkNotNull(data);
        return getInstantService(this).updateProfile(data);
    }

    /**
     * Updating user profile field by name and value
     * @param field field name (email, username etc)
     * @param value string value
     * @return
     */
    public Call<ProfileResult<ProfileRequestResult>> updateField(String field, String value) {
        Map<String, String> data = CollectionsHelper.asMap(field, value);
        return getInstantService(this).updateProfile(data);
    }

    /**
     * Upload base64 encoded avatar
     * @param b64 base64 encoded string
     * @return
     * @see ProfileEndpoint#updateAvatarBase64(String)
     */
    public Call<ProfileResult<User.Avatar>> updateAvatar(String b64) {
        return getInstantService(this).updateAvatarBase64(b64);
    }

    /**
     * Update user password using complex transaction (changing password leads re-encrypting whole encrypted data)
     * @param data model to update
     * @return
     */
    public Call<ProfileResult<Object>> changePassword(PasswordChangeRequest data) {
        return getInstantService(this).changePassword(data);
    }

    @Override
    public void configure(ApiService.Builder api) {
        api.authRequired();
    }

    @NonNull
    @Override
    protected Class<ProfileEndpoint> getServiceClass() {
	    return ProfileEndpoint.class;
    }
}
