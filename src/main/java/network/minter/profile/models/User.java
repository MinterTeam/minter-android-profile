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

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Locale;

import network.minter.profile.MinterProfileApi;

/**
 * minter-android-profile. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
@Parcel
public class User {

    public Token token;
    @SerializedName("user") public Data data;

    public User(String authToken) {
        token = new Token();
        token.tokenType = "advanced";
        token.expiresIn = ((System.currentTimeMillis() / 1000) + 315569260);
        token.accessToken = authToken;
        token.refreshToken = null;

        data = new Data();
    }

    User() {
    }

    public Data getData() {
        if (data == null) {
            data = new Data();
        }

        return data;
    }

    @Parcel
    public static class Token {
        public String tokenType;
        public long expiresIn;
        public String accessToken;
        public String refreshToken;
    }

    @Parcel
    public static class Data {
        public long id;
        public String username;
        public String name;
        public String email;
        public String phone;
        public String language;
        public Avatar avatar;
	    public ProfileAddressData mainAddress;

        public boolean hasAvatar() {
            return avatar != null;
        }

        public String getLanguage() {
            if (language == null) {
                language = "en_US";
            }
            return language;
        }

        public String getLanguageDisplay() {
            return new Locale(getLanguage()).getDisplayLanguage();
        }

        public Avatar getAvatar() {
            if (avatar == null) {
                avatar = new Avatar();
            }

            avatar.id = this.id;

            return avatar;
        }
    }

    @Parcel
    public static class Avatar {
        public String src;
        public String description;
        long id = -1;

        public Avatar() {
        }

        public String getUrl() {
            if (id < 0) {
	            return MinterProfileApi.getUserAvatarUrl(1);
            }

            if (src == null && id > 0) {
	            return MinterProfileApi.getUserAvatarUrl(id);
            }

            return src;
        }
    }
}
