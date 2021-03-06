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

package network.minter.profile.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import network.minter.core.crypto.EncryptedString;
import network.minter.core.crypto.HashUtil;

import static network.minter.core.internal.common.Preconditions.checkNotNull;

/**
 * minter-android-profile. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
@Parcel
public class PasswordChangeRequest {

    public String newPassword;
    public List<EncryptedData> addressesEncryptedData;

    public void setRawPassword(String password) {
        checkNotNull(password);
        newPassword = HashUtil.sha256HexDouble(password);
    }

	public void addEncrypted(ProfileAddressData data) {
        if (addressesEncryptedData == null) {
            addressesEncryptedData = new ArrayList<>();
        }

        final EncryptedData d = new EncryptedData();
        d.id = data.id;
        d.encrypted = data.encrypted;
        addressesEncryptedData.add(d);
    }

	public void addEncrypted(List<ProfileAddressData> items) {
		for (ProfileAddressData d : items) {
            addEncrypted(d);
        }
    }

    @Parcel
    public static final class EncryptedData {
        public String id;
        public EncryptedString encrypted;
    }


}
