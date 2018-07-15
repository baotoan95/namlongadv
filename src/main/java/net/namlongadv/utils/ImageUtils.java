package net.namlongadv.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtils {
	private static BufferedImage originalImage;
	private static BufferedImage image;
	private static Graphics2D graphics2d;
	private static File output;

	private static FileInputStream inputStream;
	private static IIOImage IOImage;
	private static FileImageOutputStream fOutput;
	private static ImageWriter writer;
	private static ImageWriteParam iwp;
	private static File fileOut;

	public static String reduceImageFileSize(File file, String output) throws IOException {
		OutputStream out = null;
		ImageOutputStream ios = null;
		try {
			BufferedImage image = ImageIO.read(file);

			File fileOutput = new File(output);
			out = new FileOutputStream(fileOutput);

			ImageWriter writer = ImageIO.getImageWritersByFormatName(FileUtils.getExtensions(file.getPath())).next();
			ios = ImageIO.createImageOutputStream(out);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();
			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.65f);
			}

			writer.write(null, new IIOImage(image, null, null), param);
			// Delete temporary file
			file.delete();
			return fileOutput.getPath();
		} finally {
			if (out != null) {
				out.close();
			}
			if (ios != null) {
				ios.close();
			}
			if (writer != null) {
				writer.dispose();
			}
		}
	}

	public static String reduceImageFileSize(int size, File file, String out) throws Exception {
		float quality = 1.0f;
		long fileSize = file.length();
		File fileOut2 = null;

		if (fileSize <= size) {
			log.debug("{}bytes: Don't need to reduce", fileSize);
			return null;
		}

		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(FileUtils.getExtensions(file.getPath()));
		writer = iter.next();
		iwp = writer.getDefaultWriteParam();
		try {
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		} catch (Exception e) {
			writer.dispose();
			throw new Exception("File type invalid");
		}

		inputStream = new FileInputStream(file);
		originalImage = ImageIO.read(inputStream);
		IOImage = new IIOImage(originalImage, null, null);

		float percent = 0.1f;
		while (fileSize > size) {
			if (percent >= quality) {
				percent = percent * quality;
			}

			quality -= percent;

			fileOut = new File(out);
			if (fileOut.exists()) {
				fileOut.delete();
			}
			fOutput = new FileImageOutputStream(fileOut);
			writer.setOutput(fOutput);
			iwp.setCompressionQuality(quality);
			writer.write(null, IOImage, iwp);

			fileOut2 = new File(out);
			long newFileSize = fileOut2.length();
			if (newFileSize == fileSize) {
				break;
			} else {
				fileSize = newFileSize;
			}
			fOutput.close();
		}

		// Delete original file
		file.delete();

		writer.dispose();
		return fileOut2.getPath();
	}

	public static File resizeImage(int x, int y, File in) throws IOException {
		log.debug("Resizing {}", in.getName());
		originalImage = ImageIO.read(in);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		// Create image object
		log.debug("Image type: {}", type);
		image = new BufferedImage(x, y, type);
		graphics2d = image.createGraphics();

		// Draw image original with new width and new height
		graphics2d.drawImage(originalImage, 0, 0, x, y, null);
		graphics2d.dispose();

		output = new File(in.getName());
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
