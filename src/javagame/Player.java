package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Entity 
{	
	public Player(int pos_x, int pos_y) throws SlickException
	{
		super(pos_x, pos_y);

		this.speed = 0.15f;
	}
	
	@Override
	protected void initMovements() throws SlickException
	{
		SpriteSheet sprtShtPlayerR = new SpriteSheet("sprites/ninja_r.png", 32, 32);
		SpriteSheet sprtShtPlayerL = new SpriteSheet("sprites/ninja_l.png", 32, 32);
		Animation[] movements      = this.getMovements();
		Animation[] jumpMvmts      = this.getJumpMvmts();
		
		movements[0] = this.getSpriteAnimationsY( sprtShtPlayerR, 2, 3, 0, 500);//UP
		movements[1] = this.getSpriteAnimationsY( sprtShtPlayerL, 2, 3, 6, 500);//LEFT
		movements[2] = movements[0]; 											//DOWN
		movements[3] = movements[0]; 											//RIGHT
		movements[4] = this.getSpriteAnimationsY( sprtShtPlayerR, 0, 3, 1, 100);//UP
		movements[5] = this.getSpriteAnimationsY( sprtShtPlayerL, 0, 3, 5, 100);//LEFT 
		movements[6] = movements[4]; 										    //DOWN
		movements[7] = movements[4]; 											//RIGHT
		
		jumpMvmts[0] = this.getSpriteAnimationsY( sprtShtPlayerR, 1, 1, 2, 100);//DOWN RIGHT
		jumpMvmts[1] = this.getSpriteAnimationsY( sprtShtPlayerL, 1, 1, 4, 100);//DOWN LEFT
		jumpMvmts[2] = jumpMvmts[0];
		jumpMvmts[3] = jumpMvmts[0];
		jumpMvmts[4] = this.getSpriteAnimationsY( sprtShtPlayerR, 0, 0, 2, 100);//UP   RIGHT
		jumpMvmts[5] = this.getSpriteAnimationsY( sprtShtPlayerL, 0, 0, 4, 100);//UP   LEFT
		jumpMvmts[6] = jumpMvmts[4];
		jumpMvmts[7] = jumpMvmts[4];
	}

	@Override
	protected void initHitbox() 
	{
		this.setHitbox( 10, 3 );
		this.setDhitbox( 13 );
	}
}
