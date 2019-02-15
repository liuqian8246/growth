# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in DefaultEntity:\Android-Studio\SDK/tools/proguard/proguard-rules.pro
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#---------------------------------1.实体类---------------------------------


#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.** { *; }

#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn org.robovm.**
-keep class org.robovm.** { *; }

#okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-keep class okhttp3.** { *;}
-keep class okio.** { *;}
-dontwarn sun.security.**
-keep class sun.security.** { *;}
-dontwarn okio.**
-dontwarn okhttp3.**

#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#rxjava
-dontwarn rx.**
-keep class rx.** { *; }

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#alipay
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#-keep class com.alipay.mobilesecuritysdk.*
#-keep class com.ut.*
#
#-dontwarn android.net.**
#-keep class android.net.** { *; }


#gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#httpclient (org.apache.http.legacy.jar)
-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-dontwarn org.apache.http.protocol.**
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.org.**{*;}
-keep class org.apache.harmony.**{*;}

#LRecyclerview_library
-dontwarn com.github.jdsjlzx.**
-keep class com.github.jdsjlzx.**{*;}
#rxlifecycle
-dontwarn com.trello.rxlifecycle.**
-keep class com.trello.rxlifecycle.**{*;}
#rxbinding
-dontwarn com.jakewharton.rxbinding.**
-keep class com.jakewharton.rxbinding.**{*;}
#rxpermissions
-dontwarn com.tbruyelle.rxpermissions.**
-keep class com.tbruyelle.rxpermissions.**{*;}
#photoview
-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.**{*;}
#kjframe
-dontwarn org.kymjs.kjframe.**
-keep class org.kymjs.kjframe.**{*;}
#hamcrestcore
-dontwarn org.hamcrest.**
-keep class org.hamcrest.**{*;}
#convertgson
-dontwarn retrofit.converter.**
-keep class retrofit.converter.**{*;}
#adapter-rxjava
-dontwarn retrofit.adapter.**
-keep class retrofit.adapter.**{*;}
#circleimageview
-dontwarn de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.**{*;}
#base64decoder
-dontwarn Decoder.**
-keep class Decoder.**{*;}
#sweetalert
-dontwarn cn.pedant.SweetAlert.**
-keep class cn.pedant.SweetAlert.**{*;}
-dontwarn com.pnikosis.materialishprogress.**
-keep class com.pnikosis.materialishprogress.**{*;}

#verticaltablayout
-dontwarn q.rorbin.verticaltablayout.**
-keep class q.rorbin.verticaltablayout.**{*;}

#materialdialog
-dontwarn me.drakeet.materialdialog.**
-keep class me.drakeet.materialdialog.**{*;}

#shorycutbadge
-dontwarn me.leolin.shortcutbadger.**
-keep class me.leolin.shortcutbadger.**{*;}

#dialogplugs
-dontwarn com.orhanobut.dialogplus.**
-keep class com.orhanobut.dialogplus.**{*;}

#hawk
-dontwarn com.orhanobut.hawk.**
-keep class com.orhanobut.hawk.**{*;}

#lambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#litepal
-dontwarn org.litepal.**
-keep class org.litepal.**{*;}
-keep class * extends org.litepal.crud.DataSupport { *;}

#pickerview
-dontwarn com.bigkoo.pickerview.**
-keep class com.bigkoo.pickerview.**{*;}

#arch mvvm
-dontwarn android.arch.**
-keep class android.arch.** { *; }

-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

  -keep class * extends java.lang.annotation.Annotation
  -keep public class * extends android.app.Activity
  -keep public class * extends android.app.Application
  -keep public class * extends android.app.Service
  -keep public class * extends android.content.BroadcastReceiver
  -keep public class * extends android.content.ContentProvider
  -keep public class * extends android.app.backup.BackupAgentHelper
  -keep public class * extends android.preference.Preference
  -keep public class com.android.vending.licensing.ILicensingService
  -keep public class com.tendcloud.tenddata.** { public protected *;}

  # ProGuard configurations for NetworkBench Lens
  -keep class com.networkbench.** { *; }
  -dontwarn com.networkbench.**
  -keepattributes Exceptions, Signature, InnerClasses
  # End NetworkBench Lens

  -keepattributes SourceFile,LineNumberTable

  -keepattributes *Annotation*
  -keepattributes JavascriptInterface

  -keepclasseswithmembernames class * {
      native <methods>;
  }

  -keepclasseswithmembernames class * {
      public <init>(android.content.Context, android.util.AttributeSet);
  }

  -keepclasseswithmembernames class * {
      public <init>(android.content.Context, android.util.AttributeSet, int);
  }
  -keepattributes Signature

  -keepclassmembers class * implements java.io.Serializable {
      static final long serialVersionUID;
      private static final java.io.ObjectStreamField[] serialPersistentFields;
      private void writeObject(java.io.ObjectOutputStream);
      private void readObject(java.io.ObjectInputStream);
      java.lang.Object writeReplace();
      java.lang.Object readResolve();
      public <fields>;
      }

  -keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
  }

  -keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
  }

  -dontwarn android.support.**
  -dontwarn com.alibaba.fastjson.**
  -dontwarn org.apache.http.**

  -keep class org.apache.http.** { *; }
  -keep class com.alibaba.fastjson.**  {* ;}
  -keep class org.apache.http.entity.mime.**  {* ;}
  -keep class com.android.volley.**  {* ;}
  -keep class net.iharder.**  {* ;}

  -keep class com.ppdai.** {*;}

#volley # -------------------------------------------
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }

#Annotation
-keepattributes *Annotation*
#icepick
-dontwarn icepick.**
-keep class **$$Icepick { *; }
-keepnames class * { @icepick.State *; }
-keepclasseswithmembernames class * {
@icepick.* <fields>;
}

#httpmine,httpcore
-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
########--------Retrofit + RxJava + gson--------#########
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-dontwarn sun.misc.Unsafe
-dontwarn com.octo.android.robospice.retrofit.RetrofitJackson**
-dontwarn retrofit.appengine.UrlFetchClient
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
@retrofit.http.* <methods>;
}
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit2.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn retrofit2.**
-dontwarn sun.misc.**
#SweetAlert
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
public <init>(...);
}
#other importent
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class org.apache.cordova.dialogs.** { *; }
-keep class org.apache.cordova.file.** { *; }
-keep class org.apache.cordova.filetransfer.** { *; }
-keep class org.apache.cordova.whitelist.** { *; }
-keep class com.nineoldandroids.** { *; }
-keep class com.alibaba.sdk.android.** { *; }
-keep class org.apache.http.** { *; }
-keep class com.google.com.** { *; }
-keep class cn.com.bsfit.** { *; }
-keep class com.treefinance.sdk.model.** { *; }
-keep class cn.pedant.SweetAlert.** { *; }
-keep class com.treefinance.** { *; }
-keep class com.datatrees.gongfudai.** { *; }

# cn.fraudmetrix.android.sdk.**
-dontwarn cn.fraudmetrix.android.sdk.**
-keep class cn.fraudmetrix.android.sdk.**
-keepclassmembers class cn.fraudmetrix.android.sdk.** { *; }
-keep enum cn.fraudmetrix.android.sdk.**
-keepclassmembers enum cn.fraudmetrix.android.sdk.** { *; }
-keep interface  cn.fraudmetrix.android.sdk.**
-keepclassmembers interface  cn.fraudmetrix.android.sdk.** { *; }
# cn.fraudmetrix.android.sdk.**

# com.tendcloud.tenddata.kpl.**
-dontwarn com.tendcloud.tenddata.kpl.**
-keep class com.tendcloud.tenddata.kpl.**
-keepclassmembers class com.tendcloud.tenddata.kpl.** { *; }
-keep enum com.tendcloud.tenddata.kpl.**
-keepclassmembers enum com.tendcloud.tenddata.kpl.** { *; }
-keep interface  com.tendcloud.tenddata.kpl.**
-keepclassmembers interface  com.tendcloud.tenddata.kpl.** { *; }
# com.tendcloud.tenddata.kpl.**

# net.sqlcipher.**
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.**
-keepclassmembers class net.sqlcipher.** { *; }
-keep enum net.sqlcipher.**
-keepclassmembers enum net.sqlcipher.** { *; }
-keep interface  net.sqlcipher.**
-keepclassmembers interface  net.sqlcipher.** { *; }
# net.sqlcipher.**

# com.jcraft.jzlib.**
-dontwarn com.jcraft.jzlib.**
-keep class com.jcraft.jzlib.**
-keepclassmembers class com.jcraft.jzlib.** { *; }
-keep enum com.jcraft.jzlib.**
-keepclassmembers enum com.jcraft.jzlib.** { *; }
-keep interface  com.jcraft.jzlib.**
-keepclassmembers interface  com.jcraft.jzlib.** { *; }
# com.jcraft.jzlib.**

# org.apache.commons.codec.**
-dontwarn org.apache.commons.codec.**
-keep class org.apache.commons.codec.**
-keepclassmembers class org.apache.commons.codec.** { *; }
-keep enum org.apache.commons.codec.**
-keepclassmembers enum org.apache.commons.codec.** { *; }
-keep interface  org.apache.commons.codec.**
-keepclassmembers interface  org.apache.commons.codec.** { *; }
# org.apache.commons.codec.**

# Decoder.**
-dontwarn Decoder.**
-keep class Decoder.**
-keepclassmembers class Decoder.** { *; }
-keep enum Decoder.**
-keepclassmembers enum Decoder.** { *; }
-keep interface Decoder.**
-keepclassmembers interface  Decoder.** { *; }
# Decoder.**

# org.apache.http
-dontwarn org.apache.http.**
-keep class org.apache.http.**
-keepclassmembers class org.apache.http.** { *; }
-keep enum org.apache.http.**
-keepclassmembers enum org.apache.http.** { *; }
-keep interface org.apache.http.**
-keepclassmembers interface  org.apache.http.** { *; }
# org.apache.http.**


#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#-ignorewarnings
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep public class * extends android.os.IInterface

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

-keepclasseswithmembernames class * { # 保持 native 方法不被混淆
 native <methods>;
}

-keepclasseswithmembers class * { # 保持自定义控件类不被混淆
 public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
 public void *(android.view.View);
}

-keepclassmembers enum * { # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}

#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}

















