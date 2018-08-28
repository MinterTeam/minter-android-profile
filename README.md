Minter Android Profile API SDK
==============================
[![Download](https://api.bintray.com/packages/minterteam/android/minter-android-profile-testnet/images/download.svg?version=0.1.0) ](https://bintray.com/minterteam/android/minter-android-profile-testnet/0.1.0/link)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Minter user profile (my.minter.network console) sdk library
-----------------------------------------------------------

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
    minterSdkVersion = "${latest_git_tag}"
}

dependencies {
    // for testnet use suffix "-testnet"
    implementation "network.minter.android:minter-android-explorer-testnet:${minterSdkVersion}"

    // for main net
    implementation "network.minter.android:minter-android-explorer:${minterSdkVersion}"
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
```java
// get auth (or other) repository
ProfileAuthRepository profileRepo = MinterProfileApi.getInstance().auth();

// login
LoginData loginData = new LoginData();
// ... fill data

// and send (retrofit https://square.github.io/retrofit/) request
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

# Build
TODO