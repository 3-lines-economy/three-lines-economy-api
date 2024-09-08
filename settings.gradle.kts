plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "tle"

include("tle:tle-application")
findProject(":tle:tle-application")?.name = "tle-application"
include("tle:tle-domain")
findProject(":tle:tle-domain")?.name = "tle-domain"
include("tle:tle-storage")
findProject(":tle:tle-storage")?.name = "tle-storage"
