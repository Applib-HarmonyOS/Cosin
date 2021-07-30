package com.nikitagordia.cosinloading;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import com.nikitagordia.cosinloading.slice.MainAbilitySlice;

/**
 * Routes to the MainAbilitySlice and has the callback method onRequestPermissionsFromUserResult.
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
