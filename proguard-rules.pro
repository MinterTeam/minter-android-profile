-dontobfuscate

-keep public class network.minter.my.** { *; }
-keep public class network.minter.my.MyMinterApi { *; }


# Parceler library
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }