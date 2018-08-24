Minter Android Profile API SDK
==============================
[ ![Download](https://api.bintray.com/packages/minterteam/android/minter-android-profile-testnet/images/download.svg?version=0.1.0) ](https://bintray.com/minterteam/android/minter-android-profile-testnet/0.1.0/link)


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

## Initialize it
```java

MinterProfileApi.initialize();
```

## Docs
TODO (javadocs available for now)

# Build
TODO