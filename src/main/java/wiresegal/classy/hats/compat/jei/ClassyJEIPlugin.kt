package wiresegal.classy.hats.compat.jei

import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.JEIPlugin
import mezz.jei.api.recipe.VanillaRecipeCategoryUid
import wiresegal.classy.hats.common.gui.GuiHat

@JEIPlugin
class ClassyJEIPlugin : IModPlugin {

    override fun register(registry: IModRegistry) {
        registry.recipeTransferRegistry.addRecipeTransferHandler(ClassyRecipeTransferInfo())
        registry.addRecipeClickArea(GuiHat::class.java, 135, 29, 16, 13, VanillaRecipeCategoryUid.CRAFTING)
    }

}