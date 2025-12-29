// In your ROOT project folder: settings.gradle.kts
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        versionCatalogs{
//            create("libs"){
//                from(files("./gradle/libs.version.toml"))
//            }
//        }
        google()
        mavenCentral()
    }
}

rootProject.name = "monapplication"
include(":app")

