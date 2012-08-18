package in.jmkl.dcsms.frame.widget;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Info extends Activity {
	private Button bt1, bt2;
	public Bitmap dcsmsframe;
	private ListView lv;
	private AdapterDen adap;
	private String imej = "watefak";
	private String nama;
	private Bitmap customBitmap;
	private Spinner frame;
	private ImageView prev, show, close;
	private RelativeLayout preleot;
	private String[] listframe = { "flip", "3d-rezarivani", "glass", "Custom",
			"Tes Read Xml" };
	private int layout;
	private List<String> listtheme = new ArrayList<String>();
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	public void onBackPressed() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		 Intent intent = getIntent();
	        Bundle extras = intent.getExtras();
	        if (extras != null) {
	            mAppWidgetId = extras.getInt(
	                    AppWidgetManager.EXTRA_APPWIDGET_ID, 
	                    AppWidgetManager.INVALID_APPWIDGET_ID);
	        }
	        
	        // If they gave us an intent without the widget id, just bail.
	        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
	            finish();
	            Log.d("ONLOAD", "BEH");
	        }
		
	
		prev = (ImageView) findViewById(R.id.iv_preview);
		show = (ImageView) findViewById(R.id.showme);
		close = (ImageView) findViewById(R.id.btnclose);
		preleot = (RelativeLayout) findViewById(R.id.leot_preview);
		close.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				preleot.setVisibility(View.GONE);
				return false;
			}
		});
		show.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				preleot.setVisibility(View.VISIBLE);
				return false;
			}
		});

		File root = new File(Environment.getExternalStorageDirectory()
				+ "/dcsmsframe");
		File theme = new File(Environment.getExternalStorageDirectory()
				+ "/dcsmsframe/theme");
		if (root.exists() && root.isDirectory()) {
			if (theme.exists()) {
				ListDir(theme);
			} else {
				theme.mkdirs();
				ListDir(theme);
			}
		} else {
			root.mkdirs();
			theme.mkdirs();
			ListDir(theme);

		}
		lv = (ListView) findViewById(R.id.listtema);
		adap = new AdapterDen(this, listtheme);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				String theme = listtheme.get(pos).toString();
				try {

					GetBitmap frame = new GetBitmap();
					GetBitmap mask = new GetBitmap();
					frame.getBtimapFromZip(theme, "skin/frame.png");
					mask.getBtimapFromZip(theme, "skin/mask.png");
					Bitmap bimej = BitmapFactory.decodeFile(imej);
					Bitmap bf = frame.getBitmapResult();
					Bitmap bm = mask.getBitmapResult();
					dcsmsframe = Bitmap.createBitmap(294, 146,
							Bitmap.Config.ARGB_8888);
					Canvas canvas = new Canvas(dcsmsframe);
					Paint p = new Paint();
					canvas.drawBitmap(bimej, 0, 0, null);
					p.setXfermode(new PorterDuffXfermode(
							PorterDuff.Mode.DST_ATOP));
					canvas.drawBitmap(bm, 0, 0, p);
					canvas.drawBitmap(bf, 0, 0, null);
					prev.setImageBitmap(dcsmsframe);
					preleot.setVisibility(View.VISIBLE);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}

			}
		});

		frame = new Spinner(this);
		ArrayAdapter aa = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, listframe);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frame.setAdapter(aa);
		frame.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				switch (pos) {

				case 0:
					layout = R.layout.widget;
					// updateWidget();

					break;

				case 1:
					layout = R.layout.widgetdua;
					// updateWidget();

					break;
				case 2:
					layout = R.layout.widgettiga;
					// updateWidget();

					break;
				case 3:
					String zipFilename = Environment
							.getExternalStorageDirectory() + "/tes/img.dcsms";
					String unzipLocation = Environment
							.getExternalStorageDirectory()
							+ "/dcsmsframe/theme/";
					// unpackZip(Environment.getExternalStorageDirectory()+"/tes/",
					// "img.zip");
					Decompress d = new Decompress(zipFilename, unzipLocation);
					d.unzip();
					break;
				case 4:
					// Keterangan ver = new
					// Keterangan("/sdcard/tes.zip","tes.xml");
					GetBitmap g = new GetBitmap();
					g.getBtimapFromZip("/sdcard/tes.zip", "frame.png");
					customBitmap = g.getBitmapResult();
					// customBitmap = getBtimapFromZip("/sdcard/tes.zip",
					// "frame.png");
					Keterangan k = new Keterangan();
					k.getKeterangan("/sdcard/tes.zip", "tes.xml");
					String a = k.getAuthor();
					String b = k.getTitle();
					String c = k.getVersion();
					Toast.makeText(Info.this, a + b + c, 10).show();
					layout = R.layout.widgetcustom;
					updateWidgetCustom();
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				layout = R.layout.widget;

			}
		});

		bt1 = (Button) findViewById(R.id.btn1);
		bt2 = (Button) findViewById(R.id.btnapply);
		bt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				updateWidgetCustom();

			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View p1) {

				Intent i = new Intent();
				i.setClassName("com.alensw.PicFolder",
						"com.alensw.PicFolder.GalleryActivity");
				i.setType("image/*");
				i.putExtra("aspectX", 294);
				i.putExtra("aspectY", 146);
				i.putExtra("outputX", 294);
				i.putExtra("outputY", 146);
				i.putExtra("crop", "true");
				i.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(i, 666);

			}

		});
	}

	private void ListDir(File f) {

		String dc = ".dcsms";
		File listFile[] = f.listFiles();

		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					ListDir(listFile[i]);
				} else {
					if (listFile[i].getName().endsWith(dc)) {
						listtheme.add(listFile[i].getPath());
					}
				}
			}
		}
	}



	private void updateWidgetCustom() {
Context context = Info.this;

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		ComponentName thisWidget = new ComponentName(context, Widget.class);
		Intent intent = new Intent(context, Widget.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widgetcustom);
			if (imej.contains("watefak")) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.defaultimage);
				views.setImageViewBitmap(R.id.img, bitmap);
			} else {
				// views.setImageViewUri(R.id.img, Uri.parse(imej));
				views.setImageViewBitmap(R.id.img, dcsmsframe);
			}

			appWidgetManager.updateAppWidget(widgetId, views);
		}
		finish();
		    

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 666) {
				Uri uri = data.getData();
				String i = uri.getPath();

				String d = Environment.getExternalStorageDirectory()
						+ "/dcsmsframe/";
				moveFile(i, d);
				imej = d + nama + "dcsms.png";
				prev.setImageURI(Uri.parse(imej));
				frame.setVisibility(View.VISIBLE);
				bt1.setVisibility(View.INVISIBLE);
				lv.setAdapter(adap);

			}
		}

	}

	private void moveFile(String inputPath, String outputPath) {

		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			in = new FileInputStream(inputPath);
			int n = (int) System.currentTimeMillis();
			nama = Integer.toString(n);
			out = new FileOutputStream(outputPath + nama + "dcsms.png");

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file
			out.flush();
			out.close();
			out = null;

			// delete the original file
			new File(inputPath).delete();

		}

		catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}

	private boolean unpackZip(String path, String zipname) {
		InputStream is;
		ZipInputStream zis;
		try {
			String filename;
			is = new FileInputStream(path + zipname);
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				filename = ze.getName();
				FileOutputStream fout = new FileOutputStream(path + filename);
				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}

				fout.close();
				zis.closeEntry();
			}

			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
