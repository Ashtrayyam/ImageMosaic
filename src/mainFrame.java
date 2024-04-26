import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class mainFrame extends JPanel implements ChangeListener{
	
	int gridSize=50;
	int arraySize,libSize;
	
	Mosaic mosaic;
	BufferedImage base;
	BufferedImage[] imageLib;
	BufferedImage[] temp;
	//JPanel p;
	JSlider slider;
	JButton button1,button2,button3,button4,button5;
	JToggleButton toggle1,toggle2,toggle3;
	JLabel label ;

	
	public mainFrame(int width, int height,int s, BufferedImage[] lib) {
		this.setSize(width, height);
		try {
			base=ImageIO.read(new File("baseImage3.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		
		libSize=s;
		temp=new BufferedImage[libSize];
		for(int i=0;i<libSize;i++)
			temp[i]=lib[i];
		imageLib=lib;
		//System.out.println(this.imageLib[0].getWidth());
		
		arraySize=(base.getWidth()*base.getHeight())/(gridSize*gridSize);
		mosaic=new Mosaic(base,gridSize,libSize,imageLib);
		mosaic.createMosaic();
		//p=new JPanel();
		 
		label=new JLabel("Tile Size");
		label.setFont(new Font("Verdana", Font.PLAIN, 20));
		
		toggle1=new JToggleButton("Colour correction off");
		toggle1.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent ie) {
	            if (toggle1.isSelected()) {
	               toggle1.setText("Colour correction on");
	               toggle1Pressed();
	            }
	            else {
	               toggle1.setText("Colour correction off");
	               toggle1Released();
	            }
	         }
	      });
//		toggle2=new JToggleButton("Saturation correction off");
//		toggle2.addItemListener(new ItemListener() {
//	         public void itemStateChanged(ItemEvent ie) {
//	            if (toggle2.isSelected()) {
//	               toggle2.setText("Saturation correction on");
//	               toggle2Pressed();
//	            }
//	            else {
//	               toggle2.setText("Saturation correction off");
//	               toggle2Released();
//	            }
//	         }
//	      });
		toggle3=new JToggleButton("Brightness correction off");
		toggle3.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent ie) {
	            if (toggle3.isSelected()) {
	               toggle3.setText("Brightness correction on");
	               toggle3Pressed();
	            }
	            else {
	               toggle3.setText("Brightness correction off");
	               toggle3Released();
	            }
	         }
	      });
		
		button1=new JButton("Base Image 1"); 
		button2=new JButton("Base Image 2"); 
		button3=new JButton("Base Image 3"); 
		button4=new JButton("Base Image 4"); 
		button5=new JButton("Base Image 5"); 
		button1.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    button1Pressed();
			  } 
			} );
		button2.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    button2Pressed();
			  } 
			} );
		button3.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    button3Pressed();
			  } 
			} );
		button4.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    button4Pressed();
			  } 
			} );
		button5.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    button5Pressed();
			  } 
			} );
		
		slider=new JSlider(0,100,100);
		slider.addChangeListener(this);
	
	
		slider.setPaintTrack(true);
	      slider.setPaintTicks(true);
	  
	    
	        slider.setMajorTickSpacing(50);
	        slider.setMinorTickSpacing(5); 
	        
	       
	//	p.add(slider);
		//p.add(label);
	        
	//	this.getContentPane().setLayout(null);
	// p.setLocation(300,100);
		
	}
	
	
	
	
	
	  public void paint(Graphics g) {
		 
		 int temp=0;
		 for(int i=0;i<base.getWidth()/gridSize;i++) {
			 for(int j=0;j<base.getHeight()/gridSize;j++) {
				 BufferedImage img=mosaic.mosaicArray[temp];
				 g.drawImage(img, i*gridSize, j*gridSize, gridSize, gridSize, this);
				 temp++;
			 }
		 }
		 g.drawImage(base, base.getWidth()+20, 0, base.getWidth(), base.getHeight() ,this);
	  }
	
	
	public static void main(String[] args) {
			int libSize=35;

			BufferedImage[] lib=new BufferedImage[libSize];
			
			for(int i=0;i<libSize;i++) {
				try {
						lib[i]=ImageIO.read(new File("imageLib/assest"+i+".jpg"));			
					}
				catch (IOException e) {
						System.out.println("Cannot load the image libary");
					}
			}
			JPanel sliderP=new JPanel();
			JPanel buttons=new JPanel();
			buttons.setBackground(Color.gray);
			sliderP.setBackground(Color.gray);
			mainFrame mainP=new mainFrame(700,700,libSize,lib);
			buttons.add(mainP.button1);
			buttons.add(mainP.button2);
			buttons.add(mainP.button3);
			buttons.add(mainP.button4);
			buttons.add(mainP.button5);
			
			sliderP.add(mainP.label);
			sliderP.add(mainP.slider);
			sliderP.add(mainP.toggle1);
			sliderP.add(mainP.toggle3);
			
			
			JFrame frame=new JFrame();
			frame.setSize(1450, 900);
			frame.setLayout(new BorderLayout());
			frame.add(buttons,BorderLayout.NORTH);
			frame.add(mainP,BorderLayout.CENTER);
		
			frame.add(sliderP,BorderLayout.SOUTH);
			
//			frame.getContentPane().add(mainP,BorderLayout.CENTER);
//			frame.getContentPane().add(mainP.slider,BorderLayout.LINE_START);
//			frame.getContentPane().add(mainP.label,BorderLayout.LINE_END);
			 frame.setVisible(true);	 
			 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			 System.out.println("ran");
			 // frame.repaint();
				
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(slider.getValue()<=100&&slider.getValue()>70) {
			mosaic.setGridsize(100);
			mosaic.createMosaic();
			gridSize=100;
			label.setText("Tile Size = 100"); 
		}
		else if(slider.getValue()<=70&&slider.getValue()>50) {
			mosaic.setGridsize(70);
			mosaic.createMosaic();
			gridSize=70;
			label.setText("Tile Size = 70"); 
		}
		else if(slider.getValue()<=50&&slider.getValue()>25) {
			mosaic.setGridsize(50);
			mosaic.createMosaic();
			gridSize=50;
			label.setText("Tile Size = 50"); 
		}
		else if(slider.getValue()<=25&&slider.getValue()>20) {
			mosaic.setGridsize(25);
			mosaic.createMosaic();
			gridSize=25;
			label.setText("Tile Size = 25"); 
		}
		else if(slider.getValue()<=20&&slider.getValue()>10) {
			mosaic.setGridsize(20);
			mosaic.createMosaic();
			gridSize=20;
			label.setText("Tile Size = 20"); 
		}
		else if(slider.getValue()<=10&&slider.getValue()>5) {
			mosaic.setGridsize(10);
			mosaic.createMosaic();
			gridSize=10;
			label.setText("Tile Size = 10"); 
		}
		else if(slider.getValue()<=5&&slider.getValue()>=0) {
			mosaic.setGridsize(5);
			mosaic.createMosaic();
			gridSize=5;
			label.setText("Tile Size = 5"); 
		}
		arraySize=mosaic.arraySize;
		slider.repaint();
		this.repaint();
	
		
	}
	
	public void toggle1Pressed() {
		 mosaic.satOn=true;
		  mosaic.hueOn=true;
          mosaic.createMosaic();
          this.repaint();
	}
	public void toggle1Released() {
		 mosaic.hueOn=false;
		 mosaic.satOn=false;
		 mosaic.reProccess(temp);
		
		 mosaic.createMosaic();
         this.repaint();
	}
//	public void toggle2Pressed() {
//		  mosaic.satOn=true;
//        mosaic.createMosaic();
//        this.repaint();
//	}
//	public void toggle2Released() {
//		 mosaic.satOn=false;
//		 mosaic.reProccess(temp);
//       mosaic.createMosaic();
//       this.repaint();
//	}
	public void toggle3Pressed() {
		  mosaic.brightOn=true;
        mosaic.createMosaic();
        this.repaint();
	}
	public void toggle3Released() {
		 mosaic.brightOn=false;
		 mosaic.reProccess(temp);
       mosaic.createMosaic();
       this.repaint();
	}
	
	public void button1Pressed() {
		try {
			base=ImageIO.read(new File("baseImage1.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		mosaic.resetImage(base, gridSize);
		mosaic.createMosaic();
		slider.repaint();
		this.repaint();
	}
	public void button2Pressed() {
		try {
			base=ImageIO.read(new File("baseImage2.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		mosaic.resetImage(base, gridSize);
		mosaic.createMosaic();
		slider.repaint();
		this.repaint();
	}
	public void button3Pressed() {
		try {
			base=ImageIO.read(new File("baseImage3.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		mosaic.resetImage(base, gridSize);
		mosaic.createMosaic();
		slider.repaint();
		this.repaint();
	}
	public void button4Pressed() {
		try {
			base=ImageIO.read(new File("baseImage4.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		mosaic.resetImage(base, gridSize);
		mosaic.createMosaic();
		slider.repaint();
		this.repaint();
	}
	public void button5Pressed() {
		try {
			base=ImageIO.read(new File("baseImage5.jpeg"));
		}
		catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
		mosaic.resetImage(base, gridSize);
		mosaic.createMosaic();
		slider.repaint();
		this.repaint();
	}
	
	
}