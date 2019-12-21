package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public abstract class Entity
{
	public static final int DIR_X = 0;
	public static final int DIR_Y =	1;
	
	public static final int DIR_UP    =	0;
	public static final int DIR_LEFT  =	1;
	public static final int DIR_DOWN  =	2;
	public static final int DIR_RIGHT =	3;

	
	private float  posX, posY;
	public int     direction;
	public float   speed;
	public boolean sprint;
	
	private Rectangle hitbox;
	private int dHitbox;
	private Animation[] movements;
	private Animation[] jumpMvmts;
	
	//Jump
	
	private boolean jumping;
	private boolean initJump;
	private float   posHJump;

	
	private boolean moving;

	public Entity( float posX, float posY ) throws SlickException
	{
		this.posX      = posX;
		this.posY 	   = posY;
		this.direction = 2;
		this.sprint    = false;

		this.moving    = false;
		this.jumping   = false;
		
		this.movements = new Animation[8];
		this.jumpMvmts = new Animation[8];
		this.hitbox    = new Rectangle(0, 0, 0, 0);
		this.dHitbox   = 0;
		
		System.out.println(dHitbox);

		this.initMovements();
		this.initHitbox();
		
		System.out.println(dHitbox);
	}
	
	public Animation[] setMovements( Animation movements[])
	{
		this.movements = movements;
		
		return movements;
	}
	
	public Animation getSpriteAnimationsX( SpriteSheet spriteSheet, int startX, int endX, int y, int frq )
	{
		int x;
		Animation animation = new Animation();
		
		for( x = startX; x <= endX; x++ )
		{
			animation.addFrame( spriteSheet.getSprite(x, y), frq );
		}
		return animation;
	}
	
	public Animation getSpriteAnimationsY( SpriteSheet spriteSheet, int startY, int endY, int x, int frq )
	{
		int y;
		Animation animation = new Animation();
		
		for( y = startY; y <= endY; y++ )
		{
			animation.addFrame( spriteSheet.getSprite(x, y), frq );
		}
		return animation;
	}
	
	public float       getSpeed()     { return this.speed; }
	public Rectangle   getHitbox()    { return this.hitbox; }
	public int         getDhitbox()   { return this.dHitbox; }
	public Animation[] getMovements() { return this.movements; }
	public Animation[] getJumpMvmts() { return this.jumpMvmts; }
	
	public float getPosX()            { return this.posX; }
	public float getPosY()            { return this.posY; }
	public void  setPosX( float posX ){ this.posX = posX; }
	public void  setPosY( float posY) { this.posY = posY; }

	public void setDhitbox( int dHitbox ) { this.dHitbox = dHitbox; }
	public void setHitbox( int w, int h ) 
	{ 
		this.hitbox.setWidth(w);
		this.hitbox.setHeight(h);
	}

	public void movingTo( float pos_x, float pos_y )
	{
		
	}
	
	protected boolean jump( float delta ) 
	{	
		float nextPos = getNextPos( delta, Entity.DIR_UP );
		
		if(!initJump)
		{
			posHJump = posY - 30;
			jumping  = true;
			initJump = true;
		}

		if( nextPos < posHJump ){ initJump = false; jumping = false;  }
		else					{ posY    = nextPos; }
		
	    return this.isJumping();
	}
	
	private float getNextPos( float delta ) 
	{
		return getNextPos( delta, this.direction ); 
	}
	
	public float getNextPos( float delta, int direction ) 
	{
	    float futur = 0.0f;
	    
	    switch ( direction ) 
	    {
	    	case Entity.DIR_UP   : futur = ( posY - (delta*speed) ); break;
	    	case Entity.DIR_LEFT : futur = ( posX - (delta*speed) ); break;
	    	case Entity.DIR_DOWN : futur = ( posY + (delta*speed) ); break;
	    	case Entity.DIR_RIGHT: futur = ( posX + (delta*speed) ); break;
	    }

	    return futur;
	}
	
	public void setDirection( int direction ) { this.direction = direction; }
	public void jumping( boolean isJumping )  { this.jumping = isJumping;   }
	
	public int     getDirection() { return this.direction; }
	public boolean isJumping()    { return this.jumping;   }
	
	protected abstract void initMovements() throws SlickException;
	protected abstract void initHitbox();
}
