
	import java.applet.AudioClip;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.InputStream;
	import java.net.URL;
	import javax.swing.JApplet;
	import javazoom.jl.player.advanced.AdvancedPlayer;

	public class Audio {
	    public static final int PLAY_ENTIRE_SONG = 0;
	    Song song = null;
	    AudioClip songWav = null;
	    
	    public Audio( String fileName ) {
	    
	        if( fileName.indexOf( ".wav" ) != -1 ) {
	            songWav = createWavSoundFile( fileName );
	        } else {
	            this.song = new Song(fileName);
	        }
	    }
	    
	    public void play( int durationSeconds ) {
	        if( song != null ) {
	            playMp3SoundFile( durationSeconds );
	        } else if( songWav != null ) {
	            songWav.play();
	        } else {
	            System.out.println( "ERROR: sound file could not be played" );
	        }
	    }
	    
	    public void stop() {
	        if( song != null ) {
	            song.stop();
	        } else if( songWav != null ) {
	            songWav.stop();
	        } else {
	            System.out.println( "ERROR: sound file could not be stopped" );
	        }
	    }
	    
	    // Use this method to play sound files with ".wav" at the end of the file name.
	    // Download .wav sound files at freesound.org
	    // freesound.org username: leagueofamazing password: code4life
	    private AudioClip createWavSoundFile(String fileName) {
	        URL url = getClass().getResource(fileName);
	        AudioClip audio = JApplet.newAudioClip(url);
	        
	        audio.play();
	        
	        return audio;
	    }
	    
	    // Use this class to play sound files with a ".mp3" at the end of the file name.
	    private void playMp3SoundFile(int durationSeconds) {
	        song.setDuration( durationSeconds );
	        song.play();
	    }

	    class Song {
	        private int duration;
	        private String songAddress;
	        private AdvancedPlayer mp3Player;
	        private InputStream songStream;

	        /**
	         * Songs can be constructed from files on your computer or Internet
	         * addresses.
	         * 
	         * Examples: <code> 
	         *      new Song("everywhere.mp3");     //from default package 
	         *      new Song("/Users/joonspoon/music/Vampire Weekend - Modern Vampires of the City/03 Step.mp3"); 
	         *      new Song("http://freedownloads.last.fm/download/569264057/Get%2BGot.mp3"); 
	         * </code>
	         */
	        public Song(String songAddress) {
	            this.songAddress = songAddress;
	        }

	        public void play() {
	            loadFile();
	            if (songStream != null) {
	                loadPlayer();
	                startSong();
	            } else
	                System.err.println("Unable to load file: " + songAddress);
	        }

	        public void setDuration(int seconds) {
	            this.duration = seconds * 100;
	        }

	        public void stop() {
	            if (mp3Player != null)
	                mp3Player.close();
	        }

	        private void startSong() {
	            Thread t = new Thread() {
	                public void run() {
	                    try {
	                        if (duration > 0)
	                            mp3Player.play(duration);
	                        else
	                            mp3Player.play();
	                    } catch (Exception e) {
	                    }
	                }
	            };
	            t.start();
	        }

	        private void loadPlayer() {
	            try {
	                this.mp3Player = new AdvancedPlayer(songStream);
	            } catch (Exception e) {
	            }
	        }

	        private void loadFile() {
	            if (songAddress.contains("http"))
	                this.songStream = loadStreamFromInternet();
	            else
	                this.songStream = loadStreamFromComputer();
	        }

	        private InputStream loadStreamFromInternet() {
	            try {
	                return new URL(songAddress).openStream();
	            } catch (Exception e) {
	                return null;
	            }
	        }

	        private InputStream loadStreamFromComputer() {
	            try {
	                return new FileInputStream(songAddress);
	            } catch (FileNotFoundException e) {
	                return this.getClass().getResourceAsStream(songAddress);
	            }
	        }
	    }
	}

