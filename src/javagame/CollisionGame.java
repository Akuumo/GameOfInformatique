package javagame;

import java.sql.Timestamp;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class CollisionGame extends BasicGame 
{
	
	public static final int RDR_LOGIC		=	13;
	public static final int RDR_FRONT2		=	12;
	public static final int RDR_FRONT1		=	11;
	public static final int RDR_SPRITE		=	10;
	public static final int RDR_INTERACTIVE	=	9;
	public static final int RDR_STATIC_OBJ	=	8;
	public static final int RDR_FURNIT		=	7;
	public static final int RDR_DOORWAY		=	6;
	public static final int RDR_WALL_D2		=	5;
	public static final int RDR_WALL_D		=	4;
	public static final int RDR_WALL		=	3;
	public static final int RDR_WALL_B		=	2;
	public static final int RDR_GROUND_D	=	1;
	public static final int RDR_GROUND		=	0;
	
	private GameContainer container	 = null;
	private TiledMap 	  map		 = null;
	private int 		  logicLayer    = 0;
	private int 		  logicFlyLayer = 0;
	private int 		  tilesMapW	 = 0;	
	private int 		  tilesMapH	 = 0;
	private int 		  tileW		 = 0;
	private int 		  tileH		 = 0;
	private int 		  mapW		 = 0;
	private int 		  mapH		 = 0;
	
	private boolean moving     = false;
	private boolean move_up	   = false;
	private boolean move_down  = false;
	private boolean move_left  = false;
	private boolean move_right = false;
	
	public 	String mouse = "No input yet";
	public double a = 0, b=0;
	
	public Image tile = null;

	private float futurX,  futurY;
	private float futurXcol,  futurYcol;
	
	float g_cntr_W, g_cntr_H, translateX, translateY, scale;
	
	//Player//
	private Player ninja = null;
	private Entity player = null;
	private Animation[] playerAnims = null;
	private Animation[] jmpMvmts = null;
	
	float     posX;
	float  	  posY;
	int       direction;
	float     speed;

	Rectangle hitbox;
	float	  wHitbox;
	float	  hHitbox; 
	int       dHitbox;
	
	float	  xHitbox;
	float	  yHitbox;
	
	
	private boolean isJumping;
	private boolean isFalling;
	
	private int timer;
	private Timestamp timestampA;
	private Timestamp timestampB;

	//
	
	public static void main(String[] args) throws SlickException 
	{
		new AppGameContainer(new CollisionGame(), 1024, 1024, false).start();
	}

	public CollisionGame() 
	{
		super("GameOfInformatique");
		System.out.println("CollisionGame()");
	}

	@Override
	public void init(GameContainer container) throws SlickException 
	{
		System.out.println("init(GameContainer container)");
		
		this.container  = container;
		this.map 	    = new TiledMap("map/datacentergame.tmx");
		this.logicLayer    = this.map.getLayerIndex("Logic");
		this.logicFlyLayer = this.map.getLayerIndex("Logic_Fly");
		this.tilesMapW	= this.map.getWidth();
		this.tilesMapH	= this.map.getHeight();
		this.tileW  	= this.map.getTileWidth();
		this.tileH  	= this.map.getTileHeight();
		this.mapW  		= tilesMapW * tileW;
		this.mapH  		= tilesMapH * tileH;
		this.futurX		= 0.0f;
		this.futurY		= 0.0f;
		this.futurXcol	= 0.0f;
		this.futurYcol	= 0.0f;
		this.speed      = 0.0f;
		
		this.scale 	  = 4;
		this.g_cntr_W = (container.getWidth() / scale);
		this.g_cntr_H = (container.getHeight() / scale);

		
		//Toit   5,   5
		//Biblio 965, 895
		ninja = new Player(965,895);
		
		//Player//

		this.player      = ninja;
		this.playerAnims = player.getMovements();
		this.jmpMvmts    = player.getJumpMvmts();

		this.posX      = player.getPosX();
		this.posY      = player.getPosY();
		this.direction = player.getDirection();
		this.speed     = player.getSpeed();
		
		this.hitbox    = player.getHitbox();
		this.wHitbox   = hitbox.getWidth();
		this.hHitbox   = hitbox.getHeight();
		this.dHitbox   = player.getDhitbox();
		
		this.isJumping = player.isJumping();
		
		this.timer = 60000;
		timestampA = new Timestamp(System.currentTimeMillis());
		this.isFalling = false;
	}
	
	public void render( GameContainer container, Graphics g ) throws SlickException 
	{
		//System.out.println("render( GameContainer container, Graphics g )");
				
		g.scale(scale, scale);
		
		translateX = (g_cntr_W/2) - player.getPosX();
		translateY = (g_cntr_H/2) - player.getPosY();

		g.translate(translateX, translateY);

		int i;
		
		for( i = 0; i < 13; i++ )
		{
			if( i == 10 )
			{
				if( player.isJumping() )
				{
					g.drawAnimation(jmpMvmts[player.direction + (isFalling ? 0 : 4)], player.getPosX()-(42/scale), player.getPosY()-(42/scale));
					System.out.println("Jump");
				}
				else
				{
					if(isFalling)
					{
						g.drawAnimation(jmpMvmts[player.direction + (isFalling ? 0 : 4)], player.getPosX()-(42/scale), player.getPosY()-(42/scale));
						System.out.println("Fall");
					}
					else
					{
						g.drawAnimation(playerAnims[player.direction + (moving ? 4 : 0)], player.getPosX()-(42/scale), player.getPosY()-(42/scale));
					}
				}
				

				//g.drawOval(player.pos_x, player.pos_y+10, 10,  10);

				g.drawRect(player.getPosX(), player.getPosY()+dHitbox, wHitbox, hHitbox );
				
				//g.drawAnimation(guardAnims[2], guard.pos_x, guard.pos_y);

				//g.drawAnimation(guardAnims[3], 20, 40);
				//g.drawAnimation(guardAnims[2], 40, 40);
			}
			else
			{
				this.map.render(0, 0, i);
			}
		}
		
		//System.out.println("R: "+x+" - "+y);
		//g.drawOval( 0, 0, tileW, tileH );
	}
	
	boolean stop = false;
	
	@Override
	public void update( GameContainer container, int delta ) throws SlickException 
	{
		/*
		if(timer-delta>0) 
		{
			timer-=delta;
			System.out.println(timer);
		}
		else if(!stop)
		{
			timestampB = new Timestamp(System.currentTimeMillis());
			System.out.println(" Time : "+(timestampB.getTime()-timestampA.getTime()));
			stop = true;
		}
		*/
		updateCharacter( container, delta );
	}	
	
	private void updateCharacter( GameContainer container, float delta ) 
	{
		if     (move_left) { player.setDirection(Entity.DIR_LEFT);  }
		else if(move_right){ player.setDirection(Entity.DIR_RIGHT); }
		if     (move_up)   { player.setDirection(Entity.DIR_UP);    }
		else if(move_down) { player.setDirection(Entity.DIR_DOWN);  }
		
	    if(player.isJumping()) 
	    {
	    	//posY      = jump( posX, posY, delta );
	    	player.jump( delta );
 	    	//System.out.println("isJumping:"+delta);
	    }
	    else if( player.getPosY() <= 895 )
		{	
	    	xHitbox   = player.getPosX()+wHitbox;
	    	yHitbox   = player.getPosY()+ dHitbox + hHitbox;
	    	futurYcol = getFuturY( delta, yHitbox, Entity.DIR_DOWN );
	    	
	    	if(!isFlyCollision(player.getPosX(), xHitbox, futurYcol))
	    	{
	    		player.setPosY(fall( player.getPosX(), player.getPosY(), delta ));
	    		this.isFalling = true;
	    		System.out.println("Fall");
	    	}
	    	else
	    	{
	    		this.isFalling = false;
	    		System.out.println("Stop Fall A");
	    	}
		}
    	else
    	{
    		if( isFalling ) this.isFalling = false; System.out.println("Stop Fall B");
    	}

		if(this.moving)
		{			
			//tile  = map.getTileImage( ((int)player.pos_x / tileW), ((int)player.pos_y / tileH), logicLayer );
			
			if(player.sprint){ speed = player.speed * 1.1f; }
			else       		 { speed = player.speed;  }
			
			xHitbox = player.getPosX(); yHitbox = player.getPosY();
			
		    switch (player.direction) 
		    {
		    	case Entity.DIR_DOWN  : xHitbox = player.getPosX();         yHitbox = player.getPosY()+hHitbox; break;
		    	case Entity.DIR_RIGHT : xHitbox = player.getPosX()+wHitbox; yHitbox = player.getPosY();         break;
		    }
		    
			futurX = getFuturX(delta);
			futurY = getFuturY(delta);
			
			futurXcol = getFuturX(delta, xHitbox);
			futurYcol = getFuturY(delta, yHitbox);
			
			//System.out.println(player.pos_x + ", " + player.pos_y);
	        if (!isCollision(futurXcol, futurYcol))
			//if(true)
	        {
				if((futurX>=0 && futurX<mapW)){ player.setPosX(futurX); }
				if((futurY>=0 && futurY<mapH)){ player.setPosY(futurY); }
	        } 
		}
	}
	
	boolean initJump  = false;
	boolean initFall  = false;
	boolean jumpingUp = true;


	float   posBJump = 0.0f;
	float   posHJump = 0.0f;
	
	private float fall( float posX, float posY, float delta ) 
	{
	    return (posY + ((delta*speed) *.7f) );
	}
	 
	private float getFuturX( float delta ) { return getFuturX(delta, player.getPosX()); }
	private float getFuturY( float delta ) { return getFuturY(delta, player.getPosY()); }
	
	private float getFuturX( float delta, float x ) 
	{
	    float futurX = x;
	    
		if     (move_left) { futurX = (x - (delta*speed)); player.direction = Entity.DIR_LEFT; }
		else if(move_right){ futurX = (x + (delta*speed)); player.direction = Entity.DIR_RIGHT;}

	    return futurX;
	}

	private float getFuturY( float delta, float y ) 
	{
	    float futurY = y;

		if     (move_up)   { futurY = (y - (delta*speed)); player.direction = Entity.DIR_UP;  }
		else if(move_down) { futurY = (y + (delta*speed)); player.direction = Entity.DIR_DOWN;}

	    return futurY;
	}
	
	private float getFuturY( float delta, float y, int direction ) 
	{
	    float futurY = y;
	    
	    switch (player.direction) 
	    {
	    	case Entity.DIR_UP   : futurY = (y - (delta*speed)); break;
	    	case Entity.DIR_DOWN : futurY = (y + (delta*speed)); break;
	    }

	    return futurY;
	}

	
	private boolean isFlyCollision( float x1, float x2, float y )
	{
		int i = (int) x1;
		boolean collision = false;
		
		while( i < x2 && !collision )
		{
			collision = isFlyCollision( i, y );
			
			System.out.println("i - x2 (y) : " + i +" - "+ x2 + "("+y+")");
			i++;
		}
		
		return collision;
	}
	
	private boolean isFlyCollision( float x, float y ) 
	{
	    Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicFlyLayer);
	    boolean collision = (tile != null);
	    if (collision) 
	    {
	        Color color = tile.getColor((int) x % tileW, (int) y % tileH);
	        collision = color.getAlpha() > 0;
	    }
	    return collision;
	}
	
	private boolean isCollision( float x, float y ) 
	{
	    Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
	    boolean collision = (tile != null);
	    if (collision) 
	    {
	        Color color = tile.getColor((int) x % tileW, (int) y % tileH);
	        collision = color.getAlpha() > 0;
	    }
	    
	    if(collision) System.out.println("Collision!");
	    
	    return collision;
	}

	boolean dirLock = false;
	
	@Override
	public void keyPressed( int key, char c ) 
	{
	    switch (key) 
	    {
	    	//case Input.KEY_Z: this.move_up    = true;  break;
	    	case Input.KEY_Q:   this.move_left  = true;  break;
	    	//case Input.KEY_S: this.move_down  = true;  break;
	    	case Input.KEY_D:   this.move_right = true;  break;
	    	case Input.KEY_LSHIFT: player.sprint = true;  break;
	    	case Input.KEY_LCONTROL: isFalling = true;  break;
	    	case Input.KEY_SPACE:  if( !player.isJumping() ){ player.jumping(true); System.out.println("player.jumping(true)"); }  break;
	    }

	    this.moving = (move_up || move_left || move_down || move_right);
	}

	@Override
	public void keyReleased( int key, char c ) 
	{
	    switch (key) 
	    {
	    	//case Input.KEY_Z: this.move_up    = false; break;
	    	case Input.KEY_Q:   this.move_left  = false; break;
	    	//case Input.KEY_S: this.move_down  = false; break;
	    	case Input.KEY_D:   this.move_right = false; break;
	    	case Input.KEY_LSHIFT: player.sprint = false;  break;
	    }
		
		if( Input.KEY_ESCAPE == key ) 
		{
			this.container.exit();
		}
		
		this.moving = (move_up || move_left || move_down || move_right);
	}
}