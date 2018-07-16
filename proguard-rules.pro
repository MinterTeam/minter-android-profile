-dontobfuscate

-keep public class network.minter.profile.** { *; }
-keep public class network.minter.profile.MinterProfileApi { *; }


# Parceler library
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }