package de.blockmaster.vanilla_taa.util;

import com.google.common.collect.Sets;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModToolActions {
    public static final Set<ToolAction> DEFAULT_DAGGER_ACTIONS = of(ToolActions.SWORD_DIG);
    public static final Set<ToolAction> DEFAULT_BATTLE_AXE_ACTIONS = of(ToolActions.AXE_DIG, ToolActions.AXE_STRIP, ToolActions.AXE_SCRAPE, ToolActions.AXE_WAX_OFF, ToolActions.SWORD_SWEEP);
    public static final Set<ToolAction> DEFAULT_SPEAR_ACTIONS = of(ToolActions.SWORD_DIG);

    private static Set<ToolAction> of(ToolAction... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
}
