package dev.goldenedit.deathdropsadjustments;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathDropsAdjustments extends JavaPlugin{

    public static StateFlag NO_TREE_PEARL_FLAG;

    @Override
    public void onLoad() {
        super.onLoad();
        registerFlags();

    }
    @Override
    public void onEnable() {
        // Register the event listeners
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    private void registerFlags() {
        FlagRegistry registry = com.sk89q.worldguard.WorldGuard.getInstance().getFlagRegistry();
        try {
            // Create and register the custom flag for the no tree pearl flag
            StateFlag flag = new StateFlag("no-tree-pearl", false);
            registry.register(flag);
            NO_TREE_PEARL_FLAG = flag;
        } catch (FlagConflictException e) {
            // Handle case where the flag has already been registered
            Flag<?> existing = registry.get("no-tree-pearl");
            if (existing instanceof StateFlag) {
                NO_TREE_PEARL_FLAG = (StateFlag) existing;
            } else {
                // Problem: the flag is already registered, but it's not an IntegerFlag
                getLogger().severe("No Tree Pearl flag conflict: existing flag is not an IntegerFlag");
            }
        }
    }
}
