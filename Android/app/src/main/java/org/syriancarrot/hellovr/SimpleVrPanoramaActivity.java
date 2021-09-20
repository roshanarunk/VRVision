

package org.syriancarrot.hellovr;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.GvrViewerParams;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.R.attr.key;
import static android.os.Build.VERSION_CODES.M;
import static android.view.KeyEvent.KEYCODE_BUTTON_B;
import static android.view.KeyEvent.KEYCODE_BUTTON_X;
import static android.view.KeyEvent.KEYCODE_BUTTON_Y;


public class SimpleVrPanoramaActivity extends GvrActivity implements GvrView.StereoRenderer, SurfaceTexture.OnFrameAvailableListener {

  private static final String TAG = "VRCamMtMMainAc";
  private static final int GL_TEXTURE_EXTERNAL_OES = 0x8D65;
  private static Camera camera = null;
    Camera.Parameters params;

    static int zoom = 0;
    int sswitch = 0;


  private FloatBuffer vertexBuffer, textureVerticesBuffer, vertexBuffer2;
  private ShortBuffer drawListBuffer, buf2;
  private int mProgram;
    private int iProgram;
  private int mPositionHandle, mPositionHandle2;
  private int mColorHandle;
  private int mTextureCoordHandle;
    private int mSizeHandle;
    private int mBrightnessHandle;
    private int mPos_xHandle;
    private int mPos_yHandle;
    private int mDif_xHandle;
    private int mDif_yHandle;

  // number of coordinates per vertex in this array
  static final int COORDS_PER_VERTEX = 2;
   static float squareVertices[] = { // in counterclockwise order:
           -1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 0.left - mid
           1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 1. right - mid
           -1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 2. left - top
           1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 3. right - top

   };

    static float squareVertices1[] = { // in counterclockwise order:
            -0.5f, -0.5f,   // 0.left - mid
            0.5f, -0.5f,   // 1. right - mid
            -0.5f, 0.5f,   // 2. left - top
            0.5f, 0.5f,   // 3. right - top

    };


  private short drawOrder[] = {0, 2, 1, 1, 2, 3}; // order to draw vertices
  private short drawOrder2[] = {2, 0, 3, 3, 0, 1}; // order to draw vertices

  static float textureVertices[] = {
          0.0f, 1.0f,  // A. left-bottom
          1.0f, 1.0f,  // B. right-bottom
          0.0f, 0.0f,  // C. left-top
          1.0f, 0.0f   // D. right-top
  };

  private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

  private ByteBuffer indexBuffer;    // Buffer for index-array

  private int texture;
  private CardboardOverlayView mOverlayView;
  private GvrView cardboardView;
  private SurfaceTexture surface;
  private float[] mView;
  private float[] mCamera;
    Uri viewer = Uri.parse("http://google.com/cardboard/cfg?p=CgdBdXJhIFZSEgpBdXJhIFZSIHYyHQrXIz0lj8J1PSoQAABIQgAASEIAAEhCAABIQlgBNSlcDz06CAAAAAAAAAAAUAFgAQ");
    public int next;
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        Camera.Parameters parameters = camera.getParameters();
        final int maxZoomLevel = parameters.getMaxZoom();
        if(event.getAction()==KeyEvent.ACTION_DOWN) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_X:
                if( next == 0) {
                    if (zoom < maxZoomLevel) {
                        zoom++;
                        //mCamera.startSmoothZoom(currentZoomLevel);
                        parameters.setZoom(zoom);
                        camera.setParameters(parameters);
                        mOverlayView.show3DToast("Zoom : " + zoom+ "/" + maxZoomLevel);
                    }
                }
                else if (next == 1){
                    if( MainActivity.fov < 1) {
                        MainActivity.fov = MainActivity.fov + 0.1f;
                        float squareVertices2[] = { // in counterclockwise order:
                                -1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 0.left - mid
                                1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 1. right - mid
                                -1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 2. left - top
                                1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 3. right - top

                        };
                         vertexBuffer.clear();
                         vertexBuffer.put(squareVertices2);
                         vertexBuffer.position(0);
                    }

                }
                else if (next == 2){
                    if (MainActivity.brightness < 2)
                    {
                        MainActivity.brightness = MainActivity.brightness + 0.1f;
                        String formatBrightness = String.format("%.1f", MainActivity.brightness);
                        mOverlayView.show3DToast("Brightness : " + formatBrightness + "/2" );
                    }
                }
                else if( next == 3){
                    if(MainActivity.size < 0.4f &&
                            MainActivity.off_y < (1 - MainActivity.size + 0.05) &&
                            MainActivity.off_x < (1 - MainActivity.size + 0.05) &&
                            MainActivity.pos_x < (1 - MainActivity.size + 0.05) &&
                            MainActivity.pos_y < (1 - MainActivity.size + 0.05)){
                        MainActivity.size = MainActivity.size + 0.05f;
                    }
                }
                else if( next == 4) {
                    if(MainActivity.pos_y >=0 ) {
                        MainActivity.pos_y = MainActivity.pos_y - 0.05f;
                        MainActivity.dif_y = MainActivity.off_y - MainActivity.pos_y ;
                        Log.i("simp"," pos :" + MainActivity.pos_y + "dif : "+ MainActivity.dif_y );
                    }
                }
                else if( next == 5) {
                    if(MainActivity.off_y >=0 ) {
                        MainActivity.off_y = MainActivity.off_y - 0.05f;
                        MainActivity.dif_y = MainActivity.off_y - MainActivity.pos_y;
                    }
                }
                return true;
            case KeyEvent.KEYCODE_BUTTON_B:
                if (next == 0) {
                    if (zoom > 0) {
                        zoom--;
                        //mCamera.startSmoothZoom(currentZoomLevel);
                        parameters.setZoom(zoom);
                        camera.setParameters(parameters);
                        mOverlayView.show3DToast("Zoom : " + zoom+ "/" + maxZoomLevel);
                    }
                }
                else if (next == 1){
                    if( MainActivity.fov > 0.1f) {
                        MainActivity.fov = MainActivity.fov - 0.1f;
                        float squareVertices2[] = { // in counterclockwise order:
                                -1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 0.left - mid
                                1.0f*MainActivity.fov, -1.0f*MainActivity.fov,   // 1. right - mid
                                -1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 2. left - top
                                1.0f*MainActivity.fov, 1.0f*MainActivity.fov,   // 3. right - top

                        };
                        vertexBuffer.clear();
                        vertexBuffer.put(squareVertices2);
                        vertexBuffer.position(0);
                    }

                }
                else if (next == 2){
                    if (MainActivity.brightness > 0.1f)
                    {
                        MainActivity.brightness = MainActivity.brightness - 0.1f;
                        String formatBrightness = String.format("%.1f", MainActivity.brightness);
                        mOverlayView.show3DToast("Brightness : " + formatBrightness + "/2" );
                    }
                }
                else if( next == 3){
                    if(MainActivity.size > 0.05) {
                        MainActivity.size = MainActivity.size - 0.05f;
                    }
                }
                else if( next == 4) {
                    if(MainActivity.pos_y < (1 - MainActivity.size)) {
                        MainActivity.pos_y = MainActivity.pos_y + 0.05f;
                        MainActivity.dif_y = MainActivity.off_y - MainActivity.pos_y ;
                    }
                }
                else if( next == 5) {
                    if(MainActivity.off_y < (1 - MainActivity.size)) {
                        MainActivity.off_y = MainActivity.off_y + 0.05f;
                        MainActivity.dif_y = MainActivity.off_y - MainActivity.pos_y;
                    }
                }

                return true;
            case KeyEvent.KEYCODE_BUTTON_Y:
                if(next == 0) {
                    if (sswitch == 1) {
                        sswitch = 0;
                    }
                   else {
                        sswitch++;
                    }

                }
                else if( next == 4) {
                    if(MainActivity.pos_x < (1 - MainActivity.size)) {
                        MainActivity.pos_x = MainActivity.pos_x + 0.05f;
                        MainActivity.dif_x = MainActivity.off_x - MainActivity.pos_x;
                    }
                }
                else if ( next == 5) {
                    if(MainActivity.off_x < (1 - MainActivity.size)) {
                        MainActivity.off_x = MainActivity.off_x + 0.05f;
                        MainActivity.dif_x = MainActivity.off_x - MainActivity.pos_x;
                    }
                }
                return true;
            case KeyEvent.KEYCODE_BUTTON_A:
                if( next == 4) {
                    if(MainActivity.pos_x >=0 ) {
                        MainActivity.pos_x = MainActivity.pos_x - 0.05f;
                        MainActivity.dif_x = MainActivity.off_x - MainActivity.pos_x;
                    }
                }
                else if ( next == 5) {
                    if(MainActivity.off_x >= 0) {
                        MainActivity.off_x = MainActivity.off_x - 0.05f;
                        MainActivity.dif_x = MainActivity.off_x - MainActivity.pos_x;
                    }
                }
                return true;
            case KeyEvent.KEYCODE_BUTTON_R1:
                next++;
                if(next == 0)  mOverlayView.show3DToast("Mode : Zoom");
                if(next == 1)  mOverlayView.show3DToast("Mode : FOV");
                if(next == 2)  mOverlayView.show3DToast("Mode : Brightness");
                if(next == 3)  mOverlayView.show3DToast("Scotoma Mode : Size");
                if(next == 4)  mOverlayView.show3DToast("Scotoma Mode : Offset");
                if(next == 5)  mOverlayView.show3DToast("Scotoma Mode : Scotoma Region");
                if(next > 5) {next = 0;  mOverlayView.show3DToast("Mode : Zoom");}
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {

        // Check that the event came from a game controller
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {

            // Process all historical movement samples in the batch
            final int historySize = event.getHistorySize();

            // Process the movements starting from the
            // earliest historical position in the batch
            //for (int i = 0; i < historySize; i++) {
            //    // Process the event at historical position i
            //    processJoystickInput(event, i);
            //}

            // Process the current movement sample in the batch (position -1)
            processJoystickInput(event, -1);
            return true;
        }
        return super.onGenericMotionEvent(event);
    }

    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {

        InputDevice mInputDevice = event.getDevice();

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.
        float x = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);
        if (x == 0) {
            x = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_X, historyPos);
        }
        if (x == 0) {
            x = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_Z, historyPos);
        }

        // Calculate the vertical distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat switch, or the right control stick.
        float y = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);
        if (y == 0) {
            y = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_Y, historyPos);
        }
        if (y == 0) {
            y = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_RZ, historyPos);
        }

        // Update the ship object based on the new x and y values
        final int maxZoomLevel = params.getMaxZoom();
        if(y<0) {
            if (zoom < maxZoomLevel) {
                zoom++;
                //mCamera.startSmoothZoom(currentZoomLevel);
                params.setZoom(zoom);
                camera.setParameters(params);
            }
        }

        if(y>0) {
            if (zoom > 0) {
                zoom--;
                //mCamera.startSmoothZoom(currentZoomLevel);
                params.setZoom(zoom);
                camera.setParameters(params);
            }
        }
    }

    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis):
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }


  public void startCamera(int texture) {
    surface = new SurfaceTexture(texture);
    surface.setOnFrameAvailableListener(this);

      //CALL FROM YOUR ACTIVITY**
      MyPermissions requestUserPermission = new MyPermissions(this);
      requestUserPermission.verifyStoragePermissions();

    camera = Camera.open();
      params = camera.getParameters();
    params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
      //params.setZoom(zoom);

    // todo Check what resolutions are supported by your camera
    camera.setParameters(params);
    try {
      camera.setPreviewTexture(surface);
      camera.startPreview();
    } catch (IOException ioe)
    {
      Log.i(TAG, "CAM LAUNCH FAILED");
    }

  }

  static private int createTexture() {
    int[] texture = new int[1];

    GLES20.glGenTextures(1, texture, 0);
    GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texture[0]);
    GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
    GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
    GLES20.glTexParameteri(GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
    GLES20.glTexParameteri(GL_TEXTURE_EXTERNAL_OES,
            GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

    return texture[0];
  }


  /**
   * Sets the view to our CardboardView and initializes the transformation matrices we will use
   * to render our scene.
   *
   * @param savedInstanceState
   */
  AudioManager mAudioManager;
    ComponentName mReceiverComponent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      setContentView(R.layout.main_layout);
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      cardboardView = (GvrView) findViewById(R.id.cardboard_view);
      cardboardView.setRenderer(this);
      setGvrView(cardboardView);

    //  //bluetooth
    //  IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);//"android.intent.action.MEDIA_BUTTON"
    //  MediaButtonIntentReceiver r = new MediaButtonIntentReceiver();
//  //    filter.setPriority(10000); //this line sets receiver priority
    //  registerReceiver(r, filter);
    //  takeKeyEvents(true);

      mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      mReceiverComponent = new ComponentName(this,MediaButtonIntentReceiver.class);
      mAudioManager.registerMediaButtonEventReceiver(mReceiverComponent);


      mCamera = new float[16];
    mView = new float[16];
    mOverlayView = (CardboardOverlayView) findViewById(R.id.overlay);
    //mOverlayView.show3DToast("Pull the magnet when you find an object.");
  }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioManager.unregisterMediaButtonEventReceiver(
                mReceiverComponent);
    }

  @Override
  public void onRendererShutdown() {
    Log.i(TAG, "onRendererShutdown");
  }

  @Override
  public void onSurfaceChanged(int width, int height) {
    Log.i(TAG, "onSurfaceChanged");
  }

    /**
     * Creates the buffers we use to store information about the 3D world. OpenGL doesn't use Java
     * arrays, but rather needs data in a format it can understand. Hence we use ByteBuffers.
     *
     * @param eglConfig The EGL configuration used when creating the surface.
     */

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        Log.i(TAG, "onSurfaceCreated");
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f); // Dark background so text shows up well

        ByteBuffer bb = ByteBuffer.allocateDirect(squareVertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareVertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(textureVertices.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textureVerticesBuffer = bb2.asFloatBuffer();
        textureVerticesBuffer.put(textureVertices);
        textureVerticesBuffer.position(0);

        int vertexShader = MyShaders.loadVertexShader();
        int fragmentShader = MyShaders.loadFragmentShader(0);
        int fragmentShaderInverse = MyShaders.loadFragmentShader(1);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);

        iProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(iProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(iProgram, fragmentShaderInverse); // add the fragment shader to program
        GLES20.glLinkProgram(iProgram);

        texture = createTexture();
        startCamera(texture);
    }



  /**
   * Prepares OpenGL ES before we draw a frame.
   *
   * @param headTransform The head transformation in the new frame.
   */
  @Override
  public void onNewFrame(HeadTransform headTransform) {
    float[] mtx = new float[16];
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    surface.updateTexImage();
    surface.getTransformMatrix(mtx);
  }

    @Override
    public void onDrawEye(Eye eye) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if(sswitch == 0 )
        GLES20.glUseProgram(mProgram);

        if(sswitch == 1 ) GLES20.glUseProgram(iProgram);


        GLES20.glActiveTexture(GL_TEXTURE_EXTERNAL_OES);
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texture);


        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "position");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, vertexBuffer);


        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, textureVerticesBuffer);

        mColorHandle = GLES20.glGetAttribLocation(mProgram, "s_texture");

        mSizeHandle = GLES20.glGetUniformLocation(mProgram,"size");
        GLES20.glUniform1f(mSizeHandle,MainActivity.size);

        mBrightnessHandle = GLES20.glGetUniformLocation(mProgram,"brightness");
        GLES20.glUniform1f(mBrightnessHandle,MainActivity.brightness);

        mPos_xHandle = GLES20.glGetUniformLocation(mProgram,"pos_x");
        GLES20.glUniform1f(mPos_xHandle,MainActivity.pos_x);

        mPos_yHandle = GLES20.glGetUniformLocation(mProgram,"pos_y");
        GLES20.glUniform1f(mPos_yHandle,MainActivity.pos_y);

        mDif_xHandle = GLES20.glGetUniformLocation(mProgram,"dif_x");
        GLES20.glUniform1f(mDif_xHandle,MainActivity.dif_x);

        mDif_yHandle = GLES20.glGetUniformLocation(mProgram,"dif_y");
        GLES20.glUniform1f(mDif_yHandle,MainActivity.dif_y);



        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

        Matrix.multiplyMM(mView, 0, eye.getEyeView(), 0, mCamera, 0);
    }

    @Override
  public void onFrameAvailable(SurfaceTexture arg0) {
  }


  @Override
  public void onFinishFrame(Viewport viewport) {
  }

  @Override
  public void onCardboardTrigger () {

      //cardboardView.updateGvrViewerParams(GvrViewerParams.createFromUri(viewer));

     // vertexBuffer.clear();
     // vertexBuffer.put(squareVertices1);
     // vertexBuffer.position(0);
     //   if(sswitch == 1){
     //       sswitch =0;
     //   }
     //   else sswitch++;

    }

    public static class MediaButtonIntentReceiver extends BroadcastReceiver {

        public MediaButtonIntentReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
                return;
            }
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null) {
                return;
            }
            int action = event.getAction();
            if (action == KeyEvent.ACTION_DOWN) {
                // do something
                Camera.Parameters parameters = camera.getParameters();
                final int maxZoomLevel = parameters.getMaxZoom();
                switch (event.getKeyCode()){
                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        //NET CODE
                        if (zoom < maxZoomLevel) {
                            zoom = zoom + (int)(0.10 * maxZoomLevel);
                            //mCamera.startSmoothZoom(currentZoomLevel);
                            parameters.setZoom(zoom);
                            camera.setParameters(parameters);
                        }
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:

                        if (zoom > 0) {
                            zoom = zoom - (int)(0.10 * maxZoomLevel);
                            //mCamera.startSmoothZoom(currentZoomLevel);
                            parameters.setZoom(zoom);
                            camera.setParameters(parameters);
                        }
                        break;
                }
//                Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            }
            abortBroadcast();
        }


    }
}


