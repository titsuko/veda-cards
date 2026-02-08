rootProject.name = "backend"

include("bootstrap")
include(":feature:auth")
include(":common:security")
include(":common:exception")
include("feature:profile")
include("common:event")