import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(Config.SdkVersions.compile)

    defaultConfig {
        applicationId = "com.treplabs.ddm"
        minSdkVersion(Config.SdkVersions.min)
        targetSdkVersion(Config.SdkVersions.target)
        versionCode = Config.PublishVersion.code
        versionName = Config.PublishVersion.name
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    androidExtensions {
        isExperimental = true
    }

    signingConfigs {
        val keystorePropertiesPath = "keystore.properties"
        if (rootProject.file(keystorePropertiesPath).exists()) {
            val keystoreProperties = Properties()
            keystoreProperties.load(File(keystorePropertiesPath).inputStream())
            create("config") {
                storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        getByName("release") {
           /* isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )*/
            versionNameSuffix = "-release"
            signingConfig = signingConfigs.getByName("config")
        }
        getByName("debug") {
            versionNameSuffix = "-debug"
            signingConfig = signingConfigs.getByName("config")
        }
    }

    flavorDimensions("implementation")

    productFlavors {
        val properties =  Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        val fbDbSecrete = properties.getProperty("FB_DB_SECRETE")
        val webIdToken = properties.getProperty("FB_AUTH_WEB_ID_TOKEN")

        create("production") {
            buildConfigField("String", "API_BASE_URL", "\"https://API-creds\"")
            buildConfigField("String", "OAUTH_CLIENT_ID", "\"API-creds\"")
            buildConfigField("String", "OAUTH_CLIENT_SECRET", "\"API-creds\"")
            buildConfigField("String", "OAUTH_GRANT_TYPE", "\"API-creds\"")
            setDimension("implementation")
            versionNameSuffix = "-production"
        }
        create("staging") {
            buildConfigField("String", "FB_AUTH_REST_BASE_URL",
                "\"https://identitytoolkit.googleapis.com/\"")
            buildConfigField("String", "FB_DB_SECRETE", "\"$fbDbSecrete\"")
            buildConfigField("String", "FB_AUTH_WEB_ID_TOKEN", "\"$webIdToken\"")
            buildConfigField("String", "API_BASE_URL", "\"https://API\"")
            buildConfigField("String", "OAUTH_CLIENT_ID", "\"API\"")
            buildConfigField("String", "OAUTH_CLIENT_SECRET", "\"API\"")
            buildConfigField("String", "OAUTH_GRANT_TYPE", "\"API\"")
            setDimension("implementation")
            versionNameSuffix = "-staging"
        }
    }

    applicationVariants.all(object : Action<ApplicationVariant> {
        override fun execute(variant: ApplicationVariant) {
            variant.outputs.map { it as BaseVariantOutputImpl }
                .forEach { output ->
                    println("The variant is: ${variant.versionName}")
                    output.outputFileName = "Itoju-${variant.versionName}.apk"
                }
        }
    })
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Tests
    testImplementation(Config.Libs.Test.jUnit)
    testImplementation(Config.Libs.Test.mockito)
    androidTestImplementation(Config.Libs.Test.androidXTestRunner)
    androidTestImplementation(Config.Libs.Test.espressoCore)

    //Kotlin
    implementation(Config.Libs.Kotlin.kotlinJvm)
    implementation(Config.Libs.Kotlin.coroutinesCore)
    implementation(Config.Libs.Kotlin.coroutinesAndroid)

    // AndroidX
    implementation(Config.Libs.AndroidX.constraintLayout)
    implementation(Config.Libs.AndroidX.browser)
    implementation(Config.Libs.AndroidX.core)
    implementation(Config.Libs.AndroidX.lifeCycleExt)
    implementation(Config.Libs.AndroidX.lifeCycleReactive)
    implementation(Config.Libs.AndroidX.lifeCycleViewModel)
    implementation(Config.Libs.AndroidX.navigationFragment)
    implementation(Config.Libs.AndroidX.navigationUI)
    implementation(Config.Libs.AndroidX.recyclerView)
    implementation(Config.Libs.AndroidX.legacySupportLib)
    implementation(Config.Libs.AndroidX.viewPager2)

    //DI with Dagger
    kapt(Config.Libs.DI.daggerCompiler)
    implementation(Config.Libs.DI.dagger)
    compileOnly(Config.Libs.DI.glassFishAnnotation)

    //Network
    implementation(Config.Libs.Network.retrofit)
    implementation(Config.Libs.Network.retrofitGsonConverter)
    implementation(Config.Libs.Network.okhttpLoggingInterceptor)
    implementation(Config.Libs.Network.retrofitMock)

    //Misc
    implementation(Config.Libs.Misc.materialDesign)
    implementation(Config.Libs.Misc.materialDialogs)
    implementation(Config.Libs.Misc.timber)
    implementation(Config.Libs.Misc.countryCodePicker)
    implementation(Config.Libs.Misc.googlePlayServices)
    implementation(Config.Libs.Misc.pinView)
    implementation(Config.Libs.Misc.lottie)

    //Firebase
    implementation(Config.Libs.Firebase.auth)
    implementation(Config.Libs.Firebase.googlePlayServices)
    implementation(Config.Libs.Firebase.fireStore)

    //Rx
    implementation(Config.Libs.Reactive.rxJava)
    implementation(Config.Libs.Reactive.rxAndroid)
    implementation(Config.Libs.Reactive.rxKotlin)
    implementation(Config.Libs.Reactive.rxRetrofitAdapter)
}

apply(from = "../spotless.gradle")
