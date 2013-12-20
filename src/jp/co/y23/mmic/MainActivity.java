// original is http://developer.android.com/guide/topics/media/audio-capture.html
/*
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */
package jp.co.y23.mmic;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends Activity implements OnClickListener
//	public class AudioRecordTest extends Activity
{
    private static final String LOG_TAG = "SSKK";
    private static String mFileName = null;

//    private RecordButton mRecordButton = null;
    private Button mRecordButton;
    private MediaRecorder mRecorder = null;
    boolean mStartRecording = true; //false;

//    private PlayButton   mPlayButton = null;
//    private MediaPlayer   mPlayer = null;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

	public void onClick(View v) {
	if (v.getId()==R.id.RecordButton) {
        onRecord(mStartRecording);
        if (mStartRecording) {
//            setText("Stop recording");
            mRecordButton.setText("Stop");
        } else {
//            setText("Start recording");
        	mRecordButton.setText("Start");
        }
        mStartRecording = !mStartRecording;
		}
	}
/*
	private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
*/
public String format0(int n)
	{
		String str;

		if (n<10)
			str="0"+n;
		else
			str=""+n;

		return str;
	}

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//       =/storage/emulated/0/audiorecordtest.3gp on Nexus 4
        Calendar cal = Calendar.getInstance();
        mFileName += "/as";
        mFileName += format0(cal.get(Calendar.MONTH)) + format0(cal.get(Calendar.DATE)) + "_";
        mFileName += format0(cal.get(Calendar.HOUR)) + format0(cal.get(Calendar.MINUTE)) + format0(cal.get(Calendar.SECOND)) + ".3gp";
        
        //        mFileName += "/audiorecordtest.3gp";

        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        Log.i(LOG_TAG, "mFileName="+mFileName);

    	TextView myText1=(TextView)findViewById(R.id.TextView1);
    	myText1.setText(mFileName); //
        
        try {
            mRecorder.prepare(); 
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

/*
 *     class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }
*/

/*    public void AudioRecordTest() {
//        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
      mFileName = Environment.getDataDirectory().getAbsolutePath();
      mFileName += "/audiorecordtest.3gp";
    }
*/
    @Override
//    public void onCreate(Bundle icicle) {
  public void onCreate(Bundle savedInstanceState) {
//    	super.onCreate(icicle);
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);

    	mRecordButton=(Button)findViewById(R.id.RecordButton);
    	mRecordButton.setOnClickListener(this);

/*
        LinearLayout ll = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        setContentView(ll);
*/
}

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
/*        if (mPlayer != null) {
 * 
            mPlayer.release();
            mPlayer = null;
        }
*/
}
}