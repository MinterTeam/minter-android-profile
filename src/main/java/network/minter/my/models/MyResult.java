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

package network.minter.my.models;

import org.parceler.Parcel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * minter-android-myminter. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class MyResult<Result> {
    public Result data;
    public Error error;
    public Meta meta;
    public Links links;

    public boolean isSuccess() {
        return error == null;
    }

    public Links getLinks() {
        if (links == null) {
            links = new Links();
        }

        return links;
    }

    public Meta getMeta() {
        if (meta == null) {
            meta = new Meta();
        }

        return meta;
    }

    public Error getError() {
        if (error == null) {
            error = new Error();
        }

        return error;
    }

    @Parcel
    public static class Meta {
        public int total;
        public int count;
        public int perPage;
        public int currentPage;
        public int firstPage;
        public int lastPage;
    }

    @Parcel
    public static class Links {
        public String prev;
        public String next;
        public String first;
        public String last;
    }

    @Parcel
    public static class Error {
        public String code;
        public String message;

        /**
         * for example, validation errors:
         * email: {
         * "invalid email format",
         * "can't be empty"
         * "et cetera"
         * },
         * "some": {
         * "another field error"
         * }
         */
        public Map<String, List<String>> data;

        public Map<String, List<String>> getData() {
            if (data == null) {
                return Collections.emptyMap();
            }

            return data;
        }
    }
}
