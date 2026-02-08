rootProject.name = "backend"

include(
    "bootstrap",
    ":common:event",
    ":common:security",
    ":common:exception",
    ":feature:profile",
    ":feature:auth",
    ":feature:card"
)