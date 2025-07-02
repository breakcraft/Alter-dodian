# Change Tracker

## Completed
- Replaced debug message in DoorPlugin with proper open/close door actions
- Documented that Alter-dodian is a highly customized OSRS server and described subprojects `game-server` and `http-api`.
- Added quick local setup instructions with Gradle commands.
- Introduced a Contributing section encouraging users to open issues for feature requests.
- Added minimal Helm chart in deploy/helm/alter for Kubernetes deployment
- Documented Helm usage in README
- Added `ROADMAP.md` describing sprint goals and linked it from the README.
- Created placeholder dashboard directory at `docs/grafana/`.
- Added Prometheus metrics endpoint via Ktor MetricsService
- Created Grafana dashboard placeholder at docs/grafana/alter.json
- Inserted dependencies for Ktor and Micrometer
- Updated game.example.yml to include MetricsService
- Added Gradle wrapper scripts and updated `.gitignore` to track them
- Corrected dialogue typos in Doomsayer, Prayer Tutor and Bartender plugins
- Ensured gradle.properties ends with a newline for consistent formatting
- Documented parameters for door helpers in WorldExt
- Documented lumbridge plugin restoration commit at [docs/history/e66e39e6.md](docs/history/e66e39e6.md)
- Clarified warning notes in `DoomsayerPlugin.kt` and removed obsolete TODO block
- Tweaked punctuation for Doomsayer's dialogue and confirmed wording in Prayer Tutor and Bartender plugins
## TODO
- Expand Helm values for advanced configuration

## Outstanding
- Implement structured logging with kotlin-logging
- Setup persistent data layer with PostgreSQL

## Planned Enhancements
- Add GitHub Actions CI matrix
- Implement plugin hot-reload
- Clarified Helm installation step
