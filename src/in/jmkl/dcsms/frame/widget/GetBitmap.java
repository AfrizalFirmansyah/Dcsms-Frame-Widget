package in.jmkl.dcsms.frame.widget;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetBitmap {
	private Bitmap result = null;

	public Bitmap getBitmapResult() {
		return result;
	}

	public void getBtimapFromZip(final String zipFilePath,
			final String imageFileInZip) {

		try {
			FileInputStream fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.getName().equals(imageFileInZip)) {
					result = BitmapFactory.decodeStream(zis);
					break;
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
