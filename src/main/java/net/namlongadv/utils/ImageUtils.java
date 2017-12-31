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
import javax.imageio.stream.FileImageOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtils {
	public static String reduceImageFileSize(int size, File file, String out) throws Exception {
		float quality = 1.0f;
		long fileSize = file.length();
		File fileOut2 = null;

		if (fileSize <= size) {
			log.debug("{}bytes: Don't need to reduce", fileSize);
			return null;
		}

		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(FileUtils.getExtensions(file.getPath()));
		ImageWriter writer = (ImageWriter) iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

		FileInputStream inputStream = new FileInputStream(file);
		BufferedImage originalImage = ImageIO.read(inputStream);
		IIOImage image = new IIOImage(originalImage, null, null);

		float percent = 0.1f;
		while (fileSize > size) {
			if (percent >= quality) {
				percent = percent * quality;
			}

			quality -= percent;

			File fileOut = new File(out);
			if (fileOut.exists()) {
				fileOut.delete();
			}
			FileImageOutputStream output = new FileImageOutputStream(fileOut);
			writer.setOutput(output);
			iwp.setCompressionQuality(quality);
			writer.write(null, image, iwp);

			fileOut2 = new File(out);
			long newFileSize = fileOut2.length();
			if (newFileSize == fileSize) {
				break;
			} else {
				fileSize = newFileSize;
			}
			output.close();
		}

		writer.dispose();
		return fileOut2.getPath();
	}

	public static File resizeImage(int x, int y, File in) throws IOException {
		log.debug("Resizing {}", in.getName());
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
	
	public static int getWidth(String path) {
		try {
			return ImageIO.read(new File(path)).getWidth();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}
	
	public static int getHeight(String path) {
		try {
			return ImageIO.read(new File(path)).getHeight();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

}
