package com.lhg.puremusic.bridge.state;

import android.graphics.drawable.Drawable;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.lhg.architecture.utils.Utils;
import com.lhg.puremusic.R;
import com.lhg.puremusic.player.PlayerManager;
import com.lhg.puremusic.player.PlayingInfoManager;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

/**
 * TODO tip：每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 *
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 *
 *
 * Create by lhg at 19/10/29
 */
public class PlayerViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> artist = new ObservableField<>();

    public final ObservableField<String> coverImg = new ObservableField<>();

    public final ObservableField<Drawable> placeHolder = new ObservableField<>();

    public final ObservableInt maxSeekDuration = new ObservableInt();

    public final ObservableInt currentSeekPosition = new ObservableInt();

    public final ObservableBoolean isPlaying = new ObservableBoolean();

    public final ObservableField<MaterialDrawableBuilder.IconValue> playModeIcon = new ObservableField<>();

    {
        title.set(Utils.getApp().getString(R.string.app_name));
        artist.set(Utils.getApp().getString(R.string.app_name));
        placeHolder.set(Utils.getApp().getResources().getDrawable(R.drawable.bg_album_default));

        if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.LIST_LOOP) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
        } else if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.ONE_LOOP) {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
        } else {
            playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
        }
    }
}
