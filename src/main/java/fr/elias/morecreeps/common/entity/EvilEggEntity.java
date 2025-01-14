package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.Explosion.Mode;
import fr.elias.morecreeps.common.MoreCreepsReboot;
import fr.elias.morecreeps.common.lists.ItemList;

public class EvilEggEntity extends ItemEntity
{
    protected double initialVelocity;
    double bounceFactor;
    int fuse;
    boolean exploded;
    public Entity owner;
    public double motionX = getMotion().x;
    public double motionY = getMotion().y;
    public double motionZ = getMotion().z;

    public EvilEggEntity(World world)
    {
        super(null, world);
        // setSize(0.25F, 0.25F);
        initialVelocity = 1.0D;
        bounceFactor = 0.84999999999999998D;
        exploded = false;
        setDefaultPickupDelay();
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = getBoundingBox().getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public EvilEggEntity(World world, Entity entity) {
        this(world);
        owner = entity;
        setRotation(entity.rotationYaw, 0.0F);
        double d = -MathHelper.sin((entity.rotationYaw * (float) Math.PI) / 180F);
        double d1 = MathHelper.cos((entity.rotationYaw * (float) Math.PI) / 180F);
        motionX = 1.1000000000000001D * d * (double) MathHelper.cos((entity.rotationPitch / 180F) * (float) Math.PI);
        motionY = -1.1000000000000001D * (double) MathHelper.sin((entity.rotationPitch / 180F) * (float) Math.PI);
        motionZ = 1.1000000000000001D * d1 * (double) MathHelper.cos((entity.rotationPitch / 180F) * (float) Math.PI);
        setPosition(entity.posX + d * 0.80000000000000004D, entity.posY, entity.posZ + d1 * 0.80000000000000004D);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }

    public EvilEggEntity(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public void func_20048_a(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / Math.PI);
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double d, double d1, double d2) {
        motionX = d;
        motionY = d1;
        motionZ = d2;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f) * 180D) / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate(World world)
    {
        double d = motionX;
        double d1 = motionY;
        double d2 = motionZ;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        setMotion(motionX, motionY, motionZ);

        if (motionX != d) {
            if (rand.nextInt(40) == 0) {
                EvilChickenEntity creepsentityevilchicken = new EvilChickenEntity(world);
                creepsentityevilchicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentityevilchicken.setMotion(rand.nextFloat() * 0.3F, rand.nextFloat() * 0.4F, rand.nextFloat() * 0.4F);
                if (!world.isRemote)
                    world.addEntity(creepsentityevilchicken);
            } else {
                explode(world);
            }
        }

        if (motionY != d1) {
            if (rand.nextInt(40) == 0) {
                EvilChickenEntity creepsentityevilchicken1 = new EvilChickenEntity(world);
                creepsentityevilchicken1.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentityevilchicken1.setMotion(rand.nextFloat() * 0.3F, rand.nextFloat() * 0.4F, rand.nextFloat() * 0.4F);
                if (!world.isRemote)
                    world.addEntity(creepsentityevilchicken1);
            } else {
                explode(world);
            }
        }

        if (motionY != d1) {
            if (rand.nextInt(40) == 0) {
                EvilChickenEntity creepsentityevilchicken2 = new EvilChickenEntity(world);
                creepsentityevilchicken2.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentityevilchicken2.setMotion(rand.nextFloat() * 0.3F, rand.nextFloat() * 0.4F, rand.nextFloat() * 0.4F);
                if (!world.isRemote)
                    world.addEntity(creepsentityevilchicken2);
            } else {
                explode(world);
            }
        } else {
            motionY -= 0.040000000000000001D;
        }

        motionX *= 0.97999999999999998D;
        motionY *= 0.995D;
        motionZ *= 0.97999999999999998D;

        if (handleWaterMovement()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 10; j++) {
                    float f = 0.85F;
                    world.addParticle(ParticleTypes.BUBBLE, posX - motionX - 0.25D * (double) f,
                            posY - motionY - 0.25D * (double) f, posZ - motionZ - 0.25D * (double) f, motionX, motionY,
                            motionZ);
                }
            }

            remove();
        }

        List list = world.getEntitiesWithinAABBExcludingEntity(this,
                getBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));

        for (int k = 0; k < list.size(); k++)
        {
            Entity entity = (Entity)list.get(k);

            if (entity != null && entity.canBeCollidedWith() && !(entity instanceof PlayerEntity))
            {
                explode(world);
            }
        }
    }

    private void explode(World world)
    {
        if (!exploded)
        {
            exploded = true;
            if(!world.isRemote)
            world.createExplosion(owner, posX, posY, posZ, 2.0F, true, Mode.BREAK);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeAdditional(CompoundNBT nbttagcompound)
    {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT nbttagcompound)
    {}

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void onCollideWithPlayer(PlayerEntity playerentity)
    {}
    
    public ItemStack getEntityItem()
    {
    	return new ItemStack(ItemList.evil_egg, 1);
    }
}
