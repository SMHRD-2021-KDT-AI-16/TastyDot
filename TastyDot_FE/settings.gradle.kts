pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(){
            setUrl("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
        }

    }
}

rootProject.name = "project3"
include(":app")
