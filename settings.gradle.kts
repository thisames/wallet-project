pluginManagement {
    repositories {
        flatDir {
            dirs("libs")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven {
            url = uri("https://jitpack.io")
        } // Repositório JitPack para projetos hospedados no GitHub
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        flatDir {
            dirs("libs")
        }
        maven {
            url = uri("https://jitpack.io")
        } // Repositório JitPack para projetos hospedados no GitHub
        mavenCentral()
    }
}

rootProject.name = "wallet-project"
include(":app")
