/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2019 Caprica Software Limited.
 */

package uk.co.caprica.vlcj.player.list;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_list_player_t;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

/**
 * Specification for a media list player component.
 * <p>
 * A media list player can be used with an embedded media player (without this a native video window
 * will be opened when video is played). For example:
 *
 * <pre>
 * MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
 *
 * Canvas canvas = new Canvas();
 * canvas.setBackground(Color.black);
 * CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
 *
 * EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
 * mediaPlayer.setVideoSurface(videoSurface);
 *
 * MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
 *
 * // Important: associate the media player with the media list player
 * mediaListPlayer.setMediaPlayer(mediaPlayer);
 *
 * MediaList mediaList = mediaPlayerFactory.newMediaList();
 * mediaList.addMedia(&quot;/movies/1.mp4&quot;);
 * mediaList.addMedia(&quot;/movies/2.mp4&quot;);
 * mediaList.addMedia(&quot;/movies/3.mp4&quot;);
 *
 * mediaListPlayer.setMediaList(mediaList);
 * mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
 *
 * mediaListPlayer.play();
 * </pre>
 */
public interface MediaListPlayer {

    /**
     * Behaviour pertaining to media list player controls.
     *
     * @return controls behaviour
     */
    ControlsService controls();

    /**
     * Behaviour pertaining to media list player events.
     *
     * @return event behaviour
     */
    EventService events();

    /**
     * Behaviour pertaining to the media list.
     *
     * @return media list behaviour
     */
    ListService list();

    /**
     * Behaviour pertaining to the associated media player.
     *
     * @return media player behaviour
     */
    MediaPlayerService mediaPlayer();

    /**
     * Behaviour pertaining to the status of the media list player.
     *
     * @return status behaviour
     */
    StatusService status();

    /**
     * Behaviour pertaining to userdata.
     *
     * @return userdata behaviour
     */
    UserDataService userData();

    /**
     * Release the media list player resources.
     */
    void release();

    /**
     * Submit a task for asynchronous execution.
     * <p>
     * This is useful in particular for event handling code as native events are generated on a native event callback
     * thread and it is not allowed to call back into LibVLC from this callback thread. If you do, either the call will
     * be ineffective, strange behaviour will happen, or a fatal JVM crash may occur.
     * <p>
     * To mitigate this, those tasks can be offloaded from the native thread, serialised and executed using this method.
     *
     * @param r task to execute
     */
    void submit(Runnable r);

    /**
     * Provide access to the native media player instance.
     * <p>
     * This is exposed on the interface as an implementation side-effect, ordinary applications are not expected to use
     * this.
     *
     * @return media player instance
     */
    libvlc_media_list_player_t mediaListPlayerInstance(); // FIXME check this needs to be on interface

}
