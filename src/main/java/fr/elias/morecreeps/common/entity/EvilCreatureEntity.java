package fr.elias.morecreeps.common.entity;

import java.util.List;

import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.util.handlers.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EvilCreatureEntity extends MobEntity
{
    public boolean jumping;
    public float modelsize;
    public ResourceLocation texture;
    public float height = getHeight();
    public float width = getWidth();
    public float length = getWidth();

    public EvilCreatureEntity(World world)
    {
        super(null, world);
        texture = new ResourceLocation(Reference.MODID + Reference.TEXTURE_PATH_ENTITES+ "evilcreature.png");
        // setSize(width * 3F, height * 3F);
        jumping = false;
        modelsize = 3F;
        // ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        // this.tasks.addTask(0, new EntityAISwimming(this));
        // this.tasks.addTask(4, new EvilCreatureEntity.AIAttackEntity());
        // this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        // this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        // this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        // this.tasks.addTask(8, new EntityAILookIdle(this));
        // this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        // this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
    }

    public void registerAttributes()
    {
    	super.registerAttributes();
    	this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(110D);
    	this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    	this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeAdditional(CompoundNBT nbttagcompound)
    {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT nbttagcompound)
    {
        super.readAdditional(nbttagcompound);
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    @SuppressWarnings("rawtypes")
	protected void attackEntity(Entity entity, float f, World world, PlayerEntity playerentity)
    {
        if (onGround && jumping)
        {
            jumping = false;
            world.playSound(playerentity, this.getPosition(), SoundsHandler.EVIL_CREATURE_JUMP, SoundCategory.HOSTILE, 1.0F * (modelsize / 3F), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (3F - modelsize) * 2.0F);
            List list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(18D, 18D, 18D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity)list.get(i);

                if ((entity1 instanceof LivingEntity) && !entity1.handleWaterMovement() && entity1.onGround)
                {
                    double d2 = getDistance(entity1);
                    entity1.motionY += (17D - d2) * 0.067057996988296509D * (double)(modelsize / 3F);
                }
            }
        }

        if (onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt(d * d + d1 * d1);
            moveForward = (float) ((d / (double)f1) * 0.5D * 0.40000000192092894D + moveForward * 0.20000000098023224D);
            moveStrafing = (float) ((d1 / (double)f1) * 0.5D * 0.30000000192092896D + moveStrafing * 0.20000000098023224D);
            moveVertical = (float) 0.35000000196046449D;
            jumping = true;
        }

        int attackTime = 20;
        if(attackTime-- < 0)
        {
            if ((double)f < 3D - (2D - (double)modelsize) && entity.getBoundingBox().maxY > getBoundingBox().minY && entity.getBoundingBox().minY < getBoundingBox().maxY)
            {
            	attackTime = 20;
                entity.motionY += 0.76999998092651367D;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            }
        }
    }

    class AIAttackEntity extends Brain
    {
    	public EvilCreatureEntity evilcreature = EvilCreatureEntity.this;
    	public int attackTime;
    	public AIAttackEntity() {}
    	
		@Override
		public boolean shouldExecute() {
            LivingEntity entitylivingbase = this.evilcreature.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isAlive();
		}
        public void updateTask()
        {
        	--attackTime;
            LivingEntity entitylivingbase = this.evilcreature.getAttackTarget();
            double d0 = this.evilcreature.getDistanceSq(entitylivingbase);
        	evilcreature.attackEntity(entitylivingbase, (float)d0, world, null);
            this.evilcreature.getLookController().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            /*if (d0 < 4.0D)
            {
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.evilcreature.attackEntityAsMob(entitylivingbase);
                }
                
                this.evilcreature.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                // ATTACK ENTITY GOES HERE
                this.evilcreature.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else*/
            //{
                this.evilcreature.getNavigator().clearPath();
                this.evilcreature.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
            //}
        }
    }
    
    /**
     * Plays living's sound at its position
     */
    @Override
    public void playAmbientSound()
    {
        World world = Minecraft.getInstance().world;
        PlayerEntity playerentity = Minecraft.getInstance().player;
        SoundEvent s = getAmbientSound();

        if (s != null)
        {
            world.playSound(playerentity, this.getPosition(), s, SoundCategory.HOSTILE, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (3F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.EVIL_CREATURE;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected SoundEvent getHurtSound(DamageSource damagesourceIn)
    {
        return SoundsHandler.EVIL_CREATURE_HURT;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.EVIL_CREATURE_DEATH;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    @Override
    public float getShadowSize()
    {
        return 2.9F;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource, World world)
    {
    	if(!world.isRemote)
    	{
            if (rand.nextInt(5) == 0)
            {
                entityDropItem(Items.BREAD, rand.nextInt(3) + 1);
            }
            else
            {
                entityDropItem(Items.COD, rand.nextInt(3) + 1);
            }
    	}
        super.onDeath(damagesource);
    }
}
