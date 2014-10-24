package com.gr8pefish.hardchoices.recipes;

import net.minecraftforge.oredict.ShapedOreRecipe;

public class DisabledShapedOreRecipe extends ShapedOreRecipe {

    public ShapedOreRecipe disabledShapedOreRecipeField;

//    public DisabledShapedOreRecipe(Block result, Object... recipe){ this(new ItemStack(result), recipe); }
//    public DisabledShapedOreRecipe(Item result, Object... recipe){ this(new ItemStack(result), recipe); }
//    public DisabledShapedOreRecipe(ItemStack result, Object... recipe) {
//        super(result, recipe);
//    }

    public DisabledShapedOreRecipe(ShapedOreRecipe shapedOreRecipe){
        super(shapedOreRecipe.getRecipeOutput(), shapedOreRecipe.getInput());
        this.disabledShapedOreRecipeField = shapedOreRecipe;
    }

//    @Override
//    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting){
//        Logger.log("In getCraftingResult");
//        try{
//            Field container = inventoryCrafting.getClass().getField("eventHandler");
//            if (container.getClass().equals(ContainerWorkbench.class)){
//                ReflectionHelper helper = new ReflectionHelper(container, ContainerWorkbench.class);
//                Object tempWorkbench = helper.runGetter();
//                ContainerWorkbench containerWorkbench = (ContainerWorkbench) tempWorkbench;
//                SlotCrafting firstSlot = (SlotCrafting) containerWorkbench.getSlot(0);
//                try{
//                    Field player = firstSlot.getClass().getField("thePlayer");
//                    ReflectionHelper helperTwo = new ReflectionHelper(player, EntityPlayer.class);
//                    Object tempPlayer = helperTwo.runGetter();
//                    EntityPlayer thePlayer = (EntityPlayer) tempPlayer;
//
//                    Logger.log("Player who is crafting: "+thePlayer.getDisplayName());
//
//                }catch (NoSuchFieldException e){
//                    Logger.log("No player field.");
//                }
//
//            }else if (container.getClass().equals(ContainerPlayer.class)){
//                try{
//                    Field player = container.getClass().getField("thePlayer");
//                    ReflectionHelper helper = new ReflectionHelper(player, EntityPlayer.class);
//                    Object tempPlayer = helper.runGetter();
//                    EntityPlayer thePlayer = (EntityPlayer) tempPlayer;
//
//                    Logger.log("Player who is crafting: "+thePlayer.getDisplayName());
//
//                }catch (NoSuchFieldException e){
//                    Logger.log("No player field");
//                }
//            }
//        }catch (NoSuchFieldException e){
//            Logger.log("Couldn't get eventHandler");
//        }
//        return null;
//    }
}
