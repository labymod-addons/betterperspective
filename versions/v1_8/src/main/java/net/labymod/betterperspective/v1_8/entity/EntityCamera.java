package net.labymod.betterperspective.v1_8.entity;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCamera extends EntityLivingBase
{
    public EntityCamera(World world)
    {
        super(world);
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    public ItemStack getHeldItem()
    {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int i)
    {
        return null;
    }

    @Override
    public ItemStack getCurrentArmor(int i)
    {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int i, ItemStack itemStack)
    {

    }

    @Override
    public ItemStack[] getInventory()
    {
        return new ItemStack[0];
    }
}
