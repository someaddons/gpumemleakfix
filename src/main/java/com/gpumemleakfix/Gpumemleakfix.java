package com.gpumemleakfix;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gpumemleakfix implements ModInitializer {
    public static final String MOD_ID = "gpumemleakfix";
    public static final Logger LOGGER = LogManager.getLogger();

    // GL_OUT_OF_MEMORY
    // GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT
    // GL_INVALID_FRAMEBUFFER_OPERATION

    public Gpumemleakfix() {

    }

    @Override
    public void onInitialize() {

    }
}
