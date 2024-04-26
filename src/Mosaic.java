import java.awt.Color;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class Mosaic{
	
	BufferedImage baseImage;
	createTile grid;
	BufferedImage[] mosaicArray;
	int[] imagelibRGB;
	boolean hueOn=false,satOn=false,brightOn=false;
	
	BufferedImage[] imagelib;

	int width,height,arraySize,libSize;
	
	public Mosaic(BufferedImage bi,int gsize,int s,BufferedImage[] lib){
		baseImage=bi;
		width=bi.getWidth();
		height=bi.getHeight();
		grid=new createTile(bi,gsize);
		imagelib=lib;
		libSize=s;
		arraySize=(width*height)/(gsize*gsize);
		imagelibRGB=new int[libSize];
		
		
		
		this.imageProccess();
	
		grid.makegrid(arraySize);
		mosaicArray=new BufferedImage[arraySize];
	}
	public void reProccess(BufferedImage[] lib) {
		BufferedImage temp;
		int width=0,height=0;
		for(int i=0;i<libSize;i++) {
			width=lib[i].getWidth();
			height=lib[i].getHeight();
			if(width>=height)
				temp=lib[i].getSubimage((width-height)/2, 0, height, height);
			else 
				temp=lib[i].getSubimage(0, (height-width)/2, width, width);
			
			BufferedImage after = new BufferedImage((int)(temp.getWidth()*0.03),(int)(temp.getHeight()*0.03),BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(0.03, 0.03);
			
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		
			after = scaleOp.filter(temp, after);
			
			imagelibRGB[i] =getAverageColor(after,0,0,after.getWidth(),after.getHeight());
			//System.out.print(imagelibRGB[i]);
			imagelib[i]= after;
			
		}
	}
	public void imageProccess(){
		BufferedImage temp;
		int width=0,height=0;
		for(int i=0;i<libSize;i++) {
			width=imagelib[i].getWidth();
			height=imagelib[i].getHeight();
			if(width>=height)
				temp=imagelib[i].getSubimage((width-height)/2, 0, height, height);
			else 
				temp=imagelib[i].getSubimage(0, (height-width)/2, width, width);
			
			BufferedImage after = new BufferedImage((int)(temp.getWidth()*0.03),(int)(temp.getHeight()*0.03),BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(0.03, 0.03);
			
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		
			after = scaleOp.filter(temp, after);
			
			imagelibRGB[i] =getAverageColor(after,0,0,after.getWidth(),after.getHeight());
			//System.out.print(imagelibRGB[i]);
			imagelib[i]= after;
		
			
		}
		
	}
	
	
	public void createMosaic() {
		BufferedImage originalTile;
		for(int i=0;i<arraySize;i++) {
			originalTile=grid.tilesArray[i];
			int baseAvg=getAverageColor(originalTile,0,0,grid.tileWidth,grid.tileWidth);
			//System.out.println(baseAvg);
			int closest=0;
			int temp=Math.abs(baseAvg-imagelibRGB[0]);
			for(int j=1;j<libSize;j++) {
				if(temp>Math.abs(baseAvg-imagelibRGB[j])) {
					//System.out.println(temp);
					temp=Math.abs(baseAvg-imagelibRGB[j]);
					closest=j;
				}
				else
					continue;
			}
			
			BufferedImage closestImage=imagelib[closest];
			
	if(hueOn||satOn||brightOn) {
			
		//calculate avg hue
			float hue=0;
			float sat=0;
			float bright=0;
			float[] hsv=new float[3];
		
			for(int j=0;j<grid.tileWidth;j++) {
				for(int k=0;k<grid.tileWidth;k++) {
					int rgb=originalTile.getRGB(j, k);
					Color c=new Color(rgb);
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					Color.RGBtoHSB(r, g, b,hsv);
					if(hueOn)
						hue=hue+hsv[0];
					if(satOn)
						sat=sat+hsv[1];
					if(brightOn)
						bright=bright+hsv[2];
					
					//System.out.println(hsv[0]);
				}
				
			}
			float avghue=hue/(grid.tileWidth*grid.tileWidth);
			float avgsat=sat/(grid.tileWidth*grid.tileWidth);
			float avgbright=bright/(grid.tileWidth*grid.tileWidth);
			//System.out.println(closestImage.getWidth());
			for(int j=0;j<closestImage.getWidth();j++) {
				for(int k=0;k<closestImage.getHeight();k++) {
					int rgb=closestImage.getRGB(j, k);
					Color c=new Color(rgb);
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					Color.RGBtoHSB(r, g, b,hsv);
					if(hueOn)
						hsv[0]=avghue;
					if(satOn)
						hsv[1]=avgsat;
					if(brightOn)
						hsv[2]=avgbright;
					
					int newrgb=Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
					closestImage.setRGB(j, k, newrgb);
					//System.out.println(hsv[0]);
				}
				
			}
	  }
			
			
			mosaicArray[i]=closestImage;

			

//			baseTile=grid.tilesArray[i];
//			BufferedImage mosaicTile=new BufferedImage(grid.tileWidth,grid.tileWidth,baseTile.getType());
//			
//			int baseAvg=getAverageColor(baseTile,0,0,grid.tileWidth,grid.tileWidth);
//			
//			for(int j=0;j<grid.tileWidth;j++) {
//				for(int k=0;k<grid.tileWidth;k++) {
//					mosaicTile.setRGB(j, k, baseAvg);
//				}	
//			}
//			mosaicArray[i]=mosaicTile;
//		
			// mosaicArray[i]=imagelib[3];
			
			
		}
	}
	
	public int getAverageColor(BufferedImage bi, int x, int y, int w,
	        int h) {
	    int x1 = x + w;
	    int y1 = y + h;
	    int r = 0, g = 0, b = 0;
	    int temp = w * h;
	    for (int i = x; i < x1; i++) {
	        for (int j = y; j < y1; j++) {
	            Color pixel = new Color(bi.getRGB(i, j));
	            r += pixel.getRed();
	            g += pixel.getGreen();
	            b += pixel.getBlue();
	        }
	    }

	    return new Color(r/temp, g/temp, b/temp).getRGB();
	   // return r/temp;
	}
	public void resetImage(BufferedImage bi, int gsize) {
		baseImage=bi;
		grid=new createTile(bi,gsize);
		arraySize=(width*height)/(gsize*gsize);
		grid.makegrid(arraySize);
		mosaicArray=new BufferedImage[arraySize];
		
	}
	
	public void setGridsize(int s){
		grid=new createTile(baseImage,s);
		arraySize=(width*height)/(s*s);
		grid.makegrid(arraySize);
		mosaicArray=new BufferedImage[arraySize];
		
	}
	
	
}