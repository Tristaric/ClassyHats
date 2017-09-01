package wiresegal.classy.hats.common.hat

import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.core.client.ModelHandler
import com.teamwizardry.librarianlib.features.base.IExtraVariantHolder
import com.teamwizardry.librarianlib.features.base.item.ItemMod
import com.teamwizardry.librarianlib.features.helpers.ItemNBTHelper
import com.teamwizardry.librarianlib.features.utilities.client.TooltipHelper
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import wiresegal.classy.hats.LibMisc
import wiresegal.classy.hats.common.core.HatConfigHandler
import wiresegal.classy.hats.common.core.HatConfigHandler.Hat

/**
 * @author WireSegal
 * Created at 8:40 PM on 8/31/17.
 */
object ItemHat : ItemMod("hat"), IExtraVariantHolder {

    fun getHat(stack: ItemStack): Hat {
        val hatId = ItemNBTHelper.getString(stack, "hat", null) ?: return HatConfigHandler.missingno
        return HatConfigHandler.hats[hatId] ?: HatConfigHandler.missingno
    }

    fun ofHat(hat: Hat): ItemStack = ofHat(hat.name)

    fun ofHat(name: String, amount: Int = 1): ItemStack {
        val stack = ItemStack(this, amount)
        ItemNBTHelper.setString(stack, "hat", name)
        return stack
    }


    override val extraVariants: Array<out String>
        get() = HatConfigHandler.hats.values.map { it.name }.toTypedArray()
    override val meshDefinition: ((stack: ItemStack) -> ModelResourceLocation)?
        get() = { ModelHandler.resourceLocations["classyhats"]!![getHat(it).name] as ModelResourceLocation }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        HatConfigHandler.hats.values
                .filter { it != HatConfigHandler.missingno }
                .mapTo(subItems) { ofHat(it) }
    }

    override fun getUnlocalizedName(stack: ItemStack)
            = "item.${LibMisc.MOD_ID}:${getHat(stack).name}"

    override fun addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
        val desc = stack.unlocalizedName + ".desc"
        val used = if (LibrarianLib.PROXY.canTranslate(desc)) desc else "${desc}0"
        if (LibrarianLib.PROXY.canTranslate(used)) {
            TooltipHelper.addToTooltip(tooltip, used)
            var i = 0
            while (LibrarianLib.PROXY.canTranslate("$desc${++i}"))
                TooltipHelper.addToTooltip(tooltip, "$desc$i")
        }
    }

    override fun getRarity(stack: ItemStack) =
            if (getHat(stack) == HatConfigHandler.missingno) EnumRarity.EPIC
            else if (getHat(stack).elusive) EnumRarity.UNCOMMON
            else EnumRarity.COMMON
}