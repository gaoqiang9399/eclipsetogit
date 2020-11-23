package app.tech.upload;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import org.apache.commons.lang3.StringUtils;

public class ImageUtil {
	/**
	 * 图片类型
	 */
	public static final String IMAGE_TYPE="bmp,jpg,jpeg,png,gif,tiff,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wav,rar";
	public static final String IMAGE_TYPE_JPG="jpg";
	public static final float Quality=(float) 0.5;
	
	public static ByteArrayInputStream getImageInputStream(String srcPath) 
	{
		ByteArrayInputStream inputStream = null;
        InputStream input = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			input = new BufferedInputStream(new FileInputStream(srcPath));
			byte[] bt = new byte[1024];
			while (input.read(bt) > 0) 
			{
				bos.write(bt);
			}
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
			//input.close();
		} catch (Exception e) {
			System.out.println(srcPath+"文件不存在");
		}finally {
		    if (input !=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return inputStream;
	}
	
	public static boolean compressPic(String srcFilePath, String descFilePath)  
    {  
        File file = null;  
        BufferedImage src = null;  
        FileOutputStream out = null;  
        ImageWriter imgWrier;  
        ImageWriteParam imgWriteParams;  
  
        // 指定写图片的方式为 jpg  
        imgWrier = ImageIO.getImageWritersByFormatName(IMAGE_TYPE_JPG).next();  
        imgWriteParams = new JPEGImageWriteParam(null);  
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT  
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，  
        imgWriteParams.setCompressionQuality(Quality);  
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ColorModel.getRGBdefault();  
        // 指定压缩时使用的色彩模式  
        imgWriteParams.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));  
  
        try  
        {  
            if(StringUtils.isBlank(srcFilePath))  
            {  
                return false;  
            }  
            else  
            {  
                file = new File(srcFilePath);  
                src = ImageIO.read(file);  
                out = new FileOutputStream(descFilePath);  
  
                imgWrier.reset();  
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造  
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));  
                // 调用write方法，就可以向输入流写图片  
                imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);  
                out.flush();  
                //out.close();
            }  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
            return false;  
        }finally {
         if (out !=null){
             try {
                 out.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        }
        return true;  
    }  
	
	/**
	 * 图片压缩
	 * @param is
	 * @param toFileName
	 * @throws Exception
	 *//*
	public static void imageCompress(InputStream is, String toFileName) throws Exception {
		// 原图路径 原图名称 目标路径 压缩比率0.5 0.75 原图宽度 原图高度
        float scale = 0.5f;
        float quality = 0.85f;    
        Image image = javax.imageio.ImageIO.read(is);
        int imageWidth = image.getWidth(null);  
        int imageHeight = image.getHeight(null);
        if (scale > 0.5) 
            scale = 0.5f;// 默认压缩比为0.5，压缩比越大，对内存要去越高，可能导致内存溢出           
        image = image.getScaledInstance(imageWidth, imageHeight,
                Image.SCALE_AREA_AVERAGING);
        BufferedImage mBufferedImage = new BufferedImage(imageWidth,
                imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = mBufferedImage.createGraphics();

        g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white,
                null);
        g2.dispose();

        float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
                -0.125f, -0.125f, -0.125f, -0.125f };
        Kernel kernel = new Kernel(3, 3, kernelData2);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        mBufferedImage = cOp.filter(mBufferedImage, null);

        FileOutputStream out = new FileOutputStream(toFileName);
        
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder
                .getDefaultJPEGEncodeParam(mBufferedImage);
        param.setQuality(quality, true);// 默认0.75
        encoder.setJPEGEncodeParam(param);
        encoder.encode(mBufferedImage);
        out.close();        
    }*/
	
}