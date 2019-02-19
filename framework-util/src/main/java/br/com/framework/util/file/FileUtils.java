package br.com.framework.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Mover um arquivo.
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return boolean
	 */
	public static boolean moveFileQuietly(File srcFile, File destFile) {
		boolean moved = false;
		if (srcFile == null)
			return false;
		try {
			copyFile(srcFile, destFile);
			deleteQuietly(srcFile);
			moved = true;
		} catch (FileExistsException e) {
			LOGGER.warn(String.format("O arquivo já existe no destino: %s", destFile.getAbsolutePath()));
		} catch (IOException e) {
			LOGGER.error(String.format("Não foi possivel mover o arquivo: %s", srcFile.getAbsolutePath()),
					e);
		}
		return moved;
	}

	/**
	 * Move um arquivo para um diretório.
	 * 
	 * @param srcFile
	 * @param destDir
	 * @param createDestDir
	 * @return boolean
	 */
	public static boolean moveFileToDirectoryQuietly(File srcFile, File destDir, boolean createDestDir) {
		boolean moved = false;
		if (srcFile == null)
			return false;
		try {
			FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);
			moved = true;
		} catch (FileExistsException e) {
			LOGGER.warn(String.format("O arquivo já existe no destino: %s", destDir.getAbsolutePath()));
		} catch (IOException e) {
			LOGGER.error(String.format("Não foi possível mover arquivo: %s para o destino: %s ",
					srcFile.getAbsolutePath(), destDir.getAbsolutePath()), e);
		}
		return moved;
	}
	
	/**
	 * Escreve dados em arquivo.
	 * 
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 */
	public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try(
				OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			) 
		{
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
		} catch (IOException e) {
			LOGGER.error(String.format("Não foi possível escrever arquivo para o destino: %s ",
					uploadedFileLocation));
		}

	}
}
