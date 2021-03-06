Minter Android Profile API SDK
==============================
[![Download](https://api.bintray.com/packages/minterteam/android/minter-android-profile-testnet/images/download.svg) ](https://bintray.com/minterteam/android/minter-android-profile-testnet/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE.txt)


Minter user profile (https://testnet.console.minter.network) sdk library
------------------------------------------------------------------------
**DEPRECATED SDK**

## Setup

Gradle
root build.gradle
```groovy
allprojects {
    repositories {
       // ... some repos
        maven { url "https://dl.bintray.com/minterteam/android" }
    }
}
```

project build.gradle
```groovy

ext {
    minterProfileSDK = "0.2.6"
}

dependencies {
    // for testnet use suffix "-testnet"
    implementation "network.minter.android:minter-android-profile-testnet:${minterProfileSDK}"

    // for main net
    implementation "network.minter.android:minter-android-profile:${minterProfileSDK}"
}
```

## Basic Usage
### Initialize it
```java

// some api requires auth token to get personal data
// this callback will called only when it required
//
// so save authorization bearer token after login/register in your secret storage to use it later
MinterProfileApi.initialize(new CallbackProvider<String>{
    @Override
    public String get() {
        return MySecretStorage.getSavedAuthToken();
    }
});
```

### Usage
SDK uses retrofit http client [see](https://square.github.io/retrofit/)
```java
// get auth (or other) repository
ProfileAuthRepository profileRepo = MinterProfileApi.getInstance().auth();

// login
LoginData loginData = new LoginData();
// ... fill data

// and send request
profileRepo.login(loginData).enqueue(new Callback<ProfileResult<User>>() {
    @Override
    public void onResponse(Call<ProfileResult<User>> call, Response<ProfileResult<User>> response) {
        // do something with response
    }

    @Override
    public void onFailure(Call<ProfileResult<User>> call, Throwable t) {
        // handle error
    }
});
```

For more examples, see our [wallet app](https://github.com/MinterTeam/minter-android-wallet)


## Docs
TODO (javadocs available)

## Build
TODO

## Tests
TODO

## Changelog

See [Release notes](RELEASE.md)


## License

This software is released under the [MIT](LICENSE.txt) License.

© 2018 MinterTeam <edward.vstock@gmail.com>, All rights reserved.