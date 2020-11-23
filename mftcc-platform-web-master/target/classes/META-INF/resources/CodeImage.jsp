<%@ page contentType="image/jpeg" import="java.awt.*, 
java.awt.image.*,java.awt.geom.*,java.util.*,javax.imageio.*" %> 
<%! 
Color getRandColor(int fc,int bc) 
{ 
Random random = new Random(); 
if(fc>255) {
    fc=255;
}
if(bc>255) {
    bc=255;
}
int r=fc+random.nextInt(bc-fc); 
int g=fc+random.nextInt(bc-fc); 
int b=fc+random.nextInt(bc-fc); 
return new Color(r,g,b); 
} 
%> 
<% 
out.clear();
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
int width=60, height=20; 
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
Graphics2D g = image.createGraphics(); 
Random random = new Random(); 
g.setColor(new Color(226,221,232)); 
g.fillRect(0, 0, width, height); 
g.setFont(new Font("Times New Roman",Font.PLAIN,18));
g.setColor(getRandColor(160,200)); 
/*for (int i=0;i<155;i++) { 
	int x = random.nextInt(width); 
	int y = random.nextInt(height); 
	int xl = random.nextInt(12); 
	int yl = random.nextInt(12); 
	g.drawLine(x,y,x+xl,y+yl); 
} */
String sRand=""; 
for (int i=0;i<4;i++){ 
	int tmp = -1;
	while(((tmp<48) || (tmp>57&&tmp<65) || (tmp>90&&tmp<97) || tmp>122) && tmp!='I' && tmp!='l' && tmp!='o'){
		tmp = random.nextInt(122);
	}
	String randChar = ((char)tmp)+""; 
	sRand+=randChar; 
	g.setColor(new Color(20+random.nextInt(150),20+random.nextInt(150),20+random.nextInt(150)));
	AffineTransform at = new AffineTransform();
	int number = random.nextInt(2)-1;
	double role = number * random.nextDouble()*(Math.PI / 4);
	at.setToRotation(role, 13*i+6, 16);
	g.setTransform(at);
	g.drawString(randChar, 13*i+6,16);
} 
session.setAttribute("code",sRand); 
g.dispose(); 
ImageIO.write(image, "JPEG", response.getOutputStream()); 
%>