package net.namlongadv.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class ImageUtils {
	public static File reduceImageFileSize(int size, String in, String out) throws Exception {
		float quality = 1.0f;
		
		File file = new File(in);
		File fileOut = new File(file.getName());
		
		long fileSize = file.length();

		if (fileSize <= size) {
			return file;
		}

		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(FileUtils.getExtensions(in));
		ImageWriter writer = (ImageWriter) iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		FileInputStream inputStream = new FileInputStream(file);

		BufferedImage originalImage = ImageIO.read(inputStream);
		IIOImage image = new IIOImage(originalImage, null, null);

		float percent = 0.1f;

		while (fileSize > size) {
			if (percent >= quality) {
				percent = percent * 0.1f;
			}

			quality -= percent;
			fileOut = new File(out);
			if (fileOut.exists()) {
				fileOut.delete();
			}
//			FileImageOutputStream output = new FileImageOutputStream(fileOut);

			writer.setOutput(fileOut);
			iwp.setCompressionQuality(quality);
			writer.write(null, image, iwp);
			File fileOut2 = new File(out);
			long newFileSize = fileOut2.length();
			if (newFileSize == fileSize) {
				break;
			} else {
				fileSize = newFileSize;
			}
//			output.close();
		}

		writer.dispose();
		return fileOut;
	}
	
	public static File resizeImage(int x, int y, File in) throws IOException {
		BufferedImage originalImage = ImageIO.read(in);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		// Create image object
		BufferedImage image = new BufferedImage(x, y, type);
		Graphics2D g = image.createGraphics();

		// Draw image original with new width and new height
		g.drawImage(originalImage, 0, 0, x, y, null);
		g.dispose();
		
		File output = new File(in.getName());
		ImageIO.write(image, FileUtils.getExtensions(in.getPath()), output);
		
		return output;
	}
}
