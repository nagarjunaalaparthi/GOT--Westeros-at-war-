package com.westerosatwar.got.Network;


import com.westerosatwar.got.Model.Battle;

import java.util.List;

/**
 * Created by Arjun.
 */
public interface ResponseCallback {
    void onResponse(List<Battle> battleList);
}
