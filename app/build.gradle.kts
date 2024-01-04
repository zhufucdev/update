plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    signing
    `maven-publish`
}

android {
    namespace = "com.zhufucdev.update"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.ktx.coroutine)
    implementation(libs.ktx.serialization)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.serialization.ktx.json)

    implementation(libs.steve.api)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

val publicationName = "maven"

publishing {
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>(publicationName) {
                afterEvaluate { from(components["release"]) }

                pom {
                    name = "com.zhufucdev.update:android"
                    description = "Steve's update SDK, Compose Android implementation"
                    url = "https://github.com/zhufucdev/sdk"
                    licenses {
                        license {
                            name = "Apache 2.0"
                            url = "https://www.apache.org/licenses/LICENSE-2.0"
                        }
                    }
                    developers {
                        developer {
                            id = "zhufucdev"
                            name = "Steve Reed"
                            email = "29772292+zhufucdev@users.noreply.github.com"
                            organization = "zhufucdev"
                            organizationUrl = "https://zhufucdev.com"
                        }
                    }
                    scm {
                        connection = "scm:git@github.com:zhufucdev/sdk.git"
                        developerConnection = "scm:git@github.com:zhufucdev/sdk.git"
                        url = "https://github.com/zhufucdev/sdk"
                    }
                }
            }
        }
    }

    signing {
        sign(publications.getAt(publicationName))
    }
}

