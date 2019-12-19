package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Guard extends Entity
{
	private int rayon_detect_vision;
	private int rayon_detect_noise;
	private int rayon_detect_touch;

	public Guard(int pos_x, int pos_y) throws SlickException
	{
		super(pos_x, pos_y);
		
		this.speed 	= 0.15f;
	}
	
	public Animation getPendingAnimsGuard( SpriteSheet spriteSheet, int x1, int x2, int y, int frq )
	{
		Animation pending =  new Animation();
		
		pending.addFrame( spriteSheet.getSprite(x1, y), frq*5 );
		pending.addFrame( spriteSheet.getSprite(x2, y), frq);
		
		return pending;
	}

	public int getRayon_detect_vision() { return rayon_detect_vision; }
	public int getRayon_detect_noise()  { return rayon_detect_noise;  }
	public int getRayon_detect_touch()  { return rayon_detect_touch;  }

	public void setRayon_detect_vision(int rayon_detect_vision) { this.rayon_detect_vision = rayon_detect_vision; }
	public void setRayon_detect_noise(int rayon_detect_noise)   { this.rayon_detect_noise = rayon_detect_noise; }
	public void setRayon_detect_touch(int rayon_detect_touch)   { this.rayon_detect_touch = rayon_detect_touch; }

	@Override
	protected void initMovements() throws SlickException
	{
		SpriteSheet sprtShtGuard = new SpriteSheet("sprites/guard.png", 64, 64);
		Animation[] guardAnimations = this.getMovements();
		
		guardAnimations[0] = this.getPendingAnimsGuard( sprtShtGuard, 1, 1, 3, 500); //UP
		guardAnimations[1] = this.getPendingAnimsGuard( sprtShtGuard, 0, 2, 0, 500); //LEFT
		guardAnimations[2] = this.getPendingAnimsGuard( sprtShtGuard, 1, 1, 2, 500); //DOWN
		guardAnimations[3] = this.getPendingAnimsGuard( sprtShtGuard, 1, 3, 1, 500); //RIGHT
		guardAnimations[4] = this.getSpriteAnimationsX( sprtShtGuard, 0, 3, 3, 500); //UP
		guardAnimations[5] = this.getSpriteAnimationsX( sprtShtGuard, 0, 2, 0, 500); //LEFT
		guardAnimations[6] = this.getSpriteAnimationsX( sprtShtGuard, 0, 3, 2, 500); //DOWN
		guardAnimations[7] = this.getSpriteAnimationsX( sprtShtGuard, 1, 3, 1, 500); //RIGHT
	}
//
	@Override
	protected void initHitbox() {
		// TODO Auto-generated method stub
		
	}
	
	//guard_scld_x = posX + 24.5f;
	//guard_scld_y = posY + 47.0f;
	//float xt = (guard.getRayon_detect_noise()-15)/2.0f;
	//float yt = ((guard.getRayon_detect_noise()*.33f)-(15*.33f))/2.0f;
	//g.drawOval(guard_scld_x-xt, guard_scld_y-yt, guard.getRayon_detect_noise(),  guard.getRayon_detect_noise()*.33f);

}
