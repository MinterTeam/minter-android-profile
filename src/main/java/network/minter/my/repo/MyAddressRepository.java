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

package network.minter.my.repo;

import android.support.annotation.NonNull;

import java.util.List;

import network.minter.mintercore.internal.api.ApiService;
import network.minter.mintercore.internal.data.DataRepository;
import network.minter.my.api.MyAddressEndpoint;
import network.minter.my.models.MyAddressData;
import network.minter.my.models.MyResult;
import retrofit2.Call;

/**
 * minter-android-myminter. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class MyAddressRepository extends DataRepository<MyAddressEndpoint> implements DataRepository.Configurator {
    public MyAddressRepository(@NonNull ApiService.Builder apiBuilder) {
        super(apiBuilder);
    }

    public Call<MyResult<List<MyAddressData>>> getAddresses() {
        return getInstantService(this).getAddresses();
    }

    public Call<MyResult<List<MyAddressData>>> getAddresses(int page) {
        return getInstantService(this).getAddresses(page);
    }

    public Call<MyResult<List<MyAddressData>>> getAddressesWithEncrypted() {
        return getInstantService(this).getAddressesWithEncrypted();
    }

    public Call<MyResult<Object>> delete(String addressId) {
        return getInstantService(this).deleteAddress(addressId);
    }

    public Call<MyResult<Object>> delete(MyAddressData address) {
        return delete(address.id);
    }

    public Call<MyResult<Object>> addAddress(MyAddressData data) {
        return getInstantService(this).addAddress(data);
    }

    public Call<MyResult<Object>> updateAddress(MyAddressData addressData) {
        return getInstantService(this).updateAddress(addressData.id, addressData);
    }

    public Call<MyResult<Object>> setAddressMain(boolean isMain, MyAddressData data) {
        data.isMain = isMain;
        return getInstantService(this).updateAddress(data.id, data);
    }

    @Override
    public void configure(ApiService.Builder api) {
        api.authRequired(true);
    }

    @NonNull
    @Override
    protected Class<MyAddressEndpoint> getServiceClass() {
        return MyAddressEndpoint.class;
    }

}
