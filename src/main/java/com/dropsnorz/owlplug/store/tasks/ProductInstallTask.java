package com.dropsnorz.owlplug.store.tasks;

import com.dropsnorz.owlplug.core.components.ApplicationDefaults;
import com.dropsnorz.owlplug.core.tasks.AbstractTask;
import com.dropsnorz.owlplug.core.tasks.TaskException;
import com.dropsnorz.owlplug.core.tasks.TaskResult;
import com.dropsnorz.owlplug.core.utils.FileUtils;
import com.dropsnorz.owlplug.store.model.StoreProduct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductInstallTask extends AbstractTask {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private StoreProduct product;
	private File targetDirectory;
	private ApplicationDefaults applicationDefaults;

	/**
	 * Creates a new Product Installation task.
	 * @param product Product to download
	 * @param targetDirectory Target directory where downloaded product is stored
	 * @param applicationDefaults Ownplug ApplicationDefaults
	 */
	public ProductInstallTask(StoreProduct product, File targetDirectory, ApplicationDefaults applicationDefaults) {

		this.product = product;
		this.targetDirectory = targetDirectory;
		this.applicationDefaults = applicationDefaults;
		setName("Install plugin - " + product.getName());
	}


	@Override
	protected TaskResult call() throws Exception {

		try {
			this.updateProgress(1, 5);
			if (targetDirectory == null || !targetDirectory.isDirectory()) {
				this.updateMessage("Installing plugin " + product.getName() + " - Invalid installation target directory");
				log.error("Invalid plugin installation target directory");
				throw new TaskException("Invalid plugin installation target directory");
			}
			this.updateMessage("Installing plugin " + product.getName() + " - Downloading files...");
			File archiveFile = downloadInTempDirectory(product);

			this.updateProgress(2, 5);
			this.updateMessage("Installing plugin " + product.getName() + " - Extracting files...");
			File extractedArchiveFolder = new File(applicationDefaults.getTempDowloadDirectory() + "/" 
					+ "temp-" + archiveFile.getName().replace(".owlpack", ""));
			FileUtils.unzip(archiveFile.getAbsolutePath(),  extractedArchiveFolder.getAbsolutePath());

			this.updateProgress(3, 5);
			this.updateMessage("Installing plugin " + product.getName() + " - Moving files...");
			installToPluginDirectory(extractedArchiveFolder, targetDirectory);

			this.updateProgress(4, 5);
			this.updateMessage("Installing plugin " + product.getName() + " - Cleaning files...");
			archiveFile.delete();
			FileUtils.deleteDirectory(extractedArchiveFolder);

			this.updateProgress(5, 5);
			this.updateMessage("Plugin " + product.getName() + " successfully Installed");

		} catch (IOException e) {
			throw new TaskException(e);
		}

		return success();
	}


	private File downloadInTempDirectory(StoreProduct product) throws TaskException {


		URL website;
		try {
			website = new URL(product.getDownloadUrl());
		} catch (MalformedURLException e) {
			this.updateMessage("Installation of " + product.getName() + " canceled: Can't download plugin files");
			throw new TaskException(e);

		}

		SimpleDateFormat horodateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");
		new File(applicationDefaults.getTempDowloadDirectory()).mkdirs();
		String outPutFileName =  horodateFormat.format(new Date()) + ".owlpack";
		String outputFilePath = applicationDefaults.getTempDowloadDirectory() + File.separator + outPutFileName;
		File outputFile = new File(outputFilePath);

		try (
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(outputFile)
		) {

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			return outputFile;

		} catch (MalformedURLException e) {
			this.updateMessage("Installation of " + product.getName() + " canceled: Can't download plugin files");
			throw new TaskException(e);
		} catch (FileNotFoundException e) {
			this.updateMessage("Installation of " + product.getName() + " canceled: File not found");
			throw new TaskException(e);
		} catch (IOException e) {
			this.updateMessage("Installation of " + product.getName() + " canceled: Can't write file on disk");
			throw new TaskException(e);
		} 

	}


	private void installToPluginDirectory(File source, File target) throws IOException {

		OwlPackStructureType structure = getStructureType(source);
		File newSource = null;
		switch (structure) {
			case NESTED: newSource = source.listFiles()[0]; 
				break;
			case NESTED_ENV: newSource = getSubfileByName(
				source.listFiles()[0], applicationDefaults.getPlatform().getCode()); 
				break;
			default: break;
		}
		if (newSource != null) {
			FileUtils.copyDirectory(newSource, target);

		} else {
			FileUtils.copyDirectory(source, target);
		}
	}


	private OwlPackStructureType getStructureType(File directory) {

		OwlPackStructureType structure = OwlPackStructureType.DIRECT;

		if (directory.listFiles().length == 1 && directory.listFiles()[0].isDirectory()) {
			structure = OwlPackStructureType.NESTED;
			for (File f : directory.listFiles()[0].listFiles()) {
				if (f.getName().equals("win") || f.getName().equals("osx")) {
					structure = OwlPackStructureType.NESTED_ENV;
				}
			}
		}
		return structure;
	}


	private File getSubfileByName(File parent, String filename) {
		for (File f : parent.listFiles()) {
			if (f.getName().equals(filename)) {
				return f;
			}
		}
		return null;
	}

	/**
	 * Compatible product archive structues
	 * --------------
	 *	DIRECT
	 *	plugin.zip/
	 *	├── plugin.dll
	 *	└── (other required files...)
	 *	--------------
	 *	NESTED
	 *	plugin.zip/
	 *	└── plugin
	 *		├── plugin.dll
	 *		└── (other required files...)
	 * --------------
	 *	NESTED_ENV
	 *	plugin.zip/
	 *	└── plugin
	 *		├── x86
	 *		│	├── plugin.dll
	 *		│	└── (other required files...)
	 *		└── x64
	 *			├── plugin.dll
	 *			└── (other required files...)
 	 *
	 *
	 */
	private enum OwlPackStructureType {
		DIRECT,
		NESTED,
		NESTED_ENV,
	}

}
