import java.awt.image.BufferedImage;

class createTile{
	
	BufferedImage baseImage;
	int width, height;
	int tileWidth;
	BufferedImage[] tilesArray;
	
	public createTile(BufferedImage bi,int t){
		width=bi.getWidth();
		height=bi.getHeight();
		baseImage=bi;
		tileWidth=t;
		
	}
	
	public void makegrid(int size) {
		BufferedImage tile;
		
		tilesArray= new BufferedImage[size];
		int temp=0;
		for(int i=0;i<width;i+=tileWidth) {
			for(int j=0;j<height;j+=tileWidth) {
				tilesArray[temp]=baseImage.getSubimage(i, j, tileWidth, tileWidth);
				temp++;		
				
			}
			
		}
		
		
	}
	
	
}