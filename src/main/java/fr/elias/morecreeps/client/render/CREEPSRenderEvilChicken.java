package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelEvilChicken;
import fr.elias.morecreeps.common.entity.EvilChickenEntity;
import fr.elias.morecreeps.common.entity.EvilCreatureEntity;

public class CREEPSRenderEvilChicken extends RenderLiving
{
    protected CREEPSModelEvilChicken modelBipedMain;

    public CREEPSRenderEvilChicken(CREEPSModelEvilChicken creepsmodelevilchicken, float f)
    {
    	
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelevilchicken, f);
        modelBipedMain = creepsmodelevilchicken;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        EvilChickenEntity creepsentityevilchicken = (EvilChickenEntity)entityliving;
        modelBipedMain.modelsize = creepsentityevilchicken.modelsize;
        fattenup((EvilChickenEntity)entityliving, f);
    }

    protected void fattenup(EvilChickenEntity creepsentityevilchicken, float f)
    {
        GL11.glScalef(creepsentityevilchicken.modelsize, creepsentityevilchicken.modelsize, creepsentityevilchicken.modelsize);
    }
    protected float func_180569_a(EvilChickenEntity creepsentityevilchicken, float f)
    {
        float f1 = creepsentityevilchicken.field_756_e + (creepsentityevilchicken.field_752_b - creepsentityevilchicken.field_756_e) * f;
        float f2 = creepsentityevilchicken.field_757_d + (creepsentityevilchicken.destPos - creepsentityevilchicken.field_757_d) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
    {
        return this.func_180569_a((EvilChickenEntity)p_77044_1_, p_77044_2_);
    }

    protected ResourceLocation getEntityTexture(EvilChickenEntity entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((EvilChickenEntity) entity);
	}
    
}
