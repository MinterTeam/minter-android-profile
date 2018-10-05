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

import javax.annotation.Nonnull;

import network.minter.core.internal.api.ApiService;
import network.minter.core.internal.data.DataRepository;
import network.minter.profile.api.ProfileAuthEndpoint;
import network.minter.profile.models.LoginData;
import network.minter.profile.models.ProfileRequestResult;
import network.minter.profile.models.ProfileResult;
import network.minter.profile.models.RegisterData;
import network.minter.profile.models.User;
import network.minter.profile.models.UsernameData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * minter-android-profile. 2018
 * User authentication api repository
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class ProfileAuthRepository extends DataRepository<ProfileAuthEndpoint> implements DataRepository.Configurator {
    public ProfileAuthRepository(@Nonnull ApiService.Builder apiBuilder) {
        super(apiBuilder);
    }

    @Override
    public void configure(ApiService.Builder api) {
        api.authRequired(true);
    }

    @Nonnull
    @Override
    protected Class<ProfileAuthEndpoint> getServiceClass() {
	    return ProfileAuthEndpoint.class;
    }

    /**
     * Authorize user by username and password
     * @param loginData login data model
     * @return
     */
    public Call<ProfileResult<User>> login(LoginData loginData) {
        return getInstantService(cfg -> cfg.authRequired(false)).login(loginData);
    }

    /**
     * Register user
     * @param registerData
     * @return
     */
    public Call<ProfileResult<ProfileRequestResult>> register(RegisterData registerData) {
        login(null).enqueue(new Callback<ProfileResult<User>>() {
            @Override
            public void onResponse(Call<ProfileResult<User>> call, Response<ProfileResult<User>> response) {

            }

            @Override
            public void onFailure(Call<ProfileResult<User>> call, Throwable t) {

            }
        });
        return getInstantService(this).register(registerData);
    }

    /**
     * Check availability for given username
     * @param username string WITHOUT prefix "@"
     * @return
     */
    public Call<ProfileResult<UsernameData>> checkUsernameAvailability(String username) {
        return getInstantService(this).checkUsernameAvailability(username);
    }
}
