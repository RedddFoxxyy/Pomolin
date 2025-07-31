# syntax=docker/dockerfile:1
FROM ubuntu:18.04 AS build

# 1. Install dependencies
RUN apt-get update && apt-get install -y \
    curl wget git gnupg ca-certificates libfuse2 curl unzip binutils file && \
    rm -rf /var/lib/apt/lists/*

# 2. Setup Azul Zulu repo and install Zulu JDK 24
RUN curl -s https://repos.azul.com/azul-repo.key | gpg --dearmor -o /usr/share/keyrings/azul.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/azul.gpg] https://repos.azul.com/zulu/deb stable main" \
      | tee /etc/apt/sources.list.d/zulu.list && \
    apt-get update && apt-get install -y zulu24-jdk && \
    rm -rf /var/lib/apt/lists/*

# 3. Clone your repo and build
WORKDIR /opt
RUN git clone https://github.com/RedddFoxxyy/Pomolin.git
WORKDIR /opt/Pomolin
RUN chmod +x gradlew
RUN ./gradlew createReleaseDistributable

# 4. Start packaging stage
FROM ubuntu:18.04

# reinstall minimal dependencies and Zulu
RUN apt-get update && apt-get install -y wget gnupg ca-certificates libfuse2 curl binutils file && \
    curl -s https://repos.azul.com/azul-repo.key | gpg --dearmor -o /usr/share/keyrings/azul.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/azul.gpg] https://repos.azul.com/zulu/deb stable main" \
      | tee /etc/apt/sources.list.d/zulu.list && \
    apt-get update && apt-get install -y zulu24-jdk && \
    rm -rf /var/lib/apt/lists/*

# Copy built distribution
WORKDIR /app
COPY --from=build /opt/Pomolin/composeApp/build/compose/binaries/main-release ./main-release

# Setup environment variables (optional)
ENV ARTIFACT_DIR=/app/main-release
ENV APP_NAME=pomolin
ENV GRADLE_APP_DIR=$ARTIFACT_DIR/app/$APP_NAME
ENV APPDIR_NAME=${APP_NAME}.AppDir

# 5. Download AppImage tool
# 5. Download and extract AppImage tool
RUN ARCH=$(uname -m) && \
    if [ "$ARCH" = "aarch64" ]; then TOOL_ARCH="aarch64"; else TOOL_ARCH="x86_64"; fi && \
    TOOL="appimagetool-${TOOL_ARCH}.AppImage" && \
    wget -q "https://github.com/AppImage/AppImageKit/releases/download/continuous/${TOOL}" && \
    chmod +x "${TOOL}" && \
    ./"${TOOL}" --appimage-extract && \
    mv squashfs-root /usr/local/bin/appimagetool

# 6. Build AppDir structure
RUN mkdir -p $APPDIR_NAME/usr/lib && \
    mv "$GRADLE_APP_DIR" "$APPDIR_NAME/usr/lib/"

# 7. Create .desktop
RUN cat <<EOF > $APPDIR_NAME/${APP_NAME}.desktop
[Desktop Entry]
Name=pomolin
Comment=A simple Pomodoro App written in Kotlin. Focus on what matters!
Exec=${APP_NAME}
Icon=${APP_NAME}
Terminal=false
Type=Application
Categories=Utility
EOF

# 8. Copy icons
RUN mkdir -p $APPDIR_NAME/usr/share/icons/hicolor/512x512/apps && \
    cp "$APPDIR_NAME/usr/lib/$APP_NAME/lib/$APP_NAME.png" "$APPDIR_NAME/usr/share/icons/hicolor/512x512/apps/${APP_NAME}.png" && \
    cp "$APPDIR_NAME/usr/lib/$APP_NAME/lib/$APP_NAME.png" "$APPDIR_NAME/${APP_NAME}.png"

# 9. AppRun
RUN cat <<'EOF' > $APPDIR_NAME/AppRun
#!/bin/sh
exec "$APPDIR/usr/lib/pomolin/bin/pomolin" "$@"
EOF
RUN chmod +x $APPDIR_NAME/AppRun

# 10. Build AppImage
WORKDIR /app
RUN /usr/local/bin/appimagetool/AppRun $APPDIR_NAME

# 11. Move AppImage
RUN mkdir -p $ARTIFACT_DIR/appimage && \
    mv pomolin-*.AppImage $ARTIFACT_DIR/appimage/

# 12. Final image: show somewhere the artifact or export volume
CMD ["bash", "-c", "echo AppImage built at $ARTIFACT_DIR/appimage && ls $ARTIFACT_DIR/appimage"]
