rootProject.name = "shop-app"

dependencyResolutionManagement {
    repositories.mavenCentral()
}

pluginManagement {
    repositories.gradlePluginPortal()
    includeBuild("gradle/plugins")
}

//include("product-service")