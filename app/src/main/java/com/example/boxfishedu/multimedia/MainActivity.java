package com.example.boxfishedu.multimedia;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1;//四个按钮的引用
    Button button2;
    Button button3;
    Button button4;
    TextView textView;//TextView的引用
    MediaPlayer mMediaPlayer;
    SoundPool soundPool;//声音
    HashMap<Integer, Integer> soundPoolMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSounds();//初始化声音
        setContentView(R.layout.activity_main);
        textView = (TextView) this.findViewById(R.id.textView);//得到TextView的引用
        button1 = (Button) this.findViewById(R.id.button1);//得到button的引用
        button2 = (Button) this.findViewById(R.id.button2);
        button3 = (Button) this.findViewById(R.id.button3);
        button4 = (Button) this.findViewById(R.id.button4);
        button1.setOnClickListener(this);//为四个按钮添加监听
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    private void initSounds() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.backsound);
        soundPool = new SoundPool.Builder()
                .setMaxStreams(4).build();
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong, 1));
    }

    @Override
    public void onClick(View view) {
        if (view == button1) {
            textView.setText("使用MediaPlayer播放声音");
            if(!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();//播放声音
            }
        }
        else if(view == button2){//点击了暂停MediaPlayer声音按钮
            textView.setText("暂停了MediaPlayer播放的声音");
            if(mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();//暂停声音
            }
        }
        else if(view == button3){//点击了使用SoundPool播放声音按钮
            textView.setText("使用SoundPool播放声音");
            this.playSound(1, 0);
        }
        else if(view == button4){//点击了暂停SoundPool声音按钮
            textView.setText("暂停了SoundPool播放的声音");
            soundPool.pause(1);//暂停SoundPool的声音
        }
    }

    private void playSound(int sound, int loop) {
        AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
    }
}
