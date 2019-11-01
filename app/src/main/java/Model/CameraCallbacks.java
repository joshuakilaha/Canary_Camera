package Model;

import androidx.annotation.NonNull;

import java.io.File;

public interface CameraCallbacks {
    void onImageCapture(@NonNull File imageFile);

    void onCameraError(@CameraError.CameraErrorCodes int errorCode);
}
