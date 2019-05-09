package com.example.plants.poisonousplants.View.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.plants.poisonousplants.View.activities.MainActivity;

import java.io.IOException;
import java.util.Random;

import org.tensorflow.Operation;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;


/**
 * The class handles functions for the camera operations.
 *
 * @author  Taylor Mauldin
 * @version 1.0
 * @since   2019-04-05
 */

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    public Camera camera;
    SurfaceHolder holder;
    Context c;

    private final String MODEL_FILE = "file:///android_asset/"+"frozen_CNN_Plants_0e.pb";
    public TensorFlowInferenceInterface inferenceInterface;
    private float[] outputs;
    private String[] outputNames;
    public static float[] output = new float[2];
    private final int newWidth = 200;
    private final int newHeight = 250;

    private int[] red = new int[newHeight*newWidth];
    private int[] green = new int[newHeight*newWidth];
    private int[] blue = new int[newHeight*newWidth];


    /**
     * Creates the container for the camera and displays
     * @param camera Native android camera object
     * @param context Environment handler
     */
    public ShowCamera(Context context, Camera camera, Context c) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        this.c = c;
    }

    private void unpackPixel(int pixel, int index) {
        red[index] = (short) ((pixel >> 16) & 0xFF);
        green[index] = (short) ((pixel >> 8) & 0xFF);
        blue[index] = (short) ((pixel >> 0) & 0xFF);
    }

    public void convertRGB(int [] rgb) {
        for (int i = 0; i < rgb.length; i++) {
            unpackPixel(rgb[i], i);
        }
    }

    public void predict(int [] rgb2, float [] output) {

        int rgbLast = rgb2[rgb2.length-1];
        int rgb2ndlast = rgb2[rgb2.length-2];

        inferenceInterface = new TensorFlowInferenceInterface(c.getAssets(), MODEL_FILE);
        final Operation operation = inferenceInterface.graphOperation("Output");
        final int numClasses = (int) operation.output(0).shape().size(1);
        System.out.println("Num classes: "+Integer.toString(numClasses));

        outputs = new float[numClasses];
        outputNames = new String[] {"Output"};
        final float threshold = Float.parseFloat("0.1");
        final int batch_size = 1;
        final int num_inputs = 3;
        float[] sampleInputs = new float[num_inputs*newHeight*newWidth];
        convertRGB(rgb2);

        for (int i = 0; i < rgb2.length*num_inputs; i++) {
            if (i < rgb2.length) {
                sampleInputs[i] = (float)red[i];
            }
            else if (i < rgb2.length*2) {
                sampleInputs[i] = (float)green[i-rgb2.length];
            }
            else {
                sampleInputs[i] = (float)blue[i-rgb2.length*2];
            }
        }


        inferenceInterface.feed("Input", sampleInputs, batch_size, newHeight,newWidth, num_inputs);
        inferenceInterface.run(outputNames, false);
        inferenceInterface.fetch("Output", outputs);

        int max_out = 0;
        float max_val = 0;
        for (int i = 0; i < outputs.length; i++) {
            if (outputs[i] > max_val) {
                max_val = outputs[i];
                max_out = i;
            }
        }

        output[0] = max_out;
        output[1] = max_val;
        final int random = new Random().nextInt(61) + 20;
        MainActivity.plant_prediction.setText(Float.toString(max_out));
        MainActivity.plant_val.setText(Float.toString(max_val));
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        System.out.println("TEST123");
        Context mContext = getContext();
        int frameHeight = camera.getParameters().getPreviewSize().height;
        int frameWidth = camera.getParameters().getPreviewSize().width;
        int newWidth = 200;
        int newHeight = 250;
        System.out.print("Height: "+Integer.toString(frameHeight)+" Width: "+Integer.toString(frameWidth));
        // number of pixels//transforms NV21 pixel data into RGB pixels
        int rgb[] = new int[frameWidth * frameHeight];
        int rgb2[] = new int[(newWidth*newHeight)];

        // conversion from YUV to RGB
        decodeYUV420SP(rgb, data, frameWidth, frameHeight);
        Bitmap bmp = Bitmap.createBitmap(rgb, frameWidth, frameHeight, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, true);
        bitmap.getPixels(rgb2, 0, newWidth, 0,0, newWidth, newHeight);
        predict(rgb2, output);

    }

    void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
        // Pulled directly from:
        // http://ketai.googlecode.com/svn/trunk/ketai/src/edu/uic/ketai/inputService/KetaiCamera.java
        final int frameSize = width * height;
        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0)
                    y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0)
                    r = 0;
                else if (r > 262143)
                    r = 262143;
                if (g < 0)
                    g = 0;
                else if (g > 262143)
                    g = 262143;
                if (b < 0)
                    b = 0;
                else if (b > 262143)
                    b = 262143;

                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
            }
        }
    }

    public Allocation renderScriptNV21ToRGBA888(Context context, int width, int height, byte[] nv21) {
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));

        Type.Builder yuvType = new Type.Builder(rs, Element.U8(rs)).setX(nv21.length);
        Allocation in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);

        Type.Builder rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(width).setY(height);
        Allocation out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);

        in.copyFrom(nv21);

        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);
        return out;
    }

    /**
     * Sets camera settings when it is created and displayed
     * @param holder Container for camera
     *
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = camera.getParameters();

        // change orientation

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            params.set("orientation", "portrait");
            camera.setDisplayOrientation(270);
            //params.setRotation(90);
        }
        else
        {
            params.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }

        camera.setParameters(params);
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            camera.setPreviewCallback(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
