# If you have folders or files which you wish to persist in between containers,
# you must set them up as volumes in `docker-compose.yml` and use the command
# `docker-compose up` to launch the container.

# Note: You will need to run the `build` gradle task at least once on the server
# before you can run this docker config properly. You will also need to run said
# task whenever you want the docker image to contain the latest changes in your
# code.

# Use kotlin image to run the server.
FROM openjdk:21-jdk-slim

# Install unzip for extracting the distribution archive
RUN apt-get update \
    && apt-get install -y --no-install-recommends unzip \
    && rm -rf /var/lib/apt/lists/*

# Define `directory` as '/app/'
ENV directory /app/

# Set the work directory as ${directory}
WORKDIR ${directory}

# Copy distribution archive
ADD game-server/build/distributions/game-server-shadow-*.zip .

# Unzip distribution archive
RUN unzip -q -o *.zip

# Delete distribution archive after unzipping
RUN rm -fv *.zip

# Move distribution files from "game-${version}" to ${WORKDIR} ["."]
RUN cp -a game*/. ./

# Delete "game-${version}" folder as its contents have been moved
# to working directory
RUN rm -rf game*
